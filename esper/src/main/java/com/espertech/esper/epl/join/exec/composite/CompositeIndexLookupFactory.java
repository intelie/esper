package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValue;

import java.util.ArrayList;
import java.util.List;

public class CompositeIndexLookupFactory {

    public static CompositeIndexLookup make(Object[] keyValues, RangeIndexLookupValue[] rangeValues, Class[] rangeCoercion) {
        // construct chain
        List<CompositeIndexLookup> queries = new ArrayList<CompositeIndexLookup>();
        if (keyValues != null && keyValues.length > 0) {
            queries.add(new CompositeIndexLookupKeyed(keyValues));
        }
        for (int i = 0; i < rangeValues.length; i++) {
            queries.add(new CompositeIndexLookupRange(rangeValues[i], rangeCoercion[i]));
        }

        // Hook up as chain for remove
        CompositeIndexLookup last = null;
        for (CompositeIndexLookup action : queries) {
            if (last != null) {
                last.setNext(action);
            }
            last = action;
        }
        return queries.get(0);
    }
}
