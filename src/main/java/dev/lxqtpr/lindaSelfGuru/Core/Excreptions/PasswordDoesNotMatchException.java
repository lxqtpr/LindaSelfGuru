package dev.lxqtpr.lindaSelfGuru.Core.Excreptions;

public class PasswordDoesNotMatchException extends RuntimeException {
    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
