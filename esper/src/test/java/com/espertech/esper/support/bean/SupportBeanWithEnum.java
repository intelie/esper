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

public class SupportBeanWithEnum implements Serializable
{
    private String string;
    private SupportEnum supportEnum;

    public SupportBeanWithEnum(String string, SupportEnum supportEnum)
    {
        this.string = string;
        this.supportEnum = supportEnum;
    }

    public String getString()
    {
        return string;
    }

    public SupportEnum getSupportEnum()
    {
        return supportEnum;
    }
}
