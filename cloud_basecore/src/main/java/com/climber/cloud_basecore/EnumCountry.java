package com.climber.cloud_basecore;


import lombok.Getter;

public enum EnumCountry {
    FIVE(5, "魏"),
    FOUR(4, "赵"),
    One(1, "齐"),
    SIX(6, "韩"),
    THREE(3, "燕"),
    TWO(2, "楚");

    @Getter
    public Integer retCode;
    @Getter
    public String retMessage;


    EnumCountry(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public static EnumCountry forEach_EnumCountry(int index) {
        EnumCountry[] arrays = EnumCountry.values();
        for (EnumCountry enumCountry : arrays) {
            if (enumCountry.retCode == index) {
                return enumCountry;
            }
        }
        return null;
    }


}
