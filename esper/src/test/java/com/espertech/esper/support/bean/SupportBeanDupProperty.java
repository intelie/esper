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

public class SupportBeanDupProperty
{
    private String myProperty;
    private String MyProperty;
    private String MYPROPERTY;
    private String myproperty;

    public SupportBeanDupProperty(String myProperty, String MyProperty, String MYPROPERTY, String myproperty)
    {
        this.myProperty = myProperty;
        this.MyProperty = MyProperty;
        this.MYPROPERTY = MYPROPERTY;
        this.myproperty = myproperty;
    }

    public String getmyProperty()
    {
        return myProperty;
    }

    public String getMyProperty()
    {
        return MyProperty;
    }

    public String getMYPROPERTY()
    {
        return MYPROPERTY;
    }

    public String getMyproperty()
    {
        return myproperty;
    }
}
