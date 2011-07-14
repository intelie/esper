package com.espertech.esper.epl.datetime.eval;

import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.*;

public class ExprDotEvalDTMethodDesc {

    private final ExprDotEval eval;
    private final ExprDotEvalTypeInfo returnType;
    private final ExprDotNodeFilterAnalyzerDTIntervalDesc intervalFilterDesc;

    public ExprDotEvalDTMethodDesc(ExprDotEval eval, ExprDotEvalTypeInfo returnType, ExprDotNodeFilterAnalyzerDTIntervalDesc intervalFilterDesc) {
        this.eval = eval;
        this.returnType = returnType;
        this.intervalFilterDesc = intervalFilterDesc;
    }

    public ExprDotEval getEval() {
        return eval;
    }

    public ExprDotEvalTypeInfo getReturnType() {
        return returnType;
    }

    public ExprDotNodeFilterAnalyzerDTIntervalDesc getIntervalFilterDesc() {
        return intervalFilterDesc;
    }
}
