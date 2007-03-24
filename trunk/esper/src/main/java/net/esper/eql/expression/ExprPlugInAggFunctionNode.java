package net.esper.eql.expression;

import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.agg.AggregationSupport;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;

/**
 * Represents a custom aggregation function in an expresson tree.
 */
public class ExprPlugInAggFunctionNode extends ExprAggregateNode
{
    private AggregationSupport aggregationSupport;

    /**
     * Ctor.
     * @param distinct - flag indicating unique or non-unique value aggregation
     */
    public ExprPlugInAggFunctionNode(boolean distinct, AggregationSupport aggregationSupport)
    {
        super(distinct);
        this.aggregationSupport = aggregationSupport;
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService) throws ExprValidationException
    {
        Class childType = null;
        if (this.getChildNodes().size() > 1)
        {
            throw new ExprValidationException("Plug-in aggregation function '" + aggregationSupport.getFunctionName() + " ' requires a single parameter");
        }
        if (this.getChildNodes().size() == 1)
        {
            childType = this.getChildNodes().get(0).getClass();
        }

        try
        {
            aggregationSupport.validate(childType);
        }
        catch (RuntimeException ex)
        {
            throw new ExprValidationException("Plug-in aggregation function '" + aggregationSupport.getFunctionName() + " ' failed validation:" + ex.getMessage());
        }

        return aggregationSupport;
    }

    protected String getAggregationFunctionName()
    {
        return aggregationSupport.getFunctionName();
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprPlugInAggFunctionNode))
        {
            return false;
        }

        ExprPlugInAggFunctionNode other = (ExprPlugInAggFunctionNode) node;
        return other.getAggregationFunctionName().equals(this.getAggregationFunctionName());
    }
}
