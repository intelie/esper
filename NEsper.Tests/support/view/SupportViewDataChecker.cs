///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.events;
using net.esper.support.util;

namespace net.esper.support.view
{
	public class SupportViewDataChecker
	{
	    /**
	     * Compare the new data underlying events underlying events captured by the child against expected values in the exact same order.
	     * Clears the last new data in the test child view after comparing.
	     * @param testChildView is the child view
	     * @param expectedValues are the expected values
	     */
	    public static void CheckNewDataUnderlying(SupportBaseView testChildView, EventBean[] expectedValues)
	    {
	        EventBean[] newData = testChildView.LastNewData;
	        Object[] expectedUnderlying = GetUnderlying(expectedValues);
	        Object[] newUnderlying = GetUnderlying(newData);
	        ArrayAssertionUtil.AreEqualExactOrder(newUnderlying, expectedUnderlying);
	        testChildView.ClearLastNewData();
	    }

	    /**
	     * Compare the new data captured by the child against expected values in the exact same order.
	     * Clears the last new data in the test child view after comparing.
	     * @param testChildView is the child view
	     * @param expectedValues are the expected values
	     */
	    public static void CheckNewData(SupportBaseView testChildView, EventBean[] expectedValues)
	    {
	        EventBean[] newData = testChildView.LastNewData;
	        ArrayAssertionUtil.AreEqualExactOrder(newData, expectedValues);
	        testChildView.ClearLastNewData();
	    }

	    /**
	     * Compare the old data captured by the child against expected values in the exact same order.
	     * Clears the last old data in the test child view after comparing.
	     * @param testChildView is the child view
	     * @param expectedValues are the expected values
	     */
	    public static void CheckOldData(SupportBaseView testChildView, EventBean[] expectedValues)
	    {
	        EventBean[] oldData = testChildView.LastOldData;
	        ArrayAssertionUtil.AreEqualExactOrder(oldData, expectedValues);
	        testChildView.ClearLastOldData();
	    }

	    /**
	     * Compare the old data underlying object captured by the child against expected values in the exact same order.
	     * Clears the last old data in the test child view after comparing.
	     * @param testChildView is the child view
	     * @param expectedValues are the expected values
	     */
	    public static void CheckOldDataUnderlying(SupportBaseView testChildView, EventBean[] expectedValues)
	    {
	        EventBean[] oldData = testChildView.LastOldData;
	        Object[] expectedUnderlying = GetUnderlying(expectedValues);
	        Object[] oldUnderlying = GetUnderlying(oldData);
	        ArrayAssertionUtil.AreEqualExactOrder(oldUnderlying, expectedUnderlying);
	        testChildView.ClearLastOldData();
	    }

	    /**
	     * Compare the new data captured by the child against expected values in the exact same order.
	     * Clears the last new data in the test child view after comparing.
	     * @param updateListener is the update listener caching the results
	     * @param expectedValues are the expected values
	     */
	    public static void CheckNewData(SupportUpdateListener updateListener, EventBean[] expectedValues)
	    {
	        EventBean[] newData = updateListener.LastNewData;
	        ArrayAssertionUtil.AreEqualExactOrder(newData, expectedValues);
	        updateListener.LastNewData = null;
	    }

	    /**
	     * Compare the new data captured by the child against expected values in the exact same order.
	     * Clears the last new data in the test child view after comparing.
	     * @param updateListener is the update listener caching the results
	     * @param expectedValues are the expected values
	     */
	    public static void CheckNewDataUnderlying(SupportUpdateListener updateListener, EventBean[] expectedValues)
	    {
	        EventBean[] newData = updateListener.LastNewData;
	        Object[] expectedUnderlying = GetUnderlying(expectedValues);
	        Object[] newUnderlying = GetUnderlying(newData);
	        ArrayAssertionUtil.AreEqualExactOrder(newUnderlying, expectedUnderlying);
	        updateListener.LastNewData = null;
	    }

	    /**
	     * Compare the old data captured by the child against expected values in the exact same order.
	     * Clears the last old data in the test child view after comparing.
	     * @param updateListener is the update listener caching the results
	     * @param expectedValues are the expected values
	     */
	    public static void CheckOldData(SupportUpdateListener updateListener, EventBean[] expectedValues)
	    {
	        EventBean[] oldData = updateListener.LastOldData;
	        ArrayAssertionUtil.AreEqualExactOrder(oldData, expectedValues);
	        updateListener.LastOldData = null;
	    }

	    private static Object[] GetUnderlying(EventBean[] events)
	    {
	        if (events == null)
	        {
	            return null;
	        }
	        Object[] underlying = new Object[events.Length];
	        for (int i = 0; i < events.Length; i++)
	        {
	            underlying[i] = events[i].Underlying;
	        }
	        return underlying;
	    }
	}
} // End of namespace
