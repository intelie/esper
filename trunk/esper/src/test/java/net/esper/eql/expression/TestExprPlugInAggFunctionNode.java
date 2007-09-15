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
        plugInNode.validate(null, null, null, null);
        assertEquals(int.class, plugInNode.getType());
    }

    public void testValidate() throws Exception
    {
        // fails with too many sub-expressions
        plugInNode.addChildNode(new SupportExprNode(Boolean.class));
        plugInNode.addChildNode(new SupportExprNode(Boolean.class));
        try
        {
            plugInNode.validate(null, null, null, null);
            fail();
        }
        catch (ExprValidationException ex)
        {
            // Expected
        }
    }

    public void testEqualsNode() throws Exception
    {
        ExprPlugInAggFunctionNode otherOne = new ExprPlugInAggFunctionNode(false, new SupportPluginAggregationMethodOne(), "matrix");
        ExprPlugInAggFunctionNode otherTwo = new ExprPlugInAggFunctionNode(false, new SupportPluginAggregationMethodOne(), "matrix2");

        assertTrue(plugInNode.equalsNode(plugInNode));
        assertFalse(plugInNode.equalsNode(new ExprMinMaxRowNode(MinMaxTypeEnum.MIN)));
        assertTrue(otherOne.equalsNode(plugInNode));
        assertFalse(otherTwo.equalsNode(plugInNode));
    }
}
