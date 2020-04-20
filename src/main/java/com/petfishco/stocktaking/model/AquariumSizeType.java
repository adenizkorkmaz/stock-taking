package com.petfishco.stocktaking.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum AquariumSizeType {
    LITER {
        @Override
        public double convertFromLiter(double size) {
            return size;
        }
    },
    GALLON {
        @Override
        public double convertFromLiter(double size) {
            BigDecimal literSize = new BigDecimal(Double.toString(size));
            BigDecimal gallonSize = literSize
                    .divide(GALLON_LITER, RoundingMode.HALF_UP)
                    .setScale(3, RoundingMode.HALF_UP);
            return gallonSize.doubleValue();
        }
    };

    public static final BigDecimal GALLON_LITER = new BigDecimal("3.785");

    public abstract double convertFromLiter(double size);
}
