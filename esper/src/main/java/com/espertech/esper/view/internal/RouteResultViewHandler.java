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

package com.espertech.esper.view.internal;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

/**
 * Handler for incoming events for split-stream syntax, encapsulates where-clause evaluation strategies.
 */
public interface RouteResultViewHandler
{
    /**
     * Handle event.
     * @param event to handle
     * @param exprEvaluatorContext expression eval context
     * @return true if at least one match was found, false if not 
     */
    public boolean handle(EventBean event, ExprEvaluatorContext exprEvaluatorContext);
}
