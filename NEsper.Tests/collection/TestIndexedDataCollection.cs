using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
    [TestFixture]
    public class TestIndexedDataCollection
    {
        [Test]
        public virtual void testAddRemoveGet()
        {
            IndexedDataCollection index = new IndexedDataCollection();

            // Empty key should return null
            Assert.AreEqual(null, index["a"]);

            // Add value for key 'a'
            index.Add("a", "a1");

            // Empty key should return null
            Assert.AreEqual(null, index["a1"]);
            Assert.AreEqual(null, index["b"]);

            // Add more values
            index.Add("b", "b1");
            index.Add("b", "b2");
            index.Add("c", "c1");
            index.Add("c", "c2");
            index.Add("c", "c3");
            index.Add("d", "d1");
            index.Add("d", "d2");
            index.Add("d", "d3");

            // Check all values
            ArrayAssertionUtil.assertEqualsExactOrder(index["a"].ToArray(), new Object[] { "a1" });
            ArrayAssertionUtil.assertEqualsExactOrder(index["b"].ToArray(), new Object[] { "b1", "b2" });
            ArrayAssertionUtil.assertEqualsExactOrder(index["c"].ToArray(), new Object[] { "c1", "c2", "c3" });
            ArrayAssertionUtil.assertEqualsExactOrder(index["d"].ToArray(), new Object[] { "d1", "d2", "d3" });

            // Remove and check some
            Assert.IsTrue(index.Remove("a", "a1"));
            Assert.AreEqual(null, index["a"]);

            Assert.IsTrue(index.Remove("c", "c1"));
            ArrayAssertionUtil.assertEqualsExactOrder(index["c"].ToArray(), new Object[] { "c2", "c3" });
            Assert.IsTrue(index.Remove("c", "c3"));
            ArrayAssertionUtil.assertEqualsExactOrder(index["c"].ToArray(), new Object[] { "c2" });
            Assert.IsFalse(index.Remove("c", "c1"));
            Assert.IsTrue(index.Remove("c", "c2"));
            Assert.IsFalse(index.Remove("c", "c2"));
            Assert.AreEqual(null, index["c"]);

            Assert.IsTrue(index.Remove("d", "d3"));
            ArrayAssertionUtil.assertEqualsExactOrder(index["d"].ToArray(), new Object[] { "d1", "d2" });

            // Add some more
            index.Add("d", "d1");
            index.Add("d", "d2");
            index.Add("d", "d3");
            ArrayAssertionUtil.assertEqualsExactOrder(index["d"].ToArray(), new Object[] { "d1", "d2", "d1", "d2", "d3" });
            Assert.IsTrue(index.Remove("d", "d1"));

            // Remove again
            ArrayAssertionUtil.assertEqualsExactOrder(index["d"].ToArray(), new Object[] { "d2", "d1", "d2", "d3" });
            Assert.IsTrue(index.Remove("d", "d1"));
            ArrayAssertionUtil.assertEqualsExactOrder(index["d"].ToArray(), new Object[] { "d2", "d2", "d3" });
            Assert.IsFalse(index.Remove("d", "d1"));
            Assert.IsTrue(index.Remove("d", "d2"));
            ArrayAssertionUtil.assertEqualsExactOrder(index["d"].ToArray(), new Object[] { "d2", "d3" });
            Assert.IsTrue(index.Remove("d", "d3"));
            ArrayAssertionUtil.assertEqualsExactOrder(index["d"].ToArray(), new Object[] { "d2" });
            Assert.IsTrue(index.Remove("d", "d2"));
            Assert.AreEqual(null, index["d"]);

            index.Remove("b", "b1");
            index.Remove("b", "b2");

            Assert.AreEqual(null, index["a"]);
            Assert.AreEqual(null, index["b"]);
            Assert.AreEqual(null, index["c"]);
            Assert.AreEqual(null, index["d"]);
        }
    }
}

