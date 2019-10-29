package com.citrine.entities;

public enum UnitEnum {

    MINUTE("minute", "min", "time", 60, "s"),
    HOUR ("hour", "h", "time", 3600, "s"),
    DAY("day", "d", "time", 86400, "s"),
    DEGREE("degree", "°", "unitless/plane angle",  Math.PI / 180, "rad"),
    ARC_MINUTE("arcminute", "'", "unitless/plane angle",  Math.PI / 10800, "rad"),
    ARC_SECOND("arcsecond", "\"", "unitless/plane angle", Math.PI /648000, "rad"),
    HECTARE("hectare", "ha", "area", 10000, "m2"),
    LITRE("litre", "L", "volume",  0.001, "m3"),
    TONNE("tonne", "t", "mass", 1000L, "kg");

    private final String name;
    private final String symbol;
    private final String quantity;
    private final double siConversion;
    private final String siName;

    UnitEnum(String name,
             String symbol,
             String quantity,
             double siConversion,
             String siName) {
        this.name = name;
        this.symbol = symbol;
        this.quantity = quantity;
        this.siConversion = siConversion;
        this.siName = siName;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getQuantity() {
        return quantity;
    }

    public double getSiConversion() {
        return siConversion;
    }

    public String getSiName() {
        return siName;
    }
}
