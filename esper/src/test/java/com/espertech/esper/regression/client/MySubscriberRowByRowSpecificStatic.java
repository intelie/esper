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

package com.espertech.esper.regression.client;

import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportEnum;
import com.espertech.esper.support.bean.SupportMarketDataBean;

import java.util.ArrayList;
import java.util.List;

public class MySubscriberRowByRowSpecificStatic
{
    private static ArrayList<Object[]> indicate = new ArrayList<Object[]>();

    public static void update(String string, int intPrimitive)
    {
        indicate.add(new Object[] {string, intPrimitive});
    }

    public static List<Object[]> getAndResetIndicate()
    {
        List<Object[]> result = indicate;
        indicate = new ArrayList<Object[]>();
        return result;
    }
}
