package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;
import com.espertech.esper.epl.join.plan.RangeKeyDesc;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;
import com.espertech.esper.event.EventBeanUtility;

public class SortedAccessStrategyFactory {

    public static SortedAccessStrategy make(EventType eventType, RangeKeyDesc rangeKeyPair)
    {
        return make(new EventType[] {eventType}, SubqueryRangeKeyDesc.createZeroStreamNum(rangeKeyPair));
    }

    public static SortedAccessStrategy make(EventType[] eventTypePerStream, SubqueryRangeKeyDesc streamRangeKey) {

        RangeKeyDesc rangeKeyPair = streamRangeKey.getRangeKey();

        if (rangeKeyPair.getOp().isRange()) {
            int streamStart = streamRangeKey.getStartStreamNum();
            EventPropertyGetter start = EventBeanUtility.getSafePropertyGetter(eventTypePerStream[streamStart], rangeKeyPair.getStart());
            boolean includeStart = rangeKeyPair.getOp().isIncludeStart();

            int streamEnd = streamRangeKey.getEndStreamNum();
            EventPropertyGetter end = EventBeanUtility.getSafePropertyGetter(eventTypePerStream[streamEnd], rangeKeyPair.getEnd());
            boolean includeEnd = rangeKeyPair.getOp().isIncludeEnd();
            if (!rangeKeyPair.getOp().isRangeInverted()) {
                return new SortedAccessStrategyRange(start, includeStart, end, includeEnd, streamStart, streamEnd, rangeKeyPair.isAllowRangeReversal());
            }
            else {
                return new SortedAccessStrategyRangeInverted(start, includeStart, end, includeEnd, streamStart, streamEnd);
            }
        }
        else {
            int keyStreamNum = streamRangeKey.getKeyStreamNum();
            EventPropertyGetter key = EventBeanUtility.getSafePropertyGetter(eventTypePerStream[keyStreamNum], rangeKeyPair.getKey());
            if (rangeKeyPair.getOp() == QueryGraphRangeEnum.GREATER_OR_EQUAL) {
                return new SortedAccessStrategyGE(key, keyStreamNum);
            }
            else if (rangeKeyPair.getOp() == QueryGraphRangeEnum.GREATER) {
                return new SortedAccessStrategyGT(key, keyStreamNum);
            }
            else if (rangeKeyPair.getOp() == QueryGraphRangeEnum.LESS_OR_EQUAL) {
                return new SortedAccessStrategyLE(key, keyStreamNum);
            }
            else if (rangeKeyPair.getOp() == QueryGraphRangeEnum.LESS) {
                return new SortedAccessStrategyLT(key, keyStreamNum);
            }
            else {
                throw new IllegalArgumentException("Comparison operator " + rangeKeyPair.getOp() + " not supported");
            }
        }
    }
}
