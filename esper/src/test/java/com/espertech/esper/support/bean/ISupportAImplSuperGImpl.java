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

public class ISupportAImplSuperGImpl extends ISupportAImplSuperG
{
    private String valueG;
    private String valueA;
    private String valueBaseAB;

    public ISupportAImplSuperGImpl(String valueG, String valueA, String valueBaseAB)
    {
        this.valueG = valueG;
        this.valueA = valueA;
        this.valueBaseAB = valueBaseAB;
    }

    public String getG()
    {
        return valueG;
    }

    public String getA()
    {
        return valueA;
    }

    public String getBaseAB()
    {
        return valueBaseAB;
    }
}
