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

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

namespace net.esper.filter
{
	[TestFixture]
	public class TestIndexTreeBuilder
	{
	    IList<FilterHandle> matches;
	    IndexTreeBuilder builder;
	    EventBean _eventBean;
	    EventType eventType;
	    FilterHandle[] testFilterCallback;

	    [SetUp]
	    public void SetUp()
	    {
	        PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;   

	        SupportBean testBean = new SupportBean();
	        testBean.SetIntPrimitive(50);
	        testBean.SetDoublePrimitive(0.5);
	        testBean.SetString("jack");
	        testBean.SetLongPrimitive(10);
	        testBean.SetShortPrimitive((short) 20);

	        builder = new IndexTreeBuilder();
	        _eventBean = SupportEventBeanFactory.CreateObject(testBean);
	        eventType = _eventBean.EventType;

	        matches = new List<FilterHandle>();

	        // Allocate a couple of callbacks
	        testFilterCallback = new SupportFilterHandle[20];
	        for (int i = 0; i < testFilterCallback.Length; i++)
	        {
	            testFilterCallback[i] = new SupportFilterHandle();
	        }
	    }

	    [Test]
	    public void testCopyParameters()
	    {
	        FilterValueSet spec = MakeFilterValues(
	                "doublePrimitive", FilterOperator.LESS, 1.1,
	                "doubleBoxed", FilterOperator.LESS, 1.1,
	                "intPrimitive", FilterOperator.EQUAL, 1,
	                "string", FilterOperator.EQUAL, "jack",
	                "intBoxed", FilterOperator.EQUAL, 2,
	                "floatBoxed", FilterOperator.RANGE_CLOSED, 1.1d, 2.2d);

	        TreeSet<FilterValueSetParam> copy = IndexTreeBuilder.CopySortParameters(spec.Parameters);

	        Assert.IsTrue(copy.First.PropertyName.Equals("intBoxed"));
	        copy.Remove(copy.First);

	        Assert.IsTrue(copy.First.PropertyName.Equals("intPrimitive"));
	        copy.Remove(copy.First);

	        Assert.IsTrue(copy.First.PropertyName.Equals("string"));
	        copy.Remove(copy.First);

	        Assert.IsTrue(copy.First.PropertyName.Equals("floatBoxed"));
	        copy.Remove(copy.First);

	        Assert.IsTrue(copy.First.PropertyName.Equals("doubleBoxed"));
	        copy.Remove(copy.First);

	        Assert.IsTrue(copy.First.PropertyName.Equals("doublePrimitive"));
	        copy.Remove(copy.First);
	    }

	    [Test]
	    public void testBuildWithMatch()
	    {
	        FilterHandleSetNode topNode = new FilterHandleSetNode();

	        // Add some parameter-less expression
	        FilterValueSet filterSpec = MakeFilterValues();
	        builder.Add(filterSpec, testFilterCallback[0], topNode);
	        Assert.IsTrue(topNode.Contains(testFilterCallback[0]));

	        // Attempt a match
	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 1);
	        matches.Clear();

	        // Add a filter that won't match, with a single parameter matching against an int
	        filterSpec = MakeFilterValues("intPrimitive", FilterOperator.EQUAL, 100);
	        builder.Add(filterSpec, testFilterCallback[1], topNode);
	        Assert.IsTrue(topNode.Indizes.Count == 1);
	        Assert.IsTrue(topNode.Indizes[0].Count == 1);

	        // Match again
	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 1);
	        matches.Clear();

	        // Add a filter that will match
	        filterSpec = MakeFilterValues("intPrimitive", FilterOperator.EQUAL, 50);
	        builder.Add(filterSpec, testFilterCallback[2], topNode);
	        Assert.IsTrue(topNode.Indizes.Count == 1);
	        Assert.IsTrue(topNode.Indizes[0].Count == 2);

	        // match
	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 2);
	        matches.Clear();

	        // Add some filter against a double
	        filterSpec = MakeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1);
	        builder.Add(filterSpec, testFilterCallback[3], topNode);
	        Assert.IsTrue(topNode.Indizes.Count == 2);
	        Assert.IsTrue(topNode.Indizes[0].Count == 2);
	        Assert.IsTrue(topNode.Indizes[1].Count == 1);

	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 3);
	        matches.Clear();

	        filterSpec = MakeFilterValues("doublePrimitive", FilterOperator.LESS_OR_EQUAL, 0.5);
	        builder.Add(filterSpec, testFilterCallback[4], topNode);
	        Assert.IsTrue(topNode.Indizes.Count == 3);
	        Assert.IsTrue(topNode.Indizes[0].Count == 2);
	        Assert.IsTrue(topNode.Indizes[1].Count == 1);
	        Assert.IsTrue(topNode.Indizes[2].Count == 1);

	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 4);
	        matches.Clear();

	        // Add an filterSpec against double and string
	        filterSpec = MakeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1,
	                                    "string", FilterOperator.EQUAL, "jack");
	        builder.Add(filterSpec, testFilterCallback[5], topNode);
	        Assert.IsTrue(topNode.Indizes.Count == 3);
	        Assert.IsTrue(topNode.Indizes[0].Count == 2);
	        Assert.IsTrue(topNode.Indizes[1].Count == 1);
	        Assert.IsTrue(topNode.Indizes[2].Count == 1);
	        FilterHandleSetNode nextLevelSetNode = (FilterHandleSetNode) topNode.Indizes[1][1.1];
	        Assert.IsTrue(nextLevelSetNode != null);
	        Assert.IsTrue(nextLevelSetNode.Indizes.Count == 1);

	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 5);
	        matches.Clear();

	        filterSpec = MakeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1,
	                                    "string", FilterOperator.EQUAL, "beta");
	        builder.Add(filterSpec, testFilterCallback[6], topNode);

	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 5);
	        matches.Clear();

	        filterSpec = MakeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1,
	                                    "string", FilterOperator.EQUAL, "jack");
	        builder.Add(filterSpec, testFilterCallback[7], topNode);
	        Assert.IsTrue(nextLevelSetNode.Indizes.Count == 1);
	        FilterHandleSetNode nodeTwo = (FilterHandleSetNode) nextLevelSetNode.Indizes[0]["jack"];
	        Assert.IsTrue(nodeTwo.FilterCallbackCount == 2);

	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 6);
	        matches.Clear();

	        // Try depth first
	        filterSpec = MakeFilterValues("string", FilterOperator.EQUAL, "jack",
	                                    "longPrimitive", FilterOperator.EQUAL, 10L,
	                                    "shortPrimitive", FilterOperator.EQUAL, (short) 20);
	        builder.Add(filterSpec, testFilterCallback[8], topNode);

	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 7);
	        matches.Clear();

	        // Add an filterSpec in the middle
	        filterSpec = MakeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L,
	                                    "string", FilterOperator.EQUAL, "jack");
	        builder.Add(filterSpec, testFilterCallback[9], topNode);

	        filterSpec = MakeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L,
	                                    "string", FilterOperator.EQUAL, "jim");
	        builder.Add(filterSpec, testFilterCallback[10], topNode);

	        filterSpec = MakeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L,
	                                    "string", FilterOperator.EQUAL, "joe");
	        builder.Add(filterSpec, testFilterCallback[11], topNode);

	        topNode.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 8);
	        matches.Clear();
	    }

	    [Test]
	    public void testBuildMatchRemove()
	    {
	        FilterHandleSetNode top = new FilterHandleSetNode();

	        // Add a parameter-less filter
	        FilterValueSet filterSpecNoParams = MakeFilterValues();
	        IndexTreePath pathAddedTo = builder.Add(filterSpecNoParams, testFilterCallback[0], top);

	        // Try a match
	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 1);
	        matches.Clear();

	        // Remove filter
	        builder.Remove(testFilterCallback[0], pathAddedTo, top);

	        // Match should not be found
	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 0);
	        matches.Clear();

	        // Add a depth-first filterSpec
	        FilterValueSet filterSpecOne = MakeFilterValues(
	                "string", FilterOperator.EQUAL, "jack",
	                "longPrimitive", FilterOperator.EQUAL, 10L,
	                "shortPrimitive", FilterOperator.EQUAL, (short) 20);
	        IndexTreePath pathAddedToOne = builder.Add(filterSpecOne, testFilterCallback[1], top);

	        FilterValueSet filterSpecTwo = MakeFilterValues(
	                "string", FilterOperator.EQUAL, "jack",
	                "longPrimitive", FilterOperator.EQUAL, 10L,
	                "shortPrimitive", FilterOperator.EQUAL, (short) 20);
	        IndexTreePath pathAddedToTwo = builder.Add(filterSpecTwo, testFilterCallback[2], top);

	        FilterValueSet filterSpecThree = MakeFilterValues(
	                "string", FilterOperator.EQUAL, "jack",
	                "longPrimitive", FilterOperator.EQUAL, 10L);
	        IndexTreePath pathAddedToThree = builder.Add(filterSpecThree, testFilterCallback[3], top);

	        FilterValueSet filterSpecFour = MakeFilterValues(
	                "string", FilterOperator.EQUAL, "jack");
	        IndexTreePath pathAddedToFour = builder.Add(filterSpecFour, testFilterCallback[4], top);

	        FilterValueSet filterSpecFive = MakeFilterValues(
	                "longPrimitive", FilterOperator.EQUAL, 10L);
	        IndexTreePath pathAddedToFive = builder.Add(filterSpecFive, testFilterCallback[5], top);

	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 5);
	        matches.Clear();

	        // Remove some of the nodes
	        builder.Remove(testFilterCallback[2], pathAddedToTwo, top);

	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 4);
	        matches.Clear();

	        // Remove some of the nodes
	        builder.Remove(testFilterCallback[4], pathAddedToFour, top);

	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 3);
	        matches.Clear();

	        // Remove some of the nodes
	        builder.Remove(testFilterCallback[5], pathAddedToFive, top);

	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 2);
	        matches.Clear();

	        // Remove some of the nodes
	        builder.Remove(testFilterCallback[1], pathAddedToOne, top);

	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 1);
	        matches.Clear();

	        // Remove some of the nodes
	        builder.Remove(testFilterCallback[3], pathAddedToThree, top);

	        top.MatchEvent(_eventBean, matches);
	        Assert.IsTrue(matches.Count == 0);
	        matches.Clear();
	    }

	    private FilterValueSet MakeFilterValues(params Object[] filterSpecArgs)
	    {
	        FilterSpecCompiled spec = SupportFilterSpecBuilder.Build(eventType, filterSpecArgs);
	        FilterValueSet filterValues = spec.GetValueSet(null);
	        return filterValues;
	    }
	}
} // End of namespace
