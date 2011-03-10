package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.ExpressionResultCacheEntry;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprEvaluatorLambda;

import java.util.Arrays;
import java.util.Collection;

public class PropertyExprEvaluatorLambda implements ExprEvaluatorLambda {

    private final String propertyNameCache;
    private final int streamId;
    private final EventType fragmentType;
    private final EventPropertyGetter getter;
    
    public PropertyExprEvaluatorLambda(String propertyName, int streamId, EventType fragmentType, EventPropertyGetter getter) {
        this.streamId = streamId;
        this.propertyNameCache = Integer.toString(streamId) + "_" + propertyName;
        this.fragmentType = fragmentType;
        this.getter = getter;
    }

    public Collection<EventBean> evaluateGetROCollection(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        EventBean eventInQuestion = eventsPerStream[streamId];

        ExpressionResultCacheEntry<EventBean, Collection<EventBean>> cacheEntry = context.getExpressionResultCacheService().getPropertyColl(propertyNameCache, eventInQuestion);
        if (cacheEntry != null) {
            return cacheEntry.getResult();
        }

        EventBean[] events = (EventBean[]) getter.getFragment(eventInQuestion);
        Collection<EventBean> coll = events == null ? null : Arrays.asList(events);
        context.getExpressionResultCacheService().savePropertyColl(propertyNameCache, eventInQuestion, coll);
        if (coll == null) {
            return null;
        }

        return coll;
    }

    public EventType getEventTypeIterator() {
        return fragmentType;
    }
}
