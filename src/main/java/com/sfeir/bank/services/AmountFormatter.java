package com.sfeir.bank.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Optional;

@Service
public class AmountFormatter {

    private static final String INTEGER_PATTERN = "[0-9]{0,9}";
    private static final String DECIMAL_PATTERN = "[0-9]{0,2}";
    private static final String DOT_AMOUNT_PATTERN = "^" + INTEGER_PATTERN + "(\\." + DECIMAL_PATTERN + ")?$";
    private static final String COMMA_AMOUNT_PATTERN = "^" + INTEGER_PATTERN + "(," + DECIMAL_PATTERN + ")?$";

    public Optional<BigDecimal> format(String amount) {
        if(amount == null) {
            return Optional.empty();
        }

        amount = amount.trim();

        boolean isDotPattern = amount.matches(DOT_AMOUNT_PATTERN);
        boolean isCommaPattern = amount.matches(COMMA_AMOUNT_PATTERN);

        if (!isDotPattern && !isCommaPattern) {
            return Optional.empty();
        }

        char separator = ',';
        if (isDotPattern) {
            separator = '.';
        }

        BigDecimal value;
        try {
            value = (BigDecimal) createFormatter(separator).parse(amount);
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    private DecimalFormat createFormatter(char separator) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(separator);
        DecimalFormat format = new DecimalFormat("##########.##", symbols);
        format.setParseBigDecimal(true);
        return format;
    }

}
