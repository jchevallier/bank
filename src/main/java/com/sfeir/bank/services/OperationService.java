package com.sfeir.bank.services;

import com.sfeir.bank.beans.ErrorType;
import com.sfeir.bank.beans.OperationResult;
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
                .filter(validAmount -> BigDecimal.ZERO.compareTo(validAmount) < 0)
                .map(accountService::addAmount)
                .orElse(OperationResult.createInvalidOperation(ErrorType.INVALID_INPUT));
    }

    public OperationResult withdraw(String amount) {
        Optional<BigDecimal> formattedAmount = formatter.format(amount);
        return formattedAmount
                .filter(validAmount -> BigDecimal.ZERO.compareTo(validAmount) < 0)
                .map(BigDecimal::negate)
                .map(accountService::addAmount)
                .orElse(OperationResult.createInvalidOperation(ErrorType.INVALID_INPUT));
    }
}
