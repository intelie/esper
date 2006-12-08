package net.esper.eql.join.plan;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestNStreamQueryPlanBuilder extends TestCase
{
    private QueryGraph queryGraph;

    public void setUp()
    {
        queryGraph = new QueryGraph(5);
        queryGraph.add(0, "p00", 1, "p10");
        queryGraph.add(0, "p01", 2, "p20");
        queryGraph.add(4, "p40", 3, "p30");
        queryGraph.add(4, "p41", 3, "p31");
        queryGraph.add(4, "p42", 2, "p21");
    }

    public void testBuild()
    {
        QueryPlan plan = NStreamQueryPlanBuilder.build(queryGraph);

        log.debug(".testBuild plan=" + plan);
    }

    public void testCreateStreamPlan()
    {
        QueryPlanIndex[] indexes = QueryPlanIndexBuilder.buildIndexSpec(queryGraph);
        for (int i = 0; i < indexes.length; i++)
        {
            log.debug(".testCreateStreamPlan index " + i + " = " + indexes[i]);
        }

        QueryPlanNode plan = NStreamQueryPlanBuilder.createStreamPlan(0, new int[] {2, 4, 3, 1}, queryGraph,indexes);

        log.debug(".testCreateStreamPlan plan=" + plan);

        assertTrue(plan instanceof NestedIterationNode);
        NestedIterationNode nested = (NestedIterationNode) plan;
        TableLookupNode tableLookupSpec = (TableLookupNode) nested.getChildNodes().get(0);

        // Check lookup strategy for first lookup
        IndexedTableLookupPlan lookupStrategySpec = (IndexedTableLookupPlan) tableLookupSpec.getLookupStrategySpec();
        assertTrue(Arrays.equals(lookupStrategySpec.getKeyProperties(), new String[] {"p01"} ));
        assertEquals(0, lookupStrategySpec.getLookupStream());
        assertEquals(2, lookupStrategySpec.getIndexedStream());
        assertEquals(0, lookupStrategySpec.getIndexNum());

        // Check lookup strategy for last lookup
        tableLookupSpec = (TableLookupNode) nested.getChildNodes().get(3);
        FullTableScanLookupPlan unkeyedSpecScan = (FullTableScanLookupPlan) tableLookupSpec.getLookupStrategySpec();
        assertEquals(1, unkeyedSpecScan.getIndexedStream());
        assertEquals(1, unkeyedSpecScan.getIndexNum());
    }

    public void testComputeBestPath()
    {
        NStreamQueryPlanBuilder.BestChainResult bestChain = NStreamQueryPlanBuilder.computeBestPath(0, queryGraph);
        assertEquals(3, bestChain.getDepth());
        assertTrue(Arrays.equals(bestChain.getChain(), new int[] {2, 4, 3, 1}));

        bestChain = NStreamQueryPlanBuilder.computeBestPath(3, queryGraph);
        assertEquals(4, bestChain.getDepth());
        assertTrue(Arrays.equals(bestChain.getChain(), new int[] {4, 2, 0, 1}));

        // try a stream that is not connected in any way
        queryGraph = new QueryGraph(6);
        bestChain = NStreamQueryPlanBuilder.computeBestPath(5, queryGraph);
        log.debug(".testComputeBestPath bestChain=" + bestChain);
        assertEquals(0, bestChain.getDepth());
        assertTrue(Arrays.equals(bestChain.getChain(), new int[] {0, 1, 2, 3, 4}));
    }

    public void testComputeNavigableDepth()
    {
        queryGraph.add(3, "p30", 2, "p20");
        queryGraph.add(2, "p30", 1, "p20");

        int depth = NStreamQueryPlanBuilder.computeNavigableDepth(0, new int[] {1, 2, 3, 4}, queryGraph);
        assertEquals(4, depth);

        depth = NStreamQueryPlanBuilder.computeNavigableDepth(0, new int[] {4, 2, 3, 1}, queryGraph);
        assertEquals(0, depth);

        depth = NStreamQueryPlanBuilder.computeNavigableDepth(4, new int[] {3, 2, 1, 0}, queryGraph);
        assertEquals(4, depth);

        depth = NStreamQueryPlanBuilder.computeNavigableDepth(1, new int[] {0, 3, 4, 2}, queryGraph);
        assertEquals(1, depth);
    }

    public void testBuildDefaultNestingOrder()
    {
        int[] result = NStreamQueryPlanBuilder.buildDefaultNestingOrder(4, 0);
        assertTrue(Arrays.equals(result, new int[] {1, 2, 3}));

        result = NStreamQueryPlanBuilder.buildDefaultNestingOrder(4, 1);
        assertTrue(Arrays.equals(result, new int[] {0, 2, 3}));

        result = NStreamQueryPlanBuilder.buildDefaultNestingOrder(4, 2);
        assertTrue(Arrays.equals(result, new int[] {0, 1, 3}));

        result = NStreamQueryPlanBuilder.buildDefaultNestingOrder(4, 3);
        assertTrue(Arrays.equals(result, new int[] {0, 1, 2}));
    }

    private static Log log = LogFactory.getLog(TestNStreamQueryPlanBuilder.class);
}