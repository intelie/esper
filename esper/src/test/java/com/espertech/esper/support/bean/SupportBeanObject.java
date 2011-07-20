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

public class SupportBeanObject implements Serializable
{
    private Object one;
    private Object two;
    private Object three;
    private Object four;
    private Object five;
    private Object six;

    public SupportBeanObject() {
    }

    public SupportBeanObject(Object one) {
        this.one = one;
    }

    public Object getFive()
    {
        return five;
    }

    public void setFive(Object five)
    {
        this.five = five;
    }

    public Object getFour()
    {
        return four;
    }

    public void setFour(Object four)
    {
        this.four = four;
    }

    public Object getOne()
    {
        return one;
    }

    public void setOne(Object one)
    {
        this.one = one;
    }

    public Object getSix()
    {
        return six;
    }

    public void setSix(Object six)
    {
        this.six = six;
    }

    public Object getThree()
    {
        return three;
    }

    public void setThree(Object three)
    {
        this.three = three;
    }

    public Object getTwo()
    {
        return two;
    }

    public void setTwo(Object two)
    {
        this.two = two;
    }
}
