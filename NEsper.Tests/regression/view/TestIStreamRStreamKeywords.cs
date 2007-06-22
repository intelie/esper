using System;

using net.esper.client;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestIStreamRStreamKeywords
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;
        private SupportUpdateListener testListenerInsertInto;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            testListenerInsertInto = new SupportUpdateListener();

            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public virtual void testRStreamOnly()
        {
            EPStatement statement = epService.EPAdministrator.CreateEQL(
                "select rstream * from " + typeof(SupportBean).FullName + ".win:length(3)");
            statement.AddListener(testListener);

            Object _event = SendEvent("a");
            Assert.IsFalse(testListener.IsInvoked);

            sendEvents(new String[] { "a", "b" });
            Assert.IsFalse(testListener.IsInvoked);

            SendEvent("d");
            Assert.AreSame(_event, testListener.LastNewData[0].Underlying); // receive 'a' as new data
            Assert.IsNull(testListener.LastOldData); // receive no more old data
            testListener.Reset();
        }

        [Test]
        public virtual void testRStreamInsertInto()
        {
            EPStatement statement = epService.EPAdministrator.CreateEQL(
                "insert into NextStream " +
                "select rstream s0.str as string from " + typeof(SupportBean).FullName + ".win:length(3) as s0"
                );
            statement.AddListener(testListener);

            statement = epService.EPAdministrator.CreateEQL("select * from NextStream");
            statement.AddListener(testListenerInsertInto);

            SendEvent("a");
            Assert.IsFalse(testListener.IsInvoked);
            Assert.AreEqual("a", testListenerInsertInto.AssertOneGetNewAndReset()["string"]); // insert into unchanged

            sendEvents(new String[] { "b", "c" });
            Assert.IsFalse(testListener.IsInvoked);
            Assert.AreEqual(2, testListenerInsertInto.NewDataList.Count); // insert into unchanged
            testListenerInsertInto.Reset();

            SendEvent("d");
            Assert.AreSame("a", testListener.LastNewData[0]["string"]); // receive 'a' as new data
            Assert.IsNull(testListener.LastOldData); // receive no more old data
            Assert.AreEqual("d", testListenerInsertInto.LastNewData[0]["string"]); // insert into unchanged
            Assert.IsNull(testListenerInsertInto.LastOldData); // receive no old data in insert into
            testListener.Reset();
        }

        [Test]
        public virtual void testRStreamInsertIntoRStream()
        {
            EPStatement statement = epService.EPAdministrator.CreateEQL(
                "insert rstream into NextStream " +
                "select rstream s0.str as string from " + typeof(SupportBean).FullName + ".win:length(3) as s0"
                );
            statement.AddListener(testListener);

            statement = epService.EPAdministrator.CreateEQL("select * from NextStream");
            statement.AddListener(testListenerInsertInto);

            SendEvent("a");
            Assert.IsFalse(testListener.IsInvoked);
            Assert.IsFalse(testListenerInsertInto.IsInvoked);

            sendEvents(new String[] { "b", "c" });
            Assert.IsFalse(testListener.IsInvoked);
            Assert.IsFalse(testListenerInsertInto.IsInvoked);

            SendEvent("d");
            Assert.AreSame("a", testListener.LastNewData[0]["string"]); // receive 'a' as new data
            Assert.IsNull(testListener.LastOldData); // receive no more old data
            Assert.AreEqual("a", testListenerInsertInto.LastNewData[0]["string"]); // insert into unchanged
            Assert.IsNull(testListener.LastOldData); // receive no old data in insert into
            testListener.Reset();
        }

        [Test]
        public virtual void testRStreamJoin()
        {
            EPStatement statement = epService.EPAdministrator.CreateEQL(
                "select rstream s1.intPrimitive as aID, s2.intPrimitive as bID " +
                "from " +
                typeof(SupportBean).FullName + "(str='a').win:length(2) as s1, " +
                typeof(SupportBean).FullName + "(str='b') as s2" +
                " where s1.intPrimitive = s2.intPrimitive"
                );
            statement.AddListener(testListener);

            SendEvent("a", 1);
            SendEvent("b", 1);
            Assert.IsFalse(testListener.IsInvoked);

            SendEvent("a", 2);
            Assert.IsFalse(testListener.IsInvoked);

            SendEvent("a", 3);
            Assert.AreEqual(1, testListener.LastNewData[0]["aID"]); // receive 'a' as new data
            Assert.AreEqual(1, testListener.LastNewData[0]["bID"]);
            Assert.IsNull(testListener.LastOldData); // receive no more old data
            testListener.Reset();
        }

        [Test]
        public virtual void testIStreamOnly()
        {
            EPStatement statement = epService.EPAdministrator.CreateEQL(
                "select istream * from " + typeof(SupportBean).FullName + ".win:length(1)");
            statement.AddListener(testListener);

            Object _event = SendEvent("a");
            Assert.AreSame(_event, testListener.AssertOneGetNewAndReset().Underlying);

            _event = SendEvent("b");
            Assert.AreSame(_event, testListener.LastNewData[0].Underlying);
            Assert.IsNull(testListener.LastOldData); // receive no old data, just istream events
            testListener.Reset();
        }

        [Test]
        public virtual void testIStreamInsertIntoRStream()
        {
            EPStatement statement = epService.EPAdministrator.CreateEQL(
                "insert rstream into NextStream " +
                "select istream a.str as string from " + typeof(SupportBean).FullName + ".win:length(1) as a"
                );
            statement.AddListener(testListener);

            statement = epService.EPAdministrator.CreateEQL("select * from NextStream");
            statement.AddListener(testListenerInsertInto);

            SendEvent("a");
            Assert.AreEqual("a", testListener.AssertOneGetNewAndReset()["string"]);
            Assert.IsFalse(testListenerInsertInto.IsInvoked);

            SendEvent("b");
            Assert.AreEqual("b", testListener.LastNewData[0]["string"]);
            Assert.IsNull(testListener.LastOldData);
            Assert.AreEqual("a", testListenerInsertInto.LastNewData[0]["string"]);
            Assert.IsNull(testListenerInsertInto.LastOldData);
        }

        [Test]
        public virtual void testIStreamJoin()
        {
            EPStatement statement = epService.EPAdministrator.CreateEQL(
                "select istream s1.intPrimitive as aID, s2.intPrimitive as bID " +
                "from " +
                typeof(SupportBean).FullName + "(str='a').win:length(2) as s1, " +
                typeof(SupportBean).FullName + "(str='b') as s2" +
                " where s1.intPrimitive = s2.intPrimitive"
                );
            statement.AddListener(testListener);

            SendEvent("a", 1);
            SendEvent("b", 1);
            Assert.AreEqual(1, testListener.LastNewData[0]["aID"]); // receive 'a' as new data
            Assert.AreEqual(1, testListener.LastNewData[0]["bID"]);
            Assert.IsNull(testListener.LastOldData); // receive no more old data
            testListener.Reset();

            SendEvent("a", 2);
            Assert.IsFalse(testListener.IsInvoked);

            SendEvent("a", 3);
            Assert.IsFalse(testListener.IsInvoked);
        }

        private void sendEvents(String[] stringValue)
        {
            for (int i = 0; i < stringValue.Length; i++)
            {
                SendEvent(stringValue[i]);
            }
        }

        private Object SendEvent(String stringValue)
        {
            return SendEvent(stringValue, -1);
        }

        private Object SendEvent(String stringValue, int intPrimitive)
        {
            SupportBean _event = new SupportBean();
            _event.SetString(stringValue);
            _event.SetIntPrimitive(intPrimitive);
            epService.EPRuntime.SendEvent(_event);
            return _event;
        }
    }
}
