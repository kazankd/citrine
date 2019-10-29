package com.citrine.viewmodels;

import java.math.BigDecimal;

public class Unit {

    private String unitName;
    private BigDecimal multiplicationFactor;

    public Unit(String unitName, BigDecimal multiplicationFactor) {
        this.unitName = unitName;
        this.multiplicationFactor = multiplicationFactor;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getMultiplicationFactor() {
        return multiplicationFactor;
    }

    public void setMultiplicationFactor(BigDecimal multiplicationFactor) {
        this.multiplicationFactor = multiplicationFactor;
    }
}
