/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.eql.core.MethodResolutionService;
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

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
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
                        childType.getSimpleName() +
                        "' to string is not allowed");
            }
        }
    }

    public Class getType() throws ExprValidationException
    {
        return String.class;
    }

    public boolean isConstantResult()
    {
        return false;
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
        if (!(node instanceof ExprConcatNode))
        {
            return false;
        }

        return true;
    }
}
