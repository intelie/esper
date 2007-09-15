package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;

public class TestExprLikeNode extends TestCase
{
    private ExprLikeNode likeNodeNormal;
    private ExprLikeNode likeNodeNot;
    private ExprLikeNode likeNodeNormalEscaped;

    public void setUp() throws Exception
    {
        likeNodeNormal = SupportExprNodeFactory.makeLikeNode(false, null);
        likeNodeNot = SupportExprNodeFactory.makeLikeNode(true, null);
        likeNodeNormalEscaped = SupportExprNodeFactory.makeLikeNode(false, "!");
    }

    public void testGetType()  throws Exception
    {
        assertEquals(Boolean.class, likeNodeNormal.getType());
        assertEquals(Boolean.class, likeNodeNot.getType());
        assertEquals(Boolean.class, likeNodeNormalEscaped.getType());
    }

    public void testValidate() throws Exception
    {
        // No subnodes: Exception is thrown.
        tryInvalidValidate(new ExprLikeNode(true));

        // singe child node not possible, must be 2 at least
        likeNodeNormal = new ExprLikeNode(false);
        likeNodeNormal.addChildNode(new SupportExprNode(new Integer(4)));
        tryInvalidValidate(likeNodeNormal);

        // test a type mismatch
        likeNodeNormal = new ExprLikeNode(true);
        likeNodeNormal.addChildNode(new SupportExprNode("sx"));
        likeNodeNormal.addChildNode(new SupportExprNode(4));
        tryInvalidValidate(likeNodeNormal);

        // test numeric supported
        likeNodeNormal = new ExprLikeNode(false);
        likeNodeNormal.addChildNode(new SupportExprNode(new Integer(4)));
        likeNodeNormal.addChildNode(new SupportExprNode("sx"));

        // test invalid escape char
        likeNodeNormal = new ExprLikeNode(false);
        likeNodeNormal.addChildNode(new SupportExprNode(new Integer(4)));
        likeNodeNormal.addChildNode(new SupportExprNode("sx"));
        likeNodeNormal.addChildNode(new SupportExprNode(5));
    }

    public void testEvaluate() throws Exception
    {
        // Build :      s0.string like "%abc__"  (with or witout escape)
        assertFalse((Boolean) likeNodeNormal.evaluate(makeEvent("abcx"), false));
        assertTrue((Boolean) likeNodeNormal.evaluate(makeEvent("dskfsljkdfabcxx"), false));
        assertTrue((Boolean) likeNodeNot.evaluate(makeEvent("abcx"), false));
        assertFalse((Boolean) likeNodeNot.evaluate(makeEvent("dskfsljkdfabcxx"), false));
    }

    public void testEquals()  throws Exception
    {
        ExprLikeNode otherLikeNodeNot = SupportExprNodeFactory.makeLikeNode(true, "@");
        ExprLikeNode otherLikeNodeNot2 = SupportExprNodeFactory.makeLikeNode(true, "!");

        assertTrue(likeNodeNot.equalsNode(otherLikeNodeNot2));
        assertTrue(otherLikeNodeNot2.equalsNode(otherLikeNodeNot)); // Escape char itself is an expression
        assertFalse(likeNodeNormal.equalsNode(otherLikeNodeNot));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("s0.string like \"%abc__\"", likeNodeNormal.toExpressionString());
        assertEquals("s0.string not like \"%abc__\"", likeNodeNot.toExpressionString());
        assertEquals("s0.string like \"%abc__\" escape \"!\"", likeNodeNormalEscaped.toExpressionString());
    }

    private EventBean[] makeEvent(String stringValue)
    {
        SupportBean event = new SupportBean();
        event.setString(stringValue);
        return new EventBean[] {SupportEventBeanFactory.createObject(event)};
    }

    private void tryInvalidValidate(ExprLikeNode exprLikeRegexpNode) throws Exception
    {
        try {
            exprLikeRegexpNode.validate(null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
