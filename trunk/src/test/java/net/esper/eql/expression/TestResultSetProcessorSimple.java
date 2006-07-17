package net.esper.eql.expression;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.TestCase;
import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportSelectExprFactory;
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
        selectExprProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.makeNoAggregateSelectList(), null, SupportEventAdapterService.getService());
        orderByProcessor = null;

		outputLimitSpecAll = new OutputLimitSpec(1, false);
		assertFalse(outputLimitSpecAll.isDisplayLastOnly());
		outputProcessorAll = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, true, false);

		outputLimitSpecLast = new OutputLimitSpec(1, true);
		assertTrue(outputLimitSpecLast.isDisplayLastOnly());
		outputProcessorLast = new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, true, true);
    }

    public void testUpdateAll() throws Exception
    {
        assertNull(ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false));

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
        assertEquals(10d, newEvents[0].get("s0.doubleBoxed"));
        assertEquals(30, newEvents[0].get("result"));

	    assertEquals(11d, newEvents[1].get("s0.doubleBoxed"));
	    assertEquals(42, newEvents[1].get("result"));

        assertEquals(2, oldEvents.length);
        assertEquals(20d, oldEvents[0].get("s0.doubleBoxed"));
        assertEquals(2, oldEvents[0].get("result"));

	    assertEquals(21d, oldEvents[1].get("s0.doubleBoxed"));
	    assertEquals(12, oldEvents[1].get("result"));
    }

    public void testProcessAll() throws Exception
    {
        assertNull(ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false));

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
        assertEquals(10d, newEvents[0].get("s0.doubleBoxed"));
        assertEquals(30, newEvents[0].get("result"));

	    assertEquals(11d, newEvents[1].get("s0.doubleBoxed"));
	    assertEquals(42, newEvents[1].get("result"));

        assertEquals(2, oldEvents.length);
        assertEquals(20d, oldEvents[0].get("s0.doubleBoxed"));
        assertEquals(2, oldEvents[0].get("result"));

	    assertEquals(21d, oldEvents[1].get("s0.doubleBoxed"));
	    assertEquals(12, oldEvents[1].get("result"));
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
        assertNull(ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, new HashSet<MultiKey<EventBean>>(), false, false));

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
	    assertEquals(11d, newEvents[0].get("s0.doubleBoxed"));
	    assertEquals(42, newEvents[0].get("result"));

        assertEquals(1, oldEvents.length);
	    assertEquals(21d, oldEvents[0].get("s0.doubleBoxed"));
	    assertEquals(12, oldEvents[0].get("result"));
	}

	public void testUpdateLast() throws Exception
	{
	       assertNull(ResultSetProcessorSimple.getSelectListEvents(selectExprProcessor, orderByProcessor, (EventBean[]) null, false, false));

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
		    assertEquals(11d, newEvents[0].get("s0.doubleBoxed"));
		    assertEquals(42, newEvents[0].get("result"));

	        assertEquals(1, oldEvents.length);
		    assertEquals(21d, oldEvents[0].get("s0.doubleBoxed"));
		    assertEquals(12, oldEvents[0].get("result"));
	}
}
