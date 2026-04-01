package br.com.miguel.ordem_management.api.controller;

import br.com.miguel.ordem_management.api.dto.order.OrderRequestDTO;
import br.com.miguel.ordem_management.api.dto.order.OrderResponseDTO;
import br.com.miguel.ordem_management.domain.entity.OrderStatus;
import br.com.miguel.ordem_management.domain.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        List<OrderResponseDTO> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> findByUserId(@PathVariable Long userId) {
        List<OrderResponseDTO> orders = orderService.findByUserId(userId);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/status")
    public ResponseEntity<List<OrderResponseDTO>> findByOrderStatus(@RequestParam OrderStatus status) {
        List<OrderResponseDTO> orders = orderService.findByOrderStatus(status);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/user/{userId}/status")
    public ResponseEntity<List<OrderResponseDTO>> findByUserIdAndStatus(@PathVariable Long userId,
                                                                        @RequestParam OrderStatus status) {
        List<OrderResponseDTO> orders = orderService.findByUserIdAndStatus(userId, status);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findByOrderId(@PathVariable Long id) {
        OrderResponseDTO order = orderService.findByOrderId(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> save(@RequestBody @Valid OrderRequestDTO requestDTO) {
        OrderResponseDTO order = orderService.save(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id, @RequestParam OrderStatus status) {
        OrderResponseDTO order = orderService.updateStatus(id, status);
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
