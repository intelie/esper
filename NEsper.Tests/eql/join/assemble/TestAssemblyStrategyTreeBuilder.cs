using System;

using net.esper.compat;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.join.assemble
{

	[TestFixture]
    public class TestAssemblyStrategyTreeBuilder 
    {
        [Test]
        public void testInvalidBuild()
        {
            // root stream out of bounds
            tryInvalidBuild(3, convert(new int[][] { new int[] { 1, 2 }, new int[] { }, new int[] { } }), new bool[] { true, true, true });
            tryInvalidBuild(-1, convert(new int[][] { new int[] { 1, 2 }, new int[] { }, new int[] { } }), new bool[] { true, true, true });

            // not matching outer-inner
            tryInvalidBuild(0, convert(new int[][] { new int[] { 1, 2 }, new int[] { }, new int[] { } }), new bool[] { true, true });

            // stream relationships not filled
            tryInvalidBuild(0, convert(new int[][] { new int[] { 1, 2 } }), new bool[] { true, true, true });

            // stream relationships duplicates
            tryInvalidBuild(0, convert(new int[][] { new int[] { 1, 2 }, new int[] { 1 }, new int[] { } }), new bool[] { true, true });
            tryInvalidBuild(0, convert(new int[][] { new int[] { 1, 2 }, new int[] { }, new int[] { 2 } }), new bool[] { true, true, true });

            // stream relationships out of range
            tryInvalidBuild(0, convert(new int[][] { new int[] { 1, 3 }, new int[] { }, new int[] { } }), new bool[] { true, true });

            // stream relationships missing stream
            tryInvalidBuild(0, convert(new int[][] { new int[] { 1 }, new int[] { }, new int[] { } }), new bool[] { true, true });
        }

        [Test]
        public void testValidBuildSimpleReqOpt()
        {
            BaseAssemblyNode node = AssemblyStrategyTreeBuilder.Build(2, convert(new int[][] { new int[] { }, new int[] { 0 }, new int[] { 1 } }), new bool[] { false, true, true });

            RootRequiredAssemblyNode child1 = (RootRequiredAssemblyNode)node;
            Assert.AreEqual(2, child1.StreamNum);
            Assert.AreEqual(1, child1.ChildNodes.Count);
            Assert.AreEqual(null, child1.ParentAssembler);

            BranchOptionalAssemblyNode child1_1 = (BranchOptionalAssemblyNode)child1.ChildNodes[0];
            Assert.AreEqual(1, child1_1.StreamNum);
            Assert.AreEqual(1, child1_1.ChildNodes.Count);
            Assert.AreEqual(child1, child1_1.ParentAssembler);

            LeafAssemblyNode leaf1_2 = (LeafAssemblyNode)child1_1.ChildNodes[0];
            Assert.AreEqual(0, leaf1_2.StreamNum);
            Assert.AreEqual(0, leaf1_2.ChildNodes.Count);
            Assert.AreEqual(child1_1, leaf1_2.ParentAssembler);
        }

        [Test]
        public void testValidBuildSimpleOptReq()
        {
            BaseAssemblyNode node = AssemblyStrategyTreeBuilder.Build(2, convert(
				new int[][] {
					new int[] { },
					new int[] { 0 },
					new int[] { 1 } }),
				new bool[] { true, false, true });

            RootOptionalAssemblyNode child1 = (RootOptionalAssemblyNode)node;
            Assert.AreEqual(2, child1.StreamNum);
            Assert.AreEqual(1, child1.ChildNodes.Count);
            Assert.AreEqual(null, child1.ParentAssembler);

            BranchRequiredAssemblyNode child1_1 = (BranchRequiredAssemblyNode)child1.ChildNodes[0];
            Assert.AreEqual(1, child1_1.StreamNum);
            Assert.AreEqual(1, child1_1.ChildNodes.Count);
            Assert.AreEqual(child1, child1_1.ParentAssembler);

            LeafAssemblyNode leaf1_2 = (LeafAssemblyNode)child1_1.ChildNodes[0];
            Assert.AreEqual(0, leaf1_2.StreamNum);
            Assert.AreEqual(0, leaf1_2.ChildNodes.Count);
            Assert.AreEqual(child1_1, leaf1_2.ParentAssembler);
        }

        [Test]
        public void testValidBuildCartesian()
        {
            BaseAssemblyNode node = AssemblyStrategyTreeBuilder.Build(1, convert(
				new int[][] {
					new int[] { },
					new int[] { 0, 2 },
					new int[] { } }),
				new bool[] { false, true, false });

            RootCartProdAssemblyNode top = (RootCartProdAssemblyNode)node;
            Assert.AreEqual(2, top.ChildNodes.Count);

            LeafAssemblyNode leaf1 = (LeafAssemblyNode)top.ChildNodes[0];
            Assert.AreEqual(0, leaf1.StreamNum);
            Assert.AreEqual(0, leaf1.ChildNodes.Count);
            Assert.AreEqual(top, leaf1.ParentAssembler);

            LeafAssemblyNode leaf2 = (LeafAssemblyNode)top.ChildNodes[0];
            Assert.AreEqual(0, leaf2.StreamNum);
            Assert.AreEqual(0, leaf2.ChildNodes.Count);
            Assert.AreEqual(top, leaf2.ParentAssembler);
        }

        private void tryInvalidBuild(int rootStream, EDictionary<int, int[]> joinedPerStream, bool[] isInnerPerStream)
        {
            try
            {
                AssemblyStrategyTreeBuilder.Build(rootStream, joinedPerStream, isInnerPerStream);
				Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                log.Debug(".tryInvalidBuild expected exception=" + ex);
                // expected
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

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
