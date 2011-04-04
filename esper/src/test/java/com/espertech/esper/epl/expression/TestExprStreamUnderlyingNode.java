package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.support.epl.SupportStreamTypeSvc3Stream;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprStreamUnderlyingNode extends TestCase
{
    private ExprStreamUnderlyingNode node;
    private StreamTypeService streamTypeService;

    public void setUp()
    {
        node = new ExprStreamUnderlyingNode("s0", false);
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
        node.validate(ExprValidationContextFactory.make(streamTypeService));
        assertEquals(0, node.getStreamId());
        assertEquals(SupportBean.class, node.getType());

        tryInvalidValidate(new ExprStreamUnderlyingNode("", false));
        tryInvalidValidate(new ExprStreamUnderlyingNode("dummy", false));
    }

    public void testEvaluate() throws Exception
    {
        EventBean event = makeEvent(10);
        EventBean[] events = new EventBean[] {event};

        node.validate(ExprValidationContextFactory.make(streamTypeService));
        assertEquals(event.getUnderlying(), node.evaluate(events, false, null));
    }

    public void testEqualsNode() throws Exception
    {
        node.validate(ExprValidationContextFactory.make(streamTypeService));
        assertTrue(node.equalsNode(new ExprStreamUnderlyingNode("s0", false)));
        assertFalse(node.equalsNode(new ExprStreamUnderlyingNode("xxx", false)));
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
            node.validate(ExprValidationContextFactory.make(streamTypeService));
            fail();
        }
        catch(ExprValidationException ex)
        {
            // expected
        }
    }

    private static final Log log = LogFactory.getLog(TestExprStreamUnderlyingNode.class);
}
