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

public class SupportBeanCopyMethod
{
    private String valOne;
    private String valTwo;

    public SupportBeanCopyMethod(String valOne, String valTwo)
    {
        this.valOne = valOne;
        this.valTwo = valTwo;
    }

    public String getValOne()
    {
        return valOne;
    }

    public void setValOne(String valOne)
    {
        this.valOne = valOne;
    }

    public String getValTwo()
    {
        return valTwo;
    }

    public void setValTwo(String valTwo)
    {
        this.valTwo = valTwo;
    }

    public SupportBeanCopyMethod myCopyMethod()
    {
        return new SupportBeanCopyMethod(valOne, valTwo);
    }
}
