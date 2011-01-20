/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.parse;

import com.espertech.esper.epl.expression.ExprConstantNode;
import com.espertech.esper.epl.expression.ExprNode;

/**
 * Helper for walking a pattern match-until clause.
 */
public class ASTMatchUntilHelper
{
    /**
     * Validate.
     * @param lowerBounds is the lower bounds, or null if none supplied
     * @param upperBounds is the upper bounds, or null if none supplied
     * @param isAllowLowerZero true to allow zero value for lower range
     * @return true if closed range of constants and the constants are the same value
     * @throws ASTWalkException if the AST is incorrect
     */
    public static boolean validate(ExprNode lowerBounds, ExprNode upperBounds, boolean isAllowLowerZero) throws ASTWalkException
    {
        boolean isConstants = true;
        Object constantLower = null;
        String numericMessage = "Match-until bounds expect a numeric or expression value";
        if (lowerBounds instanceof ExprConstantNode) {
            constantLower = lowerBounds.getExprEvaluator().evaluate(null, true, null);
            if (constantLower == null || !(constantLower instanceof Number)) {
                throw new ASTWalkException(numericMessage);
            }
        }
        else {
            isConstants = lowerBounds == null;
        }

        Object constantUpper = null;
        if (upperBounds instanceof ExprConstantNode) {
            constantUpper = upperBounds.getExprEvaluator().evaluate(null, true, null);
            if (constantUpper == null || !(constantUpper instanceof Number)) {
                throw new ASTWalkException(numericMessage);
            }
        }
        else {
            isConstants = isConstants && upperBounds == null;
        }

        if (!isConstants) {
            return true;
        }

        if (constantLower != null && constantUpper != null) {
            Integer lower = ((Number) constantLower).intValue();
            Integer upper = ((Number) constantUpper).intValue();
            if (lower > upper) {
                throw new ASTWalkException("Incorrect range specification, lower bounds value '" + lower +
                        "' is higher then higher bounds '" + upper + "'");
            }
        }
        verifyConstant(constantLower, isAllowLowerZero);
        verifyConstant(constantUpper, false);

        return constantLower != null && constantUpper != null && constantLower.equals(constantUpper);
    }

    private static void verifyConstant(Object value, boolean isAllowZero) {
        if (value != null) {
            Integer bound = ((Number) value).intValue();
            if (isAllowZero) {
                if (bound < 0) {
                    throw new ASTWalkException("Incorrect range specification, a bounds value of negative value is not allowed");
                }
            }
            else {
                if (bound <= 0) {
                    throw new ASTWalkException("Incorrect range specification, a bounds value of zero or negative value is not allowed");
                }
            }
        }
    }
}
