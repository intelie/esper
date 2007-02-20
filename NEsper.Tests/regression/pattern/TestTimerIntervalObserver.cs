using System;

using net.esper.client;
using net.esper.client.time;
using net.esper.regression.support;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{
    [TestFixture]
    public class TestTimerIntervalObserver : SupportBeanConstants
    {
        [Test]
        public virtual void testOp()
        {
            EventCollection events = EventCollectionFactory.getEventSetOne(0, 1000);
            CaseList testCaseList = new CaseList();
            EventExpressionCase testCase = null;

            // The wait is done when 2 seconds passed
            testCase = new EventExpressionCase("timer:interval(1999 msec)");
            testCase.Add("B1");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(2 sec)");
            testCase.Add("B1");
            testCaseList.AddTest(testCase);

            // 3 seconds (>2001 microseconds) passed
            testCase = new EventExpressionCase("timer:interval(2.001)");
            testCase.Add("C1");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(2999 milliseconds)");
            testCase.Add("C1");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(3 seconds)");
            testCase.Add("C1");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(3.001 seconds)");
            testCase.Add("B2");
            testCaseList.AddTest(testCase);

            // Try with an all ... repeated timer every 3 seconds
            testCase = new EventExpressionCase("every timer:interval(3.001 sec)");
            testCase.Add("B2");
            testCase.Add("F1");
            testCase.Add("D3");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every timer:interval(5000 msec)");
            testCase.Add("A2");
            testCase.Add("B3");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("not timer:interval(5000 millisecond)");
            testCase.Add(EventCollection.ON_START_EVENT_ID);
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(3.999 second) -> b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
            testCase.Add("B2", "b", events.getEvent("B2"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(4 sec) -> b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
            testCase.Add("B2", "b", events.getEvent("B2"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(4.001 sec) -> b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
            testCase.Add("B3", "b", events.getEvent("B3"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(0) -> b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
            testCase.Add("B1", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            // Try with an followed-by as a second argument
            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> timer:interval(0.001)");
            testCase.Add("C1", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> timer:interval(0)");
            testCase.Add("B1", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> timer:interval(1 sec)");
            testCase.Add("C1", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + " -> timer:interval(1.001)");
            testCase.Add("B2", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            // Try in a 3-way followed by
            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() -> timer:interval(6.000) -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS);
            testCase.Add("D2", "b", events.getEvent("B1"), "d", events.getEvent("D2"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() -> timer:interval(2.001) -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "())");
            testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("every (b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() -> timer:interval(2.000) -> d=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_D_CLASS + "())");
            testCase.Add("D1", "b", events.getEvent("B1"), "d", events.getEvent("D1"));
            testCase.Add("D3", "b", events.getEvent("B3"), "d", events.getEvent("D3"));
            testCaseList.AddTest(testCase);

            // Try with an "or"
            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() or timer:interval(1.001)");
            testCase.Add("B1");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() or timer:interval(2.001)");
            testCase.Add("B1", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "(id='B3') or timer:interval(8.500)");
            testCase.Add("D2");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(8.500) or timer:interval(7.500)");
            testCase.Add("F1");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(999999 msec) or g=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_G_CLASS);
            testCase.Add("G1", "g", events.getEvent("G1"));
            testCaseList.AddTest(testCase);

            // Try with an "and"
            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() and timer:interval(4000 msec)");
            testCase.Add("B2", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "() and timer:interval(4001 msec)");
            testCase.Add("A2", "b", events.getEvent("B1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(9999999 msec) and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS);
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(1 msec) and b=" + net.esper.support.bean.SupportBeanConstants_Fields.EVENT_B_CLASS + "(id=\"B2\")");
            testCase.Add("B2", "b", events.getEvent("B2"));
            testCaseList.AddTest(testCase);

            // Try with an "within"
            testCase = new EventExpressionCase("timer:interval(3.000) where timer:within(2.000)");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("timer:interval(3.000) where timer:within (3.000)");
            testCase.Add("C1");
            testCaseList.AddTest(testCase);

            // Run all tests
            PatternTestHarness util = new PatternTestHarness(events, testCaseList);
            util.runTest();
        }

        [Test]
        public virtual void testIntervalSpec()
        {
            EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            // External clocking
            epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));
            sendTimer(0, epService);

            // Set up a timer:within
            EPStatement statement = epService.EPAdministrator.createEQL("select * from pattern [timer:interval(1 minute 2 seconds)]");

            SupportUpdateListener testListener = new SupportUpdateListener();
            statement.AddListener(testListener);

            sendTimer(62 * 1000 - 1, epService);
            Assert.IsFalse(testListener.Invoked);

            sendTimer(62 * 1000, epService);
            Assert.IsTrue(testListener.Invoked);
        }

        private void sendTimer(long timeInMSec, EPServiceProvider epService)
        {
            CurrentTimeEvent _event = new CurrentTimeEvent(timeInMSec);
            EPRuntime runtime = epService.EPRuntime;
            runtime.SendEvent(_event);
        }
    }
}