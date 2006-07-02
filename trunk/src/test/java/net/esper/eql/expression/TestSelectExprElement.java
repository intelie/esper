package net.esper.eql.expression;

import junit.framework.TestCase;

import java.util.List;
import java.util.LinkedList;

import net.esper.support.eql.SupportExprNodeFactory;

public class TestSelectExprElement extends TestCase
{
    public void testConstructor() throws Exception
    {
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

        SelectExprElement element = new SelectExprElement(identNode, null);
        assertEquals("s0.doubleBoxed", element.getAsName());

        element = new SelectExprElement(identNode, "dudu");
        assertEquals("dudu", element.getAsName());
    }

    public void testVerifyNameUniqueness() throws Exception
    {
        // try valid case
        List<SelectExprElement> elements = new LinkedList<SelectExprElement>();
        elements.add(new SelectExprElement(null, "xx"));
        elements.add(new SelectExprElement(null, "yy"));

        SelectExprElement.verifyNameUniqueness(elements);

        // try invalid case
        elements.add(new SelectExprElement(null, "yy"));
        try
        {
            SelectExprElement.verifyNameUniqueness(elements);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
