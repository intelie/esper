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

public class SupportDeltaTwo
{
    private final String k0;
    private final String p0;
    private final String p2;
    private final String p3;
    private final String someOtherProp;

    public SupportDeltaTwo(String k0, String p0, String p2, String p3)
    {
        this.k0 = k0;
        this.p0 = p0;
        this.p2 = p2;
        this.p3 = p3;
        someOtherProp = "abc";
    }

    public String getK0()
    {
        return k0;
    }

    public String getP0()
    {
        return p0;
    }

    public String getP2()
    {
        return p2;
    }

    public String getP3()
    {
        return p3;
    }

    public String getSomeOtherProp() {
        return someOtherProp;
    }
}
