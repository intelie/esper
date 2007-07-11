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
        private EventBean _eventBean;

        [SetUp]
        public virtual void setUp()
        {
            _eventBean = SupportEventBeanFactory.CreateObject("a");
            iterator = new SingleEventIterator(_eventBean);
        }

        [Test]
        public void testNext()
        {
            Assert.IsTrue(iterator.MoveNext());
            Assert.AreEqual(_eventBean, iterator.Current);
            Assert.IsFalse(iterator.MoveNext());
        }

        [Test]
        public void testHasNext()
        {
            Assert.IsTrue(iterator.MoveNext());
            Assert.IsFalse(iterator.MoveNext());
        }
    }
}
