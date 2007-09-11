package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.eql.SupportExprNodeFactory;
import net.esper.event.EventBean;

public class TestExprPropertyExistsNode extends TestCase
{
    private ExprPropertyExistsNode[] existsNodes;

    public void setUp() throws Exception
    {
        existsNodes = new ExprPropertyExistsNode[2];

        existsNodes[0] = new ExprPropertyExistsNode();
        existsNodes[0].addChildNode(SupportExprNodeFactory.makeIdentNode("dummy?", "s0"));

        existsNodes[1] = new ExprPropertyExistsNode();
        existsNodes[1].addChildNode(SupportExprNodeFactory.makeIdentNode("boolPrimitive?", "s0"));
    }

    public void testGetType() throws Exception
    {
        for (int i = 0; i < existsNodes.length; i++)
        {
            existsNodes[i].validate(null, null, null, null);
            assertEquals(Boolean.class, existsNodes[i].getType());
        }
    }

    public void testValidate() throws Exception
    {
        ExprPropertyExistsNode castNode = new ExprPropertyExistsNode();

        // Test too few nodes under this node
        try
        {
            castNode.validate(null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }

        castNode.addChildNode(new SupportExprNode(1));
        try
        {
            castNode.validate(null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        for (int i = 0; i < existsNodes.length; i++)
        {
            existsNodes[i].validate(null, null, null, null);
        }

        assertEquals(null, existsNodes[0].evaluate(new EventBean[3], false));
        assertEquals(null, existsNodes[1].evaluate(new EventBean[3], false));

        EventBean[] events = new EventBean[] {TestExprIdentNode.makeEvent(10)};
        assertEquals(false, existsNodes[0].evaluate(events, false));
        assertEquals(true, existsNodes[1].evaluate(events, false));
    }

    public void testEquals() throws Exception
    {
        assertFalse(existsNodes[0].equalsNode(new ExprEqualsNode(true)));
        assertTrue(existsNodes[0].equalsNode(existsNodes[1]));
    }

    public void testToExpressionString() throws Exception
    {
        existsNodes[0].validate(null, null, null, null);
        assertEquals("exists(s0.dummy?)", existsNodes[0].toExpressionString());
    }
}
