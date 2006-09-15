package net.esper.eql.spec;

import junit.framework.TestCase;

import java.util.List;
import java.util.LinkedList;

import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.eql.spec.SelectExprElementSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;

public class TestSelectExprElement extends TestCase
{
    public void testConstructor() throws Exception
    {
        ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");

        SelectExprElementSpec element = new SelectExprElementSpec(identNode, null);
        assertEquals("s0.doubleBoxed", element.getAsName());

        element = new SelectExprElementSpec(identNode, "dudu");
        assertEquals("dudu", element.getAsName());
    }

    public void testVerifyNameUniqueness() throws Exception
    {
        // try valid case
        List<SelectExprElementSpec> elements = new LinkedList<SelectExprElementSpec>();
        elements.add(new SelectExprElementSpec(null, "xx"));
        elements.add(new SelectExprElementSpec(null, "yy"));

        SelectExprElementSpec.verifyNameUniqueness(elements);

        // try invalid case
        elements.add(new SelectExprElementSpec(null, "yy"));
        try
        {
            SelectExprElementSpec.verifyNameUniqueness(elements);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
