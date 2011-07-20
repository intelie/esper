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

package com.espertech.esper.support.view;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

public class SupportViewDataChecker
{
    /**
     * Compare the new data underlying events underlying events captured by the child against expected values in the exact same order.
     * Clears the last new data in the test child view after comparing.
     * @param testChildView is the child view
     * @param expectedValues are the expected values
     */
    public static void checkNewDataUnderlying(SupportBaseView testChildView, EventBean[] expectedValues)
    {
        EventBean[] newData = testChildView.getLastNewData();
        Object[] expectedUnderlying = getUnderlying(expectedValues);
        Object[] newUnderlying = getUnderlying(newData);
        ArrayAssertionUtil.assertEqualsExactOrder(newUnderlying, expectedUnderlying);
        testChildView.clearLastNewData();
    }

    /**
     * Compare the new data captured by the child against expected values in the exact same order.
     * Clears the last new data in the test child view after comparing.
     * @param testChildView is the child view
     * @param expectedValues are the expected values
     */
    public static void checkNewData(SupportBaseView testChildView, EventBean[] expectedValues)
    {
        EventBean[] newData = testChildView.getLastNewData();
        ArrayAssertionUtil.assertEqualsExactOrder(newData, expectedValues);
        testChildView.clearLastNewData();
    }

    /**
     * Compare the old data captured by the child against expected values in the exact same order.
     * Clears the last old data in the test child view after comparing.
     * @param testChildView is the child view
     * @param expectedValues are the expected values
     */
    public static void checkOldData(SupportBaseView testChildView, EventBean[] expectedValues)
    {
        EventBean[] oldData = testChildView.getLastOldData();
        ArrayAssertionUtil.assertEqualsExactOrder(oldData, expectedValues);
        testChildView.clearLastOldData();
    }

    /**
     * Compare the old data underlying object captured by the child against expected values in the exact same order.
     * Clears the last old data in the test child view after comparing.
     * @param testChildView is the child view
     * @param expectedValues are the expected values
     */
    public static void checkOldDataUnderlying(SupportBaseView testChildView, EventBean[] expectedValues)
    {
        EventBean[] oldData = testChildView.getLastOldData();
        Object[] expectedUnderlying = getUnderlying(expectedValues);
        Object[] oldUnderlying = getUnderlying(oldData);
        ArrayAssertionUtil.assertEqualsExactOrder(oldUnderlying, expectedUnderlying);
        testChildView.clearLastOldData();
    }

    /**
     * Compare the new data captured by the child against expected values in the exact same order.
     * Clears the last new data in the test child view after comparing.
     * @param updateListener is the update listener caching the results
     * @param expectedValues are the expected values
     */
    public static void checkNewData(SupportUpdateListener updateListener, EventBean[] expectedValues)
    {
        EventBean[] newData = updateListener.getLastNewData();
        ArrayAssertionUtil.assertEqualsExactOrder(newData, expectedValues);
        updateListener.setLastNewData(null);
    }

    /**
     * Compare the new data captured by the child against expected values in the exact same order.
     * Clears the last new data in the test child view after comparing.
     * @param updateListener is the update listener caching the results
     * @param expectedValues are the expected values
     */
    public static void checkNewDataUnderlying(SupportUpdateListener updateListener, EventBean[] expectedValues)
    {
        EventBean[] newData = updateListener.getLastNewData();
        Object[] expectedUnderlying = getUnderlying(expectedValues);
        Object[] newUnderlying = getUnderlying(newData);
        ArrayAssertionUtil.assertEqualsExactOrder(newUnderlying, expectedUnderlying);
        updateListener.setLastNewData(null);
    }

    /**
     * Compare the old data captured by the child against expected values in the exact same order.
     * Clears the last old data in the test child view after comparing.
     * @param updateListener is the update listener caching the results
     * @param expectedValues are the expected values
     */
    public static void checkOldData(SupportUpdateListener updateListener, EventBean[] expectedValues)
    {
        EventBean[] oldData = updateListener.getLastOldData();
        ArrayAssertionUtil.assertEqualsExactOrder(oldData, expectedValues);
        updateListener.setLastOldData(null);
    }

    private static Object[] getUnderlying(EventBean[] events)
    {
        if (events == null)
        {
            return null;
        }
        Object[] underlying = new Object[events.length];
        for (int i = 0; i < events.length; i++)
        {
            underlying[i] = events[i].getUnderlying();
        }
        return underlying;
    }
}
