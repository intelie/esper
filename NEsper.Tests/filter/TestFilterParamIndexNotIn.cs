// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.collection;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterParamIndexNotIn
	{
		private SupportEventEvaluator[] testEvaluators;
	    private SupportBean testBean;
	    private EventBean testEventBean;
	    private EventType testEventType;
	    private IList<FilterHandle> matchesList;

	    [SetUp]
	    public void SetUp()
	    {
	        testEvaluators = new SupportEventEvaluator[4];
	        for (int i = 0; i < testEvaluators.Length; i++)
	        {
	            testEvaluators[i] = new SupportEventEvaluator();
	        }

	        testBean = new SupportBean();
	        testEventBean = SupportEventBeanFactory.CreateObject(testBean);
	        testEventType = testEventBean.EventType;
	        matchesList = new List<FilterHandle>();
	    }

	    [Test]
	    public void testIndex()
	    {
	        FilterParamIndexNotIn index = new FilterParamIndexNotIn("longBoxed", testEventType);
	        Assert.AreEqual(FilterOperator.NOT_IN_LIST_OF_VALUES, index.FilterOperator);

	        index[new MultiKeyUntyped(new Object[] {2L, 5L})] = testEvaluators[0];
	        index[new MultiKeyUntyped(new Object[] {3L, 4L, 5L})] = testEvaluators[1];
	        index[new MultiKeyUntyped(new Object[] {1L, 4L, 5L})] = testEvaluators[2];
	        index[new MultiKeyUntyped(new Object[] {2L, 5L})] = testEvaluators[3];

	        Verify(index, 0L, new bool[] {true, true, true, true});
	        Verify(index, 1L, new bool[] {true, true, false, true});
	        Verify(index, 2L, new bool[] {false, true, true, false});
	        Verify(index, 3L, new bool[] {true, false, true, true});
	        Verify(index, 4L, new bool[] {true, false, false, true});
	        Verify(index, 5L, new bool[] {false, false, false, false});
	        Verify(index, 6L, new bool[] {true, true, true, true});

	        MultiKeyUntyped inList = new MultiKeyUntyped(new Object[] {3L, 4L, 5L});
            Assert.AreEqual(testEvaluators[1], index[inList]);
	        Assert.IsTrue(index.ReadWriteLock != null);
	        Assert.IsTrue(index.Remove(inList));
	        Assert.IsFalse(index.Remove(inList));
            Assert.AreEqual(null, index[inList]);

	        // now that {3,4,5} is removed, verify results again
	        Verify(index, 0L, new bool[] {true, false, true, true});
	        Verify(index, 1L, new bool[] {true, false, false, true});
	        Verify(index, 2L, new bool[] {false, false, true, false});
	        Verify(index, 3L, new bool[] {true, false, true, true});
	        Verify(index, 4L, new bool[] {true, false, false, true});
	        Verify(index, 5L, new bool[] {false, false, false, false});
	        Verify(index, 6L, new bool[] {true, false, true, true});

	        try
	        {
	            index["a"] = testEvaluators[0];
	            Assert.IsTrue(false);
	        }
	        catch (Exception ex)
	        {
	            // Expected
	        }
	    }

	    private void Verify(FilterParamIndexBase index, long? testValue, bool[] expected)
	    {
	        testBean.SetLongBoxed(testValue);
	        index.MatchEvent(testEventBean, matchesList);
	        for (int i = 0; i < expected.Length; i++)
	        {
	            Assert.AreEqual(expected[i], testEvaluators[i].GetAndResetCountInvoked() == 1);
	        }
	    }
	}
} // End of namespace
