using System;
using System.Collections.Generic;

using net.esper.collection;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
	[TestFixture]
    public class TestMultiKeyComparator 
    {
        internal IComparer<MultiKey<Object>> comparator;
        internal MultiKey<Object> firstValues;
        internal MultiKey<Object> secondValues;

        [Test]
        public virtual void testCompareSingleProperty()
        {
            comparator = new MultiKeyComparator<Object>(new bool[] { false });

            firstValues = new MultiKey<Object>(new Object[]{3d});
            secondValues = new MultiKey<Object>(new Object[]{4d});

            Assert.IsTrue(comparator.Compare(firstValues, secondValues) < 0);

            comparator = new MultiKeyComparator<Object>(new bool[] { true });

            Assert.IsTrue(comparator.Compare(firstValues, secondValues) > 0);
            Assert.IsTrue(comparator.Compare(firstValues, firstValues) == 0);
        }

        [Test]
        public virtual void testCompareTwoProperties()
        {
            comparator = new MultiKeyComparator<Object>(new bool[] { false, false });

            firstValues = new MultiKey<Object>(new Object[]{3d, 3L});
            secondValues = new MultiKey<Object>(new Object[]{3d, 4L});

            Assert.IsTrue(comparator.Compare(firstValues, secondValues) < 0);

            comparator = new MultiKeyComparator<Object>(new bool[] { false, true });

            Assert.IsTrue(comparator.Compare(firstValues, secondValues) > 0);
            Assert.IsTrue(comparator.Compare(firstValues, firstValues) == 0);
        }

        [Test]
        public virtual void testInvalid()
        {
            comparator = new MultiKeyComparator<Object>(new bool[] { false, false });

            firstValues = new MultiKey<Object>(new Object[]{3d});
            secondValues = new MultiKey<Object>(new Object[]{3d, 4L});

            try
            {
                comparator.Compare(firstValues, secondValues);
                Assert.Fail();
            }
            catch (ArgumentException e)
            {
                // Expected
            }

            firstValues = new MultiKey<Object>(new Object[]{3d});
            secondValues = new MultiKey<Object>(new Object[]{3d});

            try
            {
                comparator.Compare(firstValues, secondValues);
                Assert.Fail();
            }
            catch (ArgumentException e)
            {
                // Expected
            }
        }
    }
}
