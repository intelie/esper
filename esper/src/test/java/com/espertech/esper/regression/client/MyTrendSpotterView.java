package com.espertech.esper.regression.client;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.SingleEventIterator;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.View;
import com.espertech.esper.view.ViewSupport;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyTrendSpotterView extends ViewSupport
{
    private static final String PROPERTY_NAME = "trendcount";

    private final StatementContext statementContext;
    private final EventType eventType;
    private final ExprNode expression;
    private final EventBean[] eventsPerStream = new EventBean[1];

    private Long trendcount;
    private Double lastDataPoint;

    // The remove stream must post the same object event reference
    private EventBean lastInsertStreamEvent;

    /**
     * Constructor requires the name of the field to use in the parent view to compute a trend.
     * @param expression is the name of the field within the parent view to use to get numeric data points for this view
     * @param statementContext contains required view services
     */
    public MyTrendSpotterView(StatementContext statementContext, ExprNode expression)
    {
        this.statementContext = statementContext;
        this.expression = expression;
        eventType = createEventType(statementContext);
    }

    public View cloneView(StatementContext statementContext)
    {
        return new MyTrendSpotterView(statementContext, expression);
    }

    /**
     * Returns expression to report statistics on.
     * @return expression providing values
     */
    public final ExprNode getExpression()
    {
        return expression;
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        // The remove stream most post the same exact object references of events that were posted as the insert stream
        EventBean[] removeStreamToPost;
        if (lastInsertStreamEvent != null)
        {
            removeStreamToPost = new EventBean[] {lastInsertStreamEvent};
        }
        else
        {
            removeStreamToPost = new EventBean[] {populateMap(null)};
        }

        // add data points
        if (newData != null)
        {
            for (EventBean aNewData : newData)
            {
                eventsPerStream[0] = aNewData;
                double dataPoint = ((Number) expression.getExprEvaluator().evaluate(eventsPerStream, true, null)).doubleValue();

                if (lastDataPoint == null)
                {
                    trendcount = 1L;
                }
                else if (lastDataPoint < dataPoint)
                {
                    trendcount++;
                }
                else if (lastDataPoint > dataPoint)
                {
                    trendcount = 0L;
                }
                lastDataPoint = dataPoint;
            }
        }

        if (this.hasViews())
        {
            EventBean newDataPost = populateMap(trendcount);
            lastInsertStreamEvent = newDataPost;
            updateChildren(new EventBean[] {newDataPost}, removeStreamToPost);
        }
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final Iterator<EventBean> iterator()
    {
        EventBean event = populateMap(trendcount);
        return new SingleEventIterator(event);
    }

    public final String toString()
    {
        return this.getClass().getName() + " expression=" + expression;
    }

    private EventBean populateMap(Long trendcount)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(PROPERTY_NAME, trendcount);
        return statementContext.getEventAdapterService().adaptorForTypedMap(result, eventType);
    }

    /**
     * Creates the event type for this view.
     * @param statementContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(StatementContext statementContext)
    {
        Map<String, Object> eventTypeMap = new HashMap<String, Object>();
        eventTypeMap.put(PROPERTY_NAME, Long.class);
        return statementContext.getEventAdapterService().createAnonymousMapType("test", eventTypeMap);
    }
}
