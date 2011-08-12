/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupportBean implements Serializable
{
    private String string;

    private boolean boolPrimitive;
    private int intPrimitive;
    private long longPrimitive;
    private char charPrimitive;
    private short shortPrimitive;
    private byte bytePrimitive;
    private float floatPrimitive;
    private double doublePrimitive;

    private Boolean boolBoxed;
    private Integer intBoxed;
    private Long longBoxed;
    private Character charBoxed;
    private Short shortBoxed;
    private Byte byteBoxed;
    private Float floatBoxed;
    private Double doubleBoxed;
    private BigDecimal bigDecimal;

    private SupportEnum enumValue;

    public SupportBean()
    {
    }

    public SupportBean(String string, int intPrimitive)
    {
        this.string = string;
        this.intPrimitive = intPrimitive;
    }

    public String getString()
    {
        return string;
    }

    public boolean isBoolPrimitive()
    {
        return boolPrimitive;
    }

    public int getIntPrimitive()
    {
        return intPrimitive;
    }

    public long getLongPrimitive()
    {
        return longPrimitive;
    }

    public char getCharPrimitive()
    {
        return charPrimitive;
    }

    public short getShortPrimitive()
    {
        return shortPrimitive;
    }

    public byte getBytePrimitive()
    {
        return bytePrimitive;
    }

    public float getFloatPrimitive()
    {
        return floatPrimitive;
    }

    public double getDoublePrimitive()
    {
        return doublePrimitive;
    }

    public Boolean getBoolBoxed()
    {
        return boolBoxed;
    }

    public Integer getIntBoxed()
    {
        return intBoxed;
    }

    public Long getLongBoxed()
    {
        return longBoxed;
    }

    public Character getCharBoxed()
    {
        return charBoxed;
    }

    public Short getShortBoxed()
    {
        return shortBoxed;
    }

    public Byte getByteBoxed()
    {
        return byteBoxed;
    }

    public Float getFloatBoxed()
    {
        return floatBoxed;
    }

    public Double getDoubleBoxed()
    {
        return doubleBoxed;
    }

    public void setString(String string)
    {
        this.string = string;
    }

    public void setBoolPrimitive(boolean boolPrimitive)
    {
        this.boolPrimitive = boolPrimitive;
    }

    public void setIntPrimitive(int intPrimitive)
    {
        this.intPrimitive = intPrimitive;
    }

    public void setLongPrimitive(long longPrimitive)
    {
        this.longPrimitive = longPrimitive;
    }

    public void setCharPrimitive(char charPrimitive)
    {
        this.charPrimitive = charPrimitive;
    }

    public void setShortPrimitive(short shortPrimitive)
    {
        this.shortPrimitive = shortPrimitive;
    }

    public void setBytePrimitive(byte bytePrimitive)
    {
        this.bytePrimitive = bytePrimitive;
    }

    public void setFloatPrimitive(float floatPrimitive)
    {
        this.floatPrimitive = floatPrimitive;
    }

    public void setDoublePrimitive(double doublePrimitive)
    {
        this.doublePrimitive = doublePrimitive;
    }

    public void setBoolBoxed(Boolean boolBoxed)
    {
        this.boolBoxed = boolBoxed;
    }

    public void setIntBoxed(Integer intBoxed)
    {
        this.intBoxed = intBoxed;
    }

    public void setLongBoxed(Long longBoxed)
    {
        this.longBoxed = longBoxed;
    }

    public void setCharBoxed(Character charBoxed)
    {
        this.charBoxed = charBoxed;
    }

    public void setShortBoxed(Short shortBoxed)
    {
        this.shortBoxed = shortBoxed;
    }

    public void setByteBoxed(Byte byteBoxed)
    {
        this.byteBoxed = byteBoxed;
    }

    public void setFloatBoxed(Float floatBoxed)
    {
        this.floatBoxed = floatBoxed;
    }

    public void setDoubleBoxed(Double doubleBoxed)
    {
        this.doubleBoxed = doubleBoxed;
    }

    public SupportEnum getEnumValue()
    {
        return enumValue;
    }

    public void setEnumValue(SupportEnum enumValue)
    {
        this.enumValue = enumValue;
    }

    public SupportBean getThis()
    {
        return this;
    }

    public String toString()
    {
        return this.getClass().getSimpleName() + "(" + string + ", " + intPrimitive + ")";
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }
}
