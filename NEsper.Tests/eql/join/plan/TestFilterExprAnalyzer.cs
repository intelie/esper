///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.eql.expression;
using net.esper.support.eql;
using net.esper.support.util;

namespace net.esper.eql.join.plan
{
	[TestFixture]
	public class TestFilterExprAnalyzer
	{
	    [Test]
	    public void testAnalyzeEquals()
	    {
	        ExprEqualsNode equalsNode = SupportExprNodeFactory.MakeEqualsNode();
	        equalsNode.DumpDebug("node...");

	        QueryGraph graph = new QueryGraph(2);
	        FilterExprAnalyzer.AnalyzeEqualsNode(equalsNode, graph);

	        Assert.IsTrue(graph.IsNavigable(0, 1));
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetKeyProperties(0, 1), new String[] {"intPrimitive"});
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetIndexProperties(1, 0), new String[] {"intPrimitive"});
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetKeyProperties(1, 0), new String[] {"intBoxed"});
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetIndexProperties(0, 1), new String[] {"intBoxed"});
	    }

	    [Test]
	    public void testAnalyzeAnd()
	    {
	        ExprAndNode andNode = SupportExprNodeFactory.Make2SubNodeAnd();
	        andNode.DumpDebug("node...");

	        QueryGraph graph = new QueryGraph(2);
	        FilterExprAnalyzer.AnalyzeAndNode(andNode, graph);

	        Assert.IsTrue(graph.IsNavigable(0, 1));
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetKeyProperties(0, 1), new String[] {"intPrimitive","string"});
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetIndexProperties(1, 0), new String[] {"intPrimitive","string"});
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetKeyProperties(1, 0), new String[] {"intBoxed","string"});
	        ArrayAssertionUtil.AreEqualExactOrder(graph.GetIndexProperties(0, 1), new String[] {"intBoxed","string"});
	    }
	}
} // End of namespace
