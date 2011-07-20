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

package com.espertech.esper.support.dispatch;

import com.espertech.esper.dispatch.Dispatchable;

import java.util.List;
import java.util.LinkedList;

public class SupportDispatchable implements Dispatchable
{
    private static List<SupportDispatchable> instanceList = new LinkedList<SupportDispatchable>();
    private int numExecuted;

    public void execute()
    {
        numExecuted++;
        instanceList.add(this);
    }

    public int getAndResetNumExecuted()
    {
        int val = numExecuted;
        numExecuted = 0;
        return val;
    }

    public static List<SupportDispatchable> getAndResetInstanceList()
    {
        List<SupportDispatchable> instances = instanceList;
        instanceList = new LinkedList<SupportDispatchable>();
        return instances;
    }
}
