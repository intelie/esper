package com.espertech.esper.support.bean;

public enum SupportEnum
{
    ENUM_VALUE_1,
    ENUM_VALUE_2,
    ENUM_VALUE_3;

    public static SupportEnum getValueForEnum(int count)
    {
        return SupportEnum.values()[count]; 
    }

}
