package org.chris.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Change {

    //ENUM
    enum Denomination {
        DOLLAR(new BigDecimal("1.00")), FIFTY(new BigDecimal("0.50")), QUARTER(new BigDecimal("0.25")), PENNY(new BigDecimal("0.01"));

        private final BigDecimal currencyValue;

        Denomination(BigDecimal currencyValue)
        {
            this.currencyValue = currencyValue;
        }
    };


    public static String getChange(BigDecimal customerBalance, BigDecimal itemPrice)
    {

        BigDecimal change = customerBalance.subtract(itemPrice);
        String changeString = "";
        if (change.compareTo(new BigDecimal("0.00")) == 0) {
            changeString = "No change";
        } else {

            int[] currencyCount = new int[4];

            if (change.compareTo(Denomination.DOLLAR.currencyValue) >= 0) {
                currencyCount[Denomination.valueOf("DOLLAR").ordinal()] = change.divide(Denomination.DOLLAR.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.DOLLAR.currencyValue.multiply(new BigDecimal(currencyCount[0])));
            }
            if (change.compareTo(Denomination.FIFTY.currencyValue) >= 0) {
                currencyCount[Denomination.valueOf("FIFTY").ordinal()] = change.divide(Denomination.FIFTY.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.FIFTY.currencyValue.multiply(new BigDecimal(currencyCount[1])));
            }
            if (change.compareTo(Denomination.QUARTER.currencyValue) >= 0) {
                currencyCount[Denomination.valueOf("QUARTER").ordinal()] = change.divide(Denomination.QUARTER.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.QUARTER.currencyValue.multiply(new BigDecimal(currencyCount[2])));
            }
            if (change.compareTo(Denomination.PENNY.currencyValue) >= 0) {
                currencyCount[Denomination.valueOf("PENNY").ordinal()] = change.divide(Denomination.PENNY.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.PENNY.currencyValue.multiply(new BigDecimal(currencyCount[3])));
            }
            changeString = "Change:" + currencyCount[Denomination.valueOf("DOLLAR").ordinal()] + " " + Denomination.DOLLAR + " " + currencyCount[Denomination.valueOf("FIFTY").ordinal()] + " " + Denomination.FIFTY + " "
                    + currencyCount[Denomination.valueOf("QUARTER").ordinal()] + " " + Denomination.QUARTER + " " + currencyCount[Denomination.valueOf("PENNY").ordinal()] + " " + Denomination.PENNY;

        }

        return changeString;
    }

}

