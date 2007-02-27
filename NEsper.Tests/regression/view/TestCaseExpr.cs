using System;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestCaseExpr
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
        public virtual void testCaseSyntax1Sum()
        {
            // Testing the two forms of the case expression
            // Furthermore the test checks the different when clauses and actions related.
            String caseExpr = "select case " + " when symbol='GE' then volume " + " when symbol='DELL' then sum(price) " + "end as p1 from " + typeof(SupportMarketDataBean).FullName + ".win:length(10)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(double?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendMarketDataEvent("DELL", 10000, 50);
            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(50.0, _event["p1"]);

            sendMarketDataEvent("DELL", 10000, 50);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(100.0, _event["p1"]);

            sendMarketDataEvent("CSCO", 4000, 5);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(null, _event["p1"]);

            sendMarketDataEvent("GE", 20, 30);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(20.0, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax1WithElse()
        {
            // Adding to the EQL statement an else expression
            // when a CSCO ticker is sent the property for the else expression is selected
            String caseExpr = "select case " + " when symbol='DELL' then 3 * volume " + " else volume " + "end as p1 from " + typeof(SupportMarketDataBean).FullName + ".win:length(3)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(long?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendMarketDataEvent("CSCO", 4000, 0);
            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(4000L, _event["p1"]);

            sendMarketDataEvent("DELL", 20, 0);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(3 * 20L, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax1Branches3()
        {
            // Same test but the where clause doesn't match any of the condition of the case expresssion
            String caseExpr = "select case " + " when (symbol='GE') then volume " + " when (symbol='DELL') then volume / 2.0 " + " when (symbol='MSFT') then volume / 3.0 " + " end as p1 from " + typeof(SupportMarketDataBean).FullName;

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(double?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendMarketDataEvent("DELL", 10000, 0);
            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(10000 / 2.0, _event["p1"]);

            sendMarketDataEvent("MSFT", 10000, 0);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(10000 / 3.0, _event["p1"]);

            sendMarketDataEvent("GE", 10000, 0);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(10000.0, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2()
        {
            String caseExpr = "select case intPrimitive " + " when longPrimitive then (intPrimitive + longPrimitive) " + " when doublePrimitive then intPrimitive * doublePrimitive" + " when floatPrimitive then floatPrimitive / doublePrimitive " + " else (intPrimitive + longPrimitive + floatPrimitive + doublePrimitive) end as p1 " + " from " + typeof(SupportBean).FullName + ".win:length(10)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(double?), selectTestFixture.EventType.GetPropertyType("p1"));

            // intPrimitive = longPrimitive
            // case result is intPrimitive + longPrimitive
            sendSupportBeanEvent(2, 2L, 1.0f, 1.0);
            EventBean _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(4.0, _event["p1"]);
            // intPrimitive = doublePrimitive
            // case result is intPrimitive * doublePrimitive
            sendSupportBeanEvent(5, 1L, 1.0f, 5.0);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(25.0, _event["p1"]);
            // intPrimitive = floatPrimitive
            // case result is floatPrimitive / doublePrimitive
            sendSupportBeanEvent(12, 1L, 12.0f, 4.0);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(3.0, _event["p1"]);
            // all the properties of the event are different
            // The else part is computed: 1+2+3+4 = 10
            sendSupportBeanEvent(1, 2L, 3.0f, 4.0);
            _event = testListener.assertOneGetNewAndReset();
            Assert.AreEqual(10.0, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2StringsNBranches()
        {
            // Test of the various coercion user cases.
            String caseExpr =
              "select case intPrimitive" +
              " when 1 then System.Convert.ToString(boolPrimitive) " +
              " when 2 then System.Convert.ToString(boolBoxed) " +
              " when 3 then System.Convert.ToString(intPrimitive) " +
              " when 4 then System.Convert.ToString(intBoxed)" +
              " when 5 then System.Convert.ToString(longPrimitive) " +
              " when 6 then System.Convert.ToString(longBoxed) " +
              " when 7 then System.Convert.ToString(charPrimitive) " +
              " when 8 then System.Convert.ToString(charBoxed) " +
              " when 9 then System.Convert.ToString(shortPrimitive) " +
              " when 10 then System.Convert.ToString(shortBoxed) " +
              " when 11 then System.Convert.ToString(bytePrimitive) " +
              " when 12 then System.Convert.ToString(byteBoxed) " +
              " when 13 then System.Convert.ToString(floatPrimitive) " +
              " when 14 then System.Convert.ToString(floatBoxed) " +
              " when 15 then System.Convert.ToString(doublePrimitive) " +
              " when 16 then System.Convert.ToString(doubleBoxed) " +
              " when 17 then str " +
              " else 'x' end as p1 " +
              " from " + typeof(SupportBean).FullName + ".win:length(1)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(String), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent(true, false, 1, 0, 0L, 0L, '0', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            EventBean _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("True", _event["p1"]);

            sendSupportBeanEvent(true, false, 2, 0, 0L, 0L, '0', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("False", _event["p1"]);

            sendSupportBeanEvent(true, false, 3, 0, 0L, 0L, '0', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("3", _event["p1"]);

            sendSupportBeanEvent(true, false, 4, 4, 0L, 0L, '0', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("4", _event["p1"]);

            sendSupportBeanEvent(true, false, 5, 0, 5L, 0L, '0', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("5", _event["p1"]);

            sendSupportBeanEvent(true, false, 6, 0, 0L, 6L, '0', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("6", _event["p1"]);

            sendSupportBeanEvent(true, false, 7, 0, 0L, 0L, 'A', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("A", _event["p1"]);

            sendSupportBeanEvent(true, false, 8, 0, 0L, 0L, 'A', 'a', (short)0, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("a", _event["p1"]);

            sendSupportBeanEvent(true, false, 9, 0, 0L, 0L, 'A', 'a', (short)9, (short)0, (sbyte)0, (sbyte)0, 0.0f, (float)0, 0.0, (double)0.0, null, SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("9", _event["p1"]);

            sendSupportBeanEvent(true, false, 10, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("10", _event["p1"]);

            sendSupportBeanEvent(true, false, 11, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("11", _event["p1"]);

            sendSupportBeanEvent(true, false, 12, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("12", _event["p1"]);

            sendSupportBeanEvent(true, false, 13, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("13", _event["p1"]);

            sendSupportBeanEvent(true, false, 14, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("14", _event["p1"]);

            sendSupportBeanEvent(true, false, 15, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("15", _event["p1"]);

            sendSupportBeanEvent(true, false, 16, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("16", _event["p1"]);

            sendSupportBeanEvent(true, false, 17, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("testCoercion", _event["p1"]);

            sendSupportBeanEvent(true, false, -1, 0, 0L, 0L, 'A', 'a', (short)9, (short)10, (sbyte)11, (sbyte)12, 13.0f, (float)14, 15.0, (double)16.0, "testCoercion", SupportEnum.ENUM_VALUE_1);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual("x", _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2NoElseWithNull()
        {
            String caseExpr = "select case string " + " when null then true " + " when '' then false end as p1" + " from " + typeof(SupportBean).FullName + ".win:length(100)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(bool?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent("x");
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);

            sendSupportBeanEvent("null");
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);

            sendSupportBeanEvent(null);
            Assert.AreEqual(true, testListener.assertOneGetNewAndReset()["p1"]);

            sendSupportBeanEvent("");
            Assert.AreEqual(false, testListener.assertOneGetNewAndReset()["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax1WithNull()
        {
            String caseExpr = "select case " + " when string = null then true " + " when string = '' then false end as p1" + " from " + typeof(SupportBean).FullName + ".win:length(100)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(bool?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent("x");
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);

            sendSupportBeanEvent("null");
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);

            sendSupportBeanEvent(null);
            Assert.AreEqual(true, testListener.assertOneGetNewAndReset()["p1"]);

            sendSupportBeanEvent("");
            Assert.AreEqual(false, testListener.assertOneGetNewAndReset()["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2WithNull()
        {
            String caseExpr = "select case intPrimitive " + " when 1 then null " + " when 2 then 1.0" + " when 3 then null " + " else 2 " + " end as p1 from " + typeof(SupportBean).FullName + ".win:length(100)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(double?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent(4);
            Assert.AreEqual(2.0, testListener.assertOneGetNewAndReset()["p1"]);
            sendSupportBeanEvent(1);
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);
            sendSupportBeanEvent(2);
            Assert.AreEqual(1.0, testListener.assertOneGetNewAndReset()["p1"]);
            sendSupportBeanEvent(3);
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2WithNullBool()
        {
            String caseExpr =
                "select case boolBoxed " +
                " when null then 1 " +
                " when true then 2l" +
                " when false then 3 " +
                " end as p1 from " + typeof(SupportBean).FullName + ".win:length(100)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(long?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent(null);
            Assert.AreEqual(1L, testListener.assertOneGetNewAndReset()["p1"]);
            sendSupportBeanEvent(false);
            Assert.AreEqual(3L, testListener.assertOneGetNewAndReset()["p1"]);
            sendSupportBeanEvent(true);
            Assert.AreEqual(2L, testListener.assertOneGetNewAndReset()["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2WithCoercion()
        {
            String caseExpr =
                "select case intPrimitive " +
                " when 1.0 then null " +
                " when 4/2.0 then 'x'" +
                " end as p1 from " + typeof(SupportBean).FullName + ".win:length(100)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(String), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent(1);
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);
            sendSupportBeanEvent(2);
            Assert.AreEqual("x", testListener.assertOneGetNewAndReset()["p1"]);
            sendSupportBeanEvent(3);
            Assert.AreEqual(null, testListener.assertOneGetNewAndReset()["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2WithinExpression()
        {
            String caseExpr =
                "select 2 * (case " +
                " intPrimitive when 1 then 2 " +
                " when 2 then 3 " +
                " else 10 end) as p1 " +
                " from " + typeof(SupportBean).FullName + ".win:length(1)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(int?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent(1);
            EventBean _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(4, _event["p1"]);

            sendSupportBeanEvent(2);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(6, _event["p1"]);

            sendSupportBeanEvent(3);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(20, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2Sum()
        {
            String caseExpr =
                "select case intPrimitive when 1 then sum(longPrimitive) " +
                " when 2 then sum(floatPrimitive) " +
                " else sum(intPrimitive) end as p1 " +
                " from " + typeof(SupportBean).FullName + ".win:length(10)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(float?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent(1, 10L, 3.0f, 4.0);
            EventBean _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(10f, _event["p1"]);

            sendSupportBeanEvent(1, 15L, 3.0f, 4.0);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(25f, _event["p1"]);

            sendSupportBeanEvent(2, 1L, 3.0f, 4.0);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(9f, _event["p1"]);

            sendSupportBeanEvent(2, 1L, 3.0f, 4.0);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(12.0F, _event["p1"]);

            sendSupportBeanEvent(5, 1L, 1.0f, 1.0);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(11.0F, _event["p1"]);

            sendSupportBeanEvent(5, 1L, 1.0f, 1.0);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(16f, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2EnumChecks()
        {
            String caseExpr =
                "select case supportEnum " +
                " when net.esper.support.bean.SupportEnumHelper.GetValueForEnum(0) then 1 " +
                " when net.esper.support.bean.SupportEnumHelper.GetValueForEnum(1) then 2 " +
                " end as p1 " + " from " + typeof(SupportBeanWithEnum).FullName +
                ".win:length(10)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(int?), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent("a", SupportEnum.ENUM_VALUE_1);
            EventBean _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(1, _event["p1"]);

            sendSupportBeanEvent("b", SupportEnum.ENUM_VALUE_2);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(2, _event["p1"]);

            sendSupportBeanEvent("c", SupportEnum.ENUM_VALUE_3);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(null, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2EnumResult()
        {
            String caseExpr =
                "select case intPrimitive * 2 " +
                " when 2 then net.esper.support.bean.SupportEnumHelper.GetValueForEnum(0) " +
                " when 4 then net.esper.support.bean.SupportEnumHelper.GetValueForEnum(1) " +
                " else net.esper.support.bean.SupportEnumHelper.GetValueForEnum(2) " +
                " end as p1 " +
                " from " + typeof(SupportBean).FullName + ".win:length(10)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(SupportEnum), selectTestFixture.EventType.GetPropertyType("p1"));

            sendSupportBeanEvent(1);
            EventBean _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(SupportEnum.ENUM_VALUE_1, _event["p1"]);

            sendSupportBeanEvent(2);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(SupportEnum.ENUM_VALUE_2, _event["p1"]);

            sendSupportBeanEvent(3);
            _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(SupportEnum.ENUM_VALUE_3, _event["p1"]);
        }

        [Test]
        public virtual void testCaseSyntax2NoAsName()
        {
            String caseSubExpr = "case intPrimitive when 1 then 0 end";
            String caseExpr =
                "select " + caseSubExpr +
                " from " + typeof(SupportBean).FullName + ".win:length(10)";

            EPStatement selectTestFixture = epService.EPAdministrator.CreateEQL(caseExpr);
            selectTestFixture.AddListener(testListener.Update);
            Assert.AreEqual(typeof(int?), selectTestFixture.EventType.GetPropertyType(caseSubExpr));

            sendSupportBeanEvent(1);
            EventBean _event = testListener.getAndResetLastNewData()[0];
            Assert.AreEqual(0, _event[caseSubExpr]);
        }

        private void sendSupportBeanEvent(
            bool b_, bool boolBoxed_,
            int i_, int? intBoxed_,
            long l_, long? longBoxed_,
            char c_, char? charBoxed_,
            short s_, short? shortBoxed_,
            sbyte by_, sbyte? byteBoxed_,
            float f_, float? floatBoxed_,
            double d_, double? doubleBoxed_,
            String str_, SupportEnum enum_)
        {
            SupportBean _event = new SupportBean();
            _event.boolPrimitive = b_;
            _event.boolBoxed = boolBoxed_;
            _event.intPrimitive = i_;
            _event.intBoxed = intBoxed_;
            _event.longPrimitive = l_;
            _event.longBoxed = longBoxed_;
            _event.charPrimitive = c_;
            _event.charBoxed = charBoxed_;
            _event.shortPrimitive = s_;
            _event.shortBoxed = shortBoxed_;
            _event.bytePrimitive = by_;
            _event.byteBoxed = byteBoxed_;
            _event.floatPrimitive = f_;
            _event.floatBoxed = floatBoxed_;
            _event.doublePrimitive = d_;
            _event.doubleBoxed = doubleBoxed_;
            _event.str = str_;
            _event.EnumValue = enum_;
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendSupportBeanEvent(int intPrimitive, long longPrimitive, float floatPrimitive, double doublePrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.intPrimitive = intPrimitive;
            _event.longPrimitive = longPrimitive;
            _event.floatPrimitive = floatPrimitive;
            _event.doublePrimitive = doublePrimitive;
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendSupportBeanEvent(int intPrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.intPrimitive = intPrimitive;
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

        private void sendSupportBeanEvent(String stringValue, SupportEnum supportEnum)
        {
            SupportBeanWithEnum _event = new SupportBeanWithEnum(stringValue, supportEnum);
            epService.EPRuntime.SendEvent(_event);
        }

        private void sendMarketDataEvent(String symbol, long volume, double price)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
            epService.EPRuntime.SendEvent(bean);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}