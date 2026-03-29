package br.com.miguel.ordem_management.domain.exception;

public class SamePriceException extends RuntimeException {
    public SamePriceException(){
        super("New price must be different from the current price");
    }
    public SamePriceException(String message) {
        super(message);
    }
}
