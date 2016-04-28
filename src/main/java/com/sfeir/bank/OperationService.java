package com.sfeir.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private AmountFormatter formatter;

    public OperationResult save(String amount) {
        Optional<BigDecimal> formattedAmount = formatter.format(amount);
        return formattedAmount
                .map(OperationResult::createValidOperation)
                .orElse(OperationResult.createInvalidOperation(ErrorType.INVALID_INPUT));
    }
}
