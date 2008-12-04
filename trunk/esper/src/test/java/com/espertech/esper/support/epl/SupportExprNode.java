package com.espertech.esper.support.epl;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.schedule.TimeProvider;

public class SupportExprNode extends ExprNode
{
    private static int validateCount;

    private Class type;
    private Object value;
    private int validateCountSnapshot;

    public static void setValidateCount(int validateCount)
    {
        SupportExprNode.validateCount = validateCount;
    }

    public SupportExprNode(Class type)
    {
        this.type = type;
        this.value = null;
    }

    public SupportExprNode(Object value)
    {
        this.type = value.getClass();
        this.value = value;
    }

    public SupportExprNode(Object value, Class type)
    {
        this.value = value;
        this.type = type;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        // Keep a count for if and when this was validated
        validateCount++;
        validateCountSnapshot = validateCount;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType()
    {
        return type;
    }

    public int getValidateCountSnapshot()
    {
        return validateCountSnapshot;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public String toExpressionString()
    {
        if (value instanceof String)
        {
            return "\"" + value + "\"";
        }
        else
        {
            if (value == null)
            {
                return "null";
            }
        }
        return value.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        throw new UnsupportedOperationException("not implemented");
    }    
}
