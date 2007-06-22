using System;

using net.esper.client;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{
    [TestFixture]
    public class TestPatternStartStop
    {
        private EPServiceProvider epService;
        private SupportUpdateListener testListener;
        private EPStatement patternStmt;

        [SetUp]
        public virtual void setUp()
        {
            testListener = new SupportUpdateListener();
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            String viewExpr = "every tag=" + typeof(SupportBean).FullName;

            patternStmt = epService.EPAdministrator.CreatePattern(viewExpr);
        }

        [Test]
        public virtual void testStartStop()
        {
            // Pattern Started when created
            Assert.IsFalse(patternStmt.GetEnumerator().MoveNext());

            // Stop pattern
            patternStmt.Stop();
            SendEvent();
            Assert.IsNull(patternStmt.GetEnumerator());

            // Start pattern
            patternStmt.Start();
            Assert.IsFalse(patternStmt.GetEnumerator().MoveNext());

            // Send event
            SupportBean _event = SendEvent();
            Assert.AreSame(_event, CollectionHelper.First<EventBean>(patternStmt)["tag"]);

            // Stop pattern
            patternStmt.Stop();
            Assert.IsNull(patternStmt.GetEnumerator());

            // Start again, iterator is zero
            patternStmt.Start();
            Assert.IsFalse(patternStmt.GetEnumerator().MoveNext());
        }

        [Test]
        public virtual void testAddRemoveListener()
        {
            // Pattern Started when created

            // Add listener
            patternStmt.AddListener(testListener);
            Assert.IsNull(testListener.LastNewData);
            Assert.IsFalse(patternStmt.GetEnumerator().MoveNext());

            // Send event
            SupportBean _event = SendEvent();
            Assert.AreEqual(_event, testListener.GetAndResetLastNewData()[0]["tag"]);
            Assert.AreSame(_event, CollectionHelper.First<EventBean>(patternStmt)["tag"]);

            // Remove listener
            patternStmt.RemoveListener(testListener);
            _event = SendEvent();
            Assert.AreSame(_event, CollectionHelper.First<EventBean>(patternStmt)["tag"]);
            Assert.IsNull(testListener.LastNewData);

            // Add listener back
            patternStmt.AddListener(testListener);
            _event = SendEvent();
            Assert.AreSame(_event, CollectionHelper.First<EventBean>(patternStmt)["tag"]);
            Assert.AreEqual(_event, testListener.GetAndResetLastNewData()[0]["tag"]);
        }

        private SupportBean SendEvent()
        {
            SupportBean _event = new SupportBean();
            epService.EPRuntime.SendEvent(_event);
            return _event;
        }
    }
}
