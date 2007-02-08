using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;
using net.esper.support.view;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat
{
    [TestFixture]
    public class TestWeightedAverageView
    {
        private WeightedAverageView myView;
        private SupportBeanClassView childView;

        [SetUp]
        public virtual void setUp()
        {
            // Set up sum view and a test child view
            myView = new WeightedAverageView("price", "volume");
            myView.ViewServiceContext = SupportViewContextFactory.makeContext();

            childView = new SupportBeanClassView(typeof(SupportMarketDataBean));
            myView.AddView(childView);
        }

        // Check values against Microsoft Excel computed values
        [Test]
        public virtual void testViewComputedValues()
        {
            // Set up feed for sum view
            SupportStreamImpl stream = new SupportStreamImpl(typeof(SupportMarketDataBean), 3);
            stream.AddView(myView);

            // Send a first event, check values
            EventBean marketData = makeBean("IBM", 10, 1000);
            stream.Insert(marketData);
            checkOld(Double.NaN);
            checkNew(10);

            // Send a second event, check values
            marketData = makeBean("IBM", 11, 2000);
            stream.Insert(marketData);
            checkOld(10);
            checkNew(10.66666667);

            // Send a third event, check values
            marketData = makeBean("IBM", 10.5, 1500);
            stream.Insert(marketData);
            checkOld(10.66666667);
            checkNew(10.61111111);

            // Send a 4th event, this time the first event should be gone
            marketData = makeBean("IBM", 9.5, 600);
            stream.Insert(marketData);
            checkOld(10.61111111);
            checkNew(10.59756098);
        }

        [Test]
        public virtual void testViewAttachesTo()
        {
            WeightedAverageView view = new WeightedAverageView("symbol", "price");
            SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));

            // The symbol field in the parent is not a number, expect an error on attach
            Assert.IsTrue(view.AttachesTo(parent) != null);

            // Try a non-existing field name
            view = new WeightedAverageView("dummy", "price");
            Assert.IsTrue(view.AttachesTo(parent) != null);

            // Try a success
            view = new WeightedAverageView("price", "volume");
            Assert.IsTrue(view.AttachesTo(parent) == null);
        }

        [Test]
        public virtual void testGetSchema()
        {
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name) == typeof(double));
        }

        [Test]
        public virtual void testCopyView()
        {
            WeightedAverageView copied = (WeightedAverageView)ViewSupport.shallowCopyView(myView);
            Assert.IsTrue(myView.FieldNameWeight.Equals(copied.FieldNameWeight));
            Assert.IsTrue(myView.FieldNameX.Equals(copied.FieldNameX));
        }

        private void checkNew(double avgE)
        {
            IEnumerator<EventBean> iterator = myView.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
            checkValues(iterator.Current, avgE);
            Assert.IsFalse(iterator.MoveNext());

            Assert.IsTrue(childView.LastNewData.Length == 1);
            EventBean childViewValues = childView.LastNewData[0];
            checkValues(childViewValues, avgE);
        }

        private void checkOld(double avgE)
        {
            Assert.IsTrue(childView.LastOldData.Length == 1);
            EventBean childViewValues = childView.LastOldData[0];
            checkValues(childViewValues, avgE);
        }

        private void checkValues(EventBean values, double avgE)
        {
            double avg = getDoubleValue(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE, values);

            Assert.IsTrue(DoubleValueAssertionUtil.Equals(avg, avgE, 6));
        }

        private double getDoubleValue(ViewFieldEnum field, EventBean eventBean)
        {
            return (Double)eventBean[field.Name];
        }

        private EventBean makeBean(String symbol, double price, long volume)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
            return SupportEventBeanFactory.createObject(bean);
        }
    }
}
