using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestViewLengthWindowStats
    {
        private static String SYMBOL = "CSCO.O";

        private EPServiceProvider epService;
        private SupportUpdateListener testListener;
        private EPStatement priceStatsView;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            String viewExpr = "select * from " + typeof(SupportMarketDataBean).FullName + "(symbol='" + SYMBOL + "').win:length(3).stat:uni('price')";
            priceStatsView = epService.EPAdministrator.createEQL(viewExpr);
            priceStatsView.AddListener(testListener);
        }

        [Test]
        public virtual void testWindowStats()
        {
            testListener.reset();

            SendEvent(SYMBOL, 100);
            checkOld(0, 0, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
            checkNew(1, 100, 100, 0, Double.NaN, Double.NaN);

            SendEvent(SYMBOL, 100.5);
            checkOld(1, 100, 100, 0, Double.NaN, Double.NaN);
            checkNew(2, 200.5, 100.25, 0.25, 0.353553391, 0.125);

            SendEvent("DUMMY", 100.5);
            Assert.IsTrue(testListener.LastNewData == null);
            Assert.IsTrue(testListener.LastOldData == null);

            SendEvent(SYMBOL, 100.7);
            checkOld(2, 200.5, 100.25, 0.25, 0.353553391, 0.125);
            checkNew(3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);

            SendEvent(SYMBOL, 100.6);
            checkOld(3, 301.2, 100.4, 0.294392029, 0.360555128, 0.13);
            checkNew(3, 301.8, 100.6, 0.081649658, 0.1, 0.01);

            SendEvent(SYMBOL, 100.9);
            checkOld(3, 301.8, 100.6, 0.081649658, 0.1, 0.01);
            checkNew(3, 302.2, 100.733333333, 0.124721913, 0.152752523, 0.023333333);
        }

        private void SendEvent(String symbol, double price)
        {
            SupportMarketDataBean _event = new SupportMarketDataBean(symbol, price, 0L, "");
            epService.EPRuntime.SendEvent(_event);
        }

        private void checkNew(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
        {
            IEnumerator<EventBean> iterator = priceStatsView.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
            checkValues(iterator.Current, countE, sumE, avgE, stdevpaE, stdevE, varianceE);
            Assert.IsFalse(iterator.MoveNext());

            Assert.IsTrue(testListener.LastNewData.Length == 1);
            EventBean childViewValues = testListener.LastNewData[0];
            checkValues(childViewValues, countE, sumE, avgE, stdevpaE, stdevE, varianceE);

            testListener.reset();
        }

        private void checkOld(long countE, double sumE, double avgE, double stdevpaE, double stdevE, double varianceE)
        {
            Assert.IsTrue(testListener.LastOldData.Length == 1);
            EventBean childViewValues = testListener.LastOldData[0];
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
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(sum, sumE, 6));
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(avg, avgE, 6));
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdevpa, stdevpaE, 6));
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(stdev, stdevE, 6));
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(variance, varianceE, 6));
        }

        private double getDoubleValue(ViewFieldEnum field, EventBean values)
        {
            return (Double)values[field.Name];
        }

        private long getLongValue(ViewFieldEnum field, EventBean values)
        {
            return (Int64)values[field.Name];
        }
    }
}
