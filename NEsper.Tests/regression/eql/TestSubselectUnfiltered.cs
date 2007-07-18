// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.util;

namespace net.esper.regression.eql
{
    [TestFixture]
    public class TestSubselectUnfiltered
    {
        private EPServiceProvider epService;
        private SupportUpdateListener listener;

        [SetUp]
        public void SetUp()
        {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;

            Configuration config = new Configuration();
            config.AddEventTypeAlias("S0", typeof(SupportBean_S0));
            config.AddEventTypeAlias("S1", typeof(SupportBean_S1));
            config.AddEventTypeAlias("S2", typeof(SupportBean_S2));
            config.AddEventTypeAlias("S3", typeof(SupportBean_S3));
            config.AddEventTypeAlias("S4", typeof(SupportBean_S4));
            config.AddEventTypeAlias("S5", typeof(SupportBean_S5));
            epService = EPServiceProviderManager.GetDefaultProvider(config);
            epService.Initialize();
            listener = new SupportUpdateListener();

            // Use external clocking for the test, reduces logging
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
        }

        [Test]
        public void testSelfSubselect()
        {
            String stmtTextOne = "insert into MyCount select Count(*) as cnt from S0";
            epService.EPAdministrator.CreateEQL(stmtTextOne);

            String stmtTextTwo = "select (select cnt from MyCount.std:lastevent()) as value from S0";
            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtTextTwo);
            stmt.AddListener(listener);

            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["value"]);

            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(1L, listener.AssertOneGetNewAndReset()["value"]);
        }

        [Test]
        public void testStartStopStatement()
        {
            String stmtText = "select id from S0 where (select true from S1.win:length(1000))";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.IsFalse(listener.IsInvoked);

            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["id"]);

            stmt.Stop();
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.IsFalse(listener.IsInvoked);

            stmt.Start();
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.IsFalse(listener.IsInvoked);

            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(3));
            Assert.AreEqual(3, listener.AssertOneGetNewAndReset()["id"]);
        }

        [Test]
        public void testWhereClauseReturningTrue()
        {
            String stmtText = "select id from S0 where (select true from S1.win:length(1000))";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(2, listener.AssertOneGetNewAndReset()["id"]);
        }

        [Test]
        public void testWhereClauseWithExpression()
        {
            String stmtText = "select id from S0 where (select p10='X' from S1.win:length(1000))";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            Assert.IsFalse(listener.IsInvoked);

            epService.EPRuntime.SendEvent(new SupportBean_S1(10, "X"));
            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            Assert.AreEqual(0, listener.AssertOneGetNewAndReset()["id"]);
        }

        [Test]
        public void testJoinUnfiltered()
        {
            String stmtText = "select (select id from S3.win:length(1000)) as idS3, (select id from S4.win:length(1000)) as idS4 from S0 as s0, S1 as s1 where s0.id = s1.id";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType("idS3"));
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType("idS4"));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            epService.EPRuntime.SendEvent(new SupportBean_S1(0));
            EventBean _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(null, _event["idS3"]);
            Assert.AreEqual(null, _event["idS4"]);

            // send one event
            epService.EPRuntime.SendEvent(new SupportBean_S3(-1));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            epService.EPRuntime.SendEvent(new SupportBean_S1(1));
            _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(-1, _event["idS3"]);
            Assert.AreEqual(null, _event["idS4"]);

            // send one event
            epService.EPRuntime.SendEvent(new SupportBean_S4(-2));
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            epService.EPRuntime.SendEvent(new SupportBean_S1(2));
            _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(-1, _event["idS3"]);
            Assert.AreEqual(-2, _event["idS4"]);

            // send second event
            epService.EPRuntime.SendEvent(new SupportBean_S4(-2));
            epService.EPRuntime.SendEvent(new SupportBean_S0(3));
            epService.EPRuntime.SendEvent(new SupportBean_S1(3));
            _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(-1, _event["idS3"]);
            Assert.AreEqual(null, _event["idS4"]);

            epService.EPRuntime.SendEvent(new SupportBean_S3(-2));
            epService.EPRuntime.SendEvent(new SupportBean_S0(3));
            epService.EPRuntime.SendEvent(new SupportBean_S1(3));
            EventBean[] events = listener.GetNewDataListFlattened();
            Assert.AreEqual(3, events.Length);
            for (int i = 0; i < events.Length; i++)
            {
                Assert.AreEqual(null, events[i]["idS3"]);
                Assert.AreEqual(null, events[i]["idS4"]);
            }
        }

        [Test]
        public void testInvalidSubselect()
        {
            TryInvalid("select (select id from S1) from S0",
                       "Error starting view: Subqueries require one or more views to limit the stream, consider declaring a length or time window [select (select id from S1) from S0]");

            TryInvalid("select (select dummy from S1.std:lastevent()) as idS1 from S0",
                       "Error starting view: Property named 'dummy' is not valid in any stream [select (select dummy from S1.std:lastevent()) as idS1 from S0]");

            TryInvalid("select (select id, id from S1) as idS1 from S0",
                       "expecting \"from\", found ',' near line 1, column 18 [select (select id, id from S1) as idS1 from S0]");

            TryInvalid("select (select * from S1.std:lastevent()) as idS1 from S0",
                       "Error starting view: Invalid use of wildcard in subquery [select (select * from S1.std:lastevent()) as idS1 from S0]");

            TryInvalid("select (select id from S1.std:lastevent() group by id) as idS1 from S0",
                       "unexpected token: group near line 1, column 43 [select (select id from S1.std:lastevent() group by id) as idS1 from S0]");

            TryInvalid("select (select (select id from S1.std:lastevent()) id from S1.std:lastevent()) as idS1 from S0",
                       "unexpected token: id near line 1, column 52 [select (select (select id from S1.std:lastevent()) id from S1.std:lastevent()) as idS1 from S0]");

            TryInvalid("select (select Sum(id) from S1.std:lastevent()) as idS1 from S0",
                       "Error starting view: Aggregation functions are not supported within subqueries, consider using insert-into instead [select (select Sum(id) from S1.std:lastevent()) as idS1 from S0]");

            TryInvalid("select (select id from S1.std:lastevent() where Sum(id) = 5) as idS1 from S0",
                       "Error starting view: Aggregation functions are not supported within subqueries, consider using insert-into instead [select (select id from S1.std:lastevent() where Sum(id) = 5) as idS1 from S0]");

            TryInvalid("select * from S0(id=5 and (select id from S1))",
                       "Subselects not allowed within filters [select * from S0(id=5 and (select id from S1))]");

            TryInvalid("select * from S0 group by id + (select id from S1)",
                       "Error starting view: Subselects not allowed within group-by [select * from S0 group by id + (select id from S1)]");

            TryInvalid("select * from S0 group by id having (select id from S1)",
                       "Error starting view: Subselects not allowed within having-clause [select * from S0 group by id having (select id from S1)]");

            TryInvalid("select * from S0 order by (select id from S1) asc",
                       "Error starting view: Subselects not allowed within order-by clause [select * from S0 order by (select id from S1) asc]");

            TryInvalid("select (select id from S1.std:lastevent() where 'a') from S0",
                       "Error starting view: Subselect filter expression must return a bool value [select (select id from S1.std:lastevent() where 'a') from S0]");

            TryInvalid("select (select id from S1.std:lastevent() where id = p00) from S0",
                       "Error starting view: Property named 'p00' must be prefixed by a stream name, use the as-clause to name the stream [select (select id from S1.std:lastevent() where id = p00) from S0]");
        }

        [Test]
        public void testUnfilteredStreamPrior()
        {
            String stmtText = "select (select Prior(0, id) from S1.win:length(1000)) as idS1 from S0";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType("idS1"));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["idS1"]);

            // test one event
            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(10, listener.AssertOneGetNewAndReset()["idS1"]);

            // resend event
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(10, listener.AssertOneGetNewAndReset()["idS1"]);

            // test second event
            epService.EPRuntime.SendEvent(new SupportBean_S0(3));
            Assert.AreEqual(10, listener.AssertOneGetNewAndReset()["idS1"]);
        }

        [Test]
        public void testCustomFunction()
        {
            String stmtText = "select (select " + typeof(SupportStaticMethodLib).FullName + ".MinusOne(id) from S1.win:length(1000)) as idS1 from S0";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(double?), stmt.EventType.GetPropertyType("idS1"));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["idS1"]);

            // test one event
            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(9d, listener.AssertOneGetNewAndReset()["idS1"]);

            // resend event
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(9d, listener.AssertOneGetNewAndReset()["idS1"]);
        }

        [Test]
        public void testComputedResult()
        {
            String stmtText = "select 100*(select id from S1.win:length(1000)) as idS1 from S0";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType("idS1"));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["idS1"]);

            // test one event
            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(1000, listener.AssertOneGetNewAndReset()["idS1"]);

            // resend event
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(1000, listener.AssertOneGetNewAndReset()["idS1"]);
        }

        [Test]
        public void testFilterInside()
        {
            String stmtText = "select (select id from S1(p10='A').win:length(1000)) as idS1 from S0";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            epService.EPRuntime.SendEvent(new SupportBean_S1(1, "X"));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()["idS1"]);

            epService.EPRuntime.SendEvent(new SupportBean_S1(1, "A"));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(1, listener.AssertOneGetNewAndReset()["idS1"]);
        }

        [Test]
        public void testUnfilteredUnlimitedStream()
        {
            String stmtText = "select (select id from S1.win:length(1000)) as idS1 from S0";
            RunAssertMultiRowUnfiltered(stmtText, "idS1");
        }

        [Test]
        public void testUnfilteredLengthWindow()
        {
            String stmtText = "select (select id from S1.win:length(2)) as idS1 from S0";
            RunAssertMultiRowUnfiltered(stmtText, "idS1");
        }

        [Test]
        public void testUnfilteredAsAfterSubselect()
        {
            String stmtText = "select (select id from S1.std:lastevent()) as idS1 from S0";
            RunAssertSingleRowUnfiltered(stmtText, "idS1");
        }

        [Test]
        public void testUnfilteredWithAsWithinSubselect()
        {
            String stmtText = "select (select id as myId from S1.std:lastevent()) from S0";
            RunAssertSingleRowUnfiltered(stmtText, "myId");
        }

        [Test]
        public void testUnfilteredNoAs()
        {
            String stmtText = "select (select id from S1.std:lastevent()) from S0";
            RunAssertSingleRowUnfiltered(stmtText, "id");
        }

        [Test]
        public void testUnfilteredExpression()
        {
            String stmtText = "select (select p10 || p11 from S1.std:lastevent()) as value from S0";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(string), stmt.EventType.GetPropertyType("value"));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            EventBean _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(null, _event["value"]);

            // test one event
            epService.EPRuntime.SendEvent(new SupportBean_S1(-1, "a", "b"));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual("ab", _event["value"]);
        }

        [Test]
        public void testMultiColumnSelect()
        {
            String stmtText = "select (select id+1 as myId from S1.std:lastevent()) as idS1_0, " +
                    "(select id+2 as myId from S1.std:lastevent()) as idS1_1 from S0";

            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType("idS1_0"));
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType("idS1_1"));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            EventBean _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(null, _event["idS1_0"]);
            Assert.AreEqual(null, _event["idS1_1"]);

            // test one event
            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(11, _event["idS1_0"]);
            Assert.AreEqual(12, _event["idS1_1"]);

            // resend event
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(11, _event["idS1_0"]);
            Assert.AreEqual(12, _event["idS1_1"]);

            // test second event
            epService.EPRuntime.SendEvent(new SupportBean_S1(999));
            epService.EPRuntime.SendEvent(new SupportBean_S0(3));
            _event = listener.AssertOneGetNewAndReset();
            Assert.AreEqual(1000, _event["idS1_0"]);
            Assert.AreEqual(1001, _event["idS1_1"]);
        }

        private void RunAssertSingleRowUnfiltered(String stmtText, String columnName)
        {
            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType(columnName));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()[columnName]);

            // test one event
            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(10, listener.AssertOneGetNewAndReset()[columnName]);

            // resend event
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(10, listener.AssertOneGetNewAndReset()[columnName]);

            // test second event
            epService.EPRuntime.SendEvent(new SupportBean_S1(999));
            epService.EPRuntime.SendEvent(new SupportBean_S0(3));
            Assert.AreEqual(999, listener.AssertOneGetNewAndReset()[columnName]);
        }

        private void RunAssertMultiRowUnfiltered(String stmtText, String columnName)
        {
            EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
            stmt.AddListener(listener);

            // check type
            Assert.AreEqual(typeof(int?), stmt.EventType.GetPropertyType(columnName));

            // test no _event, should return null
            epService.EPRuntime.SendEvent(new SupportBean_S0(0));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()[columnName]);

            // test one event
            epService.EPRuntime.SendEvent(new SupportBean_S1(10));
            epService.EPRuntime.SendEvent(new SupportBean_S0(1));
            Assert.AreEqual(10, listener.AssertOneGetNewAndReset()[columnName]);

            // resend event
            epService.EPRuntime.SendEvent(new SupportBean_S0(2));
            Assert.AreEqual(10, listener.AssertOneGetNewAndReset()[columnName]);

            // test second event
            epService.EPRuntime.SendEvent(new SupportBean_S1(999));
            epService.EPRuntime.SendEvent(new SupportBean_S0(3));
            Assert.AreEqual(null, listener.AssertOneGetNewAndReset()[columnName]);
        }

        private void TryInvalid(String stmtText, String expectedMsg)
        {
            try
            {
                epService.EPAdministrator.CreateEQL(stmtText);
                Assert.Fail();
            }
            catch (EPStatementException ex)
            {
                Assert.AreEqual(expectedMsg, ex.Message);
            }
        }
    }
} // End of namespace