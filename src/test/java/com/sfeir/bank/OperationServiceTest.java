package com.sfeir.bank;

import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class OperationServiceTest {

    @InjectMocks
    private OperationService operationService = new OperationService();

    @Mock
    private AmountFormatter formatter;

    private Optional<BigDecimal> formattedValue;

    public OperationServiceTest() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(formatter.format(Mockito.anyString())).then(invocationOnMock -> formattedValue);
    }

    @Test
    public void should_save_money_when_formatter_succeed() {
        formattedValue = Optional.of(BigDecimal.TEN);

        OperationResult result = operationService.save("");

        Assertions.assertThat(result.isValid());
        Assertions.assertThat(result.getResult()).isEqualTo(formattedValue.get());
    }

    @Test
    public void should_contains_invalid_input_when_formatter_fail() {
        formattedValue = Optional.empty();

        OperationResult result = operationService.save("");

        Assertions.assertThat(result.isValid());
        Assertions.assertThat(result.getErrorType()).isEqualTo(ErrorType.INVALID_INPUT);
    }

}