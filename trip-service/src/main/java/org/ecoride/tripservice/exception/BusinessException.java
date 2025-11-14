package org.ecoride.tripservice.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
