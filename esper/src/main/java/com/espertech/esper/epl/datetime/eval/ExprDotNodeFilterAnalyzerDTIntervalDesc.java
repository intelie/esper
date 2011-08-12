/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.datetime.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprIdentNode;
import com.espertech.esper.epl.expression.ExprNodeUtility;
import com.espertech.esper.epl.join.plan.QueryGraph;
import com.espertech.esper.type.RelationalOpEnum;

public class ExprDotNodeFilterAnalyzerDTIntervalDesc implements ExprDotNodeFilterAnalyzerDesc
{
    private final DatetimeMethodEnum currentMethod;
    private final EventType[] typesPerStream;
    private final int targetStreamNum;
    private final String targetStartProp;
    private final String targetEndProp;
    private final Integer parameterStreamNum;
    private final String parameterStartProp;
    private final String parameterEndProp;

    public ExprDotNodeFilterAnalyzerDTIntervalDesc(DatetimeMethodEnum currentMethod, EventType[] typesPerStream, int targetStreamNum, String targetStartProp, String targetEndProp, Integer parameterStreamNum, String parameterStartProp, String parameterEndProp) {
        this.currentMethod = currentMethod;
        this.typesPerStream = typesPerStream;
        this.targetStreamNum = targetStreamNum;
        this.targetStartProp = targetStartProp;
        this.targetEndProp = targetEndProp;
        this.parameterStreamNum = parameterStreamNum;
        this.parameterStartProp = parameterStartProp;
        this.parameterEndProp = parameterEndProp;
    }

    public void apply(QueryGraph queryGraph) {

        if (targetStreamNum == parameterStreamNum) {
            return;
        }

        ExprIdentNode targetStartExpr = ExprNodeUtility.getExprIdentNode(typesPerStream, targetStreamNum, targetStartProp);
        ExprIdentNode targetEndExpr = ExprNodeUtility.getExprIdentNode(typesPerStream, targetStreamNum, targetEndProp);
        ExprIdentNode parameterStartExpr = ExprNodeUtility.getExprIdentNode(typesPerStream, parameterStreamNum, parameterStartProp);
        ExprIdentNode parameterEndExpr = ExprNodeUtility.getExprIdentNode(typesPerStream, parameterStreamNum, parameterEndProp);

        if (targetStartExpr.getExprEvaluator().getType() != parameterStartExpr.getExprEvaluator().getType()) {
            return;
        }

        if (currentMethod == DatetimeMethodEnum.BEFORE) {
            // a.end < b.start
            queryGraph.addRelationalOpStrict(targetStreamNum, targetEndProp, targetEndExpr,
                    parameterStreamNum, parameterStartProp, parameterStartExpr,
                    RelationalOpEnum.LT);
        }
        else if (currentMethod == DatetimeMethodEnum.AFTER) {
            // a.start > b.end
            queryGraph.addRelationalOpStrict(targetStreamNum, targetStartProp, targetStartExpr,
                    parameterStreamNum, parameterEndProp, parameterEndExpr,
                    RelationalOpEnum.GT);
        }
        else if (currentMethod == DatetimeMethodEnum.COINCIDES) {
            // a.startTimestamp = b.startTimestamp and a.endTimestamp = b.endTimestamp
            queryGraph.addStrictEquals(targetStreamNum, targetStartProp, targetStartExpr,
                    parameterStreamNum, parameterStartProp, parameterStartExpr);

            boolean noDuration = parameterEndProp.equals(parameterStartProp) && targetEndProp.equals(targetStartProp);
            if (!noDuration) {
                ExprIdentNode leftEndExpr = ExprNodeUtility.getExprIdentNode(typesPerStream, targetStreamNum, targetEndProp);
                ExprIdentNode rightEndExpr = ExprNodeUtility.getExprIdentNode(typesPerStream, parameterStreamNum, parameterEndProp);
                queryGraph.addStrictEquals(targetStreamNum, targetEndProp, leftEndExpr,
                        parameterStreamNum, parameterEndProp, rightEndExpr);
            }
        }
        else if (currentMethod == DatetimeMethodEnum.DURING || currentMethod == DatetimeMethodEnum.INCLUDES) {
            // DURING:   b.startTimestamp < a.startTimestamp <= a.endTimestamp < b.endTimestamp
            // INCLUDES: a.startTimestamp < b.startTimestamp <= b.endTimestamp < a.endTimestamp
            RelationalOpEnum relop = currentMethod == DatetimeMethodEnum.DURING ? RelationalOpEnum.LT : RelationalOpEnum.GT;
            queryGraph.addRelationalOpStrict(parameterStreamNum, parameterStartProp, parameterStartExpr,
                    targetStreamNum, targetStartProp, targetStartExpr,
                    relop);

            queryGraph.addRelationalOpStrict(targetStreamNum, targetEndProp, targetEndExpr,
                    parameterStreamNum, parameterEndProp, parameterEndExpr,
                    relop);
        }
        else if (currentMethod == DatetimeMethodEnum.FINISHES || currentMethod == DatetimeMethodEnum.FINISHEDBY) {
            // FINISHES:   b.startTimestamp < a.startTimestamp and a.endTimestamp = b.endTimestamp
            // FINISHEDBY: a.startTimestamp < b.startTimestamp and a.endTimestamp = b.endTimestamp
            RelationalOpEnum relop = currentMethod == DatetimeMethodEnum.FINISHES ? RelationalOpEnum.LT : RelationalOpEnum.GT;
            queryGraph.addRelationalOpStrict(parameterStreamNum, parameterStartProp, parameterStartExpr,
                    targetStreamNum, targetStartProp, targetStartExpr,
                    relop);

            queryGraph.addStrictEquals(targetStreamNum, targetEndProp, targetEndExpr,
                    parameterStreamNum, parameterEndProp, parameterEndExpr);
        }
        else if (currentMethod == DatetimeMethodEnum.MEETS) {
            // a.endTimestamp = b.startTimestamp
            queryGraph.addStrictEquals(targetStreamNum, targetEndProp, targetEndExpr,
                    parameterStreamNum, parameterStartProp, parameterStartExpr);
        }
        else if (currentMethod == DatetimeMethodEnum.METBY) {
            // a.startTimestamp = b.endTimestamp
            queryGraph.addStrictEquals(targetStreamNum, targetStartProp, targetStartExpr,
                    parameterStreamNum, parameterEndProp, parameterEndExpr);
        }
        else if (currentMethod == DatetimeMethodEnum.OVERLAPS || currentMethod == DatetimeMethodEnum.OVERLAPPEDBY) {
            // OVERLAPS:     a.startTimestamp < b.startTimestamp < a.endTimestamp < b.endTimestamp
            // OVERLAPPEDBY: b.startTimestamp < a.startTimestamp < b.endTimestamp < a.endTimestamp
            RelationalOpEnum relop = currentMethod == DatetimeMethodEnum.OVERLAPS ? RelationalOpEnum.LT : RelationalOpEnum.GT;
            queryGraph.addRelationalOpStrict(targetStreamNum, targetStartProp, targetStartExpr,
                    parameterStreamNum, parameterStartProp, parameterStartExpr,
                    relop);

            queryGraph.addRelationalOpStrict(targetStreamNum, targetEndProp, targetEndExpr,
                    parameterStreamNum, parameterEndProp, parameterEndExpr,
                    relop);

            if (currentMethod == DatetimeMethodEnum.OVERLAPS) {
                queryGraph.addRelationalOpStrict(parameterStreamNum, parameterStartProp, parameterStartExpr,
                        targetStreamNum, targetEndProp, targetEndExpr,
                        RelationalOpEnum.LT);
            }
            else {
                queryGraph.addRelationalOpStrict(targetStreamNum, targetStartProp, targetStartExpr,
                        parameterStreamNum, parameterEndProp, parameterEndExpr,
                        RelationalOpEnum.LT);
            }
        }
        else if (currentMethod == DatetimeMethodEnum.STARTS || currentMethod == DatetimeMethodEnum.STARTEDBY) {
            // STARTS:       a.startTimestamp = b.startTimestamp and a.endTimestamp < b.endTimestamp
            // STARTEDBY:    a.startTimestamp = b.startTimestamp and b.endTimestamp < a.endTimestamp
            queryGraph.addStrictEquals(targetStreamNum, targetStartProp, targetStartExpr,
                    parameterStreamNum, parameterStartProp, parameterStartExpr);

            RelationalOpEnum relop = currentMethod == DatetimeMethodEnum.STARTS ? RelationalOpEnum.LT : RelationalOpEnum.GT;
            queryGraph.addRelationalOpStrict(targetStreamNum, targetEndProp, targetEndExpr,
                    parameterStreamNum, parameterEndProp, parameterEndExpr,
                    relop);
        }
    }
}

