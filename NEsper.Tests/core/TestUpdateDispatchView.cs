using System;

using net.esper.client;
using net.esper.compat;
using net.esper.dispatch;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.core
{
    [TestFixture]
    public class TestUpdateDispatchView
    {
        private UpdateDispatchView updateDispatchView;
        private SupportUpdateListener listenerOne;
        private SupportUpdateListener listenerTwo;
        private DispatchService dispatchService;

        [SetUp]
        public virtual void setUp()
        {
            listenerOne = new SupportUpdateListener();
            listenerTwo = new SupportUpdateListener();

            UpdateEventHandlerSet listeners = new UpdateEventHandlerSet();
            listeners.AddListener(listenerOne);
            listeners.AddListener(listenerTwo);

            dispatchService = new DispatchServiceImpl();
            updateDispatchView = new UpdateDispatchView(listeners, dispatchService);
        }

        [Test]
        public void testUpdateOnceAndDispatch()
        {
            EventBean[] oldData = MakeEvents("old");
            EventBean[] newData = MakeEvents("new");
            updateDispatchView.Update(newData, oldData);

            Assert.IsFalse(listenerOne.IsInvoked || listenerTwo.IsInvoked);
            dispatchService.Dispatch();
            Assert.IsTrue(listenerOne.IsInvoked && listenerTwo.IsInvoked);
            Assert.AreSame(listenerOne.LastNewData[0], newData[0]);
            Assert.AreSame(listenerTwo.LastOldData[0], oldData[0]);
        }

        [Test]
        public void testUpdateTwiceAndDispatch()
        {
            EventBean[] oldDataOne = MakeEvents("old1");
            EventBean[] newDataOne = MakeEvents("new1");
            updateDispatchView.Update(newDataOne, oldDataOne);

            EventBean[] oldDataTwo = MakeEvents("old2");
            EventBean[] newDataTwo = MakeEvents("new2");
            updateDispatchView.Update(newDataTwo, oldDataTwo);

            Assert.IsFalse(listenerOne.IsInvoked || listenerTwo.IsInvoked);
            dispatchService.Dispatch();
            Assert.IsTrue(listenerOne.IsInvoked && listenerTwo.IsInvoked);
            Assert.IsTrue(listenerOne.LastNewData[1] == newDataTwo[0]);
            Assert.IsTrue(listenerTwo.LastOldData[1] == oldDataTwo[0]);
        }

        private EventBean[] MakeEvents(String text)
        {
            return new EventBean[] { SupportEventBeanFactory.CreateObject(text) };
        }
    }
}
