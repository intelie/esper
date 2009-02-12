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
