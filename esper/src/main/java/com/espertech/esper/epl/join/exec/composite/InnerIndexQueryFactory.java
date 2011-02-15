package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.QueryGraphValueRange;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InnerIndexQueryFactory {

    public static InnerIndexQuery make(EventType eventType, String[] optionalKeyProps, Class[] keyCoercionTypes, List<QueryGraphValueRange> rangeKeyPairs, Class[] rangeCoercionTypes) {
        List<SubqueryRangeKeyDesc> ranges = new ArrayList<SubqueryRangeKeyDesc>();
        for (QueryGraphValueRange range : rangeKeyPairs) {
            ranges.add(SubqueryRangeKeyDesc.createZeroStreamNum(range));
        }
        return make(new EventType[] {eventType}, new int[optionalKeyProps.length], optionalKeyProps, keyCoercionTypes, ranges, rangeCoercionTypes);
    }

    public static InnerIndexQuery make(EventType[] typesPerStream, int[] keyStreamNums, String[] keyProps, Class[] coercionKeyTypes, Collection<SubqueryRangeKeyDesc> rangeProps, Class[] rangeCoercionTypes) {
        // construct chain
        List<InnerIndexQuery> queries = new ArrayList<InnerIndexQuery>();
        if (keyProps != null && keyProps.length > 0) {
            queries.add(new InnerIndexQueryKeyed(typesPerStream, keyProps, keyStreamNums, coercionKeyTypes));
        }
        int count = 0;
        for (SubqueryRangeKeyDesc rangeProp : rangeProps) {
            Class coercionType = rangeCoercionTypes == null ? null : rangeCoercionTypes[count];
            queries.add(new InnerIndexQueryRange(typesPerStream, rangeProp, coercionType));
            count++;
        }

        // Hook up as chain for remove
        InnerIndexQuery last = null;
        for (InnerIndexQuery action : queries) {
            if (last != null) {
                last.setNext(action);
            }
            last = action;
        }
        return queries.get(0);
    }
}
