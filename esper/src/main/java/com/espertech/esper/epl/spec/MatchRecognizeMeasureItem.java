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

package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

/**
 * Specification for measure definition item within match_recognize.
 */
public class MatchRecognizeMeasureItem implements MetaDefItem, Serializable
{
    private ExprNode expr;
    private String name;
    private static final long serialVersionUID = 1609117378292500082L;

    /**
     * Ctor.
     * @param expr expression
     * @param name as name
     */
    public MatchRecognizeMeasureItem(ExprNode expr, String name) {
        this.expr = expr;
        this.name = name;
    }

    /**
     * Returns the expression.
     * @return expression
     */
    public ExprNode getExpr() {
        return expr;
    }

    /**
     * Returns the as-name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the validated expression.
     * @param validated expression
     */
    public void setExpr(ExprNode validated)
    {
        this.expr = validated;
    }
}
