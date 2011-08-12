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

package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;

public abstract class ExprDotEvalParam {
    private int parameterNum;
    private ExprNode body;
    private ExprEvaluator bodyEvaluator;

    protected ExprDotEvalParam(int parameterNum, ExprNode body, ExprEvaluator bodyEvaluator) {
        this.parameterNum = parameterNum;
        this.body = body;
        this.bodyEvaluator = bodyEvaluator;
    }

    public int getParameterNum() {
        return parameterNum;
    }

    public ExprNode getBody() {
        return body;
    }

    public ExprEvaluator getBodyEvaluator() {
        return bodyEvaluator;
    }
}
