package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;

public class TestExprInSetNode extends TestCase
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
        inNodeNormal.validate(null, null);

        // No subnodes: Exception is thrown.
        tryInvalidValidate(new ExprInSetNode(true));

        // singe child node not possible, must be 2 at least
        inNodeNormal = new ExprInSetNode(true);
        inNodeNormal.addChildNode(new SupportExprNode(new Integer(4)));
        tryInvalidValidate(inNodeNormal);

        // test a type mismatch
        inNodeNormal = new ExprInSetNode(true);
        inNodeNormal.addChildNode(new SupportExprNode("sx"));
        inNodeNormal.addChildNode(new SupportExprNode(4));
        tryInvalidValidate(inNodeNormal);
    }

    public void testEvaluate() throws Exception
    {
        assertFalse((Boolean) inNodeNormal.evaluate(makeEvent(0)));
        assertTrue((Boolean) inNodeNormal.evaluate(makeEvent(1)));
        assertTrue((Boolean) inNodeNormal.evaluate(makeEvent(2)));
        assertFalse((Boolean) inNodeNormal.evaluate(makeEvent(3)));

        assertTrue((Boolean) inNodeNotIn.evaluate(makeEvent(0)));
        assertFalse((Boolean) inNodeNotIn.evaluate(makeEvent(1)));
        assertFalse((Boolean) inNodeNotIn.evaluate(makeEvent(2)));
        assertTrue((Boolean) inNodeNotIn.evaluate(makeEvent(3)));
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
            exprInNode.validate(null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
