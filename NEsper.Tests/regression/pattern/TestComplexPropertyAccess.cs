using System;

using net.esper.client;
using net.esper.events;
using net.esper.regression.support;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.pattern
{
    [TestFixture]
    public class TestComplexPropertyAccess
    {
        private static String EVENT_COMPLEX;
        private static String EVENT_NESTED;

        [Test]
        public virtual void testComplexProperties()
        {
            Console.Out.WriteLine(SupportBeanCombinedProps.makeDefaultBean().Array[0].getMapped("0ma").Value);

            EventCollection events = EventCollectionFactory.getSetSixComplexProperties();
            CaseList testCaseList = new CaseList();
            EventExpressionCase testCase = null;

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(mapped('keyOne') = 'valueOne')");
            testCase.Add("e1", "s", events.getEvent("e1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(indexed[1] = 2)");
            testCase.Add("e1", "s", events.getEvent("e1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(indexed[0] = 2)");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(arrayProperty[1] = 20)");
            testCase.Add("e1", "s", events.getEvent("e1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(arrayProperty[1] in (10:30))");
            testCase.Add("e1", "s", events.getEvent("e1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(arrayProperty[2] = 20)");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedValue = 'nestedValue')");
            testCase.Add("e1", "s", events.getEvent("e1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedValue = 'dummy')");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedNested.nestedNestedValue = 'nestedNestedValue')");
            testCase.Add("e1", "s", events.getEvent("e1"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_COMPLEX + "(nested.nestedNested.nestedNestedValue = 'x')");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(indexed[1].mapped('1mb').value = '1ma1')");
            testCase.Add("e2", "s", events.getEvent("e2"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(indexed[0].mapped('1ma').value = 'x')");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[0].mapped('0ma').value = '0ma0')");
            testCase.Add("e2", "s", events.getEvent("e2"));
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[2].mapped('x').value = 'x')");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[879787].mapped('x').value = 'x')");
            testCaseList.AddTest(testCase);

            testCase = new EventExpressionCase("s=" + EVENT_NESTED + "(array[0].mapped('xxx').value = 'x')");
            testCaseList.AddTest(testCase);

            PatternTestHarness util = new PatternTestHarness(events, testCaseList);
            util.runTest();
        }

        [Test]
        public virtual void testIndexedFilterProp()
        {
            SupportUpdateListener testListener = new SupportUpdateListener();
            EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            String type = typeof(SupportBeanComplexProps).FullName;
            String pattern = "every a=" + type + "(indexed[0]=3)";

            EPStatement stmt = epService.EPAdministrator.createPattern(pattern);
            stmt.AddListener(testListener);

            Object _event = new SupportBeanComplexProps(new int[] { 3, 4 });
            epService.EPRuntime.SendEvent(_event);
            Assert.AreSame(_event, testListener.assertOneGetNewAndReset()["a"]);

            _event = new SupportBeanComplexProps(new int[] { 6 });
            epService.EPRuntime.SendEvent(_event);
            Assert.IsFalse(testListener.Invoked);

            _event = new SupportBeanComplexProps(new int[] { 3 });
            epService.EPRuntime.SendEvent(_event);
            Assert.AreSame(_event, testListener.assertOneGetNewAndReset()["a"]);
        }

        [Test]
        public virtual void testIndexedValueProp()
        {
            SupportUpdateListener testListener = new SupportUpdateListener();
            EPServiceProvider epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();

            String type = typeof(SupportBeanComplexProps).FullName;
            String pattern = "every a=" + type + " -> b=" + type + "(indexed[0] = a.indexed[0])";

            EPStatement stmt = epService.EPAdministrator.createPattern(pattern);
            stmt.AddListener(testListener);

            Object eventOne = new SupportBeanComplexProps(new int[] { 3 });
            epService.EPRuntime.SendEvent(eventOne);
            Assert.IsFalse(testListener.Invoked);

            Object _event = new SupportBeanComplexProps(new int[] { 6 });
            epService.EPRuntime.SendEvent(_event);
            Assert.IsFalse(testListener.Invoked);

            Object eventTwo = new SupportBeanComplexProps(new int[] { 3 });
            epService.EPRuntime.SendEvent(eventTwo);
            EventBean eventBean = testListener.assertOneGetNewAndReset();
            Assert.AreSame(eventOne, eventBean["a"]);
            Assert.AreSame(eventTwo, eventBean["b"]);
        }

        static TestComplexPropertyAccess()
        {
            EVENT_COMPLEX = typeof(SupportBeanComplexProps).FullName;
            EVENT_NESTED = typeof(SupportBeanCombinedProps).FullName;
        }
    }
}