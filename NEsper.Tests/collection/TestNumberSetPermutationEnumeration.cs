using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.collection
{
    [TestFixture]
    public class TestNumberSetPermutationEnumeration
    {
        [Test]
        public virtual void testInvalid()
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
        public virtual void testNext()
        {
            int[] numberSet = new int[] { 10, 11, 12 };
            int[][] expectedValues = new int[][]
                {
		            new int[] { 10, 11, 12 },
		            new int[] { 10, 12, 11 },
		            new int[] { 11, 10, 12 },
		            new int[] { 11, 12, 10 },
		            new int[] { 12, 10, 11 },
		            new int[] { 12, 11, 10 }
        		};

            tryPermutation(numberSet, expectedValues);
        }

        private void tryPermutation(int[] numberSet, int[][] expectedValues)
        {
            NumberSetPermutationEnumeration enumeration = new NumberSetPermutationEnumeration(numberSet);

            int count = 0;
            while (enumeration.MoveNext())
            {
                int[] result = enumeration.Current;
                int[] expected = expectedValues[count];

                log.Debug(".tryPermutation result=" + CollectionHelper.Render(result));
                log.Debug(".tryPermutation expected=" + CollectionHelper.Render(expected));

                count++;
                Assert.IsTrue(CollectionHelper.AreEqual(result, expected), "Mismatch in count=" + count);
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

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
