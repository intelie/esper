using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
    public class TestResultSetProcessorSimple 
    {
        private ResultSetProcessorSimple outputProcessorLast;
        private ResultSetProcessorSimple outputProcessorAll;
        private SelectExprProcessor selectExprProcessor;
        private OrderByProcessor orderByProcessor;
        private OutputLimitSpec outputLimitSpecLast;
        private OutputLimitSpec outputLimitSpecAll;

		[SetUp]
        public void setUp()
        {
            selectExprProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.makeNoAggregateSelectList(), null, SupportEventAdapterService.Service);
            orderByProcessor = null;

            outputLimitSpecAll = new OutputLimitSpec(1, OutputLimitSpec.DisplayLimit.ALL);
            Assert.IsFalse(outputLimitSpecAll.IsDisplayLastOnly);
            outputProcessorAll = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true, false);

            outputLimitSpecLast = new OutputLimitSpec(1, OutputLimitSpec.DisplayLimit.LAST);
            Assert.IsTrue(outputLimitSpecLast.IsDisplayLastOnly);
            outputProcessorLast = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true, true);
        }

        [Test]
        public void testUpdateAll()
        {
            Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[])null, false, false));

            EventBean testEvent1 = MakeEvent(10, 5, 6);
            EventBean testEvent2 = MakeEvent(11, 6, 7);
            EventBean[] newData = new EventBean[] { testEvent1, testEvent2 };

            EventBean testEvent3 = MakeEvent(20, 1, 2);
            EventBean testEvent4 = MakeEvent(21, 3, 4);
            EventBean[] oldData = new EventBean[] { testEvent3, testEvent4 };

            Pair<EventBean[], EventBean[]> result = outputProcessorAll.ProcessViewResult(newData, oldData);
            EventBean[] newEvents = result.First;
            EventBean[] oldEvents = result.Second;

            Assert.AreEqual(2, newEvents.Length);
            Assert.AreEqual(10d, newEvents[0]["resultOne"]);
            Assert.AreEqual(30, newEvents[0]["resultTwo"]);

            Assert.AreEqual(11d, newEvents[1]["resultOne"]);
            Assert.AreEqual(42, newEvents[1]["resultTwo"]);

            Assert.AreEqual(2, oldEvents.Length);
            Assert.AreEqual(20d, oldEvents[0]["resultOne"]);
            Assert.AreEqual(2, oldEvents[0]["resultTwo"]);

            Assert.AreEqual(21d, oldEvents[1]["resultOne"]);
            Assert.AreEqual(12, oldEvents[1]["resultTwo"]);
        }

        [Test]
        public void testProcessAll()
        {
            Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new EHashSet<MultiKey<EventBean>>(), false, false));

            EventBean testEvent1 = MakeEvent(10, 5, 6);
            EventBean testEvent2 = MakeEvent(11, 6, 7);
            ISet<MultiKey<EventBean>> newEventSet = makeEventSet(testEvent1);
            newEventSet.Add(new MultiKey<EventBean>(new EventBean[] { testEvent2 }));

            EventBean testEvent3 = MakeEvent(20, 1, 2);
            EventBean testEvent4 = MakeEvent(21, 3, 4);
            ISet<MultiKey<EventBean>> oldEventSet = makeEventSet(testEvent3);
            oldEventSet.Add(new MultiKey<EventBean>(new EventBean[] { testEvent4 }));

            Pair<EventBean[], EventBean[]> result = outputProcessorAll.ProcessJoinResult(newEventSet, oldEventSet);
            EventBean[] newEvents = result.First;
            EventBean[] oldEvents = result.Second;

            Assert.AreEqual(2, newEvents.Length);
            Assert.AreEqual(10d, newEvents[0]["resultOne"]);
            Assert.AreEqual(30, newEvents[0]["resultTwo"]);

            Assert.AreEqual(11d, newEvents[1]["resultOne"]);
            Assert.AreEqual(42, newEvents[1]["resultTwo"]);

            Assert.AreEqual(2, oldEvents.Length);
            Assert.AreEqual(20d, oldEvents[0]["resultOne"]);
            Assert.AreEqual(2, oldEvents[0]["resultTwo"]);

            Assert.AreEqual(21d, oldEvents[1]["resultOne"]);
            Assert.AreEqual(12, oldEvents[1]["resultTwo"]);
        }

        private ISet<MultiKey<EventBean>> makeEventSet(EventBean _event)
        {
            ISet<MultiKey<EventBean>> result = new LinkedHashSet<MultiKey<EventBean>>();
            result.Add(new MultiKey<EventBean>(new EventBean[] { _event }));
            return result;
        }

        private EventBean MakeEvent(double doubleBoxed, int intBoxed, int intPrimitive)
        {
            SupportBean bean = new SupportBean();
            bean.doubleBoxed = doubleBoxed;
            bean.intBoxed = intBoxed;
            bean.intPrimitive = intPrimitive;
            return SupportEventBeanFactory.createObject(bean);
        }

        [Test]
        public void testProcessLast()
        {
            Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new EHashSet<MultiKey<EventBean>>(), false, false));

            EventBean testEvent1 = MakeEvent(10, 5, 6);
            EventBean testEvent2 = MakeEvent(11, 6, 7);
            ISet<MultiKey<EventBean>> newEventSet = makeEventSet(testEvent1);
            newEventSet.Add(new MultiKey<EventBean>(new EventBean[] { testEvent2 }));

            EventBean testEvent3 = MakeEvent(20, 1, 2);
            EventBean testEvent4 = MakeEvent(21, 3, 4);
            ISet<MultiKey<EventBean>> oldEventSet = makeEventSet(testEvent3);
            oldEventSet.Add(new MultiKey<EventBean>(new EventBean[] { testEvent4 }));

            Pair<EventBean[], EventBean[]> result = outputProcessorLast.ProcessJoinResult(newEventSet, oldEventSet);
            EventBean[] newEvents = result.First;
            EventBean[] oldEvents = result.Second;

            Assert.AreEqual(1, newEvents.Length);
            Assert.AreEqual(11d, newEvents[0]["resultOne"]);
            Assert.AreEqual(42, newEvents[0]["resultTwo"]);

            Assert.AreEqual(1, oldEvents.Length);
            Assert.AreEqual(21d, oldEvents[0]["resultOne"]);
            Assert.AreEqual(12, oldEvents[0]["resultTwo"]);
        }

        [Test]
        public void testUpdateLast()
        {
            Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[])null, false, false));

            EventBean testEvent1 = MakeEvent(10, 5, 6);
            EventBean testEvent2 = MakeEvent(11, 6, 7);
            EventBean[] newData = new EventBean[] { testEvent1, testEvent2 };

            EventBean testEvent3 = MakeEvent(20, 1, 2);
            EventBean testEvent4 = MakeEvent(21, 3, 4);
            EventBean[] oldData = new EventBean[] { testEvent3, testEvent4 };

            Pair<EventBean[], EventBean[]> result = outputProcessorLast.ProcessViewResult(newData, oldData);
            EventBean[] newEvents = result.First;
            EventBean[] oldEvents = result.Second;

            Assert.AreEqual(1, newEvents.Length);
            Assert.AreEqual(11d, newEvents[0]["resultOne"]);
            Assert.AreEqual(42, newEvents[0]["resultTwo"]);

            Assert.AreEqual(1, oldEvents.Length);
            Assert.AreEqual(21d, oldEvents[0]["resultOne"]);
            Assert.AreEqual(12, oldEvents[0]["resultTwo"]);
        }
    }
}
