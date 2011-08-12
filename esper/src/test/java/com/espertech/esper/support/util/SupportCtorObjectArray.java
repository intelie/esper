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

package com.espertech.esper.support.util;

public class SupportCtorObjectArray
{
    private Object[] arguments;

    public SupportCtorObjectArray(Object[] arguments)
    {
        this.arguments = arguments;
    }

    public Object[] getArguments()
    {
        return arguments;
    }    
}
