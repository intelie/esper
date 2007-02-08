using System;

using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
    [TestFixture]
    public class TestSingleEventIterator
    {
        private SingleEventIterator iterator;
        private EventBean eventBean;

        [SetUp]
        public virtual void setUp()
        {
            eventBean = SupportEventBeanFactory.createObject("a");
            iterator = new SingleEventIterator(eventBean);
        }

        [Test]
        public virtual void testNext()
        {
            Assert.IsTrue(iterator.MoveNext());
            Assert.AreEqual(eventBean, iterator.Current);
            Assert.IsFalse(iterator.MoveNext());
        }

        [Test]
        public virtual void testHasNext()
        {
            Assert.IsTrue(iterator.MoveNext());
            Assert.IsFalse(iterator.MoveNext());
        }
    }
}
