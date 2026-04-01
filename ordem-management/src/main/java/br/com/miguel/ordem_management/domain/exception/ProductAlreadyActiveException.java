package br.com.miguel.ordem_management.domain.exception;

public class ProductAlreadyActiveException extends RuntimeException {
    public ProductAlreadyActiveException(Long id) {
        super("Product with id: " + id + " is already activate");
    }

    public ProductAlreadyActiveException(String message) {
        super(message);
    }
}
