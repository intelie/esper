using System;
using System.Collections.Generic;

using NUnit.Core;
using NUnit.Framework;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.eql.join.plan
{
    [TestFixture]
    public class TestNStreamQueryPlanBuilder
    {
        private QueryGraph queryGraph;

        [SetUp]
        public virtual void setUp()
        {
            queryGraph = new QueryGraph(5);
            queryGraph.Add(0, "p00", 1, "p10");
            queryGraph.Add(0, "p01", 2, "p20");
            queryGraph.Add(4, "p40", 3, "p30");
            queryGraph.Add(4, "p41", 3, "p31");
            queryGraph.Add(4, "p42", 2, "p21");
        }

        [Test]
        public virtual void testBuild()
        {
            QueryPlan plan = NStreamQueryPlanBuilder.Build(queryGraph);

            log.Debug(".testBuild plan=" + plan);
        }

        [Test]
        public virtual void testCreateStreamPlan()
        {
            QueryPlanIndex[] indexes = QueryPlanIndexBuilder.BuildIndexSpec(queryGraph);
            for (int i = 0; i < indexes.Length; i++)
            {
                log.Debug(".testCreateStreamPlan index " + i + " = " + indexes[i]);
            }

            QueryPlanNode plan = NStreamQueryPlanBuilder.CreateStreamPlan(0, new int[] { 2, 4, 3, 1 }, queryGraph, indexes);

            log.Debug(".testCreateStreamPlan plan=" + plan);

            Assert.IsTrue(plan is NestedIterationNode);
            NestedIterationNode nested = (NestedIterationNode)plan;
            TableLookupNode tableLookupSpec = (TableLookupNode)nested.ChildNodes[0];

            // Check lookup strategy for first lookup
            IndexedTableLookupPlan lookupStrategySpec = (IndexedTableLookupPlan)tableLookupSpec.LookupStrategySpec;
            Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<string>) lookupStrategySpec.KeyProperties,
                (ICollection<string>) new String[] { "p01" }
                ));
            Assert.AreEqual(0, lookupStrategySpec.LookupStream);
            Assert.AreEqual(2, lookupStrategySpec.IndexedStream);
            Assert.AreEqual(0, lookupStrategySpec.IndexNum);

            // Check lookup strategy for last lookup
            tableLookupSpec = (TableLookupNode)nested.ChildNodes[3];
            FullTableScanLookupPlan unkeyedSpecScan = (FullTableScanLookupPlan)tableLookupSpec.LookupStrategySpec;
            Assert.AreEqual(1, unkeyedSpecScan.IndexedStream);
            Assert.AreEqual(1, unkeyedSpecScan.IndexNum);
        }

        [Test]
        public virtual void testComputeBestPath()
        {
            NStreamQueryPlanBuilder.BestChainResult bestChain = NStreamQueryPlanBuilder.ComputeBestPath(0, queryGraph);
            Assert.AreEqual(3, bestChain.Depth);
            Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<int>) bestChain.Chain,
                (ICollection<int>) new int[] { 2, 4, 3, 1 }
                ));

            bestChain = NStreamQueryPlanBuilder.ComputeBestPath(3, queryGraph);
            Assert.AreEqual(4, bestChain.Depth);
            Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<int>)bestChain.Chain,
                (ICollection<int>)new int[] { 4, 2, 0, 1 }
                ));

            // try a stream that is not connected in any way
            queryGraph = new QueryGraph(6);
            bestChain = NStreamQueryPlanBuilder.ComputeBestPath(5, queryGraph);
            log.Debug(".testComputeBestPath bestChain=" + bestChain);
            Assert.AreEqual(0, bestChain.Depth);
            Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<int>)bestChain.Chain,
                (ICollection<int>)new int[] { 0, 1, 2, 3, 4 }
                ));
        }

        [Test]
        public virtual void testComputeNavigableDepth()
        {
            queryGraph.Add(3, "p30", 2, "p20");
            queryGraph.Add(2, "p30", 1, "p20");

            int depth = NStreamQueryPlanBuilder.ComputeNavigableDepth(0, new int[] { 1, 2, 3, 4 }, queryGraph);
            Assert.AreEqual(4, depth);

            depth = NStreamQueryPlanBuilder.ComputeNavigableDepth(0, new int[] { 4, 2, 3, 1 }, queryGraph);
            Assert.AreEqual(0, depth);

            depth = NStreamQueryPlanBuilder.ComputeNavigableDepth(4, new int[] { 3, 2, 1, 0 }, queryGraph);
            Assert.AreEqual(4, depth);

            depth = NStreamQueryPlanBuilder.ComputeNavigableDepth(1, new int[] { 0, 3, 4, 2 }, queryGraph);
            Assert.AreEqual(1, depth);
        }

        [Test]
        public virtual void testBuildDefaultNestingOrder()
		{
			int[] result = NStreamQueryPlanBuilder.BuildDefaultNestingOrder(4, 0);
			Assert.IsTrue( CollectionHelper.AreEqual(
                (ICollection<int>) result,
                (ICollection<int>) new int[]{1, 2, 3}
                ));
			
			result = NStreamQueryPlanBuilder.BuildDefaultNestingOrder(4, 1);
			Assert.IsTrue( CollectionHelper.AreEqual(
                (ICollection<int>) result,
                (ICollection<int>) new int[] { 0, 2, 3 }
                ));
			
			result = NStreamQueryPlanBuilder.BuildDefaultNestingOrder(4, 2);
			Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<int>) result,
                (ICollection<int>) new int[] { 0, 1, 3 }
                ));
			
			result = NStreamQueryPlanBuilder.BuildDefaultNestingOrder(4, 3);
			Assert.IsTrue(CollectionHelper.AreEqual(
                (ICollection<int>) result,
                (ICollection<int>) new int[] { 0, 1, 2 }
                ));
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
