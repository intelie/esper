package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;

public class TestExprInNode extends TestCase
{
    private ExprInNode inNode;

    public void setUp() throws Exception
    {
        inNode = SupportExprNodeFactory.makeInSetNode();
    }

    public void testGetType()  throws Exception
    {
        assertEquals(Boolean.class, inNode.getType());
    }

    public void testValidate() throws Exception
    {
        inNode = SupportExprNodeFactory.makeInSetNode();
        inNode.validate(null, null);

        // No subnodes: Exception is thrown.
        tryInvalidValidate(new ExprInNode());

        // singe child node not possible, must be 2 at least
        inNode = new ExprInNode();
        inNode.addChildNode(new SupportExprNode(new Integer(4)));
        tryInvalidValidate(inNode);

        // test a type mismatch
        inNode = new ExprInNode();
        inNode.addChildNode(new SupportExprNode("sx"));
        inNode.addChildNode(new SupportExprNode(4));
        tryInvalidValidate(inNode);
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

    public void testEvaluate() throws Exception
    {
        assertFalse((Boolean) inNode.evaluate(makeEvent(0)));
        assertTrue((Boolean) inNode.evaluate(makeEvent(1)));
        assertTrue((Boolean) inNode.evaluate(makeEvent(2)));
        assertFalse((Boolean) inNode.evaluate(makeEvent(3)));
    }

    private EventBean[] makeEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        return new EventBean[] {SupportEventBeanFactory.createObject(event)};
    }

    public void testEquals()  throws Exception
    {
        ExprInNode otherNode = SupportExprNodeFactory.makeInSetNode();

        assertTrue(inNode.equalsNode(otherNode));
        assertFalse(inNode.equalsNode(SupportExprNodeFactory.makeCaseSyntax1Node()));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("s0.intPrimitive in (1,2)", inNode.toExpressionString());
    }
}
