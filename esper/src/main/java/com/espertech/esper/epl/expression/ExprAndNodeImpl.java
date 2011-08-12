/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.JavaClassHelper;

import java.util.Map;

/**
 * Represents an And-condition.
 */
public class ExprAndNodeImpl extends ExprNodeBase implements ExprEvaluator, ExprAndNode
{
    private static final long serialVersionUID = 8105121208330622813L;

    private transient ExprEvaluator[] evaluators;

    public ExprAndNodeImpl()
    {
    }

    public void validate(ExprValidationContext validationContext) throws ExprValidationException
    {
        evaluators = ExprNodeUtility.getEvaluators(this.getChildNodes());

        // Sub-nodes must be returning boolean
        for (ExprEvaluator child : evaluators)
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

    public ExprEvaluator getExprEvaluator()
    {
        return this;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Map<String, Object> getEventType() {
        return null;
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
    {
        Boolean result = true;
        for (ExprEvaluator child : evaluators)
        {
            Boolean evaluated = (Boolean) child.evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
            if (evaluated == null)
            {
                result = null;
            }
            else {
                if (!evaluated)
                {
                    return false;
                }
            }
        }
        return result;
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
        if (!(node instanceof ExprAndNodeImpl))
        {
            return false;
        }

        return true;
    }
}
