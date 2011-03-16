package com.espertech.esper.epl.join.plan;

public class QueryGraphTestUtil {

    public static Object[] getStrictKeyProperties(QueryGraph graph, int lookup, int indexed) {
        QueryGraphValue val = graph.getGraphValue(lookup, indexed);
        QueryGraphValuePairHashKeyIndex pair = val.getHashKeyProps();
        return pair.getStrictKeys();
    }

    public static Object[] getIndexProperties(QueryGraph graph, int lookup, int indexed) {
        QueryGraphValue val = graph.getGraphValue(lookup, indexed);
        QueryGraphValuePairHashKeyIndex pair = val.getHashKeyProps();
        return pair.getIndexed();
    }
}
