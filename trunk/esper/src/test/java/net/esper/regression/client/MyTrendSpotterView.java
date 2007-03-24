package net.esper.regression.client;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.event.EventPropertyGetter;
import net.esper.collection.SingleEventIterator;
import net.esper.core.StatementContext;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class MyTrendSpotterView extends ViewSupport
{
    private static final String PROPERTY_NAME = "trendcount";

    private final StatementContext statementContext;
    private final EventType eventType;
    private final String fieldName;
    private EventPropertyGetter fieldGetter;

    private Long trendcount;
    private Double lastDataPoint;

    /**
     * Constructor requires the name of the field to use in the parent view to compute a trend.
     * @param fieldName is the name of the field within the parent view to use to get numeric data points for this view
     * @param statementContext contains required view services
     */
    public MyTrendSpotterView(StatementContext statementContext, String fieldName)
    {
        this.statementContext = statementContext;
        this.fieldName = fieldName;
        eventType = createEventType(statementContext);
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            fieldGetter = parent.getEventType().getGetter(fieldName);
        }
    }

    public View cloneView(StatementContext statementContext)
    {
        return new MyTrendSpotterView(statementContext, fieldName);
    }

    /**
     * Returns field name of the field to report statistics on.
     * @return field name
     */
    public final String getFieldName()
    {
        return fieldName;
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        EventBean oldDataPost = populateMap(trendcount);

        // add data points
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                double dataPoint = ((Number) fieldGetter.get(newData[i])).doubleValue();

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
            updateChildren(new EventBean[] {newDataPost}, new EventBean[] {oldDataPost});
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
        return this.getClass().getName() + " fieldName=" + fieldName;
    }

    private EventBean populateMap(Long trendcount)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(PROPERTY_NAME, trendcount);
        return statementContext.getEventAdapterService().createMapFromValues(result, eventType);
    }

    /**
     * Creates the event type for this view.
     * @param statementContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(StatementContext statementContext)
    {
        Map<String, Class> eventTypeMap = new HashMap<String, Class>();
        eventTypeMap.put(PROPERTY_NAME, Long.class);
        return statementContext.getEventAdapterService().createAnonymousMapType(eventTypeMap);
    }
}
