package com.espertech.esper.eql.join.assemble;

import com.espertech.esper.support.eql.join.SupportJoinProcNode;
import com.espertech.esper.support.eql.join.SupportJoinResultNodeFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.event.EventBean;

import junit.framework.TestCase;

public class TestRootCartProdAssemblyNode extends TestCase
{
    private SupportJoinProcNode parentNode;
    private RootCartProdAssemblyNode rootCartNodeOneReq;

    public void setUp()
    {
        rootCartNodeOneReq = new RootCartProdAssemblyNode(1, 5, false);

        parentNode = new SupportJoinProcNode(-1, 5);
        parentNode.addChild(rootCartNodeOneReq);

        // add child nodes to indicate what sub-streams to build the cartesian product from
        rootCartNodeOneReq.addChild(new SupportJoinProcNode(2, 5));
        rootCartNodeOneReq.addChild(new SupportJoinProcNode(3, 5));
        rootCartNodeOneReq.addChild(new SupportJoinProcNode(4, 5));
    }

    public void testFlowOptional()
    {
        RootCartProdAssemblyNode rootCartNodeAllOpt = new RootCartProdAssemblyNode(1, 5, true);
        rootCartNodeAllOpt.addChild(new SupportJoinProcNode(2, 5));
        rootCartNodeAllOpt.addChild(new SupportJoinProcNode(3, 5));
        rootCartNodeAllOpt.addChild(new SupportJoinProcNode(4, 5));

        parentNode.addChild(rootCartNodeAllOpt);

        rootCartNodeAllOpt.init(null);
        rootCartNodeAllOpt.process(null);

        // 5 generated rows: 2 (stream 2) + 2 (stream 3) + 1 (self, Node 2)
        assertEquals(1, parentNode.getRowsList().size());

        EventBean[][] rowArr = SupportJoinResultNodeFactory.convertTo2DimArr(parentNode.getRowsList());
        ArrayAssertionUtil.assertEqualsAnyOrder( new EventBean[][] {
                new EventBean[] {null, null, null, null, null}}, rowArr);
    }

    public void testFlowRequired()
    {
        rootCartNodeOneReq.init(null);

        EventBean[] stream2Events = SupportJoinResultNodeFactory.makeEvents(2); // for identifying rows in cartesian product
        EventBean[] stream3Events = SupportJoinResultNodeFactory.makeEvents(2); // for identifying rows in cartesian product
        EventBean[] stream4Events = SupportJoinResultNodeFactory.makeEvents(2); // for identifying rows in cartesian product

        // Post result from 3, send 2 rows
        EventBean[] childRow = new EventBean[5];
        childRow[3] = stream3Events[0];
        rootCartNodeOneReq.result(childRow, 3, null, null);
        childRow = new EventBean[5];
        childRow[3] = stream3Events[1];
        rootCartNodeOneReq.result(childRow, 3, null, null);

        // Post result from 2, send 2 rows
        childRow = new EventBean[5];
        childRow[2] = stream2Events[0];
        rootCartNodeOneReq.result(childRow, 2, null, null);
        childRow = new EventBean[5];
        childRow[2] = stream2Events[1];
        rootCartNodeOneReq.result(childRow, 2, null, null);

        // Post result from 4
        childRow = new EventBean[5];
        childRow[4] = stream4Events[0];
        rootCartNodeOneReq.result(childRow, 4, null, null);
        childRow = new EventBean[5];
        childRow[4] = stream4Events[1];
        rootCartNodeOneReq.result(childRow, 4, null, null);

        // process posted rows (child rows were stored and are compared to find other rows to generate)
        rootCartNodeOneReq.process(null);

        // 5 generated rows: 2 (stream 2) + 2 (stream 3) + 1 (self, Node 2)
        assertEquals(8, parentNode.getRowsList().size());

        EventBean[][] rowArr = SupportJoinResultNodeFactory.convertTo2DimArr(parentNode.getRowsList());
        ArrayAssertionUtil.assertEqualsAnyOrder( new EventBean[][] {
                new EventBean[] {null, null, stream2Events[0], stream3Events[0], stream4Events[0]},
                new EventBean[] {null, null, stream2Events[0], stream3Events[1], stream4Events[0]},
                new EventBean[] {null, null, stream2Events[1], stream3Events[0], stream4Events[0]},
                new EventBean[] {null, null, stream2Events[1], stream3Events[1], stream4Events[0]},
                new EventBean[] {null, null, stream2Events[0], stream3Events[0], stream4Events[1]},
                new EventBean[] {null, null, stream2Events[0], stream3Events[1], stream4Events[1]},
                new EventBean[] {null, null, stream2Events[1], stream3Events[0], stream4Events[1]},
                new EventBean[] {null, null, stream2Events[1], stream3Events[1], stream4Events[1]},
                }
                , rowArr);
    }

    public void testComputeCombined()
    {
        assertNull(RootCartProdAssemblyNode.computeCombined(new int[][] {{2}} ));
        assertNull(RootCartProdAssemblyNode.computeCombined(new int[][] {{1}, {2}} ));

        int[][] result = RootCartProdAssemblyNode.computeCombined(
                    new int[][] {  {3,4}, {2,5}, {6} });
        assertEquals(1, result.length);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 4, 2, 5}, result[0]);

        result = RootCartProdAssemblyNode.computeCombined(
                    new int[][] {  {3,4}, {2,5}, {6}, {0, 8, 9} });
        assertEquals(2, result.length);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 4, 2, 5}, result[0]);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 4, 2, 5, 6}, result[1]);

        result = RootCartProdAssemblyNode.computeCombined(
                    new int[][] {  {3,4}, {2,5}, {6}, {0, 8, 9}, {1} });
        assertEquals(3, result.length);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 4, 2, 5}, result[0]);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 4, 2, 5, 6}, result[1]);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 4, 2, 5, 6, 0, 8, 9}, result[2]);
    }
}
