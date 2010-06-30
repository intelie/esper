package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.support.epl.SupportOuterJoinDescFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.type.OuterJoinType;
import com.espertech.esper.collection.InterchangeablePair;
import com.espertech.esper.util.DependencyGraph;

import java.util.*;

import junit.framework.TestCase;

public class TestNStreamOuterQueryPlanBuilder extends TestCase
{
    public void testGraphOuterJoins() throws Exception
    {
        List<OuterJoinDesc> descList = new LinkedList<OuterJoinDesc>();
        descList.add(SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.RIGHT));
        descList.add(SupportOuterJoinDescFactory.makeDesc("simpleProperty", "s2", "string", "s1", OuterJoinType.FULL));

        OuterInnerDirectionalGraph graph = NStreamOuterQueryPlanBuilder.graphOuterJoins(3, descList);

        // assert the inner and outer streams for each stream
        assertInners(new int[][] {null,  {0, 2},  {1}}, graph);
        assertOuters(new int[][] {{1}, {2}, {1}}, graph);

        descList.clear();
        descList.add(SupportOuterJoinDescFactory.makeDesc("intPrimitive", "s1", "intBoxed", "s0", OuterJoinType.LEFT));
        descList.add(SupportOuterJoinDescFactory.makeDesc("simpleProperty", "s2", "string", "s1", OuterJoinType.RIGHT));

        graph = NStreamOuterQueryPlanBuilder.graphOuterJoins(3, descList);

        // assert the inner and outer streams for each stream
        assertInners(new int[][] {{1}, null, {1}}, graph);
        assertOuters(new int[][] {null, {0, 2}, null}, graph);

        try
        {
            descList.clear();
            NStreamOuterQueryPlanBuilder.graphOuterJoins(3, descList);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    public void testRecursiveBuild() throws Exception
    {
        int streamNum = 2;
        QueryGraph queryGraph = new QueryGraph(6);
        OuterInnerDirectionalGraph outerInnerGraph = new OuterInnerDirectionalGraph(6);
        Set<Integer> completedStreams = new HashSet<Integer>();
        LinkedHashMap<Integer, int[]> substreamsPerStream = new LinkedHashMap<Integer, int[]>();
        boolean[] requiredPerStream = new boolean[6];

        /**
         * 2    <--   3
         *                  <-- 4
         *                  --> 5
         *      -->   1
         *                  --> 0
         *
         */
        outerInnerGraph.add(3, 2).add(2, 1).add(4, 3).add(1, 0).add(3, 5);
        queryGraph.add(2, "", 3, "");
        queryGraph.add(3, "", 4, "");
        queryGraph.add(3, "", 5, "");
        queryGraph.add(2, "", 1, "");
        queryGraph.add(1, "", 0, "");

        Set<InterchangeablePair<Integer, Integer>> innerJoins = new HashSet<InterchangeablePair<Integer, Integer>>();
        Stack<Integer> streamStack = new Stack<Integer>();

        NStreamOuterQueryPlanBuilder.recursiveBuild(streamNum, streamStack, queryGraph, outerInnerGraph, innerJoins, completedStreams,
                substreamsPerStream, requiredPerStream, new DependencyGraph(6));

        assertEquals(6, substreamsPerStream.size());
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {3, 1}, substreamsPerStream.get(2));
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {4, 5}, substreamsPerStream.get(3));
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {0}, substreamsPerStream.get(1));
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {}, substreamsPerStream.get(4));
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {}, substreamsPerStream.get(5));
        ArrayAssertionUtil.assertEqualsExactOrder(new int[] {}, substreamsPerStream.get(0));

        NStreamOuterQueryPlanBuilder.verifyJoinedPerStream(2, substreamsPerStream);
        ArrayAssertionUtil.assertEqualsExactOrder(new boolean[] {false, false, false, true, true, false},
                                                  requiredPerStream);

    }

    public void testVerifyJoinedPerStream()
    {
        // stream relationships not filled
        tryVerifyJoinedPerStream(convert(new int[][] {{ 1, 2}} ));

        // stream relationships duplicates
        tryVerifyJoinedPerStream(convert(new int[][] {{1, 2}, {1}, {}}));
        tryVerifyJoinedPerStream(convert(new int[][] {{1, 2}, {}, {2}}));

        // stream relationships out of range
        tryVerifyJoinedPerStream(convert(new int[][] {{1, 3}, {}, {}}));

        // stream relationships missing stream
        tryVerifyJoinedPerStream(convert(new int[][] {{1}, {}, {}}));
    }

    private void tryVerifyJoinedPerStream(Map<Integer, int[]> map)
    {
        try
        {
            NStreamOuterQueryPlanBuilder.verifyJoinedPerStream(0, map);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    private void assertInners(int[][] innersPerStream, OuterInnerDirectionalGraph graph)
    {
        for (int i = 0; i < innersPerStream.length; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(innersPerStream[i], graph.getInner(i));
        }
    }
    private void assertOuters(int[][] outersPerStream, OuterInnerDirectionalGraph graph)
    {
        for (int i = 0; i < outersPerStream.length; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(outersPerStream[i], graph.getOuter(i));
        }
    }

    private Map<Integer, int[]> convert(int[][] array)
    {
        Map<Integer, int[]> result = new HashMap<Integer, int[]>();
        for (int i = 0; i < array.length; i++)
        {
            result.put(i, array[i]);
        }
        return result;
    }

}
