package br.com.miguel.ordem_management.domain.exception;

public class ProductInactiveException extends RuntimeException {
    public ProductInactiveException(Long id) {
        super("Product with id: " + id + " is inactive");
    }

    public ProductInactiveException(String message) {
        super(message);
    }
}
