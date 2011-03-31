package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprEvaluatorEnumeration;
import com.espertech.esper.epl.expression.ExprValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

public class PropertyExprEvaluatorScalarArray implements ExprEvaluatorEnumeration {

    private static final Log log = LogFactory.getLog(PropertyExprEvaluatorScalarArray.class);

    private final String propertyName;
    private final int streamId;
    private final EventPropertyGetter getter;
    private final Class componentType;

    public PropertyExprEvaluatorScalarArray(String propertyName, int streamId, EventPropertyGetter getter, Class componentType) {
        this.propertyName = propertyName;
        this.streamId = streamId;
        this.getter = getter;
        this.componentType = componentType;
    }

    public Collection<EventBean> evaluateGetROCollectionEvents(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        return null;
    }

    public Collection evaluateGetROCollectionScalar(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        EventBean eventInQuestion = eventsPerStream[streamId];
        Object result = getter.get(eventInQuestion);
        if (result == null) {
            return null;
        }
        if (!(result.getClass().isArray())) {
            log.warn("Expected array-type input from property '" + propertyName + "' but received " + result.getClass());
            return null;
        }
        return new ArrayWrappingCollection(result);
    }

    public EventType getEventTypeCollection() {
        return null;
    }

    public Class getComponentTypeCollection() throws ExprValidationException {
        return componentType;
    }
}