/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.variable.VariableService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Adapter for use by {@link FilterParamIndexBooleanExpr} to evaluate boolean expressions, providing
 * events per stream to expression nodes. Generated by @{link FilterSpecParamExprNode} for
 * boolean expression filter parameters.
 */
public class ExprNodeAdapter
{
    private static final Log log = LogFactory.getLog(ExprNodeAdapter.class);

    private final ExprNode exprNode;
    private final ExprEvaluator exprNodeEval;
    private final EventBean[] prototype;
    private final VariableService variableService;

    private ThreadLocal<EventBean[]> arrayPerThread = new ThreadLocal<EventBean[]>()
    {
        protected synchronized EventBean[] initialValue()
        {
            EventBean[] eventsPerStream = new EventBean[prototype.length];
            System.arraycopy(prototype, 0, eventsPerStream, 0, prototype.length);
            return eventsPerStream;
        }
    };

    /**
     * Ctor.
     * @param exprNode is the boolean expression
     * @param prototype is the row of events the we are matching on
     * @param variableService for setting variable version for evaluating variables, if required
     */
    public ExprNodeAdapter(ExprNode exprNode, EventBean[] prototype, VariableService variableService)
    {
        this.exprNode = exprNode;
        this.exprNodeEval = exprNode.getExprEvaluator();
        this.variableService = variableService;
        if (prototype == null)
        {
            this.prototype = new EventBean[1];
        }
        else
        {
            this.prototype = prototype;
        }
    }

    /**
     * Evaluate the boolean expression given the event as a stream zero event.
     * @param event is the stream zero event (current event)
     * @param exprEvaluatorContext context for expression evaluation
     * @return boolean result of the expression
     */
    public boolean evaluate(EventBean event, ExprEvaluatorContext exprEvaluatorContext)
    {
        if (variableService != null)
        {
            variableService.setLocalVersion();
        }
        EventBean[] eventsPerStream = arrayPerThread.get();
        eventsPerStream[0] = event;

        try {
            Boolean result = (Boolean) exprNodeEval.evaluate(eventsPerStream, true, exprEvaluatorContext);
            if (result == null)
            {
                return false;
            }
            return result;
        }
        catch (RuntimeException ex) {
            log.error("Error evaluating expression '" + exprNode.toExpressionString() + "': " + ex.getMessage(), ex);
            return false;
        }
    }
}
