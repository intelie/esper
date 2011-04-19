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
