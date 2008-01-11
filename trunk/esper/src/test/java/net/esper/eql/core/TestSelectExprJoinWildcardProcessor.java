package net.esper.eql.core;

import junit.framework.TestCase;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventAdapterService;

public class TestSelectExprJoinWildcardProcessor extends TestCase
{
    private SelectExprJoinWildcardProcessor processor;

    public void setUp() throws ExprValidationException
    {
        SupportStreamTypeSvc3Stream supportTypes = new SupportStreamTypeSvc3Stream();
        processor = new SelectExprJoinWildcardProcessor(supportTypes.getStreamNames(), supportTypes.getEventTypes(),
                SupportEventAdapterService.getService(), null);
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
