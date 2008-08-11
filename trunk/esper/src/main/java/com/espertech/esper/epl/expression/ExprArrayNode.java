/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
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
import java.lang.reflect.Array;

/**
 * Represents an array in a filter expressiun tree.
 */
public class ExprArrayNode extends ExprNode
{
    private Class coercionType;
    private boolean mustCoerce;
    private int length;
    private Object constantResult;

    /**
     * Ctor.
     */
    public ExprArrayNode()
    {
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        length = this.getChildNodes().size();

        // Can be an empty array with no content
        if (this.getChildNodes().size() == 0)
        {
            coercionType = Object.class;
            constantResult = new Object[0];
            return;
        }

        List<Class> comparedTypes = new LinkedList<Class>();
        for (int i = 0; i < length; i++)
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
            // expected, such as mixing String and int values, or Java classes (not boxed) and primitives
            // use Object[] in such cases
        }
        if (coercionType == null)
        {
            coercionType = Object.class;
        }

        // Determine if we are dealing with constants only
        Object[] results = new Object[length];
        int index = 0;
        for (ExprNode child : this.getChildNodes())
        {
            if (!child.isConstantResult())
            {
                results = null;  // not using a constant result
                break;
            }
            results[index++] = child.evaluate(null, false);
        }

        // Copy constants into array and coerce, if required
        if (results != null)
        {
            constantResult = Array.newInstance(coercionType, length);
            for (int i = 0; i < length; i++)
            {
                if (mustCoerce)
                {
                    Number boxed = (Number) results[i];
                    if (boxed != null)
                    {
                        Object coercedResult = JavaClassHelper.coerceBoxed(boxed, coercionType);
                        Array.set(constantResult, i, coercedResult);
                    }
                }
                else
                {
                    Array.set(constantResult, i, results[i]);
                }
            }
        }
    }

    public boolean isConstantResult()
    {
        return constantResult != null;
    }

    public Class getType() throws ExprValidationException
    {
        return Array.newInstance(coercionType, 0).getClass();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        if (constantResult != null)
        {
            return constantResult;
        }

        Object array = Array.newInstance(coercionType, length);

        if (length == 0)
        {
            return array;
        }

        int index = 0;
        for (ExprNode child : this.getChildNodes())
        {
            Object result = child.evaluate(eventsPerStream, isNewData);
            if (result != null)
            {
                if (mustCoerce)
                {
                    Number boxed = (Number) result;
                    Object coercedResult = JavaClassHelper.coerceBoxed(boxed, coercionType);
                    Array.set(array, index, coercedResult);
                }
                else
                {
                    Array.set(array, index, result);
                }
            }
            index++;
        }

        return array;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        String delimiter = "";

        buffer.append("{");
        for (ExprNode expr : this.getChildNodes())
        {
            buffer.append(delimiter);
            buffer.append(expr.toExpressionString());
            delimiter = ",";
        }

        buffer.append('}');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprArrayNode))
        {
            return false;
        }
        return true;
    }
}
