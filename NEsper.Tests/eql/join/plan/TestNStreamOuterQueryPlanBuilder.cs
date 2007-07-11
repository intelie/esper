using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.support.eql;
using net.esper.support.util;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{

	[TestFixture]
    public class TestNStreamOuterQueryPlanBuilder
    {
        [Test]
        public void testGraphOuterJoins()
        {
            IList<OuterJoinDesc> descList = new List<OuterJoinDesc>();
            descList.Add(SupportOuterJoinDescFactory.MakeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.RIGHT));
            descList.Add(SupportOuterJoinDescFactory.MakeDesc("simpleProperty", "s2", "string", "s1", OuterJoinType.FULL));

            OuterInnerDirectionalGraph graph = NStreamOuterQueryPlanBuilder.GraphOuterJoins(3, descList);

            // assert the inner and outer streams for each stream
            assertInners(new int[][] { null, new int[] { 0, 2 }, new int[] { 1 } }, graph);
            assertOuters(new int[][] { new int[] { 1 }, new int[] { 2 }, new int[] { 1 } }, graph);

            descList.Clear();
            descList.Add(SupportOuterJoinDescFactory.MakeDesc("intPrimitive", "s1", "intBoxed", "s0", OuterJoinType.LEFT));
            descList.Add(SupportOuterJoinDescFactory.MakeDesc("simpleProperty", "s2", "string", "s1", OuterJoinType.RIGHT));

            graph = NStreamOuterQueryPlanBuilder.GraphOuterJoins(3, descList);

            // assert the inner and outer streams for each stream
            assertInners(new int[][] { new int[] { 1 }, null, new int[] { 1 } }, graph);
            assertOuters(new int[][] { null, new int[] { 0, 2 }, null }, graph);

            try
            {
                descList.Clear();
                NStreamOuterQueryPlanBuilder.GraphOuterJoins(3, descList);
				Assert.Fail();
            }
            catch (ArgumentException)
            {
                // expected
            }
        }

        [Test]
        public void testRecursiveBuild()
        {
            int streamNum = 2;
            QueryGraph queryGraph = new QueryGraph(6);
            OuterInnerDirectionalGraph outerInnerGraph = new OuterInnerDirectionalGraph(6);
            Set<int> completedStreams = new HashSet<int>();
            LinkedDictionary<int, int[]> substreamsPerStream = new LinkedDictionary<int, int[]>();
            bool[] requiredPerStream = new bool[6];

            /**
            * 2    <--   3
            *                  <-- 4
            *                  --> 5
            *      -->   1
            *                  --> 0
            *
            */
            outerInnerGraph.Add(3, 2).Add(2, 1).Add(4, 3).Add(1, 0).Add(3, 5);
            queryGraph.Add(2, "", 3, "");
            queryGraph.Add(3, "", 4, "");
            queryGraph.Add(3, "", 5, "");
            queryGraph.Add(2, "", 1, "");
            queryGraph.Add(1, "", 0, "");

            NStreamOuterQueryPlanBuilder.RecursiveBuild(streamNum, queryGraph, outerInnerGraph, completedStreams, substreamsPerStream, requiredPerStream);

            Assert.AreEqual(6, substreamsPerStream.Count);
            ArrayAssertionUtil.AreEqualExactOrder(new int[] { 3, 1 }, substreamsPerStream[2]);
            ArrayAssertionUtil.AreEqualExactOrder(new int[] { 4, 5 }, substreamsPerStream[3]);
            ArrayAssertionUtil.AreEqualExactOrder(new int[] { 0 }, substreamsPerStream[1]);
            ArrayAssertionUtil.AreEqualExactOrder(new int[] { }, substreamsPerStream[4]);
            ArrayAssertionUtil.AreEqualExactOrder(new int[] { }, substreamsPerStream[5]);
            ArrayAssertionUtil.AreEqualExactOrder(new int[] { }, substreamsPerStream[0]);

            NStreamOuterQueryPlanBuilder.VerifyJoinedPerStream(2, substreamsPerStream);
            ArrayAssertionUtil.AreEqualExactOrder(new bool[] { false, false, false, true, true, false }, requiredPerStream);
        }

        [Test]
        public void testVerifyJoinedPerStream()
        {
            // stream relationships not filled
            tryVerifyJoinedPerStream(convert(new int[][] { new int[] { 1, 2 } }));

            // stream relationships duplicates
            tryVerifyJoinedPerStream(convert(new int[][] { new int[] { 1, 2 }, new int[] { 1 }, new int[] { } }));
            tryVerifyJoinedPerStream(convert(new int[][] { new int[] { 1, 2 }, new int[] { }, new int[] { 2 } }));

            // stream relationships out of range
            tryVerifyJoinedPerStream(convert(new int[][] { new int[] { 1, 3 }, new int[] { }, new int[] { } }));

            // stream relationships missing stream
            tryVerifyJoinedPerStream(convert(new int[][] { new int[] { 1 }, new int[] { }, new int[] { } }));
        }

        private void tryVerifyJoinedPerStream(EDictionary<int, int[]> map)
        {
            try
            {
                NStreamOuterQueryPlanBuilder.VerifyJoinedPerStream(0, map);
				Assert.Fail();
            }
            catch (ArgumentException)
            {
                // expected
            }
        }

        private void assertInners(int[][] innersPerStream, OuterInnerDirectionalGraph graph)
        {
            for (int i = 0; i < innersPerStream.Length; i++)
            {
                ArrayAssertionUtil.AreEqualAnyOrder(innersPerStream[i], graph.GetInner(i));
            }
        }
        private void assertOuters(int[][] outersPerStream, OuterInnerDirectionalGraph graph)
        {
            for (int i = 0; i < outersPerStream.Length; i++)
            {
                ArrayAssertionUtil.AreEqualAnyOrder(outersPerStream[i], graph.GetOuter(i));
            }
        }

        private EDictionary<int, int[]> convert(int[][] array)
        {
            EDictionary<int, int[]> result = new HashDictionary<int, int[]>();
            for (int i = 0; i < array.Length; i++)
            {
                result.Put(i, array[i]);
            }
            return result;
        }
    }
}
