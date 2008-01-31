package com.espertech.esper.eql.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.eql.SupportExprNode;
import com.espertech.esper.schedule.TimeProvider;

public class TestExprTimestampNode extends TestCase
{
    private ExprTimestampNode node;

    public void setUp()
    {
        node = new ExprTimestampNode();
    }

    public void testGetType() throws Exception
    {
        assertEquals(Long.class, node.getType());
    }

    public void testValidate() throws Exception
    {
        // Test too many nodes
        node.addChildNode(new SupportExprNode(1));
        try
        {
            node.validate(null, null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        TimeProvider provider = new TimeProvider()
        {
            public long getTime()
            {
                return 99;
            }
        };
        node.validate(null, null, null, provider, null);
        assertEquals(99L, node.evaluate(null, false));
    }

    public void testEquals() throws Exception
    {
        assertFalse(node.equalsNode(new ExprEqualsNode(true)));
        assertTrue(node.equalsNode(new ExprTimestampNode()));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("current_timestamp()", node.toExpressionString());
    }
}
