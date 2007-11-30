package net.esper.eql.expression;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.variable.VariableService;
import net.esper.event.EventBean;
import net.esper.schedule.TimeProvider;

/**
 * Represents the CURRENT_TIMESTAMP() function or reserved keyword in an expression tree.
 */
public class ExprTimestampNode extends ExprNode
{
    private TimeProvider timeProvider;

    /**
     * Ctor.
     */
    public ExprTimestampNode()
    {
    }

    public void validate(StreamTypeService streamTypeService,
                         MethodResolutionService methodResolutionService,
                         ViewResourceDelegate viewResourceDelegate,
                         TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 0)
        {
            throw new ExprValidationException("current_timestamp function node must have exactly 1 child node");
        }
        this.timeProvider = timeProvider;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType() throws ExprValidationException
    {
        return Long.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return timeProvider.getTime();
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("current_timestamp()");
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprTimestampNode))
        {
            return false;
        }
        return true;
    }
}
