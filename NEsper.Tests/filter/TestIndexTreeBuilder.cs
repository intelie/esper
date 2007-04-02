using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	[TestFixture]
    public class TestIndexTreeBuilder 
    {
        IList<FilterCallback> matches;
        internal IndexTreeBuilder builder;
        internal EventBean eventBean;
        internal EventType eventType;
        internal FilterCallback[] testFilterCallback;

        [SetUp]
        public virtual void setUp()
        {
            SupportBean testBean = new SupportBean();
            testBean.intPrimitive = 50;
            testBean.doublePrimitive = 0.5;
            testBean.str = "jack";
            testBean.longPrimitive = 10;
            testBean.shortPrimitive = (short)20;

            builder = new IndexTreeBuilder();
            eventBean = SupportEventBeanFactory.createObject(testBean);
            eventType = eventBean.EventType;

            matches = new List<FilterCallback>();

            // Allocate a couple of callbacks
            testFilterCallback = new SupportFilterCallback[20];
            for (int i = 0; i < testFilterCallback.Length; i++)
            {
                testFilterCallback[i] = new SupportFilterCallback();
            }
        }

        [Test]
        public virtual void testCopyParameters()
        {
		        FilterValueSet spec = makeFilterValues(
		                "doublePrimitive", FilterOperator.LESS, 1.1,
		                "doubleBoxed", FilterOperator.LESS, 1.1,
		                "intPrimitive", FilterOperator.EQUAL, 1,
		                "str", FilterOperator.EQUAL, "jack",
		                "intBoxed", FilterOperator.EQUAL, 2,
		                "floatBoxed", FilterOperator.RANGE_CLOSED, 1.1d, 2.2d);

		        ETreeSet<FilterValueSetParam> copy = IndexTreeBuilder.CopySortParameters(spec.Parameters);

            Assert.IsTrue(copy.First.PropertyName.Equals("intBoxed"));
            copy.Remove(copy.First);

            Assert.IsTrue(copy.First.PropertyName.Equals("intPrimitive"));
            copy.Remove(copy.First);

            Assert.IsTrue(copy.First.PropertyName.Equals("str"));
            copy.Remove(copy.First);

            Assert.IsTrue(copy.First.PropertyName.Equals("floatBoxed"));
            copy.Remove(copy.First);

            Assert.IsTrue(copy.First.PropertyName.Equals("doubleBoxed"));
            copy.Remove(copy.First);

            Assert.IsTrue(copy.First.PropertyName.Equals("doublePrimitive"));
            copy.Remove(copy.First);
        }

        [Test]
        public virtual void testBuildWithMatch()
        {
            FilterCallbackSetNode topNode = new FilterCallbackSetNode();

            // Add some parameter-less expression
            FilterValueSet filterSpec = makeFilterValues();
            builder.Add(filterSpec, testFilterCallback[0], topNode);
            Assert.IsTrue(topNode.Contains(testFilterCallback[0]));

            // Attempt a match
            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 1);
            matches.Clear();

            // Add a filter that won't match, with a single parameter matching against an int
            filterSpec = makeFilterValues("intPrimitive", FilterOperator.EQUAL, 100);
            builder.Add(filterSpec, testFilterCallback[1], topNode);
            Assert.IsTrue(topNode.Indizes.Count == 1);
            Assert.IsTrue(topNode.Indizes[0].Count == 1);

            // Match again
            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 1);
            matches.Clear();

            // Add a filter that will match
            filterSpec = makeFilterValues("intPrimitive", FilterOperator.EQUAL, 50);
            builder.Add(filterSpec, testFilterCallback[2], topNode);
            Assert.IsTrue(topNode.Indizes.Count == 1);
            Assert.IsTrue(topNode.Indizes[0].Count == 2);

            // match
            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 2);
            matches.Clear();

            // Add some filter against a double
            filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1);
            builder.Add(filterSpec, testFilterCallback[3], topNode);
            Assert.IsTrue(topNode.Indizes.Count == 2);
            Assert.IsTrue(topNode.Indizes[0].Count == 2);
            Assert.IsTrue(topNode.Indizes[1].Count == 1);

            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 3);
            matches.Clear();

            filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS_OR_EQUAL, 0.5);
            builder.Add(filterSpec, testFilterCallback[4], topNode);
            Assert.IsTrue(topNode.Indizes.Count == 3);
            Assert.IsTrue(topNode.Indizes[0].Count == 2);
            Assert.IsTrue(topNode.Indizes[1].Count == 1);
            Assert.IsTrue(topNode.Indizes[2].Count == 1);

            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 4);
            matches.Clear();

            // Add an filterSpec against double and string
            filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1, "str", FilterOperator.EQUAL, "jack");
            builder.Add(filterSpec, testFilterCallback[5], topNode);
            Assert.IsTrue(topNode.Indizes.Count == 3);
            Assert.IsTrue(topNode.Indizes[0].Count == 2);
            Assert.IsTrue(topNode.Indizes[1].Count == 1);
            Assert.IsTrue(topNode.Indizes[2].Count == 1);
            FilterCallbackSetNode nextLevelSetNode = (FilterCallbackSetNode)topNode.Indizes[1][1.1];
            Assert.IsTrue(nextLevelSetNode != null);
            Assert.IsTrue(nextLevelSetNode.Indizes.Count == 1);

            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 5);
            matches.Clear();

            filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1, "str", FilterOperator.EQUAL, "beta");
            builder.Add(filterSpec, testFilterCallback[6], topNode);

            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 5);
            matches.Clear();

            filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1, "str", FilterOperator.EQUAL, "jack");
            builder.Add(filterSpec, testFilterCallback[7], topNode);
            Assert.IsTrue(nextLevelSetNode.Indizes.Count == 1);
            FilterCallbackSetNode nodeTwo = (FilterCallbackSetNode)nextLevelSetNode.Indizes[0]["jack"];
            Assert.IsTrue(nodeTwo.FilterCallbackCount == 2);

            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 6);
            matches.Clear();

            // Try depth first
            filterSpec = makeFilterValues("str", FilterOperator.EQUAL, "jack", "longPrimitive", FilterOperator.EQUAL, 10L, "shortPrimitive", FilterOperator.EQUAL, (short)20);
            builder.Add(filterSpec, testFilterCallback[8], topNode);

            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 7);
            matches.Clear();

            // Add an filterSpec in the middle
            filterSpec = makeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L, "str", FilterOperator.EQUAL, "jack");
            builder.Add(filterSpec, testFilterCallback[9], topNode);

            filterSpec = makeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L, "str", FilterOperator.EQUAL, "jim");
            builder.Add(filterSpec, testFilterCallback[10], topNode);

            filterSpec = makeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L, "str", FilterOperator.EQUAL, "joe");
            builder.Add(filterSpec, testFilterCallback[11], topNode);

            topNode.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 8);
            matches.Clear();
        }

        [Test]
        public virtual void testBuildMatchRemove()
        {
            FilterCallbackSetNode top = new FilterCallbackSetNode();

            // Add a parameter-less filter
            FilterValueSet filterSpecNoParams = makeFilterValues();
            IndexTreePath pathAddedTo = builder.Add(filterSpecNoParams, testFilterCallback[0], top);

            // Try a match
            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 1);
            matches.Clear();

            // Remove filter
            builder.Remove(testFilterCallback[0], pathAddedTo, top);

            // Match should not be found
            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 0);
            matches.Clear();

            // Add a depth-first filterSpec
            FilterValueSet filterSpecOne = makeFilterValues("str", FilterOperator.EQUAL, "jack", "longPrimitive", FilterOperator.EQUAL, 10L, "shortPrimitive", FilterOperator.EQUAL, (short)20);
            IndexTreePath pathAddedToOne = builder.Add(filterSpecOne, testFilterCallback[1], top);

            FilterValueSet filterSpecTwo = makeFilterValues("str", FilterOperator.EQUAL, "jack", "longPrimitive", FilterOperator.EQUAL, 10L, "shortPrimitive", FilterOperator.EQUAL, (short)20);
            IndexTreePath pathAddedToTwo = builder.Add(filterSpecTwo, testFilterCallback[2], top);

            FilterValueSet filterSpecThree = makeFilterValues("str", FilterOperator.EQUAL, "jack", "longPrimitive", FilterOperator.EQUAL, 10L);
            IndexTreePath pathAddedToThree = builder.Add(filterSpecThree, testFilterCallback[3], top);

            FilterValueSet filterSpecFour = makeFilterValues("str", FilterOperator.EQUAL, "jack");
            IndexTreePath pathAddedToFour = builder.Add(filterSpecFour, testFilterCallback[4], top);

            FilterValueSet filterSpecFive = makeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L);
            IndexTreePath pathAddedToFive = builder.Add(filterSpecFive, testFilterCallback[5], top);

            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 5);
            matches.Clear();

            // Remove some of the nodes
            builder.Remove(testFilterCallback[2], pathAddedToTwo, top);

            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 4);
            matches.Clear();

            // Remove some of the nodes
            builder.Remove(testFilterCallback[4], pathAddedToFour, top);

            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 3);
            matches.Clear();

            // Remove some of the nodes
            builder.Remove(testFilterCallback[5], pathAddedToFive, top);

            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 2);
            matches.Clear();

            // Remove some of the nodes
            builder.Remove(testFilterCallback[1], pathAddedToOne, top);

            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 1);
            matches.Clear();

            // Remove some of the nodes
            builder.Remove(testFilterCallback[3], pathAddedToThree, top);

            top.MatchEvent(eventBean, matches);
            Assert.IsTrue(matches.Count == 0);
            matches.Clear();
        }

        private FilterValueSet makeFilterValues(params object[] filterSpecArgs)
        {
            FilterSpec spec = SupportFilterSpecBuilder.Build(eventType, filterSpecArgs);
            FilterValueSet filterValues = spec.GetValueSet(null);
            return filterValues;
        }
    }
}
