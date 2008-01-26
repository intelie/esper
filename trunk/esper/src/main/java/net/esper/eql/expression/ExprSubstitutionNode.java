package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.variable.VariableService;
import net.esper.event.EventBean;
import net.esper.client.EPException;
import net.esper.schedule.TimeProvider;

/**
 * Represents a substitution value to be substituted in an expression tree, not valid for any purpose of use
 * as an expression, however can take a place in an expression tree.
 */
public class ExprSubstitutionNode extends ExprNode
{
    private static final String ERROR_MSG = "Invalid use of substitution parameters marked by '?' in statement, use the prepare method to prepare statements with substitution parameters";
    private final int index;

    /**
     * Ctor.
     * @param index is the index of the substitution parameter
     */
    public ExprSubstitutionNode(int index)
    {
        this.index = index;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        throw new ExprValidationException(ERROR_MSG);
    }

    /**
     * Returns the substitution parameter index.
     * @return index
     */
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
        throw new ExprValidationException(ERROR_MSG);
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        throw new EPException(ERROR_MSG);
    }

    public String toExpressionString()
    {
        throw new EPException(ERROR_MSG);
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
