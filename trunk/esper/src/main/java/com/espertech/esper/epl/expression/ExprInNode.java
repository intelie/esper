/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.CoercionException;
import com.espertech.esper.schedule.TimeProvider;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Represents the in-clause (set check) function in an expression tree.
 */
public class ExprInNode extends ExprNode
{
    private final boolean isNotIn;

    private Class coercionType;
    private boolean mustCoerce;

    /**
     * Ctor.
     * @param isNotIn is true for "not in" and false for "in"
     */
    public ExprInNode(boolean isNotIn)
    {
        this.isNotIn = isNotIn;
    }

    /**
     * Returns true for not-in, false for regular in
     * @return false for "val in (a,b,c)" or true for "val not in (a,b,c)"
     */
    public boolean isNotIn()
    {
        return isNotIn;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        if (this.getChildNodes().size() < 2)
        {
            throw new ExprValidationException("The IN operator requires at least 2 child expressions");
        }

        List<Class> comparedTypes = new LinkedList<Class>();
        for (int i = 0; i < this.getChildNodes().size(); i++)
        {
            comparedTypes.add(this.getChildNodes().get(i).getType());
        }

        // Determine common denominator type
        try {
            coercionType = JavaClassHelper.getCommonCoercionType(comparedTypes.toArray(new Class[0]));

            // Determine if we need to coerce numbers when one type doesn't match any other type
            if (JavaClassHelper.isNumeric(coercionType))
            {
                mustCoerce = false;
                for (Class comparedType : comparedTypes)
                {
                    if (comparedType != coercionType)
                    {
                        mustCoerce = true;
                    }
                }
            }
        }
        catch (CoercionException ex)
        {
            throw new ExprValidationException("Implicit conversion not allowed: " + ex.getMessage());
        }
    }

    public Class getType()
    {
        return Boolean.class;
    }

    /**
     * Returns the coercion type to use if coercion is required.
     * @return coercion type
     */
    public Class getCoercionType()
    {
        return coercionType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        // Evaluate first child which is the base value to compare to
        Iterator<ExprNode> it = this.getChildNodes().iterator();
        Object inPropResult = it.next().evaluate(eventsPerStream, isNewData);

        boolean matched = false;
        do
        {
            ExprNode inSetValueExpr = it.next();
            Object subExprResult = inSetValueExpr.evaluate(eventsPerStream, isNewData);

            if (compare(inPropResult, subExprResult)) {
                matched = true;
                break;
            }
        }
        while (it.hasNext());

        if (isNotIn)
        {
            return !matched;
        }
        return matched;
    }

    public boolean isConstantResult()
    {
        return false;
    }        

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprInNode))
        {
            return false;
        }

        ExprInNode other = (ExprInNode) node_;
        return other.isNotIn == this.isNotIn;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        String delimiter = "";

        Iterator<ExprNode> it = this.getChildNodes().iterator();
        buffer.append(it.next().toExpressionString());
        if (isNotIn)
        {
            buffer.append(" not in (");
        }
        else
        {
            buffer.append(" in (");
        }

        do
        {
            ExprNode inSetValueExpr = it.next();
            buffer.append(delimiter);
            buffer.append(inSetValueExpr.toExpressionString());
            delimiter = ",";
        }
        while (it.hasNext());

        buffer.append(')');
        return buffer.toString();
    }

    private boolean compare(Object leftResult, Object rightResult)
    {
        if (leftResult == null)
        {
            return (rightResult == null);
        }
        if (rightResult == null)
        {
            return false;
        }

        if (!mustCoerce)
        {
            return leftResult.equals(rightResult);
        }
        else
        {
            Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
            Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
            return left.equals(right);
        }
    }
}
