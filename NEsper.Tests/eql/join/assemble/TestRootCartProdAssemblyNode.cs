using System;

using net.esper.events;
using net.esper.support.eql.join;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.assemble
{

    [TestFixture]
    public class TestRootCartProdAssemblyNode
    {
        private SupportJoinProcNode parentNode;
        private RootCartProdAssemblyNode rootCartNodeOneReq;

        [SetUp]
        public virtual void setUp()
        {
            rootCartNodeOneReq = new RootCartProdAssemblyNode(1, 5, false);

            parentNode = new SupportJoinProcNode(-1, 5);
            parentNode.AddChild(rootCartNodeOneReq);

            // add child nodes to indicate what sub-streams to build the cartesian product from
            rootCartNodeOneReq.AddChild(new SupportJoinProcNode(2, 5));
            rootCartNodeOneReq.AddChild(new SupportJoinProcNode(3, 5));
            rootCartNodeOneReq.AddChild(new SupportJoinProcNode(4, 5));
        }

        [Test]
        public void testFlowOptional()
        {
            RootCartProdAssemblyNode rootCartNodeAllOpt = new RootCartProdAssemblyNode(1, 5, true);
            rootCartNodeAllOpt.AddChild(new SupportJoinProcNode(2, 5));
            rootCartNodeAllOpt.AddChild(new SupportJoinProcNode(3, 5));
            rootCartNodeAllOpt.AddChild(new SupportJoinProcNode(4, 5));

            parentNode.AddChild(rootCartNodeAllOpt);

            rootCartNodeAllOpt.Init(null);
            rootCartNodeAllOpt.Process(null);

            // 5 generated rows: 2 (stream 2) + 2 (stream 3) + 1 (self, Node 2)
            Assert.AreEqual(1, parentNode.getRowsList().Count);

            EventBean[][] rowArr = SupportJoinResultNodeFactory.convertTo2DimArr(parentNode.getRowsList());
            ArrayAssertionUtil.AreEqualAnyOrder(new EventBean[][] { new EventBean[] { null, null, null, null, null } }, rowArr);
        }

        [Test]
        public void testFlowRequired()
        {
            rootCartNodeOneReq.Init(null);

            EventBean[] stream2Events = SupportJoinResultNodeFactory.MakeEvents(2); // for identifying rows in cartesian product
            EventBean[] stream3Events = SupportJoinResultNodeFactory.MakeEvents(2); // for identifying rows in cartesian product
            EventBean[] stream4Events = SupportJoinResultNodeFactory.MakeEvents(2); // for identifying rows in cartesian product

            // Post result from 3, send 2 rows
            EventBean[] childRow = new EventBean[5];
            childRow[3] = stream3Events[0];
            rootCartNodeOneReq.Result(childRow, 3, null, null);
            childRow = new EventBean[5];
            childRow[3] = stream3Events[1];
            rootCartNodeOneReq.Result(childRow, 3, null, null);

            // Post result from 2, send 2 rows
            childRow = new EventBean[5];
            childRow[2] = stream2Events[0];
            rootCartNodeOneReq.Result(childRow, 2, null, null);
            childRow = new EventBean[5];
            childRow[2] = stream2Events[1];
            rootCartNodeOneReq.Result(childRow, 2, null, null);

            // Post result from 4
            childRow = new EventBean[5];
            childRow[4] = stream4Events[0];
            rootCartNodeOneReq.Result(childRow, 4, null, null);
            childRow = new EventBean[5];
            childRow[4] = stream4Events[1];
            rootCartNodeOneReq.Result(childRow, 4, null, null);

            // process posted rows (child rows were stored and are compared to find other rows to generate)
            rootCartNodeOneReq.Process(null);

            // 5 generated rows: 2 (stream 2) + 2 (stream 3) + 1 (self, Node 2)
            Assert.AreEqual(8, parentNode.getRowsList().Count);

            EventBean[][] rowArr = SupportJoinResultNodeFactory.convertTo2DimArr(parentNode.getRowsList());
            ArrayAssertionUtil.AreEqualAnyOrder(new EventBean[][]{
                new EventBean[]{null, null, stream2Events[0], stream3Events[0], stream4Events[0]},
                new EventBean[]{null, null, stream2Events[0], stream3Events[1], stream4Events[0]},
                new EventBean[]{null, null, stream2Events[1], stream3Events[0], stream4Events[0]},
                new EventBean[]{null, null, stream2Events[1], stream3Events[1], stream4Events[0]},
                new EventBean[]{null, null, stream2Events[0], stream3Events[0], stream4Events[1]},
                new EventBean[]{null, null, stream2Events[0], stream3Events[1], stream4Events[1]},
                new EventBean[]{null, null, stream2Events[1], stream3Events[0], stream4Events[1]},
                new EventBean[]{null, null, stream2Events[1], stream3Events[1], stream4Events[1]}},
                rowArr);
        }

        [Test]
        public void testComputeCombined()
        {
            Assert.IsNull(RootCartProdAssemblyNode.ComputeCombined(new int[][] { new int[] { 2 } }));
            Assert.IsNull(RootCartProdAssemblyNode.ComputeCombined(new int[][] { new int[] { 1 }, new int[] { 2 } }));

            int[][] result = RootCartProdAssemblyNode.ComputeCombined(new int[][]{
                new int[]{3, 4},
                new int[]{2, 5},
                new int[]{6}});
            Assert.AreEqual(1, result.Length);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 4, 2, 5 }, result[0]);

            result = RootCartProdAssemblyNode.ComputeCombined(new int[][]{
                new int[]{3, 4},
                new int[]{2, 5},
                new int[]{6},
                new int[]{0, 8, 9}});
            Assert.AreEqual(2, result.Length);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 4, 2, 5 }, result[0]);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 4, 2, 5, 6 }, result[1]);

            result = RootCartProdAssemblyNode.ComputeCombined(new int[][]{
                new int[]{3, 4},
                new int[]{2, 5},
                new int[]{6},
                new int[]{0, 8, 9},
                new int[]{1}});
            Assert.AreEqual(3, result.Length);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 4, 2, 5 }, result[0]);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 4, 2, 5, 6 }, result[1]);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 4, 2, 5, 6, 0, 8, 9 }, result[2]);
        }
    }
}
