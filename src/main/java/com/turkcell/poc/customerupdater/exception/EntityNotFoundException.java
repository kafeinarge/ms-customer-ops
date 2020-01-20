package com.turkcell.poc.customerupdater.exception;

public class EntityNotFoundException extends Exception {

    static final long serialVersionUID = -3387516993224229948L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
