/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.expression;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.eql.core.StreamTypeService;
import com.espertech.esper.eql.core.ViewResourceDelegate;
import com.espertech.esper.eql.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;

/**
 * Represents a constant in an expressiun tree.
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

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
    }

    public boolean isConstantResult()
    {
        return true;
    }

    /**
     * Returns the constant's value.
     * @return value of constant
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * Sets the value of the constant.
     * @param value to set
     */
    public void setValue(Object value)
    {
        this.value = value;
    }

    public Class getType() throws ExprValidationException
    {
        if (value == null)
        {
            return null;
        }
        return value.getClass();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return value;
    }

    public String toExpressionString()
    {
        if (value instanceof String)
        {
            return "\"" + value + '\"';
        }
        if (value == null)
        {
            return "null";
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
