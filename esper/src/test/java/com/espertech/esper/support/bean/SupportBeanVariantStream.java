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

// For testing variant streams to act as a variant of SupportBean
public class SupportBeanVariantStream implements Serializable
{
    private String string;
    private boolean boolBoxed;
    private Integer intPrimitive;
    private int longPrimitive;
    private float doublePrimitive;
    private SupportEnum enumValue;

    public SupportBeanVariantStream(String string)
    {
        this.string = string;
    }

    public SupportBeanVariantStream(String string, boolean boolBoxed, Integer intPrimitive, int longPrimitive, float doublePrimitive, SupportEnum enumValue)
    {
        this.string = string;
        this.boolBoxed = boolBoxed;
        this.intPrimitive = intPrimitive;
        this.longPrimitive = longPrimitive;
        this.doublePrimitive = doublePrimitive;
        this.enumValue = enumValue;
    }

    public String getString()
    {
        return string;
    }

    public boolean isBoolBoxed()
    {
        return boolBoxed;
    }

    public Integer getIntPrimitive()
    {
        return intPrimitive;
    }

    public int getLongPrimitive()
    {
        return longPrimitive;
    }

    public float getDoublePrimitive()
    {
        return doublePrimitive;
    }

    public SupportEnum getEnumValue()
    {
        return enumValue;
    }
}
