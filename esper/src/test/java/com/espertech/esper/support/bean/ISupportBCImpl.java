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

public class ISupportBCImpl implements ISupportB, ISupportC
{
    private String valueB;
    private String valueBaseAB;
    private String valueC;

    public ISupportBCImpl(String valueB, String valueBaseAB, String valueC)
    {
        this.valueB = valueB;
        this.valueBaseAB = valueBaseAB;
        this.valueC = valueC;
    }

    public String getB()
    {
        return valueB;
    }

    public String getBaseAB()
    {
        return valueBaseAB;
    }

    public String getC()
    {
        return valueC;
    }
}
