package com.sfeir.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OperationService {

    @Autowired
    private AmountFormatter formatter;

    @Autowired
    private AccountService accountService;

    public OperationResult save(String amount) {
        Optional<BigDecimal> formattedAmount = formatter.format(amount);
        return formattedAmount
                .map(accountService::addAmount)
                .orElse(OperationResult.createInvalidOperation(ErrorType.INVALID_INPUT));
    }
}