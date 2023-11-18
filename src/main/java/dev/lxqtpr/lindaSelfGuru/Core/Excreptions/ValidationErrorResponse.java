package dev.lxqtpr.lindaSelfGuru.Core.Excreptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;


@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {
    private final Integer statusCode;

    private final List<Violation> violations;

}
