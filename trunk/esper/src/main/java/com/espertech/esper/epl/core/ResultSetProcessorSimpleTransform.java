package com.espertech.esper.epl.core;

import com.espertech.esper.collection.TransformEventMethod;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.event.EventBean;

/**
 * Method to transform an event based on the select expression.
 */
public class ResultSetProcessorSimpleTransform implements TransformEventMethod
{
    private final ResultSetProcessorBaseSimple resultSetProcessor;
    private final EventBean[] newData;

    /**
     * Ctor.
     * @param resultSetProcessor is applying the select expressions to the events for the transformation
     */
    public ResultSetProcessorSimpleTransform(ResultSetProcessorBaseSimple resultSetProcessor) {
        this.resultSetProcessor = resultSetProcessor;
        newData = new EventBean[1];
    }

    public EventBean transform(EventBean event)
    {
        newData[0] = event;
        UniformPair<EventBean[]> pair = resultSetProcessor.processViewResult(newData, null, true);
        return pair.getFirst()[0];
    }
}
