using System;

using net.esper.compat;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{

    [TestFixture]
    public class TestListParameter
    {
        private ListParameter listParam;

        [SetUp]
        public virtual void setUp()
        {
            listParam = new ListParameter();
            listParam.Add(new IntParameter(5));
            listParam.Add(new FrequencyParameter(3));
        }

        [Test]
        public void testIsWildcard()
        {
            // Wildcard is expected to make only a best-guess effort, not be perfect
            Assert.IsTrue(listParam.IsWildcard(5, 5));
            Assert.IsFalse(listParam.IsWildcard(6, 10));
        }

        [Test]
        public void testGetValues()
        {
            Set<int> result = listParam.GetValuesInRange(1, 8);
            ArrayAssertionUtil.AreEqualAnyOrder(new int[] { 3, 5, 6 }, result);
        }
    }
}
