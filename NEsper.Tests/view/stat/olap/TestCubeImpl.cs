using System;
using System.Collections.Generic;

using net.esper.support.view.olap;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat.olap
{

    [TestFixture]
    public class TestCubeImpl
    {
        internal CubeImpl testCube;

        protected internal virtual void setUp()
        {
            String[] measureList = new String[] { "count" };
            testCube = new CubeImpl(SupportCubeFactory.make2DimSchema(), measureList);
        }

        [Test]
        public virtual void testValidOrdinalsAndMembers()
        {
            IList<Dimension> dimensions = testCube.Dimensions;
            IList<Cell> measures = testCube.Measures;

            // Check dimensions
            Assert.AreEqual(3, dimensions.Count);
            Assert.AreEqual(12, measures.Count);

            // Check each ordinal
            for (int ordinal = 0; ordinal < measures.Count; ordinal++)
            {
                IList<DimensionMember> members = testCube.GetMembers(ordinal);
                int shouldBeOrdinal = testCube.GetOrdinal(members);

                Assert.AreEqual(shouldBeOrdinal, ordinal);

                // Pick a couple of ordinals and check member references

                // Test results for ordinal 0
                if (ordinal == 0)
                {
                    Assert.IsTrue(members[0] == dimensions[0].GetMembers()[0]);
                    Assert.IsTrue(members[1] == dimensions[1].GetMembers()[0]);
                    Assert.IsTrue(members[2] == dimensions[2].GetMembers()[0]);
                }

                // Test results for ordinal 5
                if (ordinal == 5)
                {
                    Assert.IsTrue(members[0] == dimensions[0].GetMembers()[0]);
                    Assert.IsTrue(members[1] == dimensions[1].GetMembers()[1]);
                    Assert.IsTrue(members[2] == dimensions[2].GetMembers()[1]);
                }

                // Test results for ordinal 11
                if (ordinal == 11)
                {
                    Assert.IsTrue(members[0] == dimensions[0].GetMembers()[0]);
                    Assert.IsTrue(members[1] == dimensions[1].GetMembers()[3]);
                    Assert.IsTrue(members[2] == dimensions[2].GetMembers()[2]);
                }
            }
        }

        [Test]
        public virtual void testInvalidGetMembers()
        {
            try
            {
                testCube.GetMembers(-1);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            try
            {
                testCube.GetMembers(testCube.Measures.Count);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public virtual void testInvalidGetOrdinal()
        {
            try
            {
                testCube.GetOrdinal(null);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            try
            {
                testCube.GetOrdinal(new DimensionMember[1]);
                Assert.IsTrue(false);
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }

            int ordinal = testCube.GetOrdinal(new DimensionMember[3]);
            Assert.AreEqual(-1, ordinal);
        }
    }
}
