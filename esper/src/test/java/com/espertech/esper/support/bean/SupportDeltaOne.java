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

public class SupportDeltaOne
{
    private final String k0;
    private final String p1;
    private final String p5;

    public SupportDeltaOne(String k0, String p1, String p5)
    {
        this.k0 = k0;
        this.p1 = p1;
        this.p5 = p5;
    }

    public String getK0()
    {
        return k0;
    }

    public String getP1()
    {
        return p1;
    }

    public String getP5()
    {
        return p5;
    }
}
