using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view
{
    [TestFixture]
    public class TestZeroDepthStream
    {
        private ZeroDepthStream stream;
        private SupportSchemaNeutralView testChildView;
        private EventType eventType;

        private EventBean _eventBean;

        [SetUp]
        public virtual void setUp()
        {
            eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean_A));

            stream = new ZeroDepthStream(eventType);

            testChildView = new SupportSchemaNeutralView();
            stream.AddView(testChildView);
            testChildView.Parent = stream;

            _eventBean = SupportEventBeanFactory.CreateObject(new SupportBean_A("a1"));
        }

        [Test]
        public virtual void testInsert()
        {
            testChildView.ClearLastNewData();
            stream.Insert(_eventBean);

            Assert.IsTrue(testChildView.LastNewData != null);
            Assert.AreEqual(1, testChildView.LastNewData.Length);
            Assert.AreEqual(_eventBean, testChildView.LastNewData[0]);

            // Remove view
            testChildView.ClearLastNewData();
            stream.RemoveView(testChildView);
            stream.Insert(_eventBean);
            Assert.IsTrue(testChildView.LastNewData == null);
        }
    }
}
