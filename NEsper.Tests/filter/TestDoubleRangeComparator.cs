using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.filter
{
    [TestFixture]
    public class TestDoubleRangeComparator
    {
        [Test]
        public void testComparator()
        {
            TreeSet<DoubleRange> sorted = new TreeSet<DoubleRange>(new DoubleRangeComparator());

            double[][] TEST_SET =
            {
                  new double[]{10, 20},         // 4
                  new double[]{10, 15},         // 2
                  new double[]{10, 25},         // 5
                  new double[]{5, 20},          // 0
                  new double[]{5, 25},          // 1
                  new double[]{15, 20},         // 6
                  new double[]{10, 16}          // 3
            };

            int[] EXPECTED_INDEX = { 3, 4, 1, 6, 0, 2, 5 };

            // Sort
            DoubleRange[] ranges = new DoubleRange[TEST_SET.Length];
            for (int i = 0; i < TEST_SET.Length; i++)
            {
                ranges[i] = new DoubleRange(TEST_SET[i][0], TEST_SET[i][1]);
                sorted.Add(ranges[i]);
            }

            // Check results
            int count = 0;

            foreach (DoubleRange range in sorted)
            {
                int indexExpected = EXPECTED_INDEX[count];
                DoubleRange expected = ranges[indexExpected];

                log.Debug(".testComparator count=" + count +
                        " range=" + range +
                        " expected=" + expected);

                //assertEquals(range, expected);
                count++;
            }

            Assert.AreEqual(count, TEST_SET.Length);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
