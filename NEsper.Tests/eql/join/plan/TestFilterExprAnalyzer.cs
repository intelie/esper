using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.support.eql;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	
	[TestFixture]
	public class TestFilterExprAnalyzer 
	{
		[Test]
		public virtual void  testAnalyzeEquals()
		{
			ExprEqualsNode EqualsNode = SupportExprNodeFactory.makeEqualsNode();
			EqualsNode.DumpDebug("node...");
			
			QueryGraph graph = new QueryGraph(2);
			FilterExprAnalyzer.AnalyzeEqualsNode(EqualsNode, graph);
			
			Assert.IsTrue(graph.IsNavigable(0, 1));
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetKeyProperties(0, 1),
                (ICollection<string>) new String[] { "intPrimitive" });
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetIndexProperties(1, 0),
                (ICollection<string>) new String[] { "intPrimitive" });
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetKeyProperties(1, 0),
                (ICollection<string>) new String[] { "intBoxed" });
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetIndexProperties(0, 1),
                (ICollection<string>) new String[] { "intBoxed" });
		}
		
		[Test]
		public virtual void  testAnalyzeAnd()
		{
			ExprAndNode andNode = SupportExprNodeFactory.make2SubNodeAnd();
			andNode.DumpDebug("node...");
			
			QueryGraph graph = new QueryGraph(2);
			FilterExprAnalyzer.AnalyzeAndNode(andNode, graph);
			
			Assert.IsTrue(graph.IsNavigable(0, 1));
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetKeyProperties(0, 1),
                (ICollection<string>)new String[] { "intPrimitive", "str" });
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetIndexProperties(1, 0),
                (ICollection<string>)new String[] { "intPrimitive", "str" });
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetKeyProperties(1, 0),
                (ICollection<string>)new String[] { "intBoxed", "str" });
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) graph.GetIndexProperties(0, 1),
                (ICollection<string>)new String[] { "intBoxed", "str" });
		}
	}
}
