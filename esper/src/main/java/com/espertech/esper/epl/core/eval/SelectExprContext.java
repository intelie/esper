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
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.EventAdapterService;

public class SelectExprContext {
    private final ExprEvaluator[] expressionNodes;
    private final String[] columnNames;
    private final ExprEvaluatorContext exprEvaluatorContext;
    private final EventAdapterService eventAdapterService;

    public SelectExprContext(ExprEvaluator[] expressionNodes, String[] columnNames, ExprEvaluatorContext exprEvaluatorContext, EventAdapterService eventAdapterService) {
        this.expressionNodes = expressionNodes;
        this.columnNames = columnNames;
        this.exprEvaluatorContext = exprEvaluatorContext;
        this.eventAdapterService = eventAdapterService;
    }

    public ExprEvaluator[] getExpressionNodes() {
        return expressionNodes;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public ExprEvaluatorContext getExprEvaluatorContext() {
        return exprEvaluatorContext;
    }

    public EventAdapterService getEventAdapterService() {
        return eventAdapterService;
    }
}
