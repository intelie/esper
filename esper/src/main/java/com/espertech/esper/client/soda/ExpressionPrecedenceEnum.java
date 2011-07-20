/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.client.soda;

/**
 * Precendence levels for expressions.
 */
public enum ExpressionPrecedenceEnum {

    /**
     * Precedence.
     */
    UNARY(11),
    /**
     * Precedence.
     */
    MULTIPLY(10),
    /**
     * Precedence.
     */
    ADDITIVE(9),
    /**
     * Precedence.
     */
    CONCAT(8),
    /**
     * Precedence.
     */
    RELATIONAL_BETWEEN_IN(7),
    /**
     * Precedence.
     */
    EQUALS(6),
    /**
     * Precedence.
     */
    NEGATED(5),
    /**
     * Precedence.
     */
    BITWISE(4),
    /**
     * Precedence.
     */
    AND(3),
    /**
     * Precedence.
     */
    OR(2),
    /**
     * Precedence.
     */
    CASE(1),

    /**
     * Precedence.
     */
    MINIMUM(Integer.MIN_VALUE);

    private final int level;

    private ExpressionPrecedenceEnum(int level) {
        this.level = level;
    }

    /**
     * Level.
     * @return level
     */
    public int getLevel() {
        return level;
    }
}
