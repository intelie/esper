/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.CoercionException;
import com.espertech.esper.type.RelationalOpEnum;

import java.util.Collection;
import java.util.Map;

public class SubselectEvalStrategyFactory
{
    public static SubselectEvalStrategy createStrategy(ExprSubselectNode subselectExpression,
                                                       boolean isNot,
                                                       boolean isAll,
                                                       boolean isAny,
                                                       RelationalOpEnum relationalOp) throws ExprValidationException
    {
        if (subselectExpression.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("The Subselect-IN requires 1 child expression");
        }
        ExprNode valueExpr = subselectExpression.getChildNodes().get(0);

        // Must be the same boxed type returned by expressions under this
        Class typeOne = JavaClassHelper.getBoxedType(subselectExpression.getChildNodes().get(0).getType());

        // collections, array or map not supported
        if ((typeOne.isArray()) || (JavaClassHelper.isImplementsInterface(typeOne, Collection.class)) || (JavaClassHelper.isImplementsInterface(typeOne, Map.class)))
        {
            throw new ExprValidationException("Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords");
        }

        Class typeTwo;
        if (subselectExpression.getSelectClause() != null)
        {
            typeTwo = subselectExpression.getSelectClause().getType();
        }
        else
        {
            typeTwo = subselectExpression.getRawEventType().getUnderlyingType();
        }

        if (relationalOp != null)
        {
            if ((typeOne != String.class) || (typeTwo != String.class))
            {
                if (!JavaClassHelper.isNumeric(typeOne))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" +
                            typeOne.getSimpleName() +
                            "' to numeric is not allowed");
                }
                if (!JavaClassHelper.isNumeric(typeTwo))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" +
                            typeTwo.getSimpleName() +
                            "' to numeric is not allowed");
                }
            }

            Class compareType = JavaClassHelper.getCompareToCoercionType(typeOne, typeTwo);
            RelationalOpEnum.Computer computer = relationalOp.getComputer(compareType, typeOne, typeTwo);

            if (isAny)
            {
                return new SubselectEvalStrategyRelOpAny(computer, valueExpr, subselectExpression.getSelectClause(), subselectExpression.getFilterExpr());
            }
            return new SubselectEvalStrategyRelOpAll(computer, valueExpr, subselectExpression.getSelectClause(), subselectExpression.getFilterExpr());
        }

        // Get the common type such as Bool, String or Double and Long
        Class coercionType;
        boolean mustCoerce;
        try
        {
            coercionType = JavaClassHelper.getCompareToCoercionType(typeOne, typeTwo);
        }
        catch (CoercionException ex)
        {
            throw new ExprValidationException("Implicit conversion from datatype '" +
                    typeTwo.getSimpleName() +
                    "' to '" +
                    typeOne.getSimpleName() +
                    "' is not allowed");
        }

        // Check if we need to coerce
        mustCoerce = false;
        if ((coercionType != JavaClassHelper.getBoxedType(typeOne)) ||
            (coercionType != JavaClassHelper.getBoxedType(typeTwo)))
        {
            if (JavaClassHelper.isNumeric(coercionType))
            {
                mustCoerce = true;
            }
        }

        if (isAll)
        {
            return new SubselectEvalStrategyEqualsAll(isNot, mustCoerce, coercionType, valueExpr, subselectExpression.getSelectClause(), subselectExpression.getFilterExpr());
        }
        else if (isAny)
        {
            return new SubselectEvalStrategyEqualsAny(isNot, mustCoerce, coercionType, valueExpr, subselectExpression.getSelectClause(), subselectExpression.getFilterExpr());
        }
        else
        {
            return new SubselectEvalStrategyEqualsIn(isNot, mustCoerce, coercionType, valueExpr, subselectExpression.getSelectClause(), subselectExpression.getFilterExpr());
        }
    }
}