package net.esper.eql.core;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.TestCase;
import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.eql.spec.OutputLimitSpec.DisplayLimit;
import net.esper.eql.spec.OutputLimitSpec;
import net.esper.eql.core.OrderByProcessor;
import net.esper.eql.core.ResultSetProcessorSimple;
import net.esper.eql.core.SelectExprEvalProcessor;
import net.esper.eql.core.SelectExprProcessor;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportSelectExprFactory;
import net.esper.support.eql.SupportStreamTypeSvc1Stream;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.support.event.SupportEventBeanFactory;

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

		outputLimitSpecAll = new OutputLimitSpec(1, DisplayLimit.ALL);
		assertFalse(outputLimitSpecAll.isDisplayLastOnly());
		outputProcessorAll = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true, false);

		outputLimitSpecLast = new OutputLimitSpec(1, DisplayLimit.LAST);
		assertTrue(outputLimitSpecLast.isDisplayLastOnly());
		outputProcessorLast = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, null, true, true);
    }

    public void testUpdateAll() throws Exception
    {
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        EventBean[] newData = new EventBean[] {testEvent1, testEvent2};

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
	    EventBean[] oldData = new EventBean[] {testEvent3, testEvent4};

        Pair<EventBean[], EventBean[]> result = outputProcessorAll.processViewResult(newData, oldData);
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
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        Set<MultiKey<EventBean>> newEventSet = makeEventSet(testEvent1);
	    newEventSet.add(new MultiKey<EventBean>(new EventBean[] { testEvent2}));

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
        Set<MultiKey<EventBean>> oldEventSet = makeEventSet(testEvent3);
	    oldEventSet.add(new MultiKey<EventBean>(new EventBean[] {testEvent4}));

        Pair<EventBean[], EventBean[]> result = outputProcessorAll.processJoinResult(newEventSet, oldEventSet);
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
        assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false));

        EventBean testEvent1 = makeEvent(10, 5, 6);
	    EventBean testEvent2 = makeEvent(11, 6, 7);
        Set<MultiKey<EventBean>> newEventSet = makeEventSet(testEvent1);
	    newEventSet.add(new MultiKey<EventBean>(new EventBean[] { testEvent2}));

        EventBean testEvent3 = makeEvent(20, 1, 2);
	    EventBean testEvent4 = makeEvent(21, 3, 4);
        Set<MultiKey<EventBean>> oldEventSet = makeEventSet(testEvent3);
	    oldEventSet.add(new MultiKey<EventBean>(new EventBean[] {testEvent4}));

        Pair<EventBean[], EventBean[]> result = outputProcessorLast.processJoinResult(newEventSet, oldEventSet);
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
	       assertNull(ResultSetProcessorSimple.getSelectEventsNoHaving(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false));

	        EventBean testEvent1 = makeEvent(10, 5, 6);
		    EventBean testEvent2 = makeEvent(11, 6, 7);
	        EventBean[] newData = new EventBean[] {testEvent1, testEvent2};

	        EventBean testEvent3 = makeEvent(20, 1, 2);
		    EventBean testEvent4 = makeEvent(21, 3, 4);
		    EventBean[] oldData = new EventBean[] {testEvent3, testEvent4};

	        Pair<EventBean[], EventBean[]> result = outputProcessorLast.processViewResult(newData, oldData);
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
