using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.std
{
    [TestFixture]
    public class TestSizeView
    {
        private SizeView myView;
        private SupportBeanClassView childView;

        [SetUp]
        public virtual void setUp()
        {
            // Set up length window view and a test child view
            myView = new SizeView();
            myView.ViewServiceContext = SupportViewContextFactory.makeContext();

            childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.AddView(childView);
        }

        // Check values against Microsoft Excel computed values
        [Test]
        public virtual void testViewPush()
        {
            // Set up a feed for the view under test - it will have a depth of 5 trades
            SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportBean_A), 5);
            stream.AddView(myView);

            checkIterator(0);

            // View just counts the number of events received, removing those removed in the prior view as old data
            stream.Insert(makeBeans("a", 1));
            checkOldData(0);
            checkNewData(1);
            checkIterator(1);

            stream.Insert(makeBeans("b", 2));
            checkOldData(1);
            checkNewData(3);
            checkIterator(3);

            // The EventStream has a depth of 3, it will expire the first message now, ie. will keep the size of 3, always
            stream.Insert(makeBeans("c", 1));
            checkOldData(3);
            checkNewData(4);
            checkIterator(4);

            stream.Insert(makeBeans("d", 1));
            checkOldData(4);
            checkNewData(5);
            checkIterator(5);

            stream.Insert(makeBeans("e", 2));
            Assert.IsNull(childView.LastNewData);
            Assert.IsNull(childView.LastOldData);
            checkIterator(5);

            stream.Insert(makeBeans("f", 1));
            Assert.IsNull(childView.LastNewData);
            Assert.IsNull(childView.LastOldData);
            checkIterator(5);
        }

        [Test]
        public virtual void testUpdate()
        {
            // View should not post events if data didn't change
            myView.Update(makeBeans("f", 1), null);

            checkOldData(0);
            checkNewData(1);
            childView.LastNewData = null;
            childView.LastOldData = null;

            myView.Update(makeBeans("f", 1), makeBeans("f", 1));

            Assert.IsNull(childView.LastNewData);
            Assert.IsNull(childView.LastOldData);
        }

        [Test]
        public virtual void testViewAttachesTo()
        {
            // Should attach to anything
            SizeView view = new SizeView();
            SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            Assert.IsTrue(view.AttachesTo(parent) == null);
        }

        [Test]
        public virtual void testSchema()
        {
            SizeView view = new SizeView();
            view.ViewServiceContext = SupportViewContextFactory.makeContext();

            EventType eventType = view.EventType;
            Assert.AreEqual(typeof(long), eventType.GetPropertyType(ViewFieldEnum.SIZE_VIEW__SIZE.Name));
        }

        [Test]
        public virtual void testCopyView()
        {
            ViewSupport.shallowCopyView(myView);
        }

        private void checkNewData(long expectedSize)
        {
            EventBean[] newData = childView.LastNewData;
            checkData(newData, expectedSize);
            childView.LastNewData = null;
        }

        private void checkOldData(long expectedSize)
        {
            EventBean[] oldData = childView.LastOldData;
            checkData(oldData, expectedSize);
            childView.LastOldData = null;
        }

        private void checkData(EventBean[] data, long expectedSize)
        {
            // The view posts in its update data always just one object containing the size
            Assert.AreEqual(1, data.Length);
            Int64 actualSize = (Int64)data[0][ViewFieldEnum.SIZE_VIEW__SIZE.Name];
            Assert.AreEqual((long)expectedSize, (long)actualSize);
        }

        private void checkIterator(long expectedSize)
        {
            IEnumerator<EventBean> iterator = myView.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
            EventBean eventBean = iterator.Current;
            Int64 actualSize = (Int64)eventBean[ViewFieldEnum.SIZE_VIEW__SIZE.Name];
            Assert.AreEqual(expectedSize, actualSize);
        }

        private EventBean[] makeBeans(String id, int numTrades)
        {
            EventBean[] trades = new EventBean[numTrades];
            for (int i = 0; i < numTrades; i++)
            {
                SupportBean_A bean = new SupportBean_A(id + i);
                trades[i] = SupportEventBeanFactory.createObject(bean);
            }
            return trades;
        }
    }
}
