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
     * @param aggregationSupport - is the base class for plug-in aggregation functions
     * @param functionName is the aggregation function name 
     */
    public ExprPlugInAggFunctionNode(boolean distinct, AggregationSupport aggregationSupport, String functionName)
    {
        super(distinct);
        this.aggregationSupport = aggregationSupport;
        aggregationSupport.setFunctionName(functionName);
    }

    public AggregationMethod validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService) throws ExprValidationException
    {
        Class childType = null;
        if (this.getChildNodes().size() > 1)
        {
            throw new ExprValidationException("Plug-in aggregation function '" + aggregationSupport.getFunctionName() + "' requires a single parameter");
        }
        if (this.getChildNodes().size() == 1)
        {
            childType = this.getChildNodes().get(0).getType();
        }

        try
        {
            aggregationSupport.validate(childType);
        }
        catch (RuntimeException ex)
        {
            throw new ExprValidationException("Plug-in aggregation function '" + aggregationSupport.getFunctionName() + "' failed validation: " + ex.getMessage());
        }

        return aggregationSupport;
    }

    public String getAggregationFunctionName()
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