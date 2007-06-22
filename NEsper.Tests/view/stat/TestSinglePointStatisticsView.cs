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
    public class TestSinglePointStatisticsView
    {
        internal UnivariateStatisticsView myView;
        internal SupportBeanClassView childView;

        [SetUp]
        public virtual void setUp()
        {
            // Set up sum view and a test child view
            myView = new UnivariateStatisticsView("price");
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

            // Send two events to the stream
            Assert.IsTrue(childView.LastNewData == null);

            // Send a first event, checkNew values
            EventBean marketData = makeBean("IBM", 10, 0);
            stream.Insert(marketData);
            checkOld(0, 0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
            checkNew(1, 10, 10, 0, Double.NaN, Double.NaN);

            // Send a second event, checkNew values
            marketData = makeBean("IBM", 12, 0);
            stream.Insert(marketData);
            checkOld(1, 10, 10, 0, Double.NaN, Double.NaN);
            checkNew(2, 22, 11, 1, Math.Sqrt(2.0), 2);

            // Send a third event, checkNew values
            marketData = makeBean("IBM", 9.5, 0);
            stream.Insert(marketData);
            checkOld(2, 22, 11, 1, Math.Sqrt(2.0), 2);
            checkNew(3, 31.5, 10.5, 1.08012345, 1.322875656, 1.75);

            // Send a 4th event, this time the first event should be gone, checkNew values
            marketData = makeBean("IBM", 9, 0);
            stream.Insert(marketData);
            checkOld(3, 31.5, 10.5, 1.08012345, 1.322875656, 1.75);
            checkNew(3, 30.5, 10.16666667, 1.312334646, 1.607275127, 2.583333333);
        }

        [Test]
        public virtual void testViewAttachesTo()
        {
            UnivariateStatisticsView view = new UnivariateStatisticsView("symbol");

            // The symbol field in the parent is not a number, expect an error on attach
            SupportBeanClassView parent = new SupportBeanClassView(typeof(SupportMarketDataBean));
            Assert.IsTrue(view.AttachesTo(parent) != null);

            // Try a non-existing field name
            view = new UnivariateStatisticsView("dummy");
            Assert.IsTrue(view.AttachesTo(parent) != null);
        }

        [Test]
        public virtual void testGetSchema()
        {
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name) == typeof(long));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name) == typeof(double));
            Assert.IsTrue(myView.EventType.GetPropertyType(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name) == typeof(double));
        }

        [Test]
        public virtual void testCopyView()
        {
            UnivariateStatisticsView copied = (UnivariateStatisticsView)ViewSupport.ShallowCopyView(myView);
            Assert.IsTrue(myView.FieldName.Equals(copied.FieldName));
        }

        private void checkNew(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
        {
            IEnumerator<EventBean> iterator = myView.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
            checkValues(iterator.Current, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
            Assert.IsFalse(iterator.MoveNext());

            Assert.IsTrue(childView.LastNewData.Length == 1);
            EventBean childViewValues = childView.LastNewData[0];
            checkValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
        }

        private void checkOld(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
        {
            Assert.IsTrue(childView.LastOldData.Length == 1);
            EventBean childViewValues = childView.LastOldData[0];
            checkValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
        }

        private void checkValues(EventBean values, long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
        {
            long count = getLongValue(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT, values);
            double sum = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM, values);
            double avg = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE, values);
            double stdevpa = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA, values);
            double stdev = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV, values);
            double variance = getDoubleValue(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE, values);

            Assert.AreEqual(count, countE);
            Assert.AreEqual(sum, sumE);
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(avg, avgE, 6));
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdevpa, stdevpaE, 6));
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdev, stdevE, 6));
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(variance, varianceE, 6));
        }

        private double getDoubleValue(ViewFieldEnum field, EventBean eventBean)
        {
            return (Double)eventBean[field.Name];
        }

        private long getLongValue(ViewFieldEnum field, EventBean eventBean)
        {
            return (Int64)eventBean[field.Name];
        }

        private EventBean makeBean(String symbol, double price, long volume)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, "");
            return SupportEventBeanFactory.CreateObject(bean);
        }
    }
}
