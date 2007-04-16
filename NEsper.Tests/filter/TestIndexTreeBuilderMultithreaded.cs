using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.filter
{

	[TestFixture]
    public class TestIndexTreeBuilderMultithreaded 
    {
        private List<FilterSpec> testFilterSpecs;
        private List<EventBean> matchedEvents;
        private List<EventBean> unmatchedEvents;

        private EventType eventType;

        private FilterCallbackSetNode topNode;
        private IndexTreeBuilder builder;
        private IList<FilterCallback> filterCallbacks;
        private IList<IndexTreePath> pathsAddedTo;

        [SetUp]
        public virtual void setUp()
        {
            builder = new IndexTreeBuilder();
            eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
            topNode = new FilterCallbackSetNode();
            filterCallbacks = new List<FilterCallback>();
            pathsAddedTo = new List<IndexTreePath>();

            testFilterSpecs = new List<FilterSpec>();
            matchedEvents = new List<EventBean>();
            unmatchedEvents = new List<EventBean>();

            // Any int and double value specified here must match only the current filter spec not any other filter spec
            testFilterSpecs.Add(makeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 100000 }));
            matchedEvents.Add(MakeEvent(9999999, -1));
            unmatchedEvents.Add(MakeEvent(0, -1));

            testFilterSpecs.Add(makeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 10, "doublePrimitive", FilterOperator.EQUAL, 0.5 }));
            matchedEvents.Add(MakeEvent(10, 0.5));
            unmatchedEvents.Add(MakeEvent(0, 0.5));

            testFilterSpecs.Add(makeSpec(new Object[] { "doublePrimitive", FilterOperator.EQUAL, 0.8 }));
            matchedEvents.Add(MakeEvent(-1, 0.8));
            unmatchedEvents.Add(MakeEvent(-1, 0.1));

            testFilterSpecs.Add(makeSpec(new Object[] { "doublePrimitive", FilterOperator.EQUAL, 99.99, "intPrimitive", FilterOperator.LESS, 1 }));
            matchedEvents.Add(MakeEvent(0, 99.99));
            unmatchedEvents.Add(MakeEvent(2, 0.5));

            testFilterSpecs.Add(makeSpec(new Object[] { "doublePrimitive", FilterOperator.GREATER, .99, "intPrimitive", FilterOperator.EQUAL, 5001 }));
            matchedEvents.Add(MakeEvent(5001, 1.1));
            unmatchedEvents.Add(MakeEvent(5002, 0.98));

            testFilterSpecs.Add(makeSpec(new Object[] { "intPrimitive", FilterOperator.LESS, -99000 }));
            matchedEvents.Add(MakeEvent(-99001, -1));
            unmatchedEvents.Add(MakeEvent(-98999, -1));

            testFilterSpecs.Add(makeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 11, "doublePrimitive", FilterOperator.GREATER, 888.0 }));
            matchedEvents.Add(MakeEvent(11, 888.001));
            unmatchedEvents.Add(MakeEvent(10, 888));

            testFilterSpecs.Add(makeSpec(new Object[] { "intPrimitive", FilterOperator.EQUAL, 973, "doublePrimitive", FilterOperator.EQUAL, 709.0 }));
            matchedEvents.Add(MakeEvent(973, 709));
            unmatchedEvents.Add(MakeEvent(0, 0.5));

            testFilterSpecs.Add(makeSpec(new Object[] { "intPrimitive", FilterOperator.EQUAL, 973, "doublePrimitive", FilterOperator.EQUAL, 655.0 }));
            matchedEvents.Add(MakeEvent(973, 655));
            unmatchedEvents.Add(MakeEvent(33838, 655.5));
        }

        [Test]
        public virtual void testVerifyFilterSpecSet()
        {
            // Add all the above filter definitions
            foreach (FilterSpec filterSpec in testFilterSpecs)
            {
                FilterValueSet filterValues = filterSpec.GetValueSet(null);
                FilterCallback callback = new SupportFilterCallback();
                filterCallbacks.Add(callback);
                pathsAddedTo.Add(builder.Add(filterValues, callback, topNode));
            }

            // None of the not-matching events should cause any match
            foreach (EventBean _event in unmatchedEvents)
            {
                IList<FilterCallback> matches = new List<FilterCallback>();
                topNode.MatchEvent(_event, matches);
                Assert.IsTrue(matches.Count == 0);
            }

            // All of the matching events should cause exactly one match
            foreach (EventBean _event in matchedEvents)
            {
                IList<FilterCallback> matches = new List<FilterCallback>();
                topNode.MatchEvent(_event, matches);
                Assert.IsTrue(matches.Count == 1);
            }

            // Remove all expressions previously added
            int count = 0;
            foreach (IndexTreePath treePath in pathsAddedTo)
            {
                FilterCallback callback = filterCallbacks[count++];
                builder.Remove(callback, treePath, topNode);
            }

            // After the remove no matches are expected
            foreach (EventBean _event in matchedEvents)
            {
                IList<FilterCallback> matches = new List<FilterCallback>();
                topNode.MatchEvent(_event, matches);
                Assert.IsTrue(matches.Count == 0);
            }
        }

        [Test]
        public virtual void testMultithreaded()
        {
            FilterCallbackSetNode topNode = new FilterCallbackSetNode();

            performMultithreadedTest(topNode, 2, 1000, 1);
            performMultithreadedTest(topNode, 3, 1000, 1);
            performMultithreadedTest(topNode, 4, 1000, 1);

            performMultithreadedTest(new FilterCallbackSetNode(), 2, 1000, 1);
            performMultithreadedTest(new FilterCallbackSetNode(), 3, 1000, 1);
            performMultithreadedTest(new FilterCallbackSetNode(), 4, 1000, 1);
        }

        private void performMultithreadedTest(FilterCallbackSetNode topNode, int numberOfThreads, int numberOfRunnables, int numberOfSecondsSleep)
        {
            log.Info(".performMultithreadedTest Loading thread pool work queue,numberOfRunnables=" + numberOfRunnables);

            IndexTreeBuilderRunnable[] runnables = new IndexTreeBuilderRunnable[numberOfRunnables];
            for (int i = 0; i < numberOfRunnables; i++)
            {
                runnables[i] = new IndexTreeBuilderRunnable(
                    topNode, testFilterSpecs, matchedEvents, unmatchedEvents);
            }

            for (int i = 0; i < numberOfRunnables; i++)
            {
                ThreadPool.QueueUserWorkItem(runnables[i].Run);
            }

            log.Info(".performMultithreadedTest Starting thread pool, threads=" + numberOfThreads);

            // Sleep X seconds
            sleep(numberOfSecondsSleep);

            int numberCompleted = 0 ;

            for (int i = 0; i < numberOfRunnables; i++)
            {
                if (runnables[i].IsCompleted)
                {
                    numberCompleted++;
                }
            }

            log.Info(
                ".performMultithreadedTest Completed," +
                "  numberOfRunnables=" + numberOfRunnables +
                "  numberOfThreads=" + numberOfThreads +
                "  completed=" + numberCompleted);

            for (int i = 0; i < numberOfRunnables; i++)
            {
                runnables[i].Join();

                Exception err = runnables[i].CaughtException;
                Assert.IsNull(err, "Runnable thread terminated with exception");
            }
        }

        private void sleep(int sec)
        {
            try
            {
                Thread.Sleep( sec * 1000 ) ;
            }
            catch (ThreadInterruptedException e)
            {
                log.Warn(e);
            }
        }

        private FilterSpec makeSpec(Object[] args)
        {
            return SupportFilterSpecBuilder.Build(eventType, args);
        }

        private EventBean MakeEvent(int aInt, double aDouble)
        {
            SupportBean bean = new SupportBean();
            bean.intPrimitive = aInt;
            bean.doublePrimitive = aDouble;
            return SupportEventBeanFactory.createObject(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
