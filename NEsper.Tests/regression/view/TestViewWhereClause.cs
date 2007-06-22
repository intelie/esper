using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestViewWhereClause
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;
        private EPStatement testView;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            String viewExpr =
                "select * from " + typeof(SupportMarketDataBean).FullName +
                ".win:length(3) where Symbol='CSCO'";
            testView = epService.EPAdministrator.CreateEQL(viewExpr);
            testListener = new SupportUpdateListener();
            testView.AddListener(testListener);
        }

        [Test]
        public virtual void testWhere()
        {
            sendMarketDataEvent("IBM");
            Assert.IsFalse(testListener.GetAndClearIsInvoked());

            sendMarketDataEvent("CSCO");
            Assert.IsTrue(testListener.GetAndClearIsInvoked());

            sendMarketDataEvent("IBM");
            Assert.IsFalse(testListener.GetAndClearIsInvoked());

            sendMarketDataEvent("CSCO");
            Assert.IsTrue(testListener.GetAndClearIsInvoked());
        }

        [Test]
        public virtual void testWhereNumericType()
        {
            String viewExpr =
                "select " +
                " intPrimitive + longPrimitive as p1," +
                " intPrimitive * doublePrimitive as p2," +
                " floatPrimitive / doublePrimitive as p3" +
                " from " + typeof(SupportBean).FullName +
                ".win:length(3) where " +
                "intPrimitive=longPrimitive and intPrimitive=doublePrimitive and floatPrimitive=doublePrimitive";

            testView = epService.EPAdministrator.CreateEQL(viewExpr);
            testListener = new SupportUpdateListener();
            testView.AddListener(testListener);

            sendSupportBeanEvent(1, 2, 3, 4);
            Assert.IsFalse(testListener.IsInvoked);

            sendSupportBeanEvent(2, 2, 2, 2);
            EventBean _event = testListener.GetAndResetLastNewData()[0];
            Assert.AreEqual(typeof(long?), _event.EventType.GetPropertyType("p1"));
            Assert.AreEqual(4L, _event["p1"]);
            Assert.AreEqual(typeof(double?), _event.EventType.GetPropertyType("p2"));
            Assert.AreEqual(4d, _event["p2"]);
            Assert.AreEqual(typeof(double?), _event.EventType.GetPropertyType("p3"));
            Assert.AreEqual(1d, _event["p3"]);
        }

        private void sendMarketDataEvent(String symbol)
        {
            SupportMarketDataBean _event = new SupportMarketDataBean(symbol, 0, 0L, "");
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendSupportBeanEvent(int intPrimitive, long longPrimitive, float floatPrimitive, double doublePrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.SetIntPrimitive(intPrimitive);
            _event.SetLongPrimitive(longPrimitive);
            _event.SetFloatPrimitive(floatPrimitive);
            _event.SetDoublePrimitive(doublePrimitive);
            epService.EPRuntime.SendEvent(_event);
        }
    }
}
