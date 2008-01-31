package com.espertech.esper.support.util;

import junit.framework.TestCase;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.Callable;
import java.lang.reflect.Array;
import java.io.StringWriter;
import java.io.PrintWriter;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.event.SupportEventAdapterService;

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
     * Iterate through the views collection and check the presence of all values supplied in the exact same order,
     * using the event bean underlying to compare
     * @param iterator is the iterator to iterate over and check returned values
     * @param expectedValues is an array of expected underlying events
     */
    public static void assertEqualsExactOrderUnderlying(Iterator<EventBean> iterator, Object[] expectedValues)
    {
        ArrayList<Object> underlyingValues = new ArrayList<Object>();
        while (iterator.hasNext())
        {
            underlyingValues.add(iterator.next().getUnderlying());
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
        if (underlyingValues.size() > 0)
        {
            data = underlyingValues.toArray();
        }

        assertEqualsExactOrder(data, expectedValues);
    }

    /**
     * Comparing the underlying events to the expected events using equals-semantics.
     * @param events is an event array to get the underlying objects
     * @param expectedValues is an array of expected underlying events
     */
    public static void assertEqualsExactOrderUnderlying(EventBean[] events, Object[] expectedValues)
    {
        if ((expectedValues == null) && (events == null))
        {
            return;
        }
        if ( ((expectedValues == null) && (events != null)) ||
             ((expectedValues != null) && (events == null)) )
        {
            TestCase.assertTrue(false);
        }

        TestCase.assertEquals(expectedValues.length, events.length);

        ArrayList<Object> underlying = new ArrayList<Object>();
        for (EventBean event : events)
        {
            underlying.add(event.getUnderlying());
        }

        assertEqualsExactOrder(underlying.toArray(), expectedValues);
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
     * Compare the short values in the two short arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(short[] data, short[] expectedValues)
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
     * Compare the long values in the long arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(long[] data, long[] expectedValues)
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
     * Compare the STring values in the two String arrays assuming the exact same order.
     * @param data is the data to assertEqualsExactOrder against
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(String[] data, String[] expectedValues)
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

    public static void assertEqualsAnyOrder(Object[] expected, Object[] receivedObj)
    {
        // Empty lists are fine
        if ( ((receivedObj == null) && (expected == null)) ||
             ((receivedObj.length == 0) && (expected == null)) ||
             ((receivedObj == null) && (expected.length == 0)) )
        {
            return;
        }

        // Same number
        TestCase.assertEquals(expected.length, receivedObj.length);
        Object[] received = new Object[receivedObj.length];
        System.arraycopy(receivedObj, 0, received, 0, receivedObj.length);

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

    public static void assertPropsPerRow(EventBean[] received, Object[][] propertiesPerRow)
    {
        if (propertiesPerRow == null)
        {
            if ((received == null) || (received.length == 0))
            {
                return;
            }
        }
        Assert.assertEquals(propertiesPerRow.length, received.length);

        for (int i = 0; i < propertiesPerRow.length; i++)
        {
            String name = (String) propertiesPerRow[i][0];
            Object value = propertiesPerRow[i][1];
            Object eventProp = received[i].get(name);
            Assert.assertEquals("Error asserting property named " + name,value,eventProp);
        }
    }

    public static void assertPropsPerRow(List<Object[]> received, Object[][] propertiesListPerRow)
    {
        if (propertiesListPerRow == null)
        {
            if ((received == null) || (received.size() == 0))
            {
                return;
            }
        }
        Assert.assertEquals(propertiesListPerRow.length, received.size());

        for (int i = 0; i < propertiesListPerRow.length; i++)
        {
            Object[] receivedThisRow = received.get(i);
            Object[] propertiesThisRow = propertiesListPerRow[i];
            Assert.assertEquals(receivedThisRow.length, propertiesThisRow.length);

            for (int j = 0; j < propertiesThisRow.length; j++)
            {
                Object expectedValue = propertiesThisRow[j];
                Object receivedValue = receivedThisRow[j];
                Assert.assertEquals("Error asserting property", expectedValue, receivedValue);
            }
        }
    }

    public static void assertPropsPerRow(Map[] received, String[] propertyNames, Object[][] propertiesListPerRow)
    {
        if (propertiesListPerRow == null)
        {
            if ((received == null) || (received.length == 0))
            {
                return;
            }
        }
        Assert.assertEquals(propertiesListPerRow.length, received.length);

        for (int i = 0; i < propertiesListPerRow.length; i++)
        {
            Object[] propertiesThisRow = propertiesListPerRow[i];
            for (int j = 0; j < propertiesThisRow.length; j++)
            {
                String name = propertyNames[j];
                Object value = propertiesThisRow[j];
                Object eventProp = received[i].get(name);
                Assert.assertEquals("Error asserting property named " + name,value,eventProp);
            }
        }
    }

    public static void assertPropsPerRow(Object[][] received, String[] propertyNames, Object[][] propertiesListPerRow)
    {
        if (propertiesListPerRow == null)
        {
            if ((received == null) || (received.length == 0))
            {
                return;
            }
        }
        Assert.assertEquals(propertiesListPerRow.length, received.length);

        for (int i = 0; i < propertiesListPerRow.length; i++)
        {
            Object[] propertiesThisRow = propertiesListPerRow[i];
            for (int j = 0; j < propertiesThisRow.length; j++)
            {
                String name = propertyNames[j];
                Object value = propertiesThisRow[j];
                Object eventProp = received[i][j];
                Assert.assertEquals("Error asserting property named " + name,value,eventProp);
            }
        }
    }

    public static void assertPropsPerRow(EventBean[] received, String[] propertyNames, Object[][] propertiesListPerRow)
    {
        if (propertiesListPerRow == null)
        {
            if ((received == null) || (received.length == 0))
            {
                return;
            }
        }
        Assert.assertEquals(propertiesListPerRow.length, received.length);

        for (int i = 0; i < propertiesListPerRow.length; i++)
        {
            Object[] propertiesThisRow = propertiesListPerRow[i];
            for (int j = 0; j < propertiesThisRow.length; j++)
            {
                String name = propertyNames[j];
                Object value = propertiesThisRow[j];
                Object eventProp = received[i].get(name);
                Assert.assertEquals("Error asserting property named " + name,value,eventProp);
            }
        }
    }

    public static void assertProps(EventBean received, String[] propertyNames, Object[] propertiesThisRow)
    {
        if (propertiesThisRow == null)
        {
            if (received == null)
            {
                return;
            }
        }

        for (int j = 0; j < propertiesThisRow.length; j++)
        {
            String name = propertyNames[j].trim();
            Object value = propertiesThisRow[j];
            Object eventProp = received.get(name);
            Assert.assertEquals("Error asserting property named '" + name + "'",value,eventProp);
        }
    }

    public static void assertProps(Map pojo, String[] propertyNames, Object... propertiesThisRow)
    {
        if (propertiesThisRow == null)
        {
            if (pojo == null)
            {
                return;
            }
        }

        for (int j = 0; j < propertiesThisRow.length; j++)
        {
            String name = propertyNames[j].trim();
            Object value = propertiesThisRow[j];
            Object eventProp = pojo.get(name);
            Assert.assertEquals("Error asserting property named '" + name + "'",value,eventProp);
        }
    }

    public static void assertProps(Object pojo, String[] propertyNames, Object... propertiesThisRow)
    {
        EventBean pojoEvent = SupportEventAdapterService.getService().adapterForBean(pojo);
        assertProps(pojoEvent, propertyNames, propertiesThisRow);
    }

    public static void assertEqualsAnyOrder(Iterator<EventBean> iterator, String[] propertyNames, Object[][] propertiesListPerRow)
    {
        // convert to array of events
        EventBean[] received = iteratorToArray(iterator);
        if (propertiesListPerRow == null)
        {
            if ((received == null) || (received.length == 0))
            {
                return;
            }
        }
        Assert.assertEquals(propertiesListPerRow.length, received.length);

        // build map of event and values
        Map<EventBean, Object[]> valuesEachEvent = new HashMap<EventBean, Object[]>();
        for (int i = 0; i < received.length; i++)
        {
            Object[] values = new Object[propertyNames.length];
            for (int j = 0; j < propertyNames.length; j++)
            {
                values[j] = received[i].get(propertyNames[j]);
            }
            valuesEachEvent.put(received[i], values);
        }

        // Find each list of properties
        for (int i = 0; i < propertiesListPerRow.length; i++)
        {
            Object[] propertiesThisRow = propertiesListPerRow[i];
            boolean isFound = false;

            for (Map.Entry<EventBean, Object[]> entry : valuesEachEvent.entrySet())
            {
                if (Arrays.equals(entry.getValue(), propertiesThisRow))
                {
                    entry.setValue(null);
                    isFound = true;
                    break;
                }
            }

            if (!isFound)
            {
                String text = "Error finding property set: " + Arrays.toString(propertiesListPerRow[i]) + " among values: \n" + dump(valuesEachEvent);
                Assert.fail(text);
            }
        }

        // Should be all null values
        for (Map.Entry<EventBean, Object[]> entry : valuesEachEvent.entrySet())
        {
            if (entry.getValue() != null)
            {
                Assert.fail();
            }
        }
    }

    private static String dump(Map<EventBean, Object[]> valuesEachEvent)
    {
        StringWriter buf = new StringWriter();
        PrintWriter writer = new PrintWriter(buf);
        for (Map.Entry<EventBean, Object[]> entry : valuesEachEvent.entrySet())
        {
            String values = Arrays.toString(entry.getValue());
            writer.println(values);
        }
        return buf.toString();
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

    /**
     * Asserts that all values in the given object array are boolean-typed values and are true
     * @param objects values to assert that they are all true
     */
    public static void assertAllBooleanTrue(Object[] objects)
    {
        for (int i = 0; i < objects.length; i++)
        {
            Assert.assertTrue((Boolean) objects[i]);
        }
    }

    /**
     * Assert the class of the objects in the object array matches the expected classes in the classes array.
     * @param classes is the expected class
     * @param objects is the objects to check the class for
     */
    public static void assertTypeEqualsAnyOrder(Class[] classes, Object[] objects)
    {
        TestCase.assertEquals(classes.length, objects.length);
        Class[] resultClasses = new Class[objects.length];
        for (int i = 0; i < objects.length; i++)
        {
            resultClasses[i] = objects[i].getClass();
        }
        assertEqualsAnyOrder(resultClasses, classes);
    }

    public static EventBean[] iteratorToArray(Iterator<EventBean> iterator)
    {
        if (iterator == null)
        {
            Assert.fail("Null iterator");
        }
        ArrayList<EventBean> events = new ArrayList<EventBean>();
        for (;iterator.hasNext();)
        {
            events.add(iterator.next());
        }
        return events.toArray(new EventBean[0]);
    }

    public static Object[] iteratorToArrayUnderlying(Iterator<EventBean> iterator)
    {
        ArrayList<Object> events = new ArrayList<Object>();
        for (;iterator.hasNext();)
        {
            events.add(iterator.next().getUnderlying());
        }
        return events.toArray();
    }

    public static int iteratorCount(Iterator<EventBean> iterator)
    {
        int count = 0;
        for (;iterator.hasNext();)
        {
            iterator.next();
            count++;
        }
        return count;
    }

    public static Object[] sum(Object[] srcOne, Object[] srcTwo)
    {
        Object[] result = new Object[srcOne.length + srcTwo.length];
        System.arraycopy(srcOne, 0, result, 0, srcOne.length);
        System.arraycopy(srcTwo, 0, result, srcOne.length, srcTwo.length);
        return result;
    }

    /**
     * Compare the event properties returned by the events of the iterator with the supplied values.
     * @param iterator supplies events
     * @param expectedValues is the expected values
     */
    public static void assertEqualsExactOrder(Iterator<EventBean> iterator, String[] fields, Object[][] expectedValues)
    {
        ArrayList<Object[]> rows = new ArrayList<Object[]>();
        while (iterator.hasNext())
        {
            EventBean event = iterator.next();
            Object[] eventProps = new Object[fields.length];
            for (int i = 0; i < fields.length; i++)
            {
                eventProps[i] = event.get(fields[i]);
            }
            rows.add(eventProps);
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

        if (rows.size() == 0)
        {
            Assert.assertNull(expectedValues);
            return;
        }

        Object[][] data = rows.toArray(new Object[0][]);
        if ((expectedValues == null) && (data != null))
        {
            Assert.fail("Expected no values but received data: " + data.length + " elements");
        }

        Assert.assertEquals(expectedValues.length, data.length);
        for (int i = 0; i < data.length; i++)
        {
            assertEqualsExactOrder(data[i], expectedValues[i]);
        }
    }

    private static final Log log = LogFactory.getLog(ArrayAssertionUtil.class);
}
