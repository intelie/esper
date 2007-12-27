package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.eql.core.StreamTypeService;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprStreamUnderlyingNode extends TestCase
{
    private ExprStreamUnderlyingNode node;
    private StreamTypeService streamTypeService;

    public void setUp()
    {
        node = new ExprStreamUnderlyingNode("s0");
        streamTypeService = new SupportStreamTypeSvc3Stream();
    }

    public void testValidateInvalid() throws Exception
    {
        try
        {
            node.getStreamId();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        try
        {
            node.getType();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }

    public void testValidate() throws Exception
    {
        node.validate(streamTypeService, null, null, null, null);
        assertEquals(0, node.getStreamId());
        assertEquals(SupportBean.class, node.getType());

        tryInvalidValidate(new ExprStreamUnderlyingNode(""));
        tryInvalidValidate(new ExprStreamUnderlyingNode("dummy"));
    }

    public void testEvaluate() throws Exception
    {
        EventBean event = makeEvent(10);
        EventBean[] events = new EventBean[] {event};

        node.validate(streamTypeService, null, null, null, null);
        assertEquals(event.getUnderlying(), node.evaluate(events, false));
    }

    public void testEqualsNode() throws Exception
    {
        node.validate(streamTypeService, null, null, null, null);
        assertTrue(node.equalsNode(new ExprStreamUnderlyingNode("s0")));
        assertFalse(node.equalsNode(new ExprStreamUnderlyingNode("xxx")));
    }

    protected static EventBean makeEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        return SupportEventBeanFactory.createObject(event);
    }

    private void tryInvalidValidate(ExprStreamUnderlyingNode node)
    {
        try
        {
            node.validate(streamTypeService, null, null, null, null);
            fail();
        }
        catch(ExprValidationException ex)
        {
            // expected
        }
    }

    private static final Log log = LogFactory.getLog(TestExprStreamUnderlyingNode.class);
}
