package br.com.miguel.ordem_management.domain.exception;

public class OrderSameStatusException extends RuntimeException {
  public OrderSameStatusException() {
      super("Cannot modify order to the same status");
  }

  public OrderSameStatusException(String message) {
        super(message);
    }
}
