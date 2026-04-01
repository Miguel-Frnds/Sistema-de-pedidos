package br.com.miguel.ordem_management.api.mapper;

import br.com.miguel.ordem_management.api.dto.order.OrderItemResponseDTO;
import br.com.miguel.ordem_management.api.dto.order.OrderResponseDTO;
import br.com.miguel.ordem_management.domain.entity.Order;

import java.util.List;

public class OrderMapper {
    public static OrderResponseDTO toResponseDTO(Order order){
        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getId(),
                items,
                order.getTotal(),
                order.getStatus()
        );
    }
}
