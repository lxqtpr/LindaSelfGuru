package dev.lxqtpr.lindaSelfGuru.Core.Excreptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}