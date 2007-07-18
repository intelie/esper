///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.support.util
{
	public class ArrayAssertionUtil
	{
	    /**
	     * Compare the objects in the two 2-dim String arrays assuming the exact same order.
	     * @param data is the data to assertEqualsExactOrder against
	     * @param expectedValues is the expected values
	     */
	    public static void AreEqualStringArr(String[][] data, String[][] expectedValues)
	    {
	        if ((expectedValues == null) && (data == null))
	        {
	            return;
	        }
	        if ( ((expectedValues == null)) ||
	             ((data == null)) )
	        {
	            Assert.IsTrue(false);
	        }

	        Assert.AreEqual(expectedValues.Length, data.Length, "mismatch in number to elements");

	        for (int i = 0; i < expectedValues.Length; i++)
	        {
	            Assert.IsTrue(CollectionHelper.AreEqual(data[i], expectedValues[i]));
	        }
	    }

	    /**
	     * Iterate through the views collection and check the presence of all values supplied in the exact same order,
	     * using the event bean underlying to compare
	     * @param iterator is the iterator to iterate over and check returned values
	     * @param expectedValues is an array of expected underlying events
	     */
	    public static void AreEqualExactOrderUnderlying(IEnumerator<EventBean> iterator, Object[] expectedValues)
	    {
	        List<Object> underlyingValues = new List<Object>();
	        while (iterator.MoveNext())
	        {
	        	underlyingValues.Add(iterator.Current.Underlying);
	        }

	        try
	        {
	            iterator.MoveNext();
	            Assert.Fail();
	        }
	        catch (InvalidOperationException)
	        {
	            // Expected exception - next called after hasNext returned false, for testing
	        }

	        Object[] data = null;
	        if (underlyingValues.Count > 0)
	        {
	            data = underlyingValues.ToArray();
	        }

	        AreEqualExactOrder(data, expectedValues);
	    }

        /// <summary>
        /// Widens an enumeration a specific type to an enumeration of generic objects.
        /// </summary>
        /// <param name="enumerable">The enumerable.</param>
        /// <returns></returns>
        /// <typeparam name="T"></typeparam>

        public static IEnumerator<Object> WidenEnumerable<T>( IEnumerable<T> enumerable )
        {
            return
                enumerable != null
                    ? WidenEnumerator(enumerable.GetEnumerator())
                    : NullEnumerator();
        }

        /// <summary>
        /// Widens an enumeration a specific type to an enumeration of generic objects.
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="enumerator"></param>
        /// <returns></returns>

        public static IEnumerator<Object> WidenEnumerator<T>( IEnumerator<T> enumerator )
        {
            while( enumerator.MoveNext() )
            {
                yield return enumerator.Current;
            }
        }

        /// <summary>
        /// Returns an enumerator that returns nothing.
        /// </summary>
        /// <returns></returns>
        public static IEnumerator<Object> NullEnumerator()
        {
            if ( false )
            {
                yield return null;
            }
        }

        /// <summary> Compare the data in the two object arrays.</summary>
        /// <param name="data">is the data to Assert.AreEqualExactOrder against
        /// </param>
        /// <param name="expectedValues">is the expected values
        /// </param>
        public static void AreEqualExactOrder(EventBean[] data, EventBean[] expectedValues)
        {
            AreEqualExactOrder((Object[])data, (Object[])expectedValues);
        }

        /// <summary> Iterate through the views collection and check the presence of all values supplied in the exact same order.</summary>
        /// <param name="values">is the iterator to iterate over and check returned values
        /// </param>
        /// <param name="expectedValues">is a map of expected values
        /// </param>
        public static void AreEqualExactOrder(IEnumerator<EventBean> values, EventBean[] expectedValues)
        {
            IList<EventBean> eventBeanList = expectedValues;
            IEnumerator<Object> eventBeanEnum = WidenEnumerable(eventBeanList);

            AreEqualExactOrder(
                WidenEnumerator(values),
                eventBeanEnum ) ;
        }
      
        /// <summary>
        /// Checks two enumerations to determin if they the equal in value
        /// and exact order.
        /// </summary>
        /// <param name="values">The values.</param>
        /// <param name="expected">The expected.</param>
        public static void AreEqualExactOrder<T>(IEnumerable<T> values, IEnumerable<T> expected)
        {
            AreEqualExactOrder(
                WidenEnumerable(values),
                WidenEnumerable(expected));
        }

        /// <summary>
        /// Checks two enumerations to determin if they the equal in value
        /// and exact order.
        /// </summary>
        /// <param name="values">The values.</param>
        /// <param name="expected">The expected.</param>
        public static void AreEqualExactOrder(IEnumerable<Object> values, IEnumerable<int> expected)
        {
            AreEqualExactOrder(
                values.GetEnumerator(),
                WidenEnumerable(expected));
        }

	    /// <summary>
        /// Checks two enumerations to determin if they the equal in value
        /// and exact order.
        /// </summary>
        /// <param name="values">The values.</param>
        /// <param name="expected">The expected.</param>
        public static void AreEqualExactOrder( IEnumerator<Object> values, IEnumerator<Object> expected )
        {
            while( true )
            {
                bool testA = values.MoveNext();
                bool testB = expected.MoveNext();
                if ( testA && testB )
                {
                    Assert.AreEqual( values.Current, expected.Current );
                }
                else
                {
                    Assert.IsFalse( testA || testB, "length mismatch" ) ;
                    return;
                }
            }
        }

        #region "AreEqualAnyOrder"

        public static void AreEqualAnyOrder<T>(T[] expected, Set<T> dataSet)
	    {
	        if ((dataSet == null) && (expected == null))
	        {
	            return;
	        }

	        Assert.IsNotNull(dataSet);
	        Assert.AreEqual(expected.Length, dataSet.Count, "length mismatch");
	        for (int i = 0; i < expected.Length; i++)
	        {
                Assert.IsTrue(dataSet.Contains(expected[i]), "not found: " + expected[i]);
	        }
	    }

        public static void AreEqualAnyOrder<T>(IEnumerable<T> expected, IEnumerable<T> result)
        {
            List<T> list1 = new List<T>(expected);
            List<T> list2 = new List<T>(result);

            Assert.AreEqual( list1.Count, list2.Count, "length mismatch");

            foreach( T item in list1 )
            {
                Assert.IsTrue(list2.Remove(item), item + " not found in resultant set");
            }
        }

	    public static void AreEqualAnyOrder<T>(T[] expected, T[] result)
	    {
            Assert.AreEqual(expected.Length, result.Length, "length mismatch");

	        Set<T> dataSet = new HashSet<T>();
	        for (int i = 0; i < result.Length; i++)
	        {
	            dataSet.Add(result[i]);
	        }

	        AreEqualAnyOrder(expected, dataSet);
	    }

	    public static void AreEqualAnyOrder(Object[] expected, Object[] received)
	    {
	        // Empty lists are fine
	        if ( ((received == null) && (expected == null)) ||
	             ((received.Length == 0) && (expected == null)) ||
	             ((received == null) && (expected.Length == 0)) )
	        {
	            return;
	        }

	        // Same number
	        Assert.AreEqual(expected.Length, received.Length);

	        // For each expected object find a received object
	        int numMatches = 0;
	        foreach (Object expectedObject in expected)
	        {
	            bool found = false;
	            for (int i = 0; i < received.Length; i++)
	            {
	                // Ignore found received objects
	                if (received[i] == null)
	                {
	                    continue;
	                }

	                if (received[i].Equals(expectedObject))
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
	                log.Error(".AreEqualAnyOrder Not found in received results is expected=" + expectedObject);
	                log.Error(".AreEqualAnyOrder received=" + CollectionHelper.Render(received));
	            }
	            Assert.IsTrue(found);
	        }

	        // Must have matched exactly the number of objects times
	        Assert.AreEqual(numMatches, expected.Length);
	    }

	    public static void AreEqualAnyOrder(EventBean[][] expected, EventBean[][] received)
	    {
	        // Empty lists are fine
	        if ( ((received == null) && (expected == null)) ||
	             ((received.Length == 0) && (expected == null)) ||
	             ((received == null) && (expected.Length == 0)) )
	        {
	            return;
	        }

	        // Same number
	        Assert.AreEqual(expected.Length, received.Length);

	        // For each expected object find a received object
	        int numMatches = 0;
	        bool[] foundReceived = new bool[received.Length];
	        foreach (EventBean[] expectedObject in expected)
	        {
	            bool found = false;
	            for (int i = 0; i < received.Length; i++)
	            {
	                // Ignore found received objects
	                if (foundReceived[i])
	                {
	                    continue;
	                }

	                bool match = ArrayCompareUtil.CompareEqualsExactOrder(received[i], expectedObject);
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
	                log.Error(".AreEqualAnyOrder Not found in received results is expected=" + CollectionHelper.Render(expectedObject));
	                log.Error(".AreEqualAnyOrder received=" + CollectionHelper.Render(received));
	            }
	            Assert.IsTrue(found);
	        }

	        // Must have matched exactly the number of objects times
	        Assert.AreEqual(numMatches, expected.Length);
        }

        #endregion

        public static void AssertRefAnyOrderArr(Object[][] expected, Object[][] received)
	    {
            // Empty lists are fine
	        if ( ((received == null) && (expected == null)) ||
	             ((received.Length == 0) && (expected == null)) ||
	             ((received == null) && (expected.Length == 0)) )
	        {
	            return;
	        }

	        // Same number
	        Assert.AreEqual(expected.Length, received.Length);

	        // For each expected object find a received object
	        int numMatches = 0;
	        bool[] foundReceived = new bool[received.Length];
	        foreach (Object[] expectedArr in expected)
	        {
	            bool found = false;
	            for (int i = 0; i < received.Length; i++)
	            {
	                // Ignore found received objects
	                if (foundReceived[i])
	                {
	                    continue;
	                }

	                bool match = ArrayCompareUtil.CompareRefExactOrder(received[i], expectedArr);
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
	                log.Error(".AreEqualAnyOrder Not found in received results is expected=" + CollectionHelper.Render(expectedArr));
	                for (int j = 0; j < received.Length; j++)
	                {
	                    log.Error(".AreEqualAnyOrder                              received (" + j + "):" + CollectionHelper.Render(received[j]));
	                }
	                Assert.Fail();
	            }
	        }

	        // Must have matched exactly the number of objects times
	        Assert.AreEqual(numMatches, expected.Length);
	    }

	    /**
	     * Asserts that all values in the given object array are bool-typed values and are true
	     * @param objects values to assert that they are all true
	     */
	    public static void AssertAllBooleanTrue(Object[] objects)
	    {
	        for (int i = 0; i < objects.Length; i++)
	        {
	            Assert.IsTrue((Boolean) objects[i]);
	        }
	    }

	    /**
	     * Assert the class of the objects in the object array matches the expected classes in the classes array.
	     * @param classes is the expected class
	     * @param objects is the objects to check the class for
	     */
	    public static void AssertTypeEqualsAnyOrder(Type[] classes, Object[] objects)
	    {
	        Assert.AreEqual(classes.Length, objects.Length);
	        Type[] resultClasses = new Type[objects.Length];
	        for (int i = 0; i < objects.Length; i++)
	        {
	            resultClasses[i] = objects[i].GetType();
	        }
	        AreEqualAnyOrder(resultClasses, classes);
	    }

	    public static Object[] Sum(Object[] srcOne, Object[] srcTwo)
	    {
	        Object[] result = new Object[srcOne.Length + srcTwo.Length];
	        Array.Copy(srcOne, 0, result, 0, srcOne.Length);
	        Array.Copy(srcTwo, 0, result, srcOne.Length, srcTwo.Length);
	        return result;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
