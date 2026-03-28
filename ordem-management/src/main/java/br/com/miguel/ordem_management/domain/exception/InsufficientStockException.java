package br.com.miguel.ordem_management.domain.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String name){
      super("Insufficient stock for product: " + name);
    }
}
