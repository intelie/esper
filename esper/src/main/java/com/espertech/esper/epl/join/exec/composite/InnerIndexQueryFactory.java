package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.RangeKeyDesc;

import java.util.ArrayList;
import java.util.List;

public class InnerIndexQueryFactory {

    public static InnerIndexQuery make(EventType eventType, String[] optionalKeyProps, Class[] keyCoercionTypes, List<RangeKeyDesc> rangeKeyPairs, Class[] rangeCoercionTypes) {
        // construct chain
        List<InnerIndexQuery> queries = new ArrayList<InnerIndexQuery>();
        if (optionalKeyProps != null) {
            queries.add(new InnerIndexQueryKeyed(eventType, optionalKeyProps, keyCoercionTypes));
        }
        int count = 0;
        for (RangeKeyDesc rangeProp : rangeKeyPairs) {
            Class coercionType = rangeCoercionTypes == null ? null : rangeCoercionTypes[count];
            queries.add(new InnerIndexQueryRange(eventType, rangeProp, coercionType));
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
