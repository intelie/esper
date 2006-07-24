package net.esper.eql.expression;

import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.event.EventBean;
import junit.framework.TestCase;

public class TestSelectExprJoinWildcardProcessor extends TestCase
{
    private SelectExprJoinWildcardProcessor processor;

    public void setUp()
    {
        SupportStreamTypeSvc3Stream supportTypes = new SupportStreamTypeSvc3Stream();
        processor = new SelectExprJoinWildcardProcessor(supportTypes.getStreamNames(), supportTypes.getEventTypes(),
                SupportEventAdapterService.getService());
    }

    public void testProcess()
    {
        EventBean[] testEvents = SupportStreamTypeSvc3Stream.getSampleEvents();

        EventBean result = processor.process(testEvents);
        assertEquals(testEvents[0].getUnderlying(), result.get("s0"));
        assertEquals(testEvents[1].getUnderlying(), result.get("s1"));

        // Test null events, such as in an outer join
        testEvents[1] = null;
        result = processor.process(testEvents);
        assertEquals(testEvents[0].getUnderlying(), result.get("s0"));
        assertNull(result.get("s1"));
    }

    public void testType()
    {
        assertEquals(SupportBean.class, processor.getResultEventType().getPropertyType("s0"));
        assertEquals(SupportBean.class, processor.getResultEventType().getPropertyType("s1"));
    }
}
