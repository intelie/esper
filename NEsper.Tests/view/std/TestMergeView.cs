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
    public class TestMergeView
    {
        private MergeView myView;
        private SupportBeanClassView childView;

        [SetUp]
        public virtual void setUp()
        {
            // Set up length window view and a test child view
            myView = new MergeView(new String[] { "symbol" });
            myView.ViewServiceContext = SupportViewContextFactory.makeContext();

            childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.AddView(childView);
        }

        [Test]
        public virtual void testViewPush()
        {
            SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 2);
            stream.AddView(myView);

            EventBean[] tradeBeans = new EventBean[10];

            // Send events, expect just forwarded
            tradeBeans[0] = makeTradeBean("IBM", 70);
            stream.Insert(tradeBeans[0]);

            SupportViewDataChecker.checkOldData(childView, null);
            SupportViewDataChecker.checkNewData(childView, new EventBean[] { tradeBeans[0] });

            // Send some more events, expect forwarded
            tradeBeans[1] = makeTradeBean("GE", 90);
            tradeBeans[2] = makeTradeBean("CSCO", 20);
            stream.Insert(new EventBean[] { tradeBeans[1], tradeBeans[2] });

            SupportViewDataChecker.checkOldData(childView, new EventBean[] { tradeBeans[0] });
            SupportViewDataChecker.checkNewData(childView, new EventBean[] { tradeBeans[1], tradeBeans[2] });
        }

        [Test]
        public virtual void testViewAttachesTo()
        {
            // Should attach to anything event if the field does not exists
            MergeView view = new MergeView(new String[] { "dummy" });
            SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            Assert.IsTrue(view.AttachesTo(parent) == null);
        }

        [Test]
        public virtual void testCopyView()
        {
            EventType someEventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
            SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.Parent = parent;
            myView.EventType = someEventType;

            MergeView copied = (MergeView)ViewSupport.shallowCopyView(myView);
            Assert.AreEqual(myView.GroupFieldNames, copied.GroupFieldNames);
            Assert.AreEqual(myView.EventType, someEventType);
        }

        [Test]
        public virtual void testEventType()
        {
            SupportBeanClassView topView = new SupportBeanClassView(typeof(SupportBean));
            GroupByView groupByView = new GroupByView("intPrimitive");

            SizeView sizeView = new SizeView();
            sizeView.ViewServiceContext = SupportViewContextFactory.makeContext();

            MergeView mergeView = new MergeView(new String[] { "intPrimitive" });
            mergeView.ViewServiceContext = SupportViewContextFactory.makeContext();

            topView.AddView(groupByView);
            groupByView.AddView(sizeView);
            sizeView.AddView(mergeView);

            IList<View> parents = new List<View>();
            parents.Add(topView);
            parents.Add(groupByView);
            mergeView.ParentAware = parents;

            EventType eventType = mergeView.EventType;
            Assert.AreEqual(typeof(int), eventType.GetPropertyType("intPrimitive"));
        }

        private EventBean makeTradeBean(String symbol, int price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, "");
            return SupportEventBeanFactory.createObject(bean);
        }
    }
}