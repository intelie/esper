/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.variable.VariableService;
import net.esper.schedule.TimeProvider;

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

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
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

        if (typeOne.equals(typeTwo))
        {
            mustCoerce = false;
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
                    typeTwo.getSimpleName() +
                    "' to '" +
                    typeOne.getSimpleName() +
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

    public boolean isConstantResult()
    {
        return false;
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
            return isNotEquals;
        }

        if (!mustCoerce)
        {
            return leftResult.equals(rightResult) ^ isNotEquals;
        }
        else
        {
            Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
            Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
            return left.equals(right) ^ isNotEquals;
        }
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();

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
