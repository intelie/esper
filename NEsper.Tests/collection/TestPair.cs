using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
    [TestFixture]
    public class TestPair
    {
        private Pair<String, String> pair1 = new Pair<String, String>("a", "b");
        private Pair<String, String> pair2 = new Pair<String, String>("a", "b");
        private Pair<String, String> pair3 = new Pair<String, String>("a", null);
        private Pair<String, String> pair4 = new Pair<String, String>(null, "b");
        private Pair<String, String> pair5 = new Pair<String, String>(null, null);

        [Test]
        public virtual void testHashCode()
        {
            Assert.IsTrue(pair1.GetHashCode() == ("a".GetHashCode() ^ "b".GetHashCode()));
            Assert.IsTrue(pair3.GetHashCode() == "a".GetHashCode());
            Assert.IsTrue(pair4.GetHashCode() == "b".GetHashCode());
            Assert.IsTrue(pair5.GetHashCode() == 0);

            Assert.IsTrue(pair1.GetHashCode() == pair2.GetHashCode());
            Assert.IsTrue(pair1.GetHashCode() != pair3.GetHashCode());
            Assert.IsTrue(pair1.GetHashCode() != pair4.GetHashCode());
            Assert.IsTrue(pair1.GetHashCode() != pair5.GetHashCode());
        }

        [Test]
        public virtual void testEquals()
        {
            Assert.AreEqual(pair2, pair1);
            Assert.AreEqual(pair1, pair2);

            Assert.AreNotSame(pair1, pair3);
            Assert.AreNotSame(pair3, pair1);
            Assert.AreNotSame(pair1, pair4);
            Assert.AreNotSame(pair2, pair5);
            Assert.AreNotSame(pair3, pair4);
            Assert.AreNotSame(pair4, pair5);

            Assert.AreSame(pair1, pair1);
            Assert.AreSame(pair2, pair2);
            Assert.AreSame(pair3, pair3);
            Assert.AreSame(pair4, pair4);
            Assert.AreSame(pair5, pair5);
        }
    }
}