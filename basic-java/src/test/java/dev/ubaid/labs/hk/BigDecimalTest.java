package dev.ubaid.labs.hk;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTest {
    
    @Test
    void upto6DigitPrecision() {
        double nuy = 0.61234517;
        double precies = BigDecimal.valueOf(nuy)
                .setScale(6, RoundingMode.HALF_UP)
                .doubleValue();

        System.out.println(precies);
    }
}
