using System;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestPerRowFunc
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;
        private EPStatement selectTestView;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public virtual void testCoalesceBeans()
        {
            epService.Initialize();
            String viewExpr =
		 "select coalesce(a.str, b.str) as myString, coalesce(a, b) as myBean" +
		 " from pattern [every (a=" + typeof(SupportBean).FullName +
		 "(string='s0') or b=" + typeof(SupportBean).FullName +
		 "(string='s1'))]";
            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            SupportBean _event = SendEvent("s0");
            EventBean eventReceived = testListener.assertOneGetNewAndReset();
            Assert.AreEqual("s0", eventReceived["myString"]);
            Assert.AreSame(_event, eventReceived["myBean"]);

            _event = SendEvent("s1");
            eventReceived = testListener.assertOneGetNewAndReset();
            Assert.AreEqual("s1", eventReceived["myString"]);
            Assert.AreSame(_event, eventReceived["myBean"]);
        }

        [Test]
        public virtual void testCoalesceLong()
        {
            setupCoalesce("coalesce(longBoxed, intBoxed, shortBoxed)");
            Assert.AreEqual(typeof(long?), selectTestView.EventType.GetPropertyType("result"));

            SendEvent(1L, 2, (short)3);
            Assert.AreEqual(1L, testListener.assertOneGetNewAndReset()["result"]);

            sendBoxedEvent(null, 2, null);
            Assert.AreEqual(2L, testListener.assertOneGetNewAndReset()["result"]);

            sendBoxedEvent(null, null, Int16.Parse("3"));
            Assert.AreEqual(3L, testListener.assertOneGetNewAndReset()["result"]);

            sendBoxedEvent(null, null, null);
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["result"]);
        }

        [Test]
        public virtual void testCoalesceDouble()
        {
            setupCoalesce("coalesce(null, byteBoxed, shortBoxed, intBoxed, longBoxed, floatBoxed, doubleBoxed)");
            Assert.AreEqual(typeof(double?), selectTestView.EventType.GetPropertyType("result"));

            sendEventWithDouble(null, null, null, null, null, null);
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["result"]);

            sendEventWithDouble(null, Int16.Parse("2"), null, null, null, 1d);
            Assert.AreEqual(2d, testListener.assertOneGetNewAndReset()["result"]);

            sendEventWithDouble(null, null, null, null, null, 100d);
            Assert.AreEqual(100d, testListener.assertOneGetNewAndReset()["result"]);

            sendEventWithDouble(null, null, null, null, 10f, 100d);
            Assert.AreEqual(10d, testListener.assertOneGetNewAndReset()["result"]);

            sendEventWithDouble(null, null, 1, 5L, 10f, 100d);
            Assert.AreEqual(1d, testListener.assertOneGetNewAndReset()["result"]);

            sendEventWithDouble((sbyte)SByte.Parse("3"), null, null, null, null, null);
            Assert.AreEqual(3d, testListener.assertOneGetNewAndReset()["result"]);

            sendEventWithDouble(null, null, null, 5L, 10f, 100d);
            Assert.AreEqual(5d, testListener.assertOneGetNewAndReset()["result"]);
        }

        private void setupCoalesce(String coalesceExpr)
        {
            epService.Initialize();
            String viewExpr = "select " + coalesceExpr + " as result" + " from " + typeof(SupportBean).FullName + ".win:length(1000) ";
            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);
        }

        [Test]
        public virtual void testCoalesceInvalid()
        {
            String viewExpr =
                "select coalesce(null, null) as result" +
                " from " + typeof(SupportBean).FullName + ".win:length(3) ";
            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            Assert.AreEqual(null, selectTestView.EventType.GetPropertyType("result"));

            tryCoalesceInvalid("coalesce(intPrimitive)");
            tryCoalesceInvalid("coalesce(intPrimitive, str)");
            tryCoalesceInvalid("coalesce(intPrimitive, xxx)");
            tryCoalesceInvalid("coalesce(intPrimitive, boolBoxed)");
            tryCoalesceInvalid("coalesce(charPrimitive, longBoxed)");
            tryCoalesceInvalid("coalesce(charPrimitive, str, str)");
            tryCoalesceInvalid("coalesce(str, longBoxed)");
            tryCoalesceInvalid("coalesce(null, longBoxed, string)");
            tryCoalesceInvalid("coalesce(null, null, boolBoxed, 1l)");
        }

        private void tryCoalesceInvalid(String coalesceExpr)
        {
            String viewExpr = "select " + coalesceExpr + " as result" + " from " + typeof(SupportBean).FullName + ".win:length(3) ";

            try
            {
                selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            }
            catch (EPStatementException ex)
            {
                // expected
            }
        }

        [Test]
        public virtual void testMinMaxEventType()
        {
            setUpMinMax();
            EventType type = selectTestView.EventType;
            log.Debug(".testGetEventType properties=" + CollectionHelper.Render(type.PropertyNames));
            Assert.AreEqual(typeof(long?), type.GetPropertyType("myMax"));
            Assert.AreEqual(typeof(long?), type.GetPropertyType("myMin"));
            Assert.AreEqual(typeof(long?), type.GetPropertyType("myMinEx"));
            Assert.AreEqual(typeof(long?), type.GetPropertyType("myMaxEx"));
        }

        [Test]
        public virtual void testMinMaxWindowStats()
        {
            setUpMinMax();
            testListener.reset();

            SendEvent(10, 20, (short)4);
            EventBean received = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(20L, received["myMax"]);
            Assert.AreEqual(10L, received["myMin"]);
            Assert.AreEqual(4L, received["myMinEx"]);
            Assert.AreEqual(20L, received["myMaxEx"]);

            SendEvent(-10, -20, (short)(-30));
            received = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(-10L, received["myMax"]);
            Assert.AreEqual(-20L, received["myMin"]);
            Assert.AreEqual(-30L, received["myMinEx"]);
            Assert.AreEqual(-10L, received["myMaxEx"]);
        }

        [Test]
        public virtual void testOperators()
        {
            String viewExpr =
                "select longBoxed % intBoxed as myMod " + 
                " from " + typeof(SupportBean).FullName + ".win:length(3) where not(longBoxed > intBoxed)";
            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            SendEvent(1, 1, (short)0);
            Assert.AreEqual(0L, testListener.LastNewData[0]["myMod"]);
            testListener.reset();

            SendEvent(2, 1, (short)0);
            Assert.IsFalse(testListener.getAndClearIsInvoked());

            SendEvent(2, 3, (short)0);
            Assert.AreEqual(2L, testListener.LastNewData[0]["myMod"]);
            testListener.reset();
        }

        [Test]
        public virtual void testConcat()
        {
            String viewExpr =
                "select p00 || p01 as c1, p00 || p01 || p02 as c2, p00 || '|' || p01 as c3" + 
                " from " + typeof(SupportBean_S0).FullName + ".win:length(10)";
            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);

            epService.EPRuntime.SendEvent(new SupportBean_S0(1, "a", "b", "c"));
            assertConcat("ab", "abc", "a|b");

            epService.EPRuntime.SendEvent(new SupportBean_S0(1, null, "b", "c"));
            assertConcat(null, null, null);

            epService.EPRuntime.SendEvent(new SupportBean_S0(1, "", "b", "c"));
            assertConcat("b", "bc", "|b");

            epService.EPRuntime.SendEvent(new SupportBean_S0(1, "123", null, "c"));
            assertConcat(null, null, null);

            epService.EPRuntime.SendEvent(new SupportBean_S0(1, "123", "456", "c"));
            assertConcat("123456", "123456c", "123|456");

            epService.EPRuntime.SendEvent(new SupportBean_S0(1, "123", "456", null));
            assertConcat("123456", null, "123|456");
        }

        private void setUpMinMax()
        {
            String viewExpr = 
                "select max(longBoxed, intBoxed) as myMax, " +
                "max(longBoxed, intBoxed, shortBoxed) as myMaxEx," +
                "min(longBoxed, intBoxed) as myMin," +
                "min(longBoxed, intBoxed, shortBoxed) as myMinEx" + 
                " from " + typeof(SupportBean).FullName + ".win:length(3) ";
            selectTestView = epService.EPAdministrator.createEQL(viewExpr);
            selectTestView.AddListener(testListener);
        }

        private SupportBean SendEvent(String stringValue)
        {
            SupportBean bean = new SupportBean();
            bean.str = stringValue;
            epService.EPRuntime.SendEvent(bean);
            return bean;
        }

        private void SendEvent(long longBoxed, int intBoxed, short shortBoxed)
        {
            sendBoxedEvent(longBoxed, intBoxed, shortBoxed);
        }

        private void sendBoxedEvent(
            long? longBoxed,
            int? intBoxed,
            short? shortBoxed)
        {
            SupportBean bean = new SupportBean();
            bean.longBoxed = longBoxed;
            bean.intBoxed = intBoxed;
            bean.shortBoxed = shortBoxed;
            epService.EPRuntime.SendEvent(bean);
        }

        private void sendEventWithDouble(
            sbyte? byteBoxed,
            short? shortBoxed,
            int? intBoxed,
            long? longBoxed, 
            Single? floatBoxed, 
            Double? doubleBoxed)
        {
            SupportBean bean = new SupportBean();
            bean.byteBoxed = byteBoxed;
            bean.shortBoxed = shortBoxed;
            bean.intBoxed = intBoxed;
            bean.longBoxed = longBoxed;
            bean.floatBoxed = floatBoxed;
            bean.doubleBoxed = doubleBoxed;
            epService.EPRuntime.SendEvent(bean);
        }

        private void assertConcat(String c1, String c2, String c3)
        {
            EventBean _event = testListener.LastNewData[0];
            Assert.AreEqual(c1, _event["c1"]);
            Assert.AreEqual(c2, _event["c2"]);
            Assert.AreEqual(c3, _event["c3"]);
            testListener.reset();
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
