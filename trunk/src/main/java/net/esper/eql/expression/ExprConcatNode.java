package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Represents a simple Math (+/-/divide/*) in a filter expression tree.
 */
public class ExprConcatNode extends ExprNode
{
    private StringBuffer buffer;

    /**
     * Ctor.
     */
    public ExprConcatNode()
    {
        buffer = new StringBuffer();
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if (this.getChildNodes().size() < 2)
        {
            throw new ExprValidationException("Concat node must have at least 2 child nodes");
        }

        for (int i = 0; i < this.getChildNodes().size(); i++)
        {
            Class childType = this.getChildNodes().get(i).getType();
            if (childType != String.class)
            {
                throw new ExprValidationException("Implicit conversion from datatype '" +
                        childType.getName() +
                        "' to string is not allowed");
            }
        }
    }

    public Class getType() throws ExprValidationException
    {
        return String.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        buffer.delete(0, buffer.length());
        for (ExprNode child : this.getChildNodes())
        {
            String result = (String) child.evaluate(eventsPerStream, isNewData);
            if (result == null)
            {
                return null;
            }
            buffer.append(result);
        }
        return buffer.toString();
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        String delimiter = "(";
        for (ExprNode child : this.getChildNodes())
        {
            buffer.append(delimiter);
            buffer.append(child.toExpressionString());
            delimiter = "||";
        }
        buffer.append(")");
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprConcatNode))
        {
            return false;
        }

        return true;
    }
}
