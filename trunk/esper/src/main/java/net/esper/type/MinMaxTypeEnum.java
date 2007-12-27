/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.type;


/**
 * Enumeration for the type of arithmatic to use.
 */
public enum MinMaxTypeEnum
{
    /**
     * Max.
     */
    MAX ("max"),

    /**
     * Min.
     */
    MIN ("min");

    private String expressionText;

    private MinMaxTypeEnum(String expressionText)
    {
        this.expressionText = expressionText;
    }

    /**
     * Returns textual representation of enum.
     * @return text for enum
     */
    public String getExpressionText()
    {
        return expressionText;
    }
}
