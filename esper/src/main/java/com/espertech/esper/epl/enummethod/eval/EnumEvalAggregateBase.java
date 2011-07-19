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

import java.util.Collection;
import java.util.HashMap;

public class EnumEvalAggregateBase {

    protected ExprEvaluator initialization;
    protected ExprEvaluator innerExpression;
    protected int streamNumLambda;
    protected MapEventBean resultEvent;
    protected String resultPropertyName;
    protected EventBean[] eventsLambda;

    public EnumEvalAggregateBase(ExprEvaluator initialization,
                                 ExprEvaluator innerExpression, int streamNumLambda,
                                 MapEventType resultEventType, String resultPropertyName) {
        this.initialization = initialization;
        this.innerExpression = innerExpression;
        this.streamNumLambda = streamNumLambda;
        this.resultEvent = new MapEventBean(new HashMap<String, Object>(), resultEventType);
        this.resultPropertyName = resultPropertyName;
        this.eventsLambda = new EventBean[streamNumLambda + 2];
    }

    public EventBean[] getEventsPrototype() {
        return eventsLambda;
    }
}
