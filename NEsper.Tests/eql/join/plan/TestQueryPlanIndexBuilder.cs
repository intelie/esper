using System;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{
	
	[TestFixture]
	public class TestQueryPlanIndexBuilder 
	{
		private QueryGraph queryGraph;
		
		[SetUp]
		public virtual void  setUp()
		{
			queryGraph = new QueryGraph(5);
			queryGraph.Add(0, "p00", 1, "p10");
			queryGraph.Add(0, "p01", 2, "p20");
			queryGraph.Add(4, "p40", 3, "p30");
			queryGraph.Add(4, "p41", 3, "p31");
			queryGraph.Add(4, "p42", 2, "p21");
		}
		
		[Test]
		public virtual void  testBuildIndexSpec()
		{
			QueryPlanIndex[] indexes = QueryPlanIndexBuilder.buildIndexSpec(queryGraph);
			
			String[][] expected = new String[][]{new String[]{"p00"}, new String[]{"p01"}};
			ArrayAssertionUtil.assertEqualsStringArr(indexes[0].IndexProps, expected);
			
			expected = new String[][]{new String[]{"p10"}};
            ArrayAssertionUtil.assertEqualsStringArr(indexes[1].IndexProps, expected);
			
			expected = new String[][]{new String[]{"p20"}, new String[]{"p21"}};
            ArrayAssertionUtil.assertEqualsStringArr(indexes[2].IndexProps, expected);
			
			expected = new String[][]{new String[]{"p30", "p31"}};
            ArrayAssertionUtil.assertEqualsStringArr(indexes[3].IndexProps, expected);
			
			expected = new String[][]{new String[]{"p42"}, new String[]{"p40", "p41"}};
            ArrayAssertionUtil.assertEqualsStringArr(indexes[4].IndexProps, expected);
			
			// Test no index, should have a single entry with a zero-length property name array
			queryGraph = new QueryGraph(3);
			indexes = QueryPlanIndexBuilder.buildIndexSpec(queryGraph);
			Assert.AreEqual(1, indexes[1].IndexProps.Length);
			Assert.AreEqual(0, indexes[1].IndexProps[0].Length);
		}
		
		[Test]
		public virtual void  testIndexAlreadyExists()
		{
			queryGraph = new QueryGraph(5);
			queryGraph.Add(0, "p00", 1, "p10");
			queryGraph.Add(0, "p00", 2, "p20");
			
			QueryPlanIndex[] indexes = QueryPlanIndexBuilder.buildIndexSpec(queryGraph);
			
			String[][] expected = new String[][]{new String[]{"p00"}};
			ArrayAssertionUtil.assertEqualsStringArr(indexes[0].IndexProps, expected);
		}
	}
}
