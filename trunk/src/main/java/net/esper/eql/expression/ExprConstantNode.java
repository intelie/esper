package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;

/**
 * Represents a constant in a filter expressiun tree.
 */
public class ExprConstantNode extends ExprNode
{
    private Object value;

    /**
     * Ctor.
     * @param value is the constant's value.
     */
    public ExprConstantNode(Object value)
    {
        this.value = value;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
    }

    public Class getType() throws ExprValidationException
    {
        if (value == null)
        {
            return null;
        }
        return value.getClass();
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        return value;
    }

    public String toExpressionString()
    {
        if (value instanceof String)
        {
            return "\"" + value + "\"";
        }
        return value.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprConstantNode))
        {
            return false;
        }

        ExprConstantNode other = (ExprConstantNode) node;

        if ((other.value == null) && (this.value != null))
        {
            return false;
        }
        if ((other.value != null) && (this.value == null))
        {
            return false;
        }
        if ((other.value == null) && (this.value == null))
        {
            return true;
        }
        return other.value.equals(this.value);
    }
}
