package com.espertech.esper.epl.core;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportSelectExprFactory;
import com.espertech.esper.support.epl.SupportStreamTypeSvc1Stream;
import com.espertech.esper.support.event.SupportEventAdapterService;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class TestResultSetProcessorSimple extends TestCase
{
    private ResultSetProcessorSimple outputProcessorLast;
    private ResultSetProcessorSimple outputProcessorAll;
    private SelectExprProcessor selectExprProcessor;
    private OrderByProcessor orderByProcessor;

    public void setUp() throws Exception
    {
        SelectExprEventTypeRegistry selectExprEventTypeRegistry = new SelectExprEventTypeRegistry(new HashSet<String>());

        selectExprProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.makeNoAggregateSelectList(), null, false, new SupportStreamTypeSvc1Stream(), SupportEventAdapterService.getService(), null, selectExprEventTypeRegistry);
        orderByProcessor = null;

		outputProcessorAll = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true);
		outputProcessorLast = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true);
    }

    public void testUpdateAll() throws Exception
    {
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[]) null, true, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        EventBean[] newData = new EventBean[] {testEvent1, testEvent2};

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
	    EventBean[] oldData = new EventBean[] {testEvent3, testEvent4};

        UniformPair<EventBean[]> result = outputProcessorAll.processViewResult(newData, oldData, false);
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
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), true, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        Set<MultiKey<EventBean>> newEventSet = makeEventSet(testEvent1);
	    newEventSet.add(new MultiKey<EventBean>(new EventBean[] { testEvent2}));

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
        Set<MultiKey<EventBean>> oldEventSet = makeEventSet(testEvent3);
	    oldEventSet.add(new MultiKey<EventBean>(new EventBean[] {testEvent4}));

        UniformPair<EventBean[]> result = outputProcessorAll.processJoinResult(newEventSet, oldEventSet, false);
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
}
