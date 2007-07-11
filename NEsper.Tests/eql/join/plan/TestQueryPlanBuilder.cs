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

using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.type;

namespace net.esper.eql.join.plan
{
	[TestFixture]
	public class TestQueryPlanBuilder
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
	    public void testGetPlan()
	    {
	        IList<OuterJoinDesc> descList = new List<OuterJoinDesc>();
	        OuterJoinDesc joinDesc = SupportOuterJoinDescFactory.MakeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT);
	        descList.Add(joinDesc);

	        QueryPlan plan = QueryPlanBuilder.GetPlan(typesPerStream, new List<OuterJoinDesc>(), null, null);
	        AssertPlan(plan);

	        plan = QueryPlanBuilder.GetPlan(typesPerStream, descList, null, null);
	        AssertPlan(plan);

	        plan = QueryPlanBuilder.GetPlan(typesPerStream, descList, SupportExprNodeFactory.MakeEqualsNode(), null);
	        AssertPlan(plan);

	        plan = QueryPlanBuilder.GetPlan(typesPerStream, new List<OuterJoinDesc>(), SupportExprNodeFactory.MakeEqualsNode(), null);
	        AssertPlan(plan);
	    }

	    private static void AssertPlan(QueryPlan plan)
	    {
	        Assert.AreEqual(2, plan.ExecNodeSpecs.Length);
	        Assert.AreEqual(2, plan.ExecNodeSpecs.Length);
	    }
	}
} // End of namespace
