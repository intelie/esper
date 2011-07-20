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

public class SupportChainChildOne
{
    private String text;
    private int value;

    public SupportChainChildOne(String text, int value)
    {
        this.text = text;
        this.value = value;
    }

    public SupportChainChildTwo getChildTwo(String append) {
        return new SupportChainChildTwo(text + append, 1 + value);
    }
}
