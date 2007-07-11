using System;
using System.Collections.Generic;
using System.Threading;

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
    public class TestViewTimeBatchMean
    {
        private static String SYMBOL = "CSCO.O";

        private EPServiceProvider epService;
        private SupportUpdateListener testListener;
        private EPStatement timeBatchMean;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            // Set up a 2 second time window
            timeBatchMean = epService.EPAdministrator.CreateEQL("select * from " + typeof(SupportMarketDataBean).FullName + "(symbol='" + SYMBOL + "').win:time_batch(2).stat:uni('volume')");
            timeBatchMean.AddListener(testListener);
        }

        [Test]
        public void testTimeBatchMean()
        {
            testListener.Reset();
            checkMeanIterator(Double.NaN);
            Assert.IsFalse(testListener.IsInvoked);

            // Send a couple of events, check mean
            SendEvent(SYMBOL, 500);
            SendEvent(SYMBOL, 1000);
            checkMeanIterator(Double.NaN); // The iterator is still showing no result yet as no batch was released
            Assert.IsFalse(testListener.IsInvoked); // No new data posted to the iterator, yet

            // Sleep for 1 seconds
            sleep(1000);

            // Send more events
            SendEvent(SYMBOL, 1000);
            SendEvent(SYMBOL, 1200);
            checkMeanIterator(Double.NaN); // The iterator is still showing no result yet as no batch was released
            Assert.IsFalse(testListener.IsInvoked);

            // Sleep for 1.5 seconds, thus triggering a new batch
            sleep(1500);
            checkMeanIterator(925); // Now the statistics view received the first batch
            Assert.IsTrue(testListener.IsInvoked); // Listener has been invoked
            checkMeanListener(925);

            // Send more events
            SendEvent(SYMBOL, 500);
            SendEvent(SYMBOL, 600);
            SendEvent(SYMBOL, 1000);
            checkMeanIterator(925); // The iterator is still showing the old result as next batch not released
            Assert.IsFalse(testListener.IsInvoked);

            // Sleep for 1 seconds
            sleep(1000);

            // Send more events
            SendEvent(SYMBOL, 200);
            checkMeanIterator(925);
            Assert.IsFalse(testListener.IsInvoked);

            // Sleep for 1.5 seconds, thus triggering a new batch
            sleep(1500);
            checkMeanIterator(2300d / 4d); // Now the statistics view received the second batch, the mean now is over all events
            Assert.IsTrue(testListener.IsInvoked); // Listener has been invoked
            checkMeanListener(2300d / 4d);

            // Send more events
            SendEvent(SYMBOL, 1200);
            checkMeanIterator(2300d / 4d);
            Assert.IsFalse(testListener.IsInvoked);

            // Sleep for 2 seconds, no events received anymore
            sleep(2000);
            checkMeanIterator(1200); // statistics view received the third batch
            Assert.IsTrue(testListener.IsInvoked); // Listener has been invoked
            checkMeanListener(1200);
        }

        private void SendEvent(String symbol, long volume)
        {
            SupportMarketDataBean _event = new SupportMarketDataBean(symbol, 0, volume, "");
            epService.EPRuntime.SendEvent(_event);
        }

        private void checkMeanListener(double meanExpected)
        {
            Assert.IsTrue(testListener.LastNewData.Length == 1);
            EventBean listenerValues = testListener.LastNewData[0];
            checkValue(listenerValues, meanExpected);
            testListener.Reset();
        }

        private void checkMeanIterator(double meanExpected)
        {
            IEnumerator<EventBean> iterator = timeBatchMean.GetEnumerator();
            Assert.IsTrue(iterator.MoveNext());
            checkValue(iterator.Current, meanExpected);
            Assert.IsFalse(iterator.MoveNext());
        }

        private void checkValue(EventBean values, double avgE)
        {
            double avg = GetDoubleValue(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE, values);
            Assert.IsTrue(DoubleValueAssertionUtil.Equals(avg, avgE, 6));
        }

        private double GetDoubleValue(ViewFieldEnum field, EventBean _event)
        {
            return (double)_event[field.Name];
        }

        private void sleep(int msec)
        {
            try
            {
                Thread.Sleep(new System.TimeSpan((Int64)10000 * msec));
            }
            catch (ThreadInterruptedException e)
            {
            }
        }
    }
}
