package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.event.EventBean;

/**
 * Represents the EXISTS(a?) function is an expression tree.
 */
public class ExprCastNode extends ExprNode
{
    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if (this.getChildNodes().size() < 2)
        {
            throw new ExprValidationException("Instanceof node must have at least 2 child nodes");
        }
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType() throws ExprValidationException
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return null;
    }

    public String toExpressionString()
    {
        // TODO
        StringBuilder buffer = new StringBuilder();
        for (int i = 2; i < this.getChildNodes().size(); i++)
        {
            buffer.append(',');
            buffer.append(this.getChildNodes().get(i).toExpressionString());
        }
        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        // TODO
        if (!(node instanceof ExprCastNode))
        {
            return false;
        }

        return true;
    }
}
