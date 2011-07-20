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

package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;

public class ExprDotEvalParamLambda extends ExprDotEvalParam {

    private int streamCountIncoming;    // count of incoming streams
    private List<String> goesToNames;    // (x, y) => doSomething   .... parameter names are x and y
    private EventType[] goesToTypes;

    public ExprDotEvalParamLambda(int parameterNum, ExprNode body, ExprEvaluator bodyEvaluator, int streamCountIncoming, List<String> goesToNames, EventType[] goesToTypes) {
        super(parameterNum, body, bodyEvaluator);
        this.streamCountIncoming = streamCountIncoming;
        this.goesToNames = goesToNames;
        this.goesToTypes = goesToTypes;
    }

    public int getStreamCountIncoming() {
        return streamCountIncoming;
    }

    public List<String> getGoesToNames() {
        return goesToNames;
    }

    public EventType[] getGoesToTypes() {
        return goesToTypes;
    }
}
