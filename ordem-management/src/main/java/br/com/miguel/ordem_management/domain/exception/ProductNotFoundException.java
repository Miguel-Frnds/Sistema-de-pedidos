package br.com.miguel.ordem_management.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id){
        super("Product not found with id: " + id);
    }
    public ProductNotFoundException(String message) {
        super(message);
    }
}
