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
    public class TestInBetweenLikeExpr
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public virtual void testInStringExpr()
        {
            tryString("string in ('a', 'b', 'c')", new String[] { "0", "a", "b", "c", "d", null }, new bool[] { false, true, true, true, false, false });

            tryString("string in ('a')", new String[] { "0", "a", "b", "c", "d", null }, new bool[] { false, true, false, false, false, false });

            tryString("string in ('a', 'b')", new String[] { "0", "b", "a", "c", "d", null }, new bool[] { false, true, true, false, false, false });

            tryString("string in ('a', null)", new String[] { "0", "b", "a", "c", "d", null }, new bool[] { false, false, true, false, false, true });

            tryString("string in (null)", new String[] { "0", null, "b" }, new bool[] { false, true, false });

            tryString("string not in ('a', 'b', 'c')", new String[] { "0", "a", "b", "c", "d", null }, new bool[] { true, false, false, false, true, true });

            tryString("string not in (null)", new String[] { "0", null, "b" }, new bool[] { true, false, true });
        }

        [Test]
        public virtual void testBetweenStringExpr()
        {
            String[] input = null;
            bool[] result = null;

            input = new String[] { "0", "a1", "a10", "c", "d", null, "a0", "b9", "b90" };
            result = new bool[] { false, true, true, false, false, false, true, true, false };
            tryString("string between 'a0' and 'b9'", input, result);
            tryString("string between 'b9' and 'a0'", input, result);

            tryString("string between null and 'b9'", new String[] { "0", null, "a0", "b9" }, new bool[] { false, false, false, false });

            tryString("string between null and null", new String[] { "0", null, "a0", "b9" }, new bool[] { false, false, false, false });

            tryString("string between 'a0' and null", new String[] { "0", null, "a0", "b9" }, new bool[] { false, false, false, false });

            input = new String[] { "0", "a1", "a10", "c", "d", null, "a0", "b9", "b90" };
            result = new bool[] { true, false, false, true, true, false, false, false, true };
            tryString("string not between 'a0' and 'b9'", input, result);
            tryString("string not between 'b9' and 'a0'", input, result);
        }

        [Test]
        public virtual void testInNumericExpr()
        {
            double?[] input = new double?[] { 1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d };
            bool[] result = new bool[] { false, false, true, false, false, true, true };

            tryNumeric("doubleBoxed in (1.1d, 7/3, 2*7/3, 0)", 
                input, result);
            tryNumeric("doubleBoxed in (7/3d, null)",
                new double?[] { 2d, 7 / 3d, null },
                new bool[] { false, true, true });
            tryNumeric("doubleBoxed in (5,5,5,5,5, -1)",
                new double?[] { 5.0, 5d, 0d, null, -1d },
                new bool[] { true, true, false, false, true });
            tryNumeric("doubleBoxed not in (1.1d, 7/3, 2*7/3, 0)",
                new double?[] { 1d, null, 1.1d, 1.0d, 1.0999999999, 2d, 4d },
                new bool[] { true, true, false, true, true, false, false });
        }

        [Test]
        public virtual void testBetweenNumericExpr()
        {
            double?[] input = new double?[] { 1d, null, 1.1d, 2d, 1.0999999999, 2d, 4d, 15d, 15.00001d };
            bool[] result = new bool[] { false, false, true, true, false, true, true, true, false };
            tryNumeric("doubleBoxed between 1.1 and 15", input, result);
            tryNumeric("doubleBoxed between 15 and 1.1", input, result);

            tryNumeric("doubleBoxed between null and 15",
                new double?[] { 1d, null, 1.1d },
                new bool[] { false, false, false });
            tryNumeric("doubleBoxed between 15 and null",
                new double?[] { 1d, null, 1.1d }, new bool[] { false, false, false });
            tryNumeric("doubleBoxed between null and null",
                new double?[] { 1d, null, 1.1d }, new bool[] { false, false, false });

            input = new double?[] { 1d, null, 1.1d, 2d, 1.0999999999, 2d, 4d, 15d, 15.00001d };
            result = new bool[] { true, false, false, false, true, false, false, false, true };
            tryNumeric("doubleBoxed not between 1.1 and 15", input, result);
            tryNumeric("doubleBoxed not between 15 and 1.1", input, result);

            tryNumeric("doubleBoxed not between 15 and null",
                new double?[] { 1d, null, 1.1d },
                new bool[] { false, false, false });
        }

        [Test]
        public virtual void testInBoolExpr()
        {
            tryInBoolean("boolBoxed in (true, true)",
                new bool[] { true, false },
                new bool[] { true, false });
            tryInBoolean("boolBoxed in (1>2, 2=3, 4<=2)",
                new bool[] { true, false },
                new bool[] { false, true });
            tryInBoolean("boolBoxed not in (1>2, 2=3, 4<=2)",
                new bool[] { true, false },
                new bool[] { true, false });
        }

        [Test]
        public virtual void testInNumericCoercionLong()
        {
            String caseExpr = "select intPrimitive in (shortBoxed, intBoxed, longBoxed) as result from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.createEQL(caseExpr);
            selectTestFixture.AddListener(testListener);
            Assert.AreEqual(typeof(bool), selectTestFixture.EventType.GetPropertyType("result"));

            sendAndAssert(1, 2, 3, 4L, false);
            sendAndAssert(1, 1, 3, 4L, true);
            sendAndAssert(1, 3, 1, 4L, true);
            sendAndAssert(1, 3, 7, 1L, true);
            sendAndAssert(1, 3, 7, 0L, false);
            sendAndAssert(1, 1, 0, 0L, true);
            sendAndAssert(1, 0, 0, 1L, true);

            selectTestFixture.Stop();
        }

        [Test]
        public virtual void testInNumericCoercionDouble()
        {
            String caseExpr = "select intBoxed in (floatBoxed, doublePrimitive, longBoxed) as result from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.createEQL(caseExpr);
            selectTestFixture.AddListener(testListener);
            Assert.AreEqual(typeof(bool), selectTestFixture.EventType.GetPropertyType("result"));

            sendAndAssert(1, 2f, 3d, 4L, false);
            sendAndAssert(1, 1f, 3d, 4L, true);
            sendAndAssert(1, 1.1f, 1.0d, 4L, true);
            sendAndAssert(1, 1.1f, 1.2d, 1L, true);
            sendAndAssert(1, 0, 1.2d, 1L, true);
            sendAndAssert(0, 0, 1.2d, 1L, true);
            sendAndAssert(0, 11f, 1.2d, 1L, false);

            selectTestFixture.Stop();
        }

        [Test]
        public virtual void testBetweenNumericCoercionLong()
        {
            String caseExpr = "select intPrimitive between shortBoxed and longBoxed as result from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.createEQL(caseExpr);
            selectTestFixture.AddListener(testListener);
            Assert.AreEqual(typeof(bool), selectTestFixture.EventType.GetPropertyType("result"));

            sendAndAssert(1, 2, 3L, false);
            sendAndAssert(2, 2, 3L, true);
            sendAndAssert(3, 2, 3L, true);
            sendAndAssert(4, 2, 3L, false);
            sendAndAssert(5, 10, 1L, true);
            sendAndAssert(1, 10, 1L, true);
            sendAndAssert(10, 10, 1L, true);
            sendAndAssert(11, 10, 1L, false);

            selectTestFixture.Stop();
        }

        [Test]
        public virtual void testBetweenNumericCoercionDouble()
        {
            String caseExpr = "select intBoxed between floatBoxed and doublePrimitive as result from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.createEQL(caseExpr);
            selectTestFixture.AddListener(testListener);
            Assert.AreEqual(typeof(bool), selectTestFixture.EventType.GetPropertyType("result"));

            sendAndAssert(1, 2f, 3d, false);
            sendAndAssert(2, 2f, 3d, true);
            sendAndAssert(3, 2f, 3d, true);
            sendAndAssert(4, 2f, 3d, false);
            sendAndAssert(0, 2f, 3d, false);
            sendAndAssert(0, 0f, 3d, false);
            sendAndAssert(1, 3f, 2d, false);
            sendAndAssert(2, 3f, 2d, true);
            sendAndAssert(3, 3f, 2d, true);
            sendAndAssert(4, 3f, 2d, false);
            sendAndAssert(0, 3f, 2d, false);
            sendAndAssert(0, 0f, 2d, false);

            selectTestFixture.Stop();
        }

        private void sendAndAssert(
             int intBoxed,
             float floatBoxed,
             double doublePrimitive,
             bool result)
        {
            SupportBean bean = new SupportBean();
            bean.intBoxed = intBoxed;
            bean.floatBoxed = floatBoxed;
            bean.doublePrimitive = doublePrimitive;

            epService.EPRuntime.SendEvent(bean);

            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(result, _event["result"]);
        }

        private void sendAndAssert(
            int intPrimitive,
            int shortBoxed,
            int intBoxed,
            long longBoxed,
            bool result)
        {
            SupportBean bean = new SupportBean();
            bean.intPrimitive = intPrimitive;
            bean.shortBoxed = (short)shortBoxed;
            bean.intBoxed = intBoxed;
            bean.longBoxed = longBoxed;

            epService.EPRuntime.SendEvent(bean);

            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(result, _event["result"]);
        }

        private void sendAndAssert(int intPrimitive, int shortBoxed, long longBoxed, bool result)
        {
            SupportBean bean = new SupportBean();
            bean.intPrimitive = intPrimitive;
            bean.shortBoxed = (short)shortBoxed;
            bean.longBoxed = longBoxed;

            epService.EPRuntime.SendEvent(bean);

            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(result, _event["result"]);
        }

        private void sendAndAssert(int intBoxed, float floatBoxed, double doublePrimitve, long longBoxed, bool result)
        {
            SupportBean bean = new SupportBean();
            bean.intBoxed = intBoxed;
            bean.floatBoxed = floatBoxed;
            bean.doublePrimitive = doublePrimitve;
            bean.longBoxed = longBoxed;

            epService.EPRuntime.SendEvent(bean);

            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(result, _event["result"]);
        }

        private void tryInBoolean(String expr, bool[] input, bool[] result)
        {
            String caseExpr = "select " + expr + " as result from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.createEQL(caseExpr);
            selectTestFixture.AddListener(testListener);
            Assert.AreEqual(typeof(bool), selectTestFixture.EventType.GetPropertyType("result"));

            for (int i = 0; i < input.Length; i++)
            {
                sendSupportBeanEvent(input[i]);
                EventBean _event = testListener.assertOneGetNewAndReset();
                Assert.AreEqual(result[i], _event["result"], "Wrong result for " + input[i]);
            }
            selectTestFixture.Stop();
        }

        private void tryNumeric(String expr, double?[] input, bool[] result)
        {
            String caseExpr = "select " + expr + " as result from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.createEQL(caseExpr);
            selectTestFixture.AddListener(testListener);
            Assert.AreEqual(typeof(bool), selectTestFixture.EventType.GetPropertyType("result"));

            for (int i = 0; i < input.Length; i++)
            {
                sendSupportBeanEvent(input[i]);
                EventBean _event = testListener.assertOneGetNewAndReset();
                Assert.AreEqual(result[i], _event["result"], "Wrong result for " + input[i]);
            }
            selectTestFixture.Stop();
        }

        private void tryString(String expression, String[] input, bool[] result)
        {
            String caseExpr = "select " + expression + " as result from " + typeof(SupportBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.createEQL(caseExpr);
            selectTestFixture.AddListener(testListener);
            Assert.AreEqual(typeof(bool), selectTestFixture.EventType.GetPropertyType("result"));

            for (int i = 0; i < input.Length; i++)
            {
                sendSupportBeanEvent(input[i]);
                EventBean _event = testListener.assertOneGetNewAndReset();
                Assert.AreEqual(result[i], _event["result"], "Wrong result for " + input[i]);
            }
            selectTestFixture.Stop();
        }

        private void sendSupportBeanEvent(double? doubleBoxed)
        {
            SupportBean _event = new SupportBean();
            _event.doubleBoxed = doubleBoxed;
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendSupportBeanEvent(String stringValue)
        {
            SupportBean _event = new SupportBean();
            _event.str = stringValue;
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendSupportBeanEvent(bool boolBoxed)
        {
            SupportBean _event = new SupportBean();
            _event.boolBoxed = boolBoxed;
            epService.EPRuntime.SendEvent(_event);
        }
    }
}
