using System;

using net.esper.compat;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{

    [TestFixture]
    public class TestIntParameter
    {
        [Test]
        public virtual void testIsWildcard()
        {
            IntParameter intParam = new IntParameter(5);
            Assert.IsTrue(intParam.IsWildcard(5, 5));
            Assert.IsFalse(intParam.IsWildcard(4, 5));
            Assert.IsFalse(intParam.IsWildcard(5, 6));
            Assert.IsFalse(intParam.IsWildcard(4, 6));
        }

        [Test]
        public virtual void testGetValues()
        {
            IntParameter intParam = new IntParameter(3);
            ISet<int> result = intParam.GetValuesInRange(1, 8);
            ArrayAssertionUtil.assertEqualsAnyOrder(new int[] { 3 }, result);

            result = intParam.GetValuesInRange(1, 2);
            ArrayAssertionUtil.assertEqualsAnyOrder(new int[] { }, result);

            result = intParam.GetValuesInRange(4, 10);
            ArrayAssertionUtil.assertEqualsAnyOrder(new int[] { }, result);

            result = intParam.GetValuesInRange(1, 3);
            ArrayAssertionUtil.assertEqualsAnyOrder(new int[] { 3 }, result);

            result = intParam.GetValuesInRange(3, 5);
            ArrayAssertionUtil.assertEqualsAnyOrder(new int[] { 3 }, result);
        }
    }
}
