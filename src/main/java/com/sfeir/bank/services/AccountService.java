package com.sfeir.bank.services;

import com.google.common.annotations.VisibleForTesting;
import com.sfeir.bank.beans.ErrorType;
import com.sfeir.bank.beans.OperationResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private static BigDecimal BALANCE = BigDecimal.ZERO;

    public synchronized OperationResult addAmount(BigDecimal amount) {
        if (BALANCE.add(amount).compareTo(BigDecimal.ZERO) < 0) {
            return OperationResult.createInvalidOperation(ErrorType.INSUFFICIENT_FUNDS);
        }
        BALANCE = amount.add(BALANCE);
        return OperationResult.createValidOperation(amount);
    }

    public BigDecimal getBalance() {
        return BALANCE;
    }

    @VisibleForTesting
    void resetAccount() {
        BALANCE = BigDecimal.ZERO;
    }

    @VisibleForTesting
    void setBalance(BigDecimal balance) {
        BALANCE = balance;
    }
}
