package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.support.epl.SupportStreamTypeSvc3Stream;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprStreamInstanceMethodNode extends TestCase
{
    private ExprStreamInstanceMethodNode node;
    private StreamTypeService streamTypeService;

    public void setUp()
    {
        node = new ExprStreamInstanceMethodNode("s0", "getIntPrimitive");
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
        SupportExprNodeFactory.validate3Stream(node);
        assertEquals(0, node.getStreamId());
        assertEquals(int.class, node.getType());

        tryInvalidValidate(new ExprStreamInstanceMethodNode("s0", "dummy"));
        tryInvalidValidate(new ExprStreamInstanceMethodNode("dummy", "getString()"));
    }

    public void testEvaluate() throws Exception
    {
        EventBean event = makeEvent(10);
        EventBean[] events = new EventBean[] {event};

        SupportExprNodeFactory.validate3Stream(node);
        assertEquals(10, node.evaluate(events, false));
    }

    public void testEqualsNode() throws Exception
    {
        SupportExprNodeFactory.validate3Stream(node);
        ExprNode other = new ExprStreamInstanceMethodNode("s0", "getIntPrimitive");
        SupportExprNodeFactory.validate3Stream(other);
        
        assertTrue(node.equalsNode(other));
        assertFalse(node.equalsNode(new ExprStreamInstanceMethodNode("s1", "getIntPrimitive")));
        assertFalse(node.equalsNode(new ExprStreamInstanceMethodNode("s0", "xxx")));
        assertFalse(node.equalsNode(new ExprStreamUnderlyingNode("xxx")));
    }

    protected static EventBean makeEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        return SupportEventBeanFactory.createObject(event);
    }

    private void tryInvalidValidate(ExprStreamInstanceMethodNode node)
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

    private static final Log log = LogFactory.getLog(TestExprStreamInstanceMethodNode.class);
}
