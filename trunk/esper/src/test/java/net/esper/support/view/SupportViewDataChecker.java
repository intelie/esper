package net.esper.support.view;

import net.esper.event.EventBean;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

public class SupportViewDataChecker
{
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
}
