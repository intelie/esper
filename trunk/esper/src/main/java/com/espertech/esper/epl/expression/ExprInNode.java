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
import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.CoercionException;
import com.espertech.esper.util.SimpleNumberCoercerFactory;
import com.espertech.esper.util.SimpleNumberCoercer;
import com.espertech.esper.schedule.TimeProvider;

import java.util.*;
import java.lang.reflect.Array;

/**
 * Represents the in-clause (set check) function in an expression tree.
 */
public class ExprInNode extends ExprNode
{
    private final boolean isNotIn;

    private boolean mustCoerce;
    private boolean hasCollection;
    private boolean[] isMap;
    private boolean[] isCollection;
    private SimpleNumberCoercer coercer;

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

        Class typeOne = this.getChildNodes().get(0).getType();
        if ((typeOne.isArray()) || (JavaClassHelper.isImplementsInterface(typeOne, Collection.class)) || (JavaClassHelper.isImplementsInterface(typeOne, Map.class)))
        {
            throw new ExprValidationException("Group comparison is not allowed for the IN-keyword, use the ANY, SOME or ALL keywords");
        }

        List<Class> comparedTypes = new ArrayList<Class>();
        for (int i = 0; i < this.getChildNodes().size(); i++)
        {
            Class propType = this.getChildNodes().get(i).getType();
            if ((propType != null) && (propType.isArray()))
            {
                hasCollection = true;
                if (propType.getComponentType() != Object.class)
                {
                    comparedTypes.add(propType.getComponentType());
                }
            }
            else if ((propType != null) && (JavaClassHelper.isImplementsInterface(propType, Collection.class)))
            {
                hasCollection = true;
                if (isCollection == null)
                {
                    isCollection = new boolean[this.getChildNodes().size()];
                }
                isCollection[i] = true;
            }
            else if ((propType != null) && (JavaClassHelper.isImplementsInterface(propType, Map.class)))
            {
                hasCollection = true;
                if (isMap == null)
                {
                    isMap = new boolean[this.getChildNodes().size()];
                }
                isMap[i] = true;
            }
            else
            {
                comparedTypes.add(propType);
            }
        }

        // Determine common denominator type
        try {
            Class coercionType = JavaClassHelper.getCommonCoercionType(comparedTypes.toArray(new Class[comparedTypes.size()]));

            // Determine if we need to coerce numbers when one type doesn't match any other type
            if (JavaClassHelper.isNumeric(coercionType))
            {
                mustCoerce = false;
                for (Class comparedType : comparedTypes)
                {
                    if (JavaClassHelper.getBoxedType(comparedType) != JavaClassHelper.getBoxedType(coercionType))
                    {
                        mustCoerce = true;
                        break;
                    }
                }

                if (mustCoerce)
                {
                    coercer = SimpleNumberCoercerFactory.getCoercer(null, coercionType);
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

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        // Evaluate first child which is the base value to compare to
        Iterator<ExprNode> it = this.getChildNodes().iterator();
        Object inPropResult = it.next().evaluate(eventsPerStream, isNewData);

        boolean matched = false;
        if (!hasCollection)
        {
            // coerce upfront when comparing single values, coerce later when comparing against collections
            if (mustCoerce)
            {
                if (inPropResult != null)
                {
                    inPropResult = coercer.coerceBoxed((Number) inPropResult);
                }
            }

            // handle value-by-value compare
            boolean hasNonNullRow = false;
            boolean hasNullRow = false;
            do
            {
                ExprNode inSetValueExpr = it.next();
                Object subExprResult = inSetValueExpr.evaluate(eventsPerStream, isNewData);

                if (subExprResult == null)
                {
                    hasNullRow = true;
                }
                else
                {
                    hasNonNullRow = true;
                    if (compare(inPropResult, subExprResult)) {
                        matched = true;
                        break;
                    }
                }
            }
            while (it.hasNext());

            if (!matched)
            {
                if ((!hasNonNullRow) || (hasNullRow))
                {
                    return null;
                }
            }
        }
        else
        {
            int index = 1;  // right-side nodes start at 1
            boolean hasNonNullRow = false;
            boolean hasNullRow = false;
            do
            {
                ExprNode inSetValueExpr = it.next();
                Object subExprResult = inSetValueExpr.evaluate(eventsPerStream, isNewData);

                if (subExprResult == null)
                {
                    hasNullRow = true;
                }
                else
                {
                    if (compareCollectionable(inPropResult, subExprResult, index)) {
                        matched = true;
                        break;
                    }
                    index++;
                }
            }
            while (it.hasNext());

            if (!matched)
            {
                if ((!hasNonNullRow) || (hasNullRow))
                {
                    return null;
                }
            }            
        }

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
            Number right = coercer.coerceBoxed((Number) rightResult);
            return leftResult.equals(right);
        }
    }

    private boolean compareCollectionable(Object leftResult, Object rightResult, int index)
    {
        if (leftResult == null)
        {
            return (rightResult == null);
        }
        if (rightResult == null)
        {
            return false;
        }

        if (rightResult.getClass().isArray())
        {
            if (mustCoerce)
            {
                leftResult = coercer.coerceBoxed((Number) leftResult);
            }
            for (int i = 0; i < Array.getLength(rightResult); i++)
            {
                Object value = Array.get(rightResult, i);
                if ((value != null) && (leftResult.equals(value)))
                {
                    return true;
                }
                if (mustCoerce && (value != null) && (value instanceof Number))
                {
                    Number right = coercer.coerceBoxed((Number) value);
                    if (leftResult.equals(right))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        else if (isMap != null && isMap[index])
        {
            Map map = (Map) rightResult;
            return map.containsKey(leftResult);
        }
        else if (isCollection != null && isCollection[index])
        {
            Collection coll = (Collection) rightResult;
            return coll.contains(leftResult);
        }
        else
        {
            if (mustCoerce)
            {
                leftResult = coercer.coerceBoxed((Number) leftResult);
                rightResult = coercer.coerceBoxed((Number) rightResult);
            }
            return leftResult.equals(rightResult);
        }

    }
}
