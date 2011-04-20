/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;

import java.util.Collection;

public interface ExprPreviousEval
{
    public Object evaluate(EventBean[] eventsPerStream, ExprEvaluatorContext exprEvaluatorContext);
    public Collection<EventBean> evaluateGetCollEvents(EventBean[] eventsPerStream, ExprEvaluatorContext context);
    public Collection evaluateGetCollScalar(EventBean[] eventsPerStream, ExprEvaluatorContext context);
    public EventBean evaluateGetEventBean(EventBean[] eventsPerStream, ExprEvaluatorContext context);
}