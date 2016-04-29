package com.sfeir.bank.services;

import com.sfeir.bank.services.AmountFormatter;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Optional;

public class AmountFormatterTest {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#########.##");
    static {
        DECIMAL_FORMAT.setParseBigDecimal(true);
    }
    private AmountFormatter formatter = new AmountFormatter();

    @DataProvider(name = "inexpected_amount")
    private Object[][] provideInexpectedAmount() {
        return new Object[][] {
                new Object[] {null},
                new Object[] {"abc"},
                new Object[] {"10L"},
                new Object[] {"10 100"},
                new Object[] {"10,100"},
                new Object[] {"1234567890,01"},
                new Object[] {"1234567890"},
        };
    }

    @Test(dataProvider = "inexpected_amount")
    public void should_contains_an_empty_optional_when_input_is_expected(String amount) {
        Optional<BigDecimal> result = formatter.format(amount);
        Assertions.assertThat(result).isEmpty();
    }

    @DataProvider(name = "expected_amount")
    private Object[][] provideExpectedAmount() throws ParseException {
        return new Object[][] {
                new Object[] {"10", BigDecimal.TEN},
                new Object[] {" 10", BigDecimal.TEN},
                new Object[] {"10 ", BigDecimal.TEN},
                new Object[] {" 10 ", BigDecimal.TEN},
                new Object[] {"10,1", BigDecimal.valueOf(10.1)},
                new Object[] {"999999999", BigDecimal.valueOf(999999999L)},
                new Object[] {"999999999,99", (BigDecimal) DECIMAL_FORMAT.parse("999999999,99")},
                new Object[] {"10.1", BigDecimal.valueOf(10.1)},
        };
    }

    @Test(dataProvider = "expected_amount")
    public void should_contains_an_empty_optional_when_input_is_expected(String amount, BigDecimal expectedValue) {
        Optional<BigDecimal> result = formatter.format(amount);
        Assertions.assertThat(result).contains(expectedValue);
    }
}