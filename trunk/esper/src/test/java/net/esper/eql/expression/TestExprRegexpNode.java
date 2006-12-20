package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;

public class TestExprRegexpNode extends TestCase
{
    private ExprRegexpNode regexpNodeNormal;
    private ExprRegexpNode regexpNodeNot;

    public void setUp() throws Exception
    {
        regexpNodeNormal = SupportExprNodeFactory.makeRegexpNode(false);
        regexpNodeNot = SupportExprNodeFactory.makeRegexpNode(true);
    }

    public void testGetType()  throws Exception
    {
        assertEquals(Boolean.class, regexpNodeNormal.getType());
        assertEquals(Boolean.class, regexpNodeNot.getType());
    }

    public void testValidate() throws Exception
    {
        // No subnodes: Exception is thrown.
        tryInvalidValidate(new ExprRegexpNode(true));

        // singe child node not possible, must be 2 at least
        regexpNodeNormal = new ExprRegexpNode(false);
        regexpNodeNormal.addChildNode(new SupportExprNode(new Integer(4)));
        tryInvalidValidate(regexpNodeNormal);

        // test a type mismatch
        regexpNodeNormal = new ExprRegexpNode(true);
        regexpNodeNormal.addChildNode(new SupportExprNode("sx"));
        regexpNodeNormal.addChildNode(new SupportExprNode(4));
        tryInvalidValidate(regexpNodeNormal);

        // test numeric supported
        regexpNodeNormal = new ExprRegexpNode(false);
        regexpNodeNormal.addChildNode(new SupportExprNode(new Integer(4)));
        regexpNodeNormal.addChildNode(new SupportExprNode("sx"));
    }

    public void testEvaluate() throws Exception
    {
        assertFalse((Boolean) regexpNodeNormal.evaluate(makeEvent("bcd")));
        assertTrue((Boolean) regexpNodeNormal.evaluate(makeEvent("ab")));
        assertTrue((Boolean) regexpNodeNot.evaluate(makeEvent("bcd")));
        assertFalse((Boolean) regexpNodeNot.evaluate(makeEvent("ab")));
    }

    public void testEquals()  throws Exception
    {
        ExprRegexpNode otherRegexpNodeNot = SupportExprNodeFactory.makeRegexpNode(true);

        assertTrue(regexpNodeNot.equalsNode(otherRegexpNodeNot));
        assertFalse(regexpNodeNormal.equalsNode(otherRegexpNodeNot));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("s0.string regexp \"[a-z][a-z]\"", regexpNodeNormal.toExpressionString());
        assertEquals("s0.string not regexp \"[a-z][a-z]\"", regexpNodeNot.toExpressionString());
    }

    private EventBean[] makeEvent(String stringValue)
    {
        SupportBean event = new SupportBean();
        event.setString(stringValue);
        return new EventBean[] {SupportEventBeanFactory.createObject(event)};
    }

    private void tryInvalidValidate(ExprRegexpNode exprLikeRegexpNode) throws Exception
    {
        try {
            exprLikeRegexpNode.validate(null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
