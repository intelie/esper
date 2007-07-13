using System;

using NUnit.Framework;

using net.esper.compat;

namespace net.esper.collection
{
	[TestFixture]
    public class TestRefCountedSet 
    {
        private RefCountedSet<string> refSet;

        [SetUp]
        public virtual void setUp()
        {
            refSet = new RefCountedSet<String>();
        }

        [Test]
        public void testAdd()
        {
            Assert.IsTrue(refSet.Add("a"));
            Assert.AreEqual(1, refSet.Count);

            Assert.IsFalse(refSet.Add("a"));
            Assert.AreEqual(2, refSet.Count);

            Assert.IsTrue(refSet.Add("A"));
            Assert.AreEqual(3, refSet.Count);
        }

        [Test]
        public void testRemove()
        {
            refSet.Add("a");
            refSet.Add("a");
            refSet.Add("a");
            Assert.AreEqual(3, refSet.Count);
            Assert.IsFalse(refSet.Remove("a"));
            Assert.AreEqual(2, refSet.Count);
            Assert.IsFalse(refSet.Remove("a"));
            Assert.AreEqual(1, refSet.Count);
            Assert.IsTrue(refSet.Remove("a"));
            Assert.AreEqual(0, refSet.Count);

            refSet.Add("a");
            Assert.IsTrue(refSet.Remove("a"));

            refSet.Add("b");
            refSet.Add("b");
            Assert.IsFalse(refSet.Remove("b"));
            Assert.IsTrue(refSet.Remove("b"));

            try
            {
                refSet.Remove("c");
                Assert.Fail();
            }
            catch (IllegalStateException)
            {
                // expected
            }

            try
            {
                refSet.Add("a");
                refSet.Remove("a");
                refSet.Remove("a");
                Assert.Fail();
            }
            catch (IllegalStateException)
            {
                // expected
            }
        }
    }
}
