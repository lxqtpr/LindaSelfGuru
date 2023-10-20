package dev.lxqtpr.lindaSelfGuru.Core.Excreptions;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
