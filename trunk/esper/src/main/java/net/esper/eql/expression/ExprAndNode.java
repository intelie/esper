package net.esper.eql.expression;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Represents And-condition.
 */
public class ExprAndNode extends ExprNode
{
    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        // Sub-nodes must be returning boolean
        for (ExprNode child : this.getChildNodes())
        {
            Class childType = child.getType();
            if (!JavaClassHelper.isBoolean(childType))
            {
                throw new ExprValidationException("Incorrect use of AND clause, sub-expressions do not return boolean");
            }
        }

        if (this.getChildNodes().size() <= 1)
        {
            throw new ExprValidationException("The AND operator requires at least 2 child expressions");
        }
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        for (ExprNode child : this.getChildNodes())
        {
            Boolean evaluated = (Boolean) child.evaluate(eventsPerStream, isNewData);
            if (!evaluated)
            {
                return false;
            }
        }
        return true;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(');

        String appendStr = "";
        for (ExprNode child : this.getChildNodes())
        {
            buffer.append(appendStr);
            buffer.append(child.toExpressionString());
            appendStr = " AND ";
        }

        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprAndNode))
        {
            return false;
        }

        return true;
    }
}
