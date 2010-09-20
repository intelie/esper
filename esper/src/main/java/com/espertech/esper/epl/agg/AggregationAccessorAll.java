package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;

import java.lang.reflect.Array;
import java.util.Iterator;

public class AggregationAccessorAll implements AggregationAccessor
{
    private final int streamNum;
    private final ExprEvaluator childNode;
    private final EventBean[] eventsPerStream;
    private final Class componentType;

    public AggregationAccessorAll(int streamNum, ExprEvaluator childNode, Class componentType)
    {
        this.streamNum = streamNum;
        this.childNode = childNode;
        this.eventsPerStream = new EventBean[streamNum + 1];
        this.componentType = componentType;
    }

    public Object getValue(AggregationAccess access) {
        if (access.size() == 0) {
            return null;
        }
        Object array = Array.newInstance(componentType, access.size());
        Iterator<EventBean> it = access.iterator();
        int count = 0;
        for (;it.hasNext();) {
            EventBean bean = it.next();
            eventsPerStream[streamNum] = bean;
            Object value = childNode.evaluate(eventsPerStream, true, null);
            Array.set(array, count++, value);
        }

        return array;
    }
}