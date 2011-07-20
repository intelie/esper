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

package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

public abstract class EnumEvalBaseScalarIndex implements EnumEval {

    protected final ExprEvaluator innerExpression;
    protected final int streamNumLambda;
    protected final String evalPropertyName;
    protected final MapEventBean evalEvent;
    protected final String indexPropertyName;
    protected final MapEventBean indexEvent;

    protected EventBean[] eventsLambda;

    public EnumEvalBaseScalarIndex(ExprEvaluator innerExpression, int streamNumLambda, MapEventType evalEventType, String evalPropertyName, MapEventType indexEventType, String indexPropertyName) {
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.evalPropertyName = evalPropertyName;
        this.evalEvent = new MapEventBean(new HashMap<String, Object>(), evalEventType);
        this.indexPropertyName = indexPropertyName;
        this.indexEvent = new MapEventBean(new HashMap<String, Object>(), indexEventType);
        this.eventsLambda = new EventBean[streamNumLambda + 2];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }
}
