package com.espertech.esper.epl.expression;

import com.espertech.esper.core.ExpressionResultCacheService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.support.epl.SupportExprNode;
import junit.framework.TestCase;

public class TestExprTimestampNode extends TestCase
{
    private ExprTimestampNode node;
    private ExprEvaluatorContext context;

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
            node.validate(ExprValidationContextFactory.makeEmpty());
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEvaluate() throws Exception
    {
        final TimeProvider provider = new TimeProvider()
        {
            public long getTime()
            {
                return 99;
            }
        };
        context = new ExprEvaluatorContext() {
            public TimeProvider getTimeProvider() {
                return provider;
            }

            public ExpressionResultCacheService getExpressionResultCacheService() {
                return null;
            }
        };
        node.validate(new ExprValidationContext(null, null, null, provider, null, null, null, null, null));
        assertEquals(99L, node.evaluate(null, false, context));
    }

    public void testEquals() throws Exception
    {
        assertFalse(node.equalsNode(new ExprEqualsNodeImpl(true)));
        assertTrue(node.equalsNode(new ExprTimestampNode()));
    }

    public void testToExpressionString() throws Exception
    {
        assertEquals("current_timestamp()", node.toExpressionString());
    }
}
