package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNodeFactory;

public class TestExprVariableNode extends TestCase
{
    private ExprVariableNode varNode;

    public void setUp() throws Exception
    {
        varNode = new ExprVariableNode("var1");
    }

    public void testGetType()  throws Exception
    {
        SupportExprNodeFactory.validate(varNode);
        assertEquals(String.class, varNode.getType());
    }

    public void testEvaluate() throws Exception
    {
        SupportExprNodeFactory.validate(varNode);
        assertEquals("my_variable_value", varNode.evaluate(null, true));
    }

    public void testValidate() throws Exception
    {
        // variable doesn't exists
        tryInvalidValidate(new ExprVariableNode("dummy"));

        // variable and property are ambigours
        tryInvalidValidate(new ExprVariableNode("intPrimitive"));
    }

    public void testEquals()  throws Exception
    {
        ExprInNode otherInNode = SupportExprNodeFactory.makeInSetNode(false);
        ExprVariableNode otherVarOne = new ExprVariableNode("dummy");
        ExprVariableNode otherVarTwo = new ExprVariableNode("var1");

        assertTrue(varNode.equalsNode(varNode));
        assertTrue(varNode.equalsNode(otherVarTwo));
        assertFalse(varNode.equalsNode(otherVarOne));
        assertFalse(varNode.equalsNode(otherInNode));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("var1", varNode.toExpressionString());
    }

    private void tryInvalidValidate(ExprVariableNode varNode) throws Exception
    {
        try {
            SupportExprNodeFactory.validate(varNode);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // expected
        }
    }
}
