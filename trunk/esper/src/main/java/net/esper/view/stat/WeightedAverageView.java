package net.esper.view.stat;

import net.esper.event.*;
import net.esper.view.*;
import net.esper.collection.SingleEventIterator;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * View for computing a weighted average. The view uses 2 fields within the parent view to compute the weighted average.
 * The X field and weight field. In a price-volume example it calculates the volume-weighted average price
 * as   (sum(price * volume) / sum(volume)).
 * Example: weighted_avg("price", "volume")
 */
public final class WeightedAverageView extends ViewSupport implements CloneableView
{
    private final EventType eventType;
    private final ViewServiceContext viewServiceContext;
    private final String fieldNameX;
    private final String fieldNameWeight;
    private EventPropertyGetter fieldXGetter;
    private EventPropertyGetter fieldWeightGetter;

    private double sumXtimesW = Double.NaN;
    private double sumW = Double.NaN;
    private double currentValue = Double.NaN;

    /**
     * Constructor requires the name of the field to use in the parent view to compute the weighted average on,
     * as well as the name of the field in the parent view to get the weight from.
     * @param fieldNameX is the name of the field within the parent view to use to get numeric data points for this view to
     * compute the average for.
     * @param fieldNameWeight is the field name for the weight to apply to each data point
     */
    public WeightedAverageView(ViewServiceContext viewServiceContext, String fieldNameX, String fieldNameWeight)
    {
        this.fieldNameX = fieldNameX;
        this.fieldNameWeight = fieldNameWeight;
        this.viewServiceContext = viewServiceContext;
        eventType = createEventType(viewServiceContext);
    }

    public View cloneView(ViewServiceContext viewServiceContext)
    {
        return new WeightedAverageView(viewServiceContext, fieldNameX, fieldNameWeight);
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            fieldXGetter = parent.getEventType().getGetter(fieldNameX);
            fieldWeightGetter = parent.getEventType().getGetter(fieldNameWeight);
        }
    }

    /**
     * Returns the name of the field supplying the X values.
     * @return field name supplying X data points
     */
    public final String getFieldNameX()
    {
        return fieldNameX;
    }

    /**
     * Returns the name of the field supplying the weight values.
     * @return field name supplying weight
     */
    public final String getFieldNameWeight()
    {
        return fieldNameWeight;
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        double oldValue = currentValue;

        // add data points to the bean
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                double point = ((Number) fieldXGetter.get(newData[i])).doubleValue();
                double weight = ((Number) fieldWeightGetter.get(newData[i])).doubleValue();

                if (Double.valueOf(sumXtimesW).isNaN())
                {
                    sumXtimesW = point * weight;
                    sumW = weight;
                }
                else
                {
                    sumXtimesW += point * weight;
                    sumW += weight;
                }
            }
        }

        // remove data points from the bean
        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                double point = ((Number) fieldXGetter.get(oldData[i])).doubleValue();
                double weight = ((Number) fieldWeightGetter.get(oldData[i])).doubleValue();

                sumXtimesW -= point * weight;
                sumW -= weight;
            }
        }

        if (sumW != 0)
        {
            currentValue = sumXtimesW / sumW;
        }
        else
        {
            currentValue = Double.NaN;
        }

        // If there are child view, fire update method
        if (this.hasViews())
        {
            Map<String, Object> newDataMap = new HashMap<String, Object>();
            newDataMap.put(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.getName(), currentValue);
            EventBean newDataEvent = viewServiceContext.getEventAdapterService().createMapFromValues(newDataMap, eventType);

            Map<String, Object> oldDataMap = new HashMap<String, Object>();
            oldDataMap.put(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.getName(), oldValue);
            EventBean oldDataEvent = viewServiceContext.getEventAdapterService().createMapFromValues(oldDataMap, eventType);

            updateChildren(new EventBean[] {newDataEvent}, new EventBean[] {oldDataEvent});
        }
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final Iterator<EventBean> iterator()
    {
        Map<String, Object> newDataMap = new HashMap<String, Object>();
        newDataMap.put(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.getName(), currentValue);
        return new SingleEventIterator(viewServiceContext.getEventAdapterService().createMapFromValues(newDataMap, eventType));
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " fieldName=" + fieldNameX +
                " fieldNameWeight=" + fieldNameWeight;
    }

    /**
     * Creates the event type for this view.
     * @param viewServiceContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(ViewServiceContext viewServiceContext)
    {
        Map<String, Class> schemaMap = new HashMap<String, Class>();
        schemaMap.put(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.getName(), double.class);
        EventType eventType = viewServiceContext.getEventAdapterService().createAnonymousMapType(schemaMap);
        return eventType;
    }
}
