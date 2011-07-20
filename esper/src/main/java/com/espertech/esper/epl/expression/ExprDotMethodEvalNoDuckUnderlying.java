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

package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExprDotMethodEvalNoDuckUnderlying extends ExprDotMethodEvalNoDuck
{
    private static final Log log = LogFactory.getLog(ExprDotMethodEvalNoDuckUnderlying.class);

    public ExprDotMethodEvalNoDuckUnderlying(String statementName, FastMethod method, ExprEvaluator[] parameters) {
        super(statementName, method, parameters);
    }

    public Object evaluate(Object target, EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        if (target == null) {
            return null;
        }
        if (!(target instanceof EventBean)) {
            log.warn("Expected EventBean return value but received '" + target.getClass().getName() + "' for statement " + super.statementName);
            return null;
        }
        EventBean bean = (EventBean) target;
        return super.evaluate(bean.getUnderlying(), eventsPerStream, isNewData, exprEvaluatorContext);
    }
}
