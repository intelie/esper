package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.event.EventBean;

/**
 * Represents a subselect in an expression tree.
 */
public class ExprSubselectNode extends ExprNode
{
    private StatementSpecRaw statementSpec;

    /**
     * Ctor.
     */
    public ExprSubselectNode(StatementSpecRaw statementSpec)
    {
        this.statementSpec = statementSpec;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
    }

    public StatementSpecRaw getStatementSpec()
    {
        return statementSpec;
    }

    public Class getType() throws ExprValidationException
    {
        return null;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return null;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        String delimiter = "(";
        for (ExprNode child : this.getChildNodes())
        {
            buffer.append(delimiter);
            buffer.append(child.toExpressionString());
            delimiter = "||";
        }
        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprSubselectNode))
        {
            return false;
        }

        return true;
    }
}
