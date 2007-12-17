package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNode;

public class TestExprEqualsNode extends TestCase
{
    private ExprEqualsNode[] equalsNodes;

    public void setUp()
    {
        equalsNodes = new ExprEqualsNode[4];
        equalsNodes[0] = new ExprEqualsNode(false);

        equalsNodes[1] = new ExprEqualsNode(false);
        equalsNodes[1].addChildNode(new SupportExprNode(1L));
        equalsNodes[1].addChildNode(new SupportExprNode(new Integer(1)));

        equalsNodes[2] = new ExprEqualsNode(true);
        equalsNodes[2].addChildNode(new SupportExprNode(1.5D));
        equalsNodes[2].addChildNode(new SupportExprNode(new Integer(1)));

        equalsNodes[3] = new ExprEqualsNode(false);
        equalsNodes[3].addChildNode(new SupportExprNode(1D));
        equalsNodes[3].addChildNode(new SupportExprNode(new Integer(1)));
    }

    public void testGetType()
    {
        assertEquals(Boolean.class, equalsNodes[0].getType());
    }

    public void testValidate() throws Exception
    {
        // Test success
        equalsNodes[0].addChildNode(new SupportExprNode(String.class));
        equalsNodes[0].addChildNode(new SupportExprNode(String.class));
        equalsNodes[0].validate(null, null, null, null, null);

        equalsNodes[1].validate(null, null, null, null, null);
        equalsNodes[2].validate(null, null, null, null, null);
        equalsNodes[3].validate(null, null, null, null, null);

        equalsNodes[0].getChildNodes().clear();
        equalsNodes[0].addChildNode(new SupportExprNode(String.class));

        // Test too few nodes under this node
        try
        {
            equalsNodes[0].validate(null, null, null, null, null);
            fail();
        }
        catch (IllegalStateException ex)
        {
            // Expected
        }

        // Test mismatch type
        equalsNodes[0].addChildNode(new SupportExprNode(Boolean.class));
        try
        {
            equalsNodes[0].validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluateEquals() throws Exception
    {
        equalsNodes[0] = makeNode(true, false, false);
        assertFalse((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(false, false, false);
        assertTrue((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(true, true, false);
        assertTrue((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(true, Boolean.class, null, Boolean.class, false);
        assertFalse((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(null, String.class, "ss", String.class, false);
        assertFalse((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(null, String.class, null, String.class, false);
        assertTrue((Boolean)equalsNodes[0].evaluate(null, false));

        // try a long and int
        equalsNodes[1].validate(null, null, null, null, null);
        assertTrue((Boolean)equalsNodes[1].evaluate(null, false));

        // try a double and int
        equalsNodes[2].validate(null, null, null, null, null);
        assertTrue((Boolean)equalsNodes[2].evaluate(null, false));

        equalsNodes[3].validate(null, null, null, null, null);
        assertTrue((Boolean)equalsNodes[3].evaluate(null, false));
    }

    public void testEvaluateNotEquals()
    {
        equalsNodes[0] = makeNode(true, false, true);
        assertTrue((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(false, false, true);
        assertFalse((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(true, true, true);
        assertFalse((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(true, Boolean.class, null, Boolean.class, true);
        assertTrue((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(null, String.class, "ss", String.class, true);
        assertTrue((Boolean)equalsNodes[0].evaluate(null, false));

        equalsNodes[0] = makeNode(null, String.class, null, String.class, true);
        assertFalse((Boolean)equalsNodes[0].evaluate(null, false));
    }

    public void testToExpressionString() throws Exception
    {
        equalsNodes[0].addChildNode(new SupportExprNode(true));
        equalsNodes[0].addChildNode(new SupportExprNode(false));
        assertEquals("true = false", equalsNodes[0].toExpressionString());
    }

    private ExprEqualsNode makeNode(Object valueLeft, Object valueRight, boolean isNot)
    {
        ExprEqualsNode equalsNode = new ExprEqualsNode(isNot);
        equalsNode.addChildNode(new SupportExprNode(valueLeft));
        equalsNode.addChildNode(new SupportExprNode(valueRight));
        return equalsNode;
    }

    private ExprEqualsNode makeNode(Object valueLeft, Class typeLeft, Object valueRight, Class typeRight, boolean isNot)
    {
        ExprEqualsNode equalsNode = new ExprEqualsNode(isNot);
        equalsNode.addChildNode(new SupportExprNode(valueLeft, typeLeft));
        equalsNode.addChildNode(new SupportExprNode(valueRight, typeRight));
        return equalsNode;
    }

    public void testEqualsNode()
    {
        assertTrue(equalsNodes[0].equalsNode(equalsNodes[1]));
        assertFalse(equalsNodes[0].equalsNode(equalsNodes[2]));
    }
}
