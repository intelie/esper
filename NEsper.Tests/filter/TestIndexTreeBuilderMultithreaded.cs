///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Threading;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.filter
{
	[TestFixture]
	public class TestIndexTreeBuilderMultithreaded
	{
	    private IList<FilterSpecCompiled> testFilterSpecs;
	    private IList<EventBean> matchedEvents;
	    private IList<EventBean> unmatchedEvents;

	    private EventType eventType;

	    private FilterHandleSetNode topNode;
	    private IndexTreeBuilder builder;
	    private IList<FilterHandle> filterCallbacks;
	    private IList<IndexTreePath> pathsAddedTo;

	    [SetUp]
	    public void SetUp()
	    {
	        builder = new IndexTreeBuilder();
	        eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        topNode = new FilterHandleSetNode();
	        filterCallbacks = new List<FilterHandle>();
	        pathsAddedTo = new List<IndexTreePath>();

	        testFilterSpecs = new List<FilterSpecCompiled>();
	        matchedEvents = new List<EventBean>();
	        unmatchedEvents = new List<EventBean>();

	        // Any int and double value specified here must match only the current filter spec not any other filter spec
	        testFilterSpecs.Add(MakeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 100000 }));
	        matchedEvents.Add(MakeEvent(9999999, -1));
	        unmatchedEvents.Add(MakeEvent(0, -1));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 10,
	                                          "doublePrimitive", FilterOperator.EQUAL, 0.5}));
	        matchedEvents.Add(MakeEvent(10, 0.5));
	        unmatchedEvents.Add(MakeEvent(0, 0.5));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "doublePrimitive", FilterOperator.EQUAL, 0.8}));
	        matchedEvents.Add(MakeEvent(-1, 0.8));
	        unmatchedEvents.Add(MakeEvent(-1, 0.1));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "doublePrimitive", FilterOperator.EQUAL, 99.99,
	                                                     "intPrimitive", FilterOperator.LESS, 1}));
	        matchedEvents.Add(MakeEvent(0, 99.99));
	        unmatchedEvents.Add(MakeEvent(2, 0.5));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "doublePrimitive", FilterOperator.GREATER, .99,
	                                                     "intPrimitive", FilterOperator.EQUAL, 5001}));
	        matchedEvents.Add(MakeEvent(5001, 1.1));
	        unmatchedEvents.Add(MakeEvent(5002, 0.98));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "intPrimitive", FilterOperator.LESS, -99000}));
	        matchedEvents.Add(MakeEvent(-99001, -1));
	        unmatchedEvents.Add(MakeEvent(-98999, -1));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 11,
	                                          "doublePrimitive", FilterOperator.GREATER, 888.0}));
	        matchedEvents.Add(MakeEvent(11, 888.001));
	        unmatchedEvents.Add(MakeEvent(10, 888));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "intPrimitive", FilterOperator.EQUAL, 973,
	                                          "doublePrimitive", FilterOperator.EQUAL, 709.0}));
	        matchedEvents.Add(MakeEvent(973, 709));
	        unmatchedEvents.Add(MakeEvent(0, 0.5));

	        testFilterSpecs.Add(MakeSpec(new Object[] { "intPrimitive", FilterOperator.EQUAL, 973,
	                                          "doublePrimitive", FilterOperator.EQUAL, 655.0}));
	        matchedEvents.Add(MakeEvent(973, 655));
	        unmatchedEvents.Add(MakeEvent(33838, 655.5));
	    }

	    [Test]
	    public void testVerifyFilterSpecSet()
	    {
	        // Add all the above filter definitions
	        foreach (FilterSpecCompiled filterSpec in testFilterSpecs)
	        {
	            FilterValueSet filterValues = filterSpec.GetValueSet(null);
	            FilterHandle callback = new SupportFilterHandle();
	            filterCallbacks.Add(callback);
	            pathsAddedTo.Add(builder.Add(filterValues, callback, topNode));
	        }

	        // None of the not-matching events should cause any match
	        foreach (EventBean _event in unmatchedEvents)
	        {
	            List<FilterHandle> matches = new List<FilterHandle>();
	            topNode.MatchEvent(_event, matches);
	            Assert.IsTrue(matches.Count == 0);
	        }

	        // All of the matching events should cause exactly one match
	        foreach (EventBean _event in matchedEvents)
	        {
	            List<FilterHandle> matches = new List<FilterHandle>();
	            topNode.MatchEvent(_event, matches);
	            Assert.IsTrue(matches.Count == 1);
	        }

	        // Remove all expressions previously added
	        int count = 0;
	        foreach (IndexTreePath treePath in pathsAddedTo)
	        {
	            FilterHandle callback = filterCallbacks[count++];
	            builder.Remove(callback, treePath, topNode);
	        }

	        // After the remove no matches are expected
	        foreach (EventBean _event in matchedEvents)
	        {
	            List<FilterHandle> matches = new List<FilterHandle>();
	            topNode.MatchEvent(_event, matches);
	            Assert.IsTrue(matches.Count == 0);
	        }
	    }

	    [Test]
	    public void testMultithreaded()
	    {
	        FilterHandleSetNode topNode = new FilterHandleSetNode();

	        PerformMultithreadedTest(topNode, 2, 1000, 1);
	        PerformMultithreadedTest(topNode, 3, 1000, 1);
	        PerformMultithreadedTest(topNode, 4, 1000, 1);

	        PerformMultithreadedTest(new FilterHandleSetNode(), 2, 1000, 1);
	        PerformMultithreadedTest(new FilterHandleSetNode(), 3, 1000, 1);
	        PerformMultithreadedTest(new FilterHandleSetNode(), 4, 1000, 1);
	    }

	    private void PerformMultithreadedTest(FilterHandleSetNode topNode,
	                             int numberOfThreads,
	                             int numberOfRunnables,
	                             int numberOfSecondsSleep)
	    {
	        log.Info(".performMultithreadedTest Loading thread pool work queue,numberOfRunnables=" + numberOfRunnables);

	        ExecutorService pool = Executors.NewFixedThreadPool(numberOfThreads);

	        for (int i = 0; i < numberOfRunnables; i++)
	        {
	            IndexTreeBuilderRunnable runnable = new IndexTreeBuilderRunnable(eventType, topNode,
	                    testFilterSpecs, matchedEvents, unmatchedEvents);
	            pool.Submit(runnable);
	        }

	        log.Info(".performMultithreadedTest Starting thread pool, threads=" + numberOfThreads);
	        //pool.SetCorePoolSize(numberOfThreads);

	        // Sleep X seconds
	        Sleep(numberOfSecondsSleep);

	        log.Info(".performMultithreadedTest Completed, numberOfRunnables=" + numberOfRunnables +
	                 "  numberOfThreads=" + numberOfThreads +
	                 "  completed=" + pool.NumExecuted);

	        pool.Shutdown();
	        pool.AwaitTermination(new TimeSpan(0, 0, 1));

	        Assert.IsTrue(pool.NumExecuted == numberOfRunnables);
	    }

	    private void Sleep(int sec)
	    {
            Thread.Sleep(sec*1000);
	    }

	    private FilterSpecCompiled MakeSpec(Object[] args)
	    {
	        return SupportFilterSpecBuilder.Build(eventType, args);
	    }

	    private EventBean MakeEvent(int aInt, double aDouble)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(aInt);
	        bean.SetDoublePrimitive(aDouble);
	        return SupportEventBeanFactory.CreateObject(bean);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
