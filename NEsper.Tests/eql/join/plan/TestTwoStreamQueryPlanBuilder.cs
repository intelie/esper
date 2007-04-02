using System;
using System.Collections.Generic;

using net.esper.support.util;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	
	[TestFixture]
	public class TestTwoStreamQueryPlanBuilder 
	{
		[Test]
		public virtual void  testBuildNoOuter()
		{
			QueryGraph graph = makeQueryGraph();
			QueryPlan spec = TwoStreamQueryPlanBuilder.Build(graph, null);
			
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) spec.IndexSpecs[0].IndexProps[0],
                (ICollection<string>) new String[] { "p01", "p02" });
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) spec.IndexSpecs[1].IndexProps[0],
                (ICollection<string>) new String[] { "p11", "p12" });
			Assert.AreEqual(2, spec.ExecNodeSpecs.Length);
		}
		
		[Test]
		public virtual void  testBuildOuter()
		{
			QueryGraph graph = makeQueryGraph();
			QueryPlan spec = TwoStreamQueryPlanBuilder.Build(graph, OuterJoinType.LEFT);
			
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) spec.IndexSpecs[0].IndexProps[0],
                (ICollection<string>) new String[]{"p01", "p02"});
			ArrayAssertionUtil.assertEqualsExactOrder(
                (ICollection<string>) spec.IndexSpecs[1].IndexProps[0],
                (ICollection<string>) new String[] { "p11", "p12" });
			Assert.AreEqual(2, spec.ExecNodeSpecs.Length);
			Assert.AreEqual(typeof(TableOuterLookupNode), spec.ExecNodeSpecs[0].GetType());
            Assert.AreEqual(typeof(TableLookupNode), spec.ExecNodeSpecs[1].GetType());
		}
		
		private QueryGraph makeQueryGraph()
		{
			QueryGraph graph = new QueryGraph(2);
			graph.Add(0, "p01", 1, "p11");
			graph.Add(0, "p02", 1, "p12");
			return graph;
		}
	}
}
