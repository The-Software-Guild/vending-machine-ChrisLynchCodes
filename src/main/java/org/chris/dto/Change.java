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

            int[] currenyCount = new int[4];

            if (change.compareTo(Denomination.DOLLAR.currencyValue) >= 0) {
                currenyCount[0] = change.divide(Denomination.DOLLAR.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.DOLLAR.currencyValue.multiply(new BigDecimal(currenyCount[0])));
            }
            if (change.compareTo(Denomination.FIFTY.currencyValue) >= 0) {
                currenyCount[1] = change.divide(Denomination.FIFTY.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.FIFTY.currencyValue.multiply(new BigDecimal(currenyCount[1])));
            }
            if (change.compareTo(Denomination.DOLLAR.currencyValue) >= 0) {
                currenyCount[2] = change.divide(Denomination.DOLLAR.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.DOLLAR.currencyValue.multiply(new BigDecimal(currenyCount[2])));
            }
            if (change.compareTo(Denomination.PENNY.currencyValue) >= 0) {
                currenyCount[3] = change.divide(Denomination.PENNY.currencyValue, RoundingMode.HALF_UP).intValue();
                change = change.subtract(Denomination.PENNY.currencyValue.multiply(new BigDecimal(currenyCount[3])));
            }
            changeString = "Change:" + currenyCount[0] + " " + Denomination.DOLLAR + " " + currenyCount[1] + " " + Denomination.FIFTY + " "
                    + currenyCount[2] + " " + Denomination.QUARTER + " " + currenyCount[3] + " " + Denomination.PENNY;

        }

        return changeString;
    }

}

