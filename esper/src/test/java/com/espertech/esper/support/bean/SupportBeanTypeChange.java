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

public final class SupportBeanTypeChange
{
    private final Integer intBoxed;
    private final String intPrimitive;

    public SupportBeanTypeChange(Integer intBoxed, String intPrimitive)
    {
        this.intBoxed = intBoxed;
        this.intPrimitive = intPrimitive;
    }

    public Integer getIntBoxed()
    {
        return intBoxed;
    }

    public String getIntPrimitive()
    {
        return intPrimitive;
    }
}
