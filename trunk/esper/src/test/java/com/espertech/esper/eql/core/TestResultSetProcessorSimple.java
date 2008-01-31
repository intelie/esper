package com.espertech.esper.eql.core;

import junit.framework.TestCase;
import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.eql.spec.OutputLimitSpec;
import com.espertech.esper.eql.spec.OutputLimitLimitType;
import com.espertech.esper.eql.spec.OutputLimitRateType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.eql.SupportSelectExprFactory;
import com.espertech.esper.support.eql.SupportStreamTypeSvc1Stream;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.event.SupportEventBeanFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class TestResultSetProcessorSimple extends TestCase
{
    private ResultSetProcessorSimple outputProcessorLast;
    private ResultSetProcessorSimple outputProcessorAll;
    private SelectExprProcessor selectExprProcessor;
    private OrderByProcessor orderByProcessor;
	private OutputLimitSpec outputLimitSpecLast;
	private OutputLimitSpec outputLimitSpecAll;

    public void setUp() throws Exception
    {
        selectExprProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.makeNoAggregateSelectList(), null, false, new SupportStreamTypeSvc1Stream(), SupportEventAdapterService.getService());
        orderByProcessor = null;

		outputLimitSpecAll = new OutputLimitSpec(1d, null, OutputLimitRateType.EVENTS, OutputLimitLimitType.ALL);
		outputProcessorAll = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true, false);

		outputLimitSpecLast = new OutputLimitSpec(1d, null, OutputLimitRateType.EVENTS, OutputLimitLimitType.LAST);
		outputProcessorLast = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true, true);
    }

    public void testUpdateAll() throws Exception
    {
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false, true, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        EventBean[] newData = new EventBean[] {testEvent1, testEvent2};

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
	    EventBean[] oldData = new EventBean[] {testEvent3, testEvent4};

        Pair<EventBean[], EventBean[]> result = outputProcessorAll.processViewResult(newData, oldData, false);
        EventBean[] newEvents = result.getFirst();
        EventBean[] oldEvents = result.getSecond();

        assertEquals(2, newEvents.length);
        assertEquals(10d, newEvents[0].get("resultOne"));
        assertEquals(30, newEvents[0].get("resultTwo"));

	    assertEquals(11d, newEvents[1].get("resultOne"));
	    assertEquals(42, newEvents[1].get("resultTwo"));

        assertEquals(2, oldEvents.length);
        assertEquals(20d, oldEvents[0].get("resultOne"));
        assertEquals(2, oldEvents[0].get("resultTwo"));

	    assertEquals(21d, oldEvents[1].get("resultOne"));
	    assertEquals(12, oldEvents[1].get("resultTwo"));
    }

    public void testProcessAll() throws Exception
    {
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false, true, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        Set<MultiKey<EventBean>> newEventSet = makeEventSet(testEvent1);
	    newEventSet.add(new MultiKey<EventBean>(new EventBean[] { testEvent2}));

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
        Set<MultiKey<EventBean>> oldEventSet = makeEventSet(testEvent3);
	    oldEventSet.add(new MultiKey<EventBean>(new EventBean[] {testEvent4}));

        Pair<EventBean[], EventBean[]> result = outputProcessorAll.processJoinResult(newEventSet, oldEventSet, false);
        EventBean[] newEvents = result.getFirst();
        EventBean[] oldEvents = result.getSecond();

        assertEquals(2, newEvents.length);
        assertEquals(10d, newEvents[0].get("resultOne"));
        assertEquals(30, newEvents[0].get("resultTwo"));

	    assertEquals(11d, newEvents[1].get("resultOne"));
	    assertEquals(42, newEvents[1].get("resultTwo"));

        assertEquals(2, oldEvents.length);
        assertEquals(20d, oldEvents[0].get("resultOne"));
        assertEquals(2, oldEvents[0].get("resultTwo"));

	    assertEquals(21d, oldEvents[1].get("resultOne"));
	    assertEquals(12, oldEvents[1].get("resultTwo"));
    }

    private Set<MultiKey<EventBean>> makeEventSet(EventBean event)
    {
        Set<MultiKey<EventBean>> result = new LinkedHashSet<MultiKey<EventBean>>();
        result.add(new MultiKey<EventBean>(new EventBean[] { event}));
        return result;
    }

    private EventBean makeEvent(double doubleBoxed, int intBoxed, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setDoubleBoxed(doubleBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setIntPrimitive(intPrimitive);
        return SupportEventBeanFactory.createObject(bean);
    }

	public void testProcessLast() throws Exception
	{
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false, true, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        Set<MultiKey<EventBean>> newEventSet = makeEventSet(testEvent1);
	    newEventSet.add(new MultiKey<EventBean>(new EventBean[] { testEvent2}));

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
        Set<MultiKey<EventBean>> oldEventSet = makeEventSet(testEvent3);
	    oldEventSet.add(new MultiKey<EventBean>(new EventBean[] {testEvent4}));

        Pair<EventBean[], EventBean[]> result = outputProcessorLast.processJoinResult(newEventSet, oldEventSet, false);
        EventBean[] newEvents = result.getFirst();
        EventBean[] oldEvents = result.getSecond();

        assertEquals(1, newEvents.length);
	    assertEquals(11d, newEvents[0].get("resultOne"));
	    assertEquals(42, newEvents[0].get("resultTwo"));

        assertEquals(1, oldEvents.length);
	    assertEquals(21d, oldEvents[0].get("resultOne"));
	    assertEquals(12, oldEvents[0].get("resultTwo"));
	}

	public void testUpdateLast() throws Exception
	{
	       assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false, true, false));

	        EventBean testEvent1 = makeEvent(10, 5, 6);
		    EventBean testEvent2 = makeEvent(11, 6, 7);
	        EventBean[] newData = new EventBean[] {testEvent1, testEvent2};

	        EventBean testEvent3 = makeEvent(20, 1, 2);
		    EventBean testEvent4 = makeEvent(21, 3, 4);
		    EventBean[] oldData = new EventBean[] {testEvent3, testEvent4};

	        Pair<EventBean[], EventBean[]> result = outputProcessorLast.processViewResult(newData, oldData, false);
	        EventBean[] newEvents = result.getFirst();
	        EventBean[] oldEvents = result.getSecond();

	        assertEquals(1, newEvents.length);
		    assertEquals(11d, newEvents[0].get("resultOne"));
		    assertEquals(42, newEvents[0].get("resultTwo"));

	        assertEquals(1, oldEvents.length);
		    assertEquals(21d, oldEvents[0].get("resultOne"));
		    assertEquals(12, oldEvents[0].get("resultTwo"));
	}
}
