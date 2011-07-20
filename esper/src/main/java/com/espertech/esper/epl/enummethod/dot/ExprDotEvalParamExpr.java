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

public class ExprDotEvalParamExpr extends ExprDotEvalParam {

    public ExprDotEvalParamExpr(int parameterNum, ExprNode body, ExprEvaluator bodyEvaluator) {
        super(parameterNum, body, bodyEvaluator);
    }
}
