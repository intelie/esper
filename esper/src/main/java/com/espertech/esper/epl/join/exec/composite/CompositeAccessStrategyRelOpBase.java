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

package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

public abstract class CompositeAccessStrategyRelOpBase {
    protected ExprEvaluator key;
    protected Class coercionType;

    private final EventBean[] events;
    private final int lookupStream;
    private final boolean isNWOnTrigger;

    protected CompositeAccessStrategyRelOpBase(boolean isNWOnTrigger, int lookupStream, int numStreams, ExprEvaluator key, Class coercionType) {
        this.key = key;
        this.coercionType = coercionType;

        if (lookupStream != -1) {
            events = new EventBean[lookupStream + 1];
        }
        else {
            events = new EventBean[numStreams + 1];
        }
        this.lookupStream = lookupStream;
        this.isNWOnTrigger = isNWOnTrigger;
    }

    public Object evaluateLookup(EventBean event, ExprEvaluatorContext context) {
        events[lookupStream] = event;
        return key.evaluate(events, true, context);
    }

    public Object evaluatePerStream(EventBean[] eventPerStream, ExprEvaluatorContext context) {
        if (isNWOnTrigger) {
            return key.evaluate(eventPerStream, true, context);
        }
        else {
            System.arraycopy(eventPerStream, 0, events, 1, eventPerStream.length);
            return key.evaluate(events, true, context);
        }
    }
}
