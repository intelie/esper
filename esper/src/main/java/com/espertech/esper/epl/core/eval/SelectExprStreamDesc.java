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

package com.espertech.esper.epl.core.eval;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.spec.SelectClauseExprCompiledSpec;
import com.espertech.esper.epl.spec.SelectClauseStreamCompiledSpec;

public class SelectExprStreamDesc {
    private final SelectClauseStreamCompiledSpec streamSelected;
    private final SelectClauseExprCompiledSpec expressionSelectedAsStream;

    public SelectExprStreamDesc(SelectClauseStreamCompiledSpec streamSelected) {
        this.streamSelected = streamSelected;
        this.expressionSelectedAsStream = null;
    }

    public SelectExprStreamDesc(SelectClauseExprCompiledSpec expressionSelectedAsStream) {
        this.expressionSelectedAsStream = expressionSelectedAsStream;
        this.streamSelected = null;
    }

    public SelectClauseStreamCompiledSpec getStreamSelected() {
        return streamSelected;
    }

    public SelectClauseExprCompiledSpec getExpressionSelectedAsStream() {
        return expressionSelectedAsStream;
    }
}
