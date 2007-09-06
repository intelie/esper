package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.event.EventBean;
import net.esper.client.EPException;

/**
 * Represents a substitution value to be substituted in an expression tree, not valid for any purpose of use
 * as an expression, however can take a place in an expression tree.
 */
public class ExprSubstitutionNode extends ExprNode
{
    private final int index;

    /**
     * Ctor.
     */
    public ExprSubstitutionNode(int index)
    {
        this.index = index;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        throw new ExprValidationException("Substitution parameter not satisfied");
    }

    public int getIndex()
    {
        return index;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType() throws ExprValidationException
    {
        throw new ExprValidationException("Substitution parameter not satisfied");
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        throw new EPException("Substitution parameter not satisfied");
    }

    public String toExpressionString()
    {
        throw new EPException("Substitution parameter not satisfied");
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprSubstitutionNode))
        {
            return false;
        }

        return true;
    }
}
