package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.util.JavaClassHelper;
import net.esper.util.CoercionException;
import net.esper.event.EventBean;

/**
 * Represents the COALESCE(a,b,...) function is an expression tree.
 */
public class ExprCoalesceNode extends ExprNode
{
    private Class resultType;
    private boolean[] isNumericCoercion;

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        if (this.getChildNodes().size() < 2)
        {
            throw new ExprValidationException("Coalesce node must have at least 2 child nodes");
        }

        // get child expression types
        Class[] childTypes = new Class[getChildNodes().size()];
        int count = 0;
        for (ExprNode child : this.getChildNodes())
        {
            childTypes[count] = child.getType();
            count++;
        }

        // determine coercion type
        try {
            resultType = JavaClassHelper.getCommonCoercionType(childTypes);
        }
        catch (CoercionException ex)
        {
            throw new ExprValidationException("Implicit conversion not allowed: " + ex.getMessage());
        }

        // determine which child nodes need numeric coercion
        isNumericCoercion = new boolean[getChildNodes().size()];
        count = 0;
        for (ExprNode child : this.getChildNodes())
        {
            if ((JavaClassHelper.getBoxedType(child.getType()) != resultType) &&
                (child.getType() != null) && (resultType != null))
            {
                if (!JavaClassHelper.isNumeric(resultType))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" +
                            resultType +
                            "' to " + child.getType() + " is not allowed");
                }
                isNumericCoercion[count] = true;
            }
            count++;
        }
    }

    public Class getType() throws ExprValidationException
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        Object value = null;

        // Look for the first non-null return value
        int count = 0;
        for (ExprNode childNode : this.getChildNodes())
        {
            value = childNode.evaluate(eventsPerStream);

            if (value != null)
            {
                // Check if we need to coerce
                if (isNumericCoercion[count])
                {
                    return JavaClassHelper.coerceNumber((Number)value, resultType);
                }
                return value;
            }
            count++;
        }

        return null;
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 2; i < this.getChildNodes().size(); i++)
        {
            buffer.append(",");
            buffer.append(this.getChildNodes().get(i).toExpressionString());
        }
        buffer.append(")");
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprCoalesceNode))
        {
            return false;
        }

        return true;
    }
}
