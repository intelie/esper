package com.espertech.esper.eql.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.eql.SupportExprNode;
import com.espertech.esper.support.bean.SupportBean;

public class TestExprInstanceOfNode extends TestCase
{
    private ExprInstanceofNode[] instanceofNodes;

    public void setUp()
    {
        instanceofNodes = new ExprInstanceofNode[5];

        instanceofNodes[0] = new ExprInstanceofNode(new String[] {"long"});
        instanceofNodes[0].addChildNode(new SupportExprNode(1l, Long.class));

        instanceofNodes[1] = new ExprInstanceofNode(new String[] {SupportBean.class.getName(), "int", "string"});
        instanceofNodes[1].addChildNode(new SupportExprNode("", String.class));

        instanceofNodes[2] = new ExprInstanceofNode(new String[] {"string"});
        instanceofNodes[2].addChildNode(new SupportExprNode(null, Boolean.class));

        instanceofNodes[3] = new ExprInstanceofNode(new String[] {"string", "char"});
        instanceofNodes[3].addChildNode(new SupportExprNode(new SupportBean(), Object.class));

        instanceofNodes[4] = new ExprInstanceofNode(new String[] {"int", "float", SupportBean.class.getName()});
        instanceofNodes[4].addChildNode(new SupportExprNode(new SupportBean(), Object.class));
    }

    public void testGetType() throws Exception
    {
        for (int i = 0; i < instanceofNodes.length; i++)
        {
            instanceofNodes[i].validate(null, null, null, null, null);
            assertEquals(Boolean.class, instanceofNodes[i].getType());
        }
    }

    public void testValidate() throws Exception
    {
        ExprInstanceofNode instanceofNode = new ExprInstanceofNode(new String[0]);
        instanceofNode.addChildNode(new SupportExprNode(1));

        // Test too few nodes under this node
        try
        {
            instanceofNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        // Test node result type not fitting
        instanceofNode.addChildNode(new SupportExprNode("s"));
        try
        {
            instanceofNode.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        for (int i = 0; i < instanceofNodes.length; i++)
        {
            instanceofNodes[i].validate(null, null, null, null, null);
        }

        assertEquals(true, instanceofNodes[0].evaluate(null, false));
        assertEquals(true, instanceofNodes[1].evaluate(null, false));
        assertEquals(false, instanceofNodes[2].evaluate(null, false));
        assertEquals(false, instanceofNodes[3].evaluate(null, false));
        assertEquals(true, instanceofNodes[4].evaluate(null, false));
    }

    public void testEquals() throws Exception
    {
        assertFalse(instanceofNodes[0].equalsNode(new ExprEqualsNode(true)));
        assertFalse(instanceofNodes[0].equalsNode(instanceofNodes[1]));
        assertTrue(instanceofNodes[0].equalsNode(instanceofNodes[0]));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("instanceof(\"\", " + SupportBean.class.getName() + ", int, string)", instanceofNodes[1].toExpressionString());
    }
}
