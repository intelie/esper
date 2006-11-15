package net.esper.support.util;

import junit.framework.TestCase;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.event.EventBean;

public class ArrayAssertionUtil
{
    /**
     * Compare the objects in the two 2-dim String arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsStringArr(String[][] data, String[][] expectedValues)
    {
        if ((expectedValues == null) && (data == null))
        {
            return;
        }
        if ( ((expectedValues == null) && (data != null)) ||
             ((expectedValues != null) && (data == null)) )
        {
            TestCase.assertTrue(false);
        }

        TestCase.assertEquals("mismatch in number to elements", expectedValues.length, data.length);

        for (int i = 0; i < expectedValues.length; i++)
        {
            TestCase.assertTrue(Arrays.equals(data[i], expectedValues[i]));
        }
    }

    /**
     * Compare the data in the two object arrays.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(EventBean[] data, EventBean[] expectedValues)
    {
        assertEqualsExactOrder((Object[]) data, (Object[]) expectedValues);
    }

    /**
     * Iterate through the views collection and check the presence of all values supplied in the exact same order.
     * @param iterator is the iterator to iterate over and check returned values
     * @param expectedValues is a map of expected values
     */
    public static void assertEqualsExactOrder(Iterator<EventBean> iterator, EventBean[] expectedValues)
    {
        assertEqualsExactOrder((Iterator) iterator, (Object[]) expectedValues);
    }

    /**
     * Compare the objects in the two object arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(Object[] data, Object[] expectedValues)
    {
        if ((expectedValues == null) && (data == null))
        {
            return;
        }
        if ( ((expectedValues == null) && (data != null)) ||
             ((expectedValues != null) && (data == null)) )
        {
            TestCase.assertTrue(false);
        }

        TestCase.assertEquals(expectedValues.length, data.length);

        for (int i = 0; i < expectedValues.length; i++)
        {
            TestCase.assertEquals(expectedValues[i], data[i]);
        }
    }

    /**
     * Compare the integer values in the two int arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(int[] data, int[] expectedValues)
    {
        if ((expectedValues == null) && (data == null))
        {
            return;
        }
        if ( ((expectedValues == null) && (data != null)) ||
             ((expectedValues != null) && (data == null)) )
        {
            TestCase.assertTrue(false);
        }

        TestCase.assertEquals(expectedValues.length, data.length);

        for (int i = 0; i < expectedValues.length; i++)
        {
            TestCase.assertEquals(expectedValues[i], data[i]);
        }
    }

    /**
     * Compare the boolean values in the two bool arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(boolean[] data, boolean[] expectedValues)
    {
        if ((expectedValues == null) && (data == null))
        {
            return;
        }
        if ( ((expectedValues == null) && (data != null)) ||
             ((expectedValues != null) && (data == null)) )
        {
            TestCase.assertTrue(false);
        }

        TestCase.assertEquals(expectedValues.length, data.length);

        for (int i = 0; i < expectedValues.length; i++)
        {
            TestCase.assertEquals(expectedValues[i], data[i]);
        }
    }

    /**
     * Compare the objects in the two object arrays.
     * @param iterator returns the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(Iterator<Object> iterator, Object[] expectedValues)
    {
        ArrayList<Object> values = new ArrayList<Object>();
        while (iterator.hasNext())
        {
            values.add(iterator.next());
        }

        try
        {
            iterator.next();
            TestCase.fail();
        }
        catch (NoSuchElementException ex)
        {
            // Expected exception - next called after hasNext returned false, for testing
        }

        Object[] data = null;
        if (values.size() > 0)
        {
            data = values.toArray();
        }

        assertEqualsExactOrder(data, expectedValues);
    }

    public static void assertEqualsAnyOrder(int[] expected, Set<Integer> intSet)
    {
        if ((intSet == null) && (expected == null))
        {
            return;
        }

        TestCase.assertEquals("length mismatch", expected.length, intSet.size());
        for (int i = 0; i < expected.length; i++)
        {
            TestCase.assertTrue("not found: " + expected[i], intSet.contains(expected[i]));
        }
    }

    public static void assertEqualsAnyOrder(int[] expected, int[] result)
    {
        TestCase.assertEquals("length mismatch", expected.length, result.length);

        Set<Integer> intSet = new HashSet<Integer>();
        for (int i = 0; i < result.length; i++)
        {
            intSet.add(result[i]);
        }

        assertEqualsAnyOrder(expected, intSet);
    }

    public static void assertEqualsAnyOrder(Object[] expected, Object[] received)
    {
        // Empty lists are fine
        if ( ((received == null) && (expected == null)) ||
             ((received.length == 0) && (expected == null)) ||
             ((received == null) && (expected.length == 0)) )
        {
            return;
        }

        // Same number
        TestCase.assertEquals(expected.length, received.length);

        // For each expected object find a received object
        int numMatches = 0;
        for (Object expectedObject : expected)
        {
            boolean found = false;
            for (int i = 0; i < received.length; i++)
            {
                // Ignore found received objects
                if (received[i] == null)
                {
                    continue;
                }

                if (received[i].equals(expectedObject))
                {
                    found = true;
                    numMatches++;
                    // Blank out received object so as to not match again
                    received[i] = null;
                    break;
                }
            }

            if (!found)
            {
                log.error(".assertEqualsAnyOrder Not found in received results is expected=" + expectedObject);
                log.error(".assertEqualsAnyOrder received=" + Arrays.toString(received));
            }
            TestCase.assertTrue(found);
        }

        // Must have matched exactly the number of objects times
        TestCase.assertEquals(numMatches, expected.length);
    }

    public static void assertEqualsAnyOrder(EventBean[][] expected, EventBean[][] received)
    {
        // Empty lists are fine
        if ( ((received == null) && (expected == null)) ||
             ((received.length == 0) && (expected == null)) ||
             ((received == null) && (expected.length == 0)) )
        {
            return;
        }

        // Same number
        TestCase.assertEquals(expected.length, received.length);

        // For each expected object find a received object
        int numMatches = 0;
        boolean[] foundReceived = new boolean[received.length];
        for (EventBean[] expectedObject : expected)
        {
            boolean found = false;
            for (int i = 0; i < received.length; i++)
            {
                // Ignore found received objects
                if (foundReceived[i])
                {
                    continue;
                }

                boolean match = ArrayCompareUtil.compareEqualsExactOrder(received[i], expectedObject);
                if (match)
                {
                    found = true;
                    numMatches++;
                    foundReceived[i] = true;
                    break;
                }
            }

            if (!found)
            {
                log.error(".assertEqualsAnyOrder Not found in received results is expected=" + Arrays.toString(expectedObject));
                log.error(".assertEqualsAnyOrder received=" + Arrays.toString(received));
            }
            TestCase.assertTrue(found);
        }

        // Must have matched exactly the number of objects times
        TestCase.assertEquals(numMatches, expected.length);
    }

    public static void assertRefAnyOrderArr(Object[][] expected, Object[][] received)
    {
        // Empty lists are fine
        if ( ((received == null) && (expected == null)) ||
             ((received.length == 0) && (expected == null)) ||
             ((received == null) && (expected.length == 0)) )
        {
            return;
        }

        // Same number
        TestCase.assertEquals(expected.length, received.length);

        // For each expected object find a received object
        int numMatches = 0;
        boolean[] foundReceived = new boolean[received.length];
        for (Object[] expectedArr : expected)
        {
            boolean found = false;
            for (int i = 0; i < received.length; i++)
            {
                // Ignore found received objects
                if (foundReceived[i])
                {
                    continue;
                }

                boolean match = ArrayCompareUtil.compareRefExactOrder(received[i], expectedArr);
                if (match)
                {
                    found = true;
                    numMatches++;
                    // Blank out received object so as to not match again
                    foundReceived[i] = true;
                    break;
                }
            }

            if (!found)
            {
                log.error(".assertEqualsAnyOrder Not found in received results is expected=" + Arrays.toString(expectedArr));
                for (int j = 0; j < received.length; j++)
                {
                    log.error(".assertEqualsAnyOrder                              received (" + j + "):" + Arrays.toString(received[j]));
                }
                Assert.fail();
            }
        }

        // Must have matched exactly the number of objects times
        TestCase.assertEquals(numMatches, expected.length);
    }

    private static final Log log = LogFactory.getLog(ArrayAssertionUtil.class);
}
