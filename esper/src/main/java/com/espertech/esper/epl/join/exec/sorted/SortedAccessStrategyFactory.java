package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;
import com.espertech.esper.epl.join.plan.QueryGraphValueEntryRange;
import com.espertech.esper.epl.join.plan.QueryGraphValueEntryRangeIn;
import com.espertech.esper.epl.join.plan.QueryGraphValueEntryRangeRelOp;
import com.espertech.esper.epl.lookup.SubordPropRangeKey;

public class SortedAccessStrategyFactory {

    public static SortedAccessStrategy make(boolean isNWOnTrigger, int lookupStream, int numStreams, QueryGraphValueEntryRange rangeKeyPair, Class coercionType)
    {
        return make(isNWOnTrigger, lookupStream, numStreams, new SubordPropRangeKey(rangeKeyPair, coercionType));
    }

    public static SortedAccessStrategy make(boolean isNWOnTrigger, int lookupStream, int numStreams, SubordPropRangeKey streamRangeKey) {

        QueryGraphValueEntryRange rangeKeyPair = streamRangeKey.getRangeInfo();

        if (rangeKeyPair.getType().isRange()) {
            QueryGraphValueEntryRangeIn in = (QueryGraphValueEntryRangeIn) rangeKeyPair;
            ExprEvaluator startExpr = in.getExprStart().getExprEvaluator();
            ExprEvaluator endExpr = in.getExprEnd().getExprEvaluator();
            boolean includeStart = rangeKeyPair.getType().isIncludeStart();

            boolean includeEnd = rangeKeyPair.getType().isIncludeEnd();
            if (!rangeKeyPair.getType().isRangeInverted()) {
                return new SortedAccessStrategyRange(isNWOnTrigger, lookupStream, numStreams, startExpr, includeStart, endExpr, includeEnd, in.isAllowRangeReversal());
            }
            else {
                return new SortedAccessStrategyRangeInverted(isNWOnTrigger, lookupStream, numStreams, startExpr, includeStart, endExpr, includeEnd);
            }
        }
        else {
            QueryGraphValueEntryRangeRelOp relOp = (QueryGraphValueEntryRangeRelOp) rangeKeyPair;
            ExprEvaluator keyExpr = relOp.getExpression().getExprEvaluator();
            if (rangeKeyPair.getType() == QueryGraphRangeEnum.GREATER_OR_EQUAL) {
                return new SortedAccessStrategyGE(isNWOnTrigger, lookupStream, numStreams, keyExpr);
            }
            else if (rangeKeyPair.getType() == QueryGraphRangeEnum.GREATER) {
                return new SortedAccessStrategyGT(isNWOnTrigger, lookupStream, numStreams, keyExpr);
            }
            else if (rangeKeyPair.getType() == QueryGraphRangeEnum.LESS_OR_EQUAL) {
                return new SortedAccessStrategyLE(isNWOnTrigger, lookupStream, numStreams, keyExpr);
            }
            else if (rangeKeyPair.getType() == QueryGraphRangeEnum.LESS) {
                return new SortedAccessStrategyLT(isNWOnTrigger, lookupStream, numStreams, keyExpr);
            }
            else {
                throw new IllegalArgumentException("Comparison operator " + rangeKeyPair.getType() + " not supported");
            }
        }
    }
}
