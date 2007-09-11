package net.esper.support.eql;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventBean;
import net.esper.schedule.TimeProvider;

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

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider) throws ExprValidationException
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
