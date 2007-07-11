using System;
using System.Collections.Generic;

using NUnit.Core;
using NUnit.Framework;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.collection
{

    [TestFixture]
    public class TestPermutationEnumeration
    {
        [Test]
        public void testInvalid()
        {
            try
            {
                new PermutationEnumeration(0);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // expected
            }
        }

        [Test]
        public void testNext()
        {
            int[][] expectedValues4 = new int[][] {
                new int[] { 0, 1, 2, 3 },     // 0
                new int[] { 0, 1, 3, 2 },
                new int[] { 0, 2, 1, 3 },
                new int[] { 0, 2, 3, 1 },
                new int[] { 0, 3, 1, 2 },
                new int[] { 0, 3, 2, 1 },     // 5

                new int[] { 1, 0, 2, 3 },     // 6
                new int[] { 1, 0, 3, 2 },     // 7
                new int[] { 1, 2, 0, 3 },     // 8
                new int[] { 1, 2, 3, 0 },
                new int[] { 1, 3, 0, 2 },
                new int[] { 1, 3, 2, 0 },     // 11

                new int[] { 2, 0, 1, 3 },     // 12
                new int[] { 2, 0, 3, 1 },
                new int[] { 2, 1, 0, 3 },
                new int[] { 2, 1, 3, 0 },
                new int[] { 2, 3, 0, 1 },
                new int[] { 2, 3, 1, 0 },     // 17

                new int[] { 3, 0, 1, 2 },     // 18
                new int[] { 3, 0, 2, 1 },
                new int[] { 3, 1, 0, 2 },
                new int[] { 3, 1, 2, 0 },     // 21
                new int[] { 3, 2, 0, 1 },
                new int[] { 3, 2, 1, 0 } };
            tryPermutation(4, expectedValues4);

            int[][] expectedValues3 = new int[][] {
                new int[] { 0, 1, 2 },
                new int[] { 0, 2, 1 },
                new int[] { 1, 0, 2 },
                new int[] { 1, 2, 0 },
                new int[] { 2, 0, 1 },
                new int[] { 2, 1, 0 }};
            tryPermutation(3, expectedValues3);

            int[][] expectedValues2 = new int[][] {
                new int[] { 0, 1},
                new int[] { 1, 0}};
            tryPermutation(2, expectedValues2);

            int[][] expectedValues1 = new int[][] {
                new int[] {0}};
            tryPermutation(1, expectedValues1);
        }

        private void tryPermutation(int numElements, int[][] expectedValues)
        {
            /*
            Total: 4 * 3 * 2 = 24 = 6!  (6 Faculty)

            Example:8
            n / 6 = first number        == index 1, total {1}, remains {0, 2, 3}
            remainder 8 - 1 * 6         == 2
            n / 2 = second number       == index 1, total {1, 2}, remain {0, 3}
            remainder 2 - 1 * 2         == 0
            == total {1, 2, 0, 3}

            Example:21   out {0, 1, 2, 3}
            21 / 6                      == index 3 -> in {3}, out {0, 1, 2}
            remainder 21 - 3 * 6        == 3
            3 / 2 = second number       == index 1 -> in {3, 1}, remain {0, 2}
            remainder 3 - 1 * 2         == 1
            == index 1 -> in {3, 1, 2} out {0}
            */
            PermutationEnumeration enumeration = new PermutationEnumeration(numElements);

            int count = 0;
            while (enumeration.MoveNext())
            {
                int[] result = enumeration.Current;
                int[] expected = expectedValues[count];

                log.Debug(".tryPermutation result=" + CollectionHelper.Render(result));
                log.Debug(".tryPermutation expected=" + CollectionHelper.Render(result));

                count++;
                Assert.IsTrue(
                    CollectionHelper.AreEqual(
                        (ICollection<int>) result,
                        (ICollection<int>) expected ),
                    "Mismatch in count=" + count );
            }
            Assert.AreEqual(count, expectedValues.Length);

            try
            {
                enumeration.MoveNext();
                Object temp = enumeration.Current;
                Assert.Fail();
            }
            catch (System.ArgumentOutOfRangeException ex)
            {
                // Expected
            }
        }

        public static void testGetPermutation()
        {
            int[] factors = PermutationEnumeration.GetFactors(4);
            int[] result = PermutationEnumeration.GetPermutation(4, 21, factors);

            log.Debug(".testGetPermutation result=" + CollectionHelper.Render(result));
            Assert.IsTrue( CollectionHelper.AreEqual(
                (ICollection<int>) result, 
                (ICollection<int>) new int[] { 3, 1, 2, 0 }
                ));
        }

        public static void testGetFactors()
        {
            int[] factors = PermutationEnumeration.GetFactors(5);
            Assert.IsTrue(CollectionHelper.AreEqual(factors, new int[] { 24, 6, 2, 1, 0 }));

            factors = PermutationEnumeration.GetFactors(4);
            Assert.IsTrue(CollectionHelper.AreEqual(factors, new int[] { 6, 2, 1, 0 }));

            factors = PermutationEnumeration.GetFactors(3);
            Assert.IsTrue(CollectionHelper.AreEqual(factors, new int[] { 2, 1, 0 }));

            factors = PermutationEnumeration.GetFactors(2);
            Assert.IsTrue(CollectionHelper.AreEqual(factors, new int[] { 1, 0 }));

            factors = PermutationEnumeration.GetFactors(1);
            Assert.IsTrue(CollectionHelper.AreEqual(factors, new int[] { 0 }));

            //log.Debug(".testGetFactors " + CollectionHelper.Render(factors));
        }

        public static void testFaculty()
        {
            Assert.AreEqual(0, PermutationEnumeration.Faculty(0));
            Assert.AreEqual(1, PermutationEnumeration.Faculty(1));
            Assert.AreEqual(2, PermutationEnumeration.Faculty(2));
            Assert.AreEqual(6, PermutationEnumeration.Faculty(3));
            Assert.AreEqual(24, PermutationEnumeration.Faculty(4));
            Assert.AreEqual(120, PermutationEnumeration.Faculty(5));
            Assert.AreEqual(720, PermutationEnumeration.Faculty(6));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
