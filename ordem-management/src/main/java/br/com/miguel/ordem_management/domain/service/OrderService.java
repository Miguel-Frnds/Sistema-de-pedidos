package br.com.miguel.ordem_management.domain.service;

import br.com.miguel.ordem_management.api.dto.order.OrderItemRequestDTO;
import br.com.miguel.ordem_management.api.dto.order.OrderRequestDTO;
import br.com.miguel.ordem_management.api.dto.order.OrderResponseDTO;
import br.com.miguel.ordem_management.api.dto.product.ProductStockRequestDTO;
import br.com.miguel.ordem_management.api.mapper.OrderMapper;
import br.com.miguel.ordem_management.domain.entity.*;
import br.com.miguel.ordem_management.domain.exception.*;
import br.com.miguel.ordem_management.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public List<OrderResponseDTO> findAll(){
        return orderRepository.findAll().stream()
                .map(OrderMapper::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> findByUserId(Long userId){
        return orderRepository.findByUserId(userId).stream()
                .map(OrderMapper::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> findByOrderStatus(OrderStatus status){
        return orderRepository.findByStatus(status).stream()
                .map(OrderMapper::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> findByUserIdAndStatus(Long userId, OrderStatus status){
        return orderRepository.findByUserIdAndStatus(userId, status).stream()
                .map(OrderMapper::toResponseDTO)
                .toList();
    }

    public OrderResponseDTO findByOrderId(Long id){
        return OrderMapper.toResponseDTO(getById(id));
    }

    @Transactional
    public OrderResponseDTO save(OrderRequestDTO requestDTO){
        User user = userService.getById(requestDTO.userId());
        BigDecimal total = BigDecimal.ZERO;

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = new ArrayList<>();

        for(int i = 0; i < requestDTO.items().size(); i++){
            OrderItemRequestDTO itemRequestDTO = requestDTO.items().get(i);
            Product product = productService.getById(itemRequestDTO.productId());

            productService.removeStock(product.getId(), new ProductStockRequestDTO(itemRequestDTO.quantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setQuantity(itemRequestDTO.quantity());
            orderItem.setPrice(product.getPrice());

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequestDTO.quantity())));

            items.add(orderItem);
        }

        order.setItems(items);
        order.setTotal(total);
        return OrderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long id, OrderStatus status){
        Order order = getById(id);

        if(order.getStatus().equals(OrderStatus.FINISHED)) {
            throw new InvalidOrderStatusTransitionException(order.getStatus(), status);
        }

        if(order.getStatus().equals(OrderStatus.CANCELED)) {
            throw new InvalidOrderStatusTransitionException(order.getStatus(), status);
        }

        if(order.getStatus().equals(OrderStatus.PROCESSING) && status.equals(OrderStatus.CREATED)) {
            throw new InvalidOrderStatusTransitionException(order.getStatus(), status);
        }

        if(order.getStatus().equals(status)) {
            throw new OrderSameStatusException();
        }

        if(status.equals(OrderStatus.CANCELED)) {
            for(int i = 0; i < order.getItems().size(); i++) {
                OrderItem item = order.getItems().get(i);
                productService.addStock(item.getProduct().getId(), new ProductStockRequestDTO(item.getQuantity()));
            }
        }

        order.setStatus(status);
        return OrderMapper.toResponseDTO(orderRepository.save(order));
    }

    @Transactional
    public void delete(Long id) {
        Order order = getById(id);
        if(order.getStatus().equals(OrderStatus.FINISHED)) {
            throw new InvalidOrderStatusTransitionException(order.getStatus());
        }

        if(order.getStatus().equals(OrderStatus.CANCELED)) {
            throw new InvalidOrderStatusTransitionException(order.getStatus());
        }

        for(int i = 0; i < order.getItems().size(); i++) {
            OrderItem item = order.getItems().get(i);
            productService.addStock(item.getProduct().getId(), new ProductStockRequestDTO(item.getQuantity()));
        }

        orderRepository.delete(order);
    }

    private Order getById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
