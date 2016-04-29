package com.sfeir.bank.services;

import com.google.common.annotations.VisibleForTesting;
import com.sfeir.bank.beans.Operation;
import com.sfeir.bank.beans.OperationResult;
import com.sfeir.bank.beans.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DetailService {

    private static final List<Operation> OPERATIONS = new ArrayList<>();

    @Autowired
    private TimeService timeService;

    public synchronized OperationResult addOperation(BigDecimal amount) {
        Operation operation = createOperation(amount);
        OPERATIONS.add(operation);
        return OperationResult.createValidOperation(amount);
    }

    private Operation createOperation(BigDecimal amount) {
        OperationType type = isDeposite(amount) ? OperationType.DEPOSITE : OperationType.WITHDRAW;
        Date now = timeService.now();
        return new Operation(type, now, amount);
    }

    private boolean isDeposite(BigDecimal amount) {
        return BigDecimal.ZERO.compareTo(amount) < 0;
    }

    public List<Operation> getOperations() {
        return OPERATIONS;
    }

    @VisibleForTesting
    void resetOperations() {
        OPERATIONS.clear();
    }
}
