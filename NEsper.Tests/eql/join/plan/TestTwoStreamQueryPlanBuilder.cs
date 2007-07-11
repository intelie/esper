///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.eql.join.plan;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.type;

namespace net.esper.eql.join.plan
{
	[TestFixture]
	public class TestTwoStreamQueryPlanBuilder
	{
	    private EventType[] typesPerStream;

	    [SetUp]
	    public void SetUp()
	    {
	        typesPerStream = new EventType[] {
	                SupportEventAdapterService.GetService().AddBeanType(typeof(SupportBean_S0).FullName, typeof(SupportBean_S0)),
	                SupportEventAdapterService.GetService().AddBeanType(typeof(SupportBean_S1).FullName, typeof(SupportBean_S1))
	        };
	    }

	    [Test]
	    public void testBuildNoOuter()
	    {
	        QueryGraph graph = MakeQueryGraph();
	        QueryPlan spec = TwoStreamQueryPlanBuilder.Build(typesPerStream, graph, null);

	        ArrayAssertionUtil.AreEqualExactOrder(spec.IndexSpecs[0].IndexProps[0], new String[] {"p01", "p02"});
	        ArrayAssertionUtil.AreEqualExactOrder(spec.IndexSpecs[1].IndexProps[0], new String[] {"p11", "p12"});
	        Assert.AreEqual(2, spec.ExecNodeSpecs.Length);
	    }

	    [Test]
	    public void testBuildOuter()
	    {
	        QueryGraph graph = MakeQueryGraph();
	        QueryPlan spec = TwoStreamQueryPlanBuilder.Build(typesPerStream, graph, OuterJoinType.LEFT);

	        ArrayAssertionUtil.AreEqualExactOrder(spec.IndexSpecs[0].IndexProps[0], new String[] {"p01", "p02"});
	        ArrayAssertionUtil.AreEqualExactOrder(spec.IndexSpecs[1].IndexProps[0], new String[] {"p11", "p12"});
	        Assert.AreEqual(2, spec.ExecNodeSpecs.Length);
	        Assert.AreEqual(typeof(TableOuterLookupNode), spec.ExecNodeSpecs[0].GetType());
	        Assert.AreEqual(typeof(TableLookupNode), spec.ExecNodeSpecs[1].GetType());
	    }

	    private QueryGraph MakeQueryGraph()
	    {
	        QueryGraph graph = new QueryGraph(2);
	        graph.Add(0, "p01", 1, "p11");
	        graph.Add(0, "p02", 1, "p12");
	        return graph;
	    }
	}
} // End of namespace
