package dev.lxqtpr.lindaSelfGuru.Core.Excreptions;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ControllerAdvice{

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleImageUpload(ImageUploadException e) {
        return new ExceptionBody(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(PasswordDoesNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleImageUpload(PasswordDoesNotMatchException e) {
        return new ExceptionBody(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleJwtException(JwtException e) {
        return new ExceptionBody(e.getMessage(), HttpStatus.FORBIDDEN.value());
    }
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAuthenticationExceptions(AuthenticationException e) {
        return new ExceptionBody(e.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        var violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        var violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(), violations);
    }

    @ExceptionHandler({AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("You have no access to this endpoint", HttpStatus.FORBIDDEN.value());
    }
}