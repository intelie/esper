using System;

using net.esper.compat;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{

    [TestFixture]
    public class TestFrequencyParameter
    {
        [Test]
        public void testInvalid()
        {
            try
            {
                new FrequencyParameter(0);
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // Expected
            }
        }

        [Test]
        public void testIsWildcard()
        {
            FrequencyParameter freq = new FrequencyParameter(1);
            Assert.IsTrue(freq.IsWildcard(1, 10));

            freq = new FrequencyParameter(2);
            Assert.IsFalse(freq.IsWildcard(1, 20));
        }

        [Test]
        public void testGetValues()
        {
            FrequencyParameter freq = new FrequencyParameter(3);
            Set<int> result = freq.GetValuesInRange(1, 8);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 6 }, result);

            freq = new FrequencyParameter(4);
            result = freq.GetValuesInRange(6, 16);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 8, 12, 16 }, result);

            freq = new FrequencyParameter(4);
            result = freq.GetValuesInRange(0, 14);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 0, 4, 8, 12 }, result);

            freq = new FrequencyParameter(1);
            result = freq.GetValuesInRange(2, 5);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 2, 3, 4, 5 }, result);
        }
    }
}
