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

/**
 * Represents a NOT expression in an expression tree.
 */
public class ExprNotNode extends ExprNode
{
    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        // Must have a single child node
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("The NOT node requires exactly 1 child node");
        }

        Class childType = this.getChildNodes().get(0).getType();
        if (!JavaClassHelper.isBoolean(childType))
        {
            throw new ExprValidationException("Incorrect use of NOT clause, sub-expressions do not return boolean");
        }
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Boolean evaluated = (Boolean) this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);

        return !evaluated;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("NOT(");
        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprNotNode))
        {
            return false;
        }

        return true;
    }
}
