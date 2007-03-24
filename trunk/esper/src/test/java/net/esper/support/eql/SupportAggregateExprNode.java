package net.esper.support.eql;

import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.expression.ExprAggregateNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventBean;

public class SupportAggregateExprNode extends ExprAggregateNode
{
    private static int validateCount;

    private Class type;
    private Object value;
    private int validateCountSnapshot;

    public static void setValidateCount(int validateCount)
    {
        SupportAggregateExprNode.validateCount = validateCount;
    }

    public SupportAggregateExprNode(Class type)
    {
        super(false);
        this.type = type;
        this.value = null;
    }

    public SupportAggregateExprNode(Object value)
    {
        super(false);
        this.type = value.getClass();
        this.value = value;
    }

    public SupportAggregateExprNode(Object value, Class type)
    {
        super(false);
        this.value = value;
        this.type = type;
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService) throws ExprValidationException
    {
        // Keep a count for if and when this was validated
        validateCount++;
        validateCountSnapshot = validateCount;
        return null;
    }

    public Class getType()
    {
        return type;
    }

    public int getValidateCountSnapshot()
    {
        return validateCountSnapshot;
    }

    public AggregationMethod getAggregationFunction()
    {
        return null;
    }

    protected String getAggregationFunctionName()
    {
        return "support";
    }

    public boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        throw new UnsupportedOperationException("not implemented");
    }

    public void evaluateEnter(EventBean[] eventsPerStream)
    {
    }

    public void evaluateLeave(EventBean[] eventsPerStream)
    {
    }

    public void setValue(Object value)
    {
        this.value = value;
    }
}
