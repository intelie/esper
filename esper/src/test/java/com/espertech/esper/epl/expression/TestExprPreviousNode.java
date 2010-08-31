package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.view.window.RandomAccessByIndexGetter;
import com.espertech.esper.view.window.IStreamRandomAccess;

public class TestExprPreviousNode extends TestCase {
    private ExprPreviousNode prevNode;

    public void setUp() throws Exception
    {
        prevNode = SupportExprNodeFactory.makePreviousNode();
    }

    public void testGetType()  throws Exception
    {
        assertEquals(double.class, prevNode.getType());
    }

    public void testValidate() throws Exception
    {
        prevNode = new ExprPreviousNode(PreviousType.PREV);

        // No subnodes: Exception is thrown.
        tryInvalidValidate(prevNode);

        // singe child node not possible, must be 2 at least
        prevNode.addChildNode(new SupportExprNode(new Integer(4)));
        tryInvalidValidate(prevNode);
    }

    public void testEvaluate() throws Exception
    {
        RandomAccessByIndexGetter getter = new RandomAccessByIndexGetter();
        IStreamRandomAccess buffer = new IStreamRandomAccess(getter);
        getter.updated(buffer);

        prevNode.setViewResource(getter);
        EventBean[] events = makeEvent(0, 5d);
        buffer.update(events, null);

        assertEquals(5d, prevNode.evaluate(events, true, null));
    }

    public void testEquals()  throws Exception
    {
        ExprPreviousNode node1 = new ExprPreviousNode(PreviousType.PREV);
        assertTrue(node1.equalsNode(prevNode));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("prev(s1.intPrimitive,s1.doublePrimitive)", prevNode.toExpressionString());
    }

    private EventBean[] makeEvent(int intPrimitive, double doublePrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        event.setDoublePrimitive(doublePrimitive);
        return new EventBean[] {null, SupportEventBeanFactory.createObject(event)};
    }

    private void tryInvalidValidate(ExprPreviousNode exprPrevNode) throws Exception
    {
        try {
            exprPrevNode.validate(null, null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
