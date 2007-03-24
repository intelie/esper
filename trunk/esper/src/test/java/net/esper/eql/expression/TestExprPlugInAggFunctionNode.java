package net.esper.eql.expression;

import net.esper.support.eql.SupportExprNode;
import net.esper.support.eql.SupportPluginAggregationMethodOne;
import net.esper.type.MinMaxTypeEnum;
import junit.framework.TestCase;

public class TestExprPlugInAggFunctionNode extends TestCase
{
    private ExprPlugInAggFunctionNode plugInNode;

    public void setUp()
    {
        plugInNode = new ExprPlugInAggFunctionNode(false, new SupportPluginAggregationMethodOne(), "matrix");
    }

    public void testGetType() throws Exception
    {
        plugInNode.validate(null, null, null);
        assertEquals(String.class, plugInNode.getType());
    }

    public void testValidate() throws Exception
    {
        // fails with too many sub-expressions
        plugInNode.addChildNode(new SupportExprNode(Boolean.class));
        plugInNode.addChildNode(new SupportExprNode(Boolean.class));
        try
        {
            plugInNode.validate(null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEqualsNode() throws Exception
    {
        ExprPlugInAggFunctionNode other = new ExprPlugInAggFunctionNode(false, new SupportPluginAggregationMethodOne(), "matrix");

        assertTrue(plugInNode.equalsNode(plugInNode));
        assertFalse(plugInNode.equalsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
        assertFalse(other.equalsNode(plugInNode));
    }       
}
