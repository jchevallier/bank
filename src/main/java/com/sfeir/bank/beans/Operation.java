package com.sfeir.bank.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Operation {

    private final OperationType type;
    private final Date creationDate;
    private final BigDecimal amount;

    public Operation(OperationType type, Date creationDate, BigDecimal amount) {
        this.type = type;
        this.creationDate = creationDate;
        this.amount = amount;
    }

    public OperationType getType() {
        return type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
