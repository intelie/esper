/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

/**
 * Specification for a range for the pattern-repeat operator.
 */
public class EvalMatchUntilSpec implements MetaDefItem, Serializable
{
    private final ExprNode lowerBounds;
    private final ExprNode upperBounds;
    private final boolean hasBounds;
    private static final long serialVersionUID = -78885747872100470L;

    /**
     * Ctor.
     * @param lowerBounds is the lower bounds, or null if none supplied
     * @param upperBounds is the upper bounds, or null if none supplied
     */
    public EvalMatchUntilSpec(ExprNode lowerBounds, ExprNode upperBounds)
    {
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;

        if ((lowerBounds != null) || (upperBounds != null))
        {
            hasBounds = true;
        }
        else
        {
            hasBounds = false;
        }
    }

    /**
     * Returns true if there is any endpoint, either low or high. Returns false for no endpoint.
     * @return true for has endpoint
     */
    public boolean isBounded()
    {
        return hasBounds;
    }

    /**
     * Returns the lower endpoint or null if undefined.
     * @return lower endpoint
     */
    public ExprNode getLowerBounds()
    {
        return lowerBounds;
    }

    /**
     * Returns the high endpoint or null if undefined.
     * @return high endpoint
     */
    public ExprNode getUpperBounds()
    {
        return upperBounds;
    }
}
