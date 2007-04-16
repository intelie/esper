using System;
using System.Diagnostics;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.collection
{
	[TestFixture]
    public class TestSortedRefCountedSet 
    {
        private SortedRefCountedSet<String> refSet;
        private Random random = new Random();

        [SetUp]
        public virtual void setUp()
        {
            refSet = new SortedRefCountedSet<String>();
        }

        [Test]
        public virtual void testMaxMinValue()
        {
            refSet.Add("a");
            refSet.Add("b");
            Assert.AreEqual("ba", refSet.MaxValue + refSet.MinValue);
            refSet.Remove("a");
            Assert.AreEqual("bb", refSet.MaxValue + refSet.MinValue);
            refSet.Remove("b");
            Assert.IsNull(refSet.MaxValue);
            Assert.IsNull(refSet.MinValue);

            refSet.Add("b");
            refSet.Add("a");
            refSet.Add("d");
            refSet.Add("a");
            refSet.Add("c");
            refSet.Add("a");
            refSet.Add("c");
            Assert.AreEqual("da", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("d");
            Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("a");
            Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("a");
            Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("c");
            Assert.AreEqual("ca", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("c");
            Assert.AreEqual("ba", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("a");
            Assert.AreEqual("bb", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("b");
            Assert.IsNull(refSet.MaxValue);
            Assert.IsNull(refSet.MinValue);
        }

        [Test]
        public virtual void testAdd()
        {
            refSet.Add("a");
            refSet.Add("b");
            refSet.Add("a");
            refSet.Add("c");
            refSet.Add("a");

            Assert.AreEqual("c", refSet.MaxValue);
            Assert.AreEqual("a", refSet.MinValue);
        }

        [Test]
        public virtual void testRemove()
        {
            refSet.Add("a");
            refSet.Remove("a");
            Assert.IsNull(refSet.MaxValue);
            Assert.IsNull(refSet.MinValue);

            refSet.Add("a");
            refSet.Add("a");
            Assert.AreEqual("aa", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("a");
            Assert.AreEqual("aa", refSet.MaxValue + refSet.MinValue);

            refSet.Remove("a");
            Assert.IsNull(refSet.MaxValue);
            Assert.IsNull(refSet.MinValue);

            try
            {
                refSet.Remove("c");
                Assert.Fail();
            }
            catch (System.SystemException ex)
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
            catch (System.SystemException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testMemoryUse()
        {
            SortedRefCountedSet<Double> set = new SortedRefCountedSet<Double>();

            long memoryBefore = GC.GetTotalMemory(false);
            Process.GetCurrentProcess();
            
            GC.Collect();

            for (int i = 0; i < 2; i++)
            {
                performLoop(i, set);

                GC.Collect();

                long memoryAfter = GC.GetTotalMemory(false);

                log.Info(
                    "Memory before=" + memoryBefore + 
                    " after=" + memoryAfter + 
                    " delta=" + (memoryAfter - memoryBefore));

                //Assert.IsTrue(memoryBefore + 10000 <= memoryAfter);
            }
        }

        private void performLoop(int loop, SortedRefCountedSet<Double> set)
        {
            for (int i = 0; i < 1000; i++)
            {
                double price = 500000 + 4900 * random.NextDouble();
                set.Add(price);
                set.Remove(price);
            }
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
