package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;
import com.espertech.esper.epl.join.plan.QueryGraphValueRange;
import com.espertech.esper.epl.join.plan.QueryGraphValueRangeIn;
import com.espertech.esper.epl.join.plan.QueryGraphValueRangeRelOp;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.filter.FilterOperator;

public class SortedAccessStrategyFactory {

    public static SortedAccessStrategy make(EventType eventType, QueryGraphValueRange rangeKeyPair)
    {
        return make(new EventType[] {eventType}, SubqueryRangeKeyDesc.createSingleStreamNum(rangeKeyPair, 0));
    }

    public static SortedAccessStrategy make(EventType[] eventTypePerStream, SubqueryRangeKeyDesc streamRangeKey) {

        QueryGraphValueRange rangeKeyPair = streamRangeKey.getRangeInfo();

        if (rangeKeyPair.getType().isRange()) {
            QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) rangeKeyPair;
            int streamStart = streamRangeKey.getStartStreamNum();
            EventPropertyGetter start = EventBeanUtility.getSafePropertyGetter(eventTypePerStream[streamStart], in.getPropertyStart());
            boolean includeStart = rangeKeyPair.getType().isIncludeStart();

            int streamEnd = streamRangeKey.getEndStreamNum();
            EventPropertyGetter end = EventBeanUtility.getSafePropertyGetter(eventTypePerStream[streamEnd], in.getPropertyEnd());
            boolean includeEnd = rangeKeyPair.getType().isIncludeEnd();
            if (!rangeKeyPair.getType().isRangeInverted()) {
                return new SortedAccessStrategyRange(start, includeStart, end, includeEnd, streamStart, streamEnd, in.isAllowRangeReversal());
            }
            else {
                return new SortedAccessStrategyRangeInverted(start, includeStart, end, includeEnd, streamStart, streamEnd);
            }
        }
        else {
            QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) rangeKeyPair;
            int keyStreamNum = streamRangeKey.getKeyStreamNum();
            EventPropertyGetter key = EventBeanUtility.getSafePropertyGetter(eventTypePerStream[keyStreamNum], relOp.getPropertyKey());
            if (rangeKeyPair.getType() == QueryGraphRangeEnum.GREATER_OR_EQUAL) {
                return new SortedAccessStrategyGE(key, keyStreamNum);
            }
            else if (rangeKeyPair.getType() == QueryGraphRangeEnum.GREATER) {
                return new SortedAccessStrategyGT(key, keyStreamNum);
            }
            else if (rangeKeyPair.getType() == QueryGraphRangeEnum.LESS_OR_EQUAL) {
                return new SortedAccessStrategyLE(key, keyStreamNum);
            }
            else if (rangeKeyPair.getType() == QueryGraphRangeEnum.LESS) {
                return new SortedAccessStrategyLT(key, keyStreamNum);
            }
            else {
                throw new IllegalArgumentException("Comparison operator " + rangeKeyPair.getType() + " not supported");
            }
        }
    }
}
