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

package com.espertech.esper.support.epl;

import com.espertech.esper.support.bean.SupportBean_S0;

import java.util.List;
import java.util.ArrayList;

public class SupportStaticMethodInvocations
{
    private static List<String> invocations = new ArrayList<String>();

    public static int getInvocationSizeReset()
    {
        int size = invocations.size();
        invocations.clear();
        return size;
    }

    public static SupportBean_S0 fetchObjectLog(String fetchId, int passThroughNumber)
    {
        invocations.add(fetchId);
        return new SupportBean_S0(passThroughNumber, "|" + fetchId + "|");
    }
}
