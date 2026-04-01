package br.com.miguel.ordem_management.domain.exception;

import br.com.miguel.ordem_management.domain.entity.OrderStatus;

public class InvalidOrderStatusTransitionException extends RuntimeException {
    public InvalidOrderStatusTransitionException(OrderStatus statusBefore, OrderStatus newStatus){
      super("Cannot transition order status from " + statusBefore + " to " + newStatus);
    }

  public InvalidOrderStatusTransitionException(OrderStatus status){
    super("Cannot transition order status to " + status);
  }

    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }
}
