package com.espertech.esper.epl.core;

import junit.framework.TestCase;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportStreamTypeSvc3Stream;
import com.espertech.esper.support.event.SupportEventAdapterService;

import java.util.HashSet;

public class TestSelectExprJoinWildcardProcessor extends TestCase
{
    private SelectExprJoinWildcardProcessor processor;

    public void setUp() throws ExprValidationException
    {
        SelectExprEventTypeRegistry selectExprEventTypeRegistry = new SelectExprEventTypeRegistry(new HashSet<String>());
        SupportStreamTypeSvc3Stream supportTypes = new SupportStreamTypeSvc3Stream();

        processor = new SelectExprJoinWildcardProcessor(supportTypes.getStreamNames(), supportTypes.getEventTypes(),
                SupportEventAdapterService.getService(), null, selectExprEventTypeRegistry);
    }

    public void testProcess()
    {
        EventBean[] testEvents = SupportStreamTypeSvc3Stream.getSampleEvents();

        EventBean result = processor.process(testEvents, true, false);
        assertEquals(testEvents[0].getUnderlying(), result.get("s0"));
        assertEquals(testEvents[1].getUnderlying(), result.get("s1"));

        // Test null events, such as in an outer join
        testEvents[1] = null;
        result = processor.process(testEvents, true, false);
        assertEquals(testEvents[0].getUnderlying(), result.get("s0"));
        assertNull(result.get("s1"));
    }

    public void testType()
    {
        assertEquals(SupportBean.class, processor.getResultEventType().getPropertyType("s0"));
        assertEquals(SupportBean.class, processor.getResultEventType().getPropertyType("s1"));
    }
}
