package br.com.miguel.ordem_management.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(Long id, Long productId){
        super("Price with id " + id + " not found for product with id " + productId);
    }
    public PriceNotFoundException(String message) {
        super(message);
    }
}
