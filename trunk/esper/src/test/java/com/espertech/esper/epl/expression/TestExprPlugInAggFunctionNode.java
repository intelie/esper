package com.espertech.esper.epl.expression;

import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportPluginAggregationMethodOne;
import com.espertech.esper.type.MinMaxTypeEnum;
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
        plugInNode.validate(null, null, null, null, null);
        assertEquals(int.class, plugInNode.getType());
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
