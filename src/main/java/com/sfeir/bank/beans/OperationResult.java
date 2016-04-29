package com.sfeir.bank.beans;

import java.math.BigDecimal;

public class OperationResult {

    private final boolean isValid;
    private final ErrorType errorType;
    private final BigDecimal result;

    private OperationResult(boolean isValid, ErrorType errorType, BigDecimal result) {
        this.isValid = isValid;
        this.errorType = errorType;
        this.result = result;
    }

    public static OperationResult createValidOperation(BigDecimal result) {
        return new OperationResult(true, null, result);
    }

    public static OperationResult createInvalidOperation(ErrorType errorType) {
        return new OperationResult(false, errorType, null);
    }

    public boolean isValid() {
        return isValid;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public BigDecimal getResult() {
        return result;
    }
}
