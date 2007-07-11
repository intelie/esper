using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
    [TestFixture]
    public class TestRefCountedMap
    {
        private RefCountedMap<string, int?> refMap;

        [SetUp]
        public virtual void setUp()
        {
            refMap = new RefCountedMap<string, int?>();
            refMap["a"] = 100;
        }

        [Test]
        public void testPut()
        {
            try
            {
                refMap["a"] = 10;
                Assert.Fail();
            }
            catch (System.SystemException ex)
            {
                // Expected exception
            }

            try
            {
                refMap[null] = 10;
                Assert.Fail();
            }
            catch (ArgumentException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public void testGet()
        {
            int? val = refMap["b"];
            Assert.IsNull(val);

            val = refMap["a"];
            Assert.AreEqual(100, (int)val);
        }

        [Test]
        public void testReference()
        {
            refMap.Reference("a");

            try
            {
                refMap.Reference("b");
                Assert.Fail();
            }
            catch (System.SystemException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public void testDereference()
        {
            bool isLast = refMap.Dereference("a");
            Assert.IsTrue(isLast);

            refMap["b"] = 100;
            refMap.Reference("b");
            Assert.IsFalse(refMap.Dereference("b"));
            Assert.IsTrue(refMap.Dereference("b"));

            try
            {
                refMap.Dereference("b");
                Assert.Fail();
            }
            catch (System.SystemException ex)
            {
                // Expected exception
            }
        }

        [Test]
        public void testFlow()
        {
            refMap["b"] = -1;
            refMap.Reference("b");

            Assert.AreEqual(-1, (int)refMap["b"]);
            Assert.IsFalse(refMap.Dereference("b"));
            Assert.AreEqual(-1, (int)refMap["b"]);
            Assert.IsTrue(refMap.Dereference("b"));
            Object temp = refMap["b"];
            Assert.IsNull(refMap["b"]);

            refMap["b"] = 2;
            refMap.Reference("b");

            refMap["c"] = 3;
            refMap.Reference("c");

            refMap.Dereference("b");
            refMap.Reference("b");

            Assert.AreEqual(2, (int)refMap["b"]);
            Assert.IsFalse(refMap.Dereference("b"));
            Assert.IsTrue(refMap.Dereference("b"));
            Assert.IsNull(refMap["b"]);

            Assert.AreEqual(3, (int)refMap["c"]);
            Assert.IsFalse(refMap.Dereference("c"));
            Assert.AreEqual(3, (int)refMap["c"]);
            Assert.IsTrue(refMap.Dereference("c"));
            Assert.IsNull(refMap["c"]);
        }
    }
}