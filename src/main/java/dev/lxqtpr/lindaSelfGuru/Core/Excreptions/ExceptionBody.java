package dev.lxqtpr.lindaSelfGuru.Core.Excreptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExceptionBody {
    private String message;
    private Integer status;
}