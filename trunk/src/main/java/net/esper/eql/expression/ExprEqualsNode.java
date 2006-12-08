package net.esper.eql.expression;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewFactoryDelegate;

/**
 * Represents an equals (=) comparator in a filter expressiun tree.
 */
public class ExprEqualsNode extends ExprNode
{
    private final boolean isNotEquals;

    private boolean mustCoerce;
    private Class coercionType;

    /**
     * Ctor.
     * @param isNotEquals - true if this is a (!=) not equals rather then equals, false if its a '=' equals
     */
    public ExprEqualsNode(boolean isNotEquals)
    {
        this.isNotEquals = isNotEquals;
    }

    /**
     * Returns true if this is a NOT EQUALS node, false if this is a EQUALS node.
     * @return true for !=, false for =
     */
    public boolean isNotEquals()
    {
        return isNotEquals;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewFactoryDelegate viewFactoryDelegate) throws ExprValidationException
    {
        // Must have 2 child nodes
        if (this.getChildNodes().size() != 2)
        {
            throw new IllegalStateException("Equals node does not have exactly 2 child nodes");
        }

        // Must be the same boxed type returned by expressions under this
        Class typeOne = JavaClassHelper.getBoxedType(this.getChildNodes().get(0).getType());
        Class typeTwo = JavaClassHelper.getBoxedType(this.getChildNodes().get(1).getType());

        // Null constants can be compared for any type
        if ((typeOne == null) || (typeTwo == null))
        {
            return;
        }

        // Get the common type such as Bool, String or Double and Long
        try
        {
            coercionType = JavaClassHelper.getCompareToCoercionType(typeOne, typeTwo);
        }
        catch (IllegalArgumentException ex)
        {
            throw new ExprValidationException("Implicit conversion from datatype '" +
                    typeOne.getName() +
                    "' to '" +
                    typeTwo.getName() +
                    "' is not allowed");
        }

        // Check if we need to coerce
        if ((coercionType == JavaClassHelper.getBoxedType(typeOne)) &&
            (coercionType == JavaClassHelper.getBoxedType(typeTwo)))
        {
            mustCoerce = false;
        }
        else
        {
            if (!JavaClassHelper.isNumeric(coercionType))
            {
                throw new IllegalStateException("Coercion type " + coercionType + " not numeric");
            }
            mustCoerce = true;
        }
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object leftResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        Object rightResult = this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);

        if (leftResult == null)
        {
            return (rightResult == null) ^ isNotEquals;
        }
        if (rightResult == null)
        {
            return (leftResult == null) ^ isNotEquals;
        }

        if (!mustCoerce)
        {
            return leftResult.equals(rightResult) ^ isNotEquals;
        }
        else
        {
            Number left = JavaClassHelper.coerceNumber((Number) leftResult, coercionType);
            Number right = JavaClassHelper.coerceNumber((Number) rightResult, coercionType);
            return left.equals(right) ^ isNotEquals;
        }
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(" = ");
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprEqualsNode))
        {
            return false;
        }

        ExprEqualsNode other = (ExprEqualsNode) node;

        if (other.isNotEquals != this.isNotEquals)
        {
            return false;
        }

        return true;
    }
}