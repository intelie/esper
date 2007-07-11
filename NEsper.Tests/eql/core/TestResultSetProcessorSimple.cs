///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.collection;
using net.esper.eql.core;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

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
	    public void SetUp()
	    {
	        selectExprProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.MakeNoAggregateSelectList(), null, false, new SupportStreamTypeSvc1Stream(), SupportEventAdapterService.GetService());
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
	        Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false, true));

	        EventBean testEvent1 = MakeEvent(10, 5, 6);
		    EventBean testEvent2 = MakeEvent(11, 6, 7);
	        EventBean[] newData = new EventBean[] {testEvent1, testEvent2};

	        EventBean testEvent3 = MakeEvent(20, 1, 2);
		    EventBean testEvent4 = MakeEvent(21, 3, 4);
		    EventBean[] oldData = new EventBean[] {testEvent3, testEvent4};

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
	        Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false, true));

	        EventBean testEvent1 = MakeEvent(10, 5, 6);
		    EventBean testEvent2 = MakeEvent(11, 6, 7);
	        Set<MultiKey<EventBean>> newEventSet = MakeEventSet(testEvent1);
		    newEventSet.Add(new MultiKey<EventBean>(new EventBean[] { testEvent2}));

	        EventBean testEvent3 = MakeEvent(20, 1, 2);
		    EventBean testEvent4 = MakeEvent(21, 3, 4);
	        Set<MultiKey<EventBean>> oldEventSet = MakeEventSet(testEvent3);
		    oldEventSet.Add(new MultiKey<EventBean>(new EventBean[] {testEvent4}));

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

	    private Set<MultiKey<EventBean>> MakeEventSet(EventBean _event)
	    {
	        Set<MultiKey<EventBean>> result = new LinkedHashSet<MultiKey<EventBean>>();
	        result.Add(new MultiKey<EventBean>(new EventBean[] { _event }));
	        return result;
	    }

	    private EventBean MakeEvent(double doubleBoxed, int intBoxed, int intPrimitive)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetDoubleBoxed(doubleBoxed);
	        bean.SetIntBoxed(intBoxed);
	        bean.SetIntPrimitive(intPrimitive);
	        return SupportEventBeanFactory.CreateObject(bean);
	    }

		[Test]
		public void testProcessLast()
		{
	        Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false, true));

	        EventBean testEvent1 = MakeEvent(10, 5, 6);
		    EventBean testEvent2 = MakeEvent(11, 6, 7);
	        Set<MultiKey<EventBean>> newEventSet = MakeEventSet(testEvent1);
		    newEventSet.Add(new MultiKey<EventBean>(new EventBean[] { testEvent2}));

	        EventBean testEvent3 = MakeEvent(20, 1, 2);
		    EventBean testEvent4 = MakeEvent(21, 3, 4);
	        Set<MultiKey<EventBean>> oldEventSet = MakeEventSet(testEvent3);
		    oldEventSet.Add(new MultiKey<EventBean>(new EventBean[] {testEvent4}));

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
		       Assert.IsNull(ResultSetProcessorSimple.GetSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false, true));

		        EventBean testEvent1 = MakeEvent(10, 5, 6);
			    EventBean testEvent2 = MakeEvent(11, 6, 7);
		        EventBean[] newData = new EventBean[] {testEvent1, testEvent2};

		        EventBean testEvent3 = MakeEvent(20, 1, 2);
			    EventBean testEvent4 = MakeEvent(21, 3, 4);
			    EventBean[] oldData = new EventBean[] {testEvent3, testEvent4};

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
} // End of namespace
