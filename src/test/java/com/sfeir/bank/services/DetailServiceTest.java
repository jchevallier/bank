package com.sfeir.bank.services;

import com.sfeir.bank.beans.Operation;
import com.sfeir.bank.beans.OperationResult;
import com.sfeir.bank.beans.OperationType;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;

public class DetailServiceTest {

    private static final Date NOW = new Date();

    @InjectMocks
    private DetailService detailService;

    @Mock
    private TimeService timeService;

    public DetailServiceTest() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(timeService.now()).thenReturn(NOW);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        detailService.resetOperations();
    }

    @Test
    public void should_create_deposite_operation_when_positive_amount() {
        BigDecimal amount = BigDecimal.TEN;

        OperationResult result = detailService.addOperation(amount);

        Assertions.assertThat(result.isValid()).isTrue();
        Assertions.assertThat(result.getResult()).isEqualTo(amount);
        Assertions.assertThat(detailService.getOperations()).hasSize(1);
        Operation operation = detailService.getOperations().get(0);
        Assertions.assertThat(operation.getAmount()).isEqualTo(amount);
        Assertions.assertThat(operation.getCreationDate()).isEqualTo(NOW);
        Assertions.assertThat(operation.getType()).isEqualTo(OperationType.DEPOSITE);
    }
}