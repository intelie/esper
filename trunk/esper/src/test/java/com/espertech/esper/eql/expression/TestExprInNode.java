package com.espertech.esper.eql.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.eql.SupportExprNodeFactory;
import com.espertech.esper.support.eql.SupportExprNode;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.event.EventBean;

public class TestExprInNode extends TestCase
{
    private ExprInNode inNodeNormal;
    private ExprInNode inNodeNotIn;

    public void setUp() throws Exception
    {
        inNodeNormal = SupportExprNodeFactory.makeInSetNode(false);
        inNodeNotIn = SupportExprNodeFactory.makeInSetNode(true);
    }

    public void testGetType()  throws Exception
    {
        assertEquals(Boolean.class, inNodeNormal.getType());
        assertEquals(Boolean.class, inNodeNotIn.getType());
    }

    public void testValidate() throws Exception
    {
        inNodeNormal = SupportExprNodeFactory.makeInSetNode(true);
        inNodeNormal.validate(null, null, null, null, null);

        // No subnodes: Exception is thrown.
        tryInvalidValidate(new ExprInNode(true));

        // singe child node not possible, must be 2 at least
        inNodeNormal = new ExprInNode(true);
        inNodeNormal.addChildNode(new SupportExprNode(new Integer(4)));
        tryInvalidValidate(inNodeNormal);

        // test a type mismatch
        inNodeNormal = new ExprInNode(true);
        inNodeNormal.addChildNode(new SupportExprNode("sx"));
        inNodeNormal.addChildNode(new SupportExprNode(4));
        tryInvalidValidate(inNodeNormal);
    }

    public void testEvaluate() throws Exception
    {
        assertFalse((Boolean) inNodeNormal.evaluate(makeEvent(0), false));
        assertTrue((Boolean) inNodeNormal.evaluate(makeEvent(1), false));
        assertTrue((Boolean) inNodeNormal.evaluate(makeEvent(2), false));
        assertFalse((Boolean) inNodeNormal.evaluate(makeEvent(3), false));

        assertTrue((Boolean) inNodeNotIn.evaluate(makeEvent(0), false));
        assertFalse((Boolean) inNodeNotIn.evaluate(makeEvent(1), false));
        assertFalse((Boolean) inNodeNotIn.evaluate(makeEvent(2), false));
        assertTrue((Boolean) inNodeNotIn.evaluate(makeEvent(3), false));
    }

    public void testEquals()  throws Exception
    {
        ExprInNode otherInNodeNormal = SupportExprNodeFactory.makeInSetNode(false);
        ExprInNode otherInNodeNotIn = SupportExprNodeFactory.makeInSetNode(true);

        assertTrue(inNodeNormal.equalsNode(otherInNodeNormal));
        assertTrue(inNodeNotIn.equalsNode(otherInNodeNotIn));

        assertFalse(inNodeNormal.equalsNode(otherInNodeNotIn));
        assertFalse(inNodeNotIn.equalsNode(otherInNodeNormal));
        assertFalse(inNodeNotIn.equalsNode(SupportExprNodeFactory.makeCaseSyntax1Node()));
        assertFalse(inNodeNormal.equalsNode(SupportExprNodeFactory.makeCaseSyntax1Node()));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("s0.intPrimitive in (1,2)", inNodeNormal.toExpressionString());
        assertEquals("s0.intPrimitive not in (1,2)", inNodeNotIn.toExpressionString());
    }

    private EventBean[] makeEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        return new EventBean[] {SupportEventBeanFactory.createObject(event)};
    }

    private void tryInvalidValidate(ExprInNode exprInNode) throws Exception
    {
        try {
            exprInNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
