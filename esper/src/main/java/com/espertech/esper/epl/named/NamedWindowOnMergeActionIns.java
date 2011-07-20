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

package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.InternalEventRouteDest;
import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.epl.core.SelectExprProcessor;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public class NamedWindowOnMergeActionIns extends NamedWindowOnMergeAction {
    private final SelectExprProcessor insertHelper;
    private final InternalEventRouter internalEventRouter;
    private final EPStatementHandle statementHandle;
    private final InternalEventRouteDest internalEventRouteDest;

    public NamedWindowOnMergeActionIns(ExprEvaluator optionalFilter, SelectExprProcessor insertHelper, InternalEventRouter internalEventRouter, EPStatementHandle statementHandle, InternalEventRouteDest internalEventRouteDest) {
        super(optionalFilter);
        this.insertHelper = insertHelper;
        this.internalEventRouter = internalEventRouter;
        this.statementHandle = statementHandle;
        this.internalEventRouteDest = internalEventRouteDest;
    }

    public void apply(EventBean matchingEvent, EventBean[] eventsPerStream, OneEventCollection newData, OneEventCollection oldData, ExprEvaluatorContext exprEvaluatorContext) {
        EventBean event = insertHelper.process(eventsPerStream, true, true);
        if (internalEventRouter == null) {
            newData.add(event);
            return;
        }

        internalEventRouter.route(event, statementHandle, internalEventRouteDest, exprEvaluatorContext, false);
    }
}
