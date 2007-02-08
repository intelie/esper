using System;

using net.esper.compat;
using net.esper.support.view.olap;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat.olap
{
    [TestFixture]
    public class TestCubeDimensionHelper
    {
        [Test]
        public virtual void testGetDimensionSizes()
        {
            Cube testCube = SupportCubeFactory.make2DimCube();
            int[] dimensionSizes = CubeDimensionHelper.GetDimensionSizes(CollectionHelper.ToArray(testCube.Dimensions));

            Assert.AreEqual(3, dimensionSizes.Length);
            Assert.AreEqual(3, dimensionSizes[0]); // 3 derived values
            Assert.AreEqual(4, dimensionSizes[1]);
            Assert.AreEqual(3, dimensionSizes[2]);
        }

        [Test]
        public virtual void testNextIndize()
        {
            int[] indizes = new int[3];

            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    for (int k = 0; k < 4; k++)
                    {
                        Assert.AreEqual(k, indizes[0]);
                        Assert.AreEqual(j, indizes[1]);
                        Assert.AreEqual(i, indizes[2]);

                        if (i == 4 && j == 2 && k == 3)
                        {
                            break;
                        }
                        CubeDimensionHelper.NextIndize(new int[] { 4, 3, 5 }, indizes);
                    }
                }
            }
        }

        [Test]
        public virtual void testCalculateTotalSize()
        {
            Assert.AreEqual(3, CubeDimensionHelper.GetTotalCells(new int[] { 3 }));
            Assert.AreEqual(6, CubeDimensionHelper.GetTotalCells(new int[] { 3, 2 }));
            Assert.AreEqual(30, CubeDimensionHelper.GetTotalCells(new int[] { 3, 2, 5 }));
            Assert.AreEqual(60, CubeDimensionHelper.GetTotalCells(new int[] { 3, 2, 5, 2 }));
            Assert.AreEqual(60, CubeDimensionHelper.GetTotalCells(new int[] { 3, 2, 5, 2, 1 }));
            Assert.AreEqual(120, CubeDimensionHelper.GetTotalCells(new int[] { 3, 2, 5, 2, 1, 2 }));
        }

        [Test]
        public virtual void testCalculateOrdinal()
        {
            // 1-dimensional
            Assert.AreEqual(0, CubeDimensionHelper.GetOrdinal(new int[] { 0 }, new int[] { 3 }));
            Assert.AreEqual(1, CubeDimensionHelper.GetOrdinal(new int[] { 1 }, new int[] { 3 }));
            Assert.AreEqual(2, CubeDimensionHelper.GetOrdinal(new int[] { 2 }, new int[] { 5 }));

            // 2-dimensional
            Assert.AreEqual(0, CubeDimensionHelper.GetOrdinal(new int[] { 0, 0 }, new int[] { 4, 3 }));
            Assert.AreEqual(2 + 2 * 4, CubeDimensionHelper.GetOrdinal(new int[] { 2, 2 }, new int[] { 4, 3 }));
            Assert.AreEqual(3 + 1 * 4, CubeDimensionHelper.GetOrdinal(new int[] { 3, 1 }, new int[] { 4, 3 }));

            // 3-dimensional
            Assert.AreEqual(0 + 3 * 4 + 1 * 12, CubeDimensionHelper.GetOrdinal(new int[] { 0, 3, 1 }, new int[] { 4, 3, 5 }));
            Assert.AreEqual(3 + 2 * 4 + 4 * 12, CubeDimensionHelper.GetOrdinal(new int[] { 3, 2, 4 }, new int[] { 4, 3, 5 }));

            // 4-dimensional
            Assert.AreEqual(0 + 3 * 4 + 1 * 12 + 1 * 60, CubeDimensionHelper.GetOrdinal(new int[] { 0, 3, 1, 1 }, new int[] { 4, 3, 5, 2 }));
            Assert.AreEqual(3 + 0 * 4 + 1 * 12 + 1 * 60, CubeDimensionHelper.GetOrdinal(new int[] { 3, 0, 1, 1 }, new int[] { 4, 3, 5, 2 }));
        }
    }
}
