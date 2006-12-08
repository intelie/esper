package net.esper.view.stat;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import net.esper.view.ViewSupport;
import net.esper.event.*;
import net.esper.view.ViewFieldEnum;
import net.esper.view.*;
import net.esper.collection.SingleEventIterator;

/**
 * View for computing statistics, which the view exposes via fields representing the sum, count, standard deviation
 * for sample and for population and variance.
 */
public final class UnivariateStatisticsView extends ViewSupport implements ContextAwareView
{
    private ViewServiceContext viewServiceContext;
    private EventType eventType;
    private String fieldName;
    private EventPropertyGetter fieldGetter;
    private final BaseStatisticsBean baseStatisticsBean = new BaseStatisticsBean();

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public UnivariateStatisticsView()
    {
    }

    /**
     * Constructor requires the name of the field to use in the parent view to compute the statistics.
     * @param fieldName is the name of the field within the parent view to use to get numeric data points for this view to
     * compute the statistics on.
     */
    public UnivariateStatisticsView(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public ViewServiceContext getViewServiceContext()
    {
        return viewServiceContext;
    }

    public void setViewServiceContext(ViewServiceContext viewServiceContext)
    {
        this.viewServiceContext = viewServiceContext;

        Map<String, Class> eventTypeMap = new HashMap<String, Class>();
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.getName(), long.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.getName(), double.class);
        eventType = viewServiceContext.getEventAdapterService().createAnonymousMapType(eventTypeMap);
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            fieldGetter = parent.getEventType().getGetter(fieldName);
        }
    }

    /**
     * Returns field name of the field to report statistics on.
     * @return field name
     */
    public final String getFieldName()
    {
        return fieldName;
    }

    /**
     * Set the field name of the field to report statistics on.
     * @param fieldName is the field to report statistics on
     */
    public final void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        // If we have child views, keep a reference to the old values, so we can fire them as old data event.
        EventBean oldDataMap = null;
        if (this.hasViews())
        {
            oldDataMap = populateMap(baseStatisticsBean, viewServiceContext.getEventAdapterService(), eventType);
        }

        // add data points to the bean
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                double point = ((Number) fieldGetter.get(newData[i])).doubleValue();
                baseStatisticsBean.addPoint(point, 0);
            }
        }

        // remove data points from the bean
        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                double point = ((Number) fieldGetter.get(oldData[i])).doubleValue();
                baseStatisticsBean.removePoint(point, 0);
            }
        }

        // If there are child view, fire update method
        if (this.hasViews())
        {
            EventBean newDataMap = populateMap(baseStatisticsBean, viewServiceContext.getEventAdapterService(), eventType);
            updateChildren(new EventBean[] {newDataMap}, new EventBean[] {oldDataMap});
        }
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final Iterator<EventBean> iterator()
    {
        return new SingleEventIterator(populateMap(baseStatisticsBean,
                viewServiceContext.getEventAdapterService(),
                eventType));
    }

    public final String toString()
    {
        return this.getClass().getName() + " fieldName=" + fieldName;
    }

    private static EventBean populateMap(BaseStatisticsBean baseStatisticsBean,
                                         EventAdapterService eventAdapterService,
                                         EventType eventType)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.getName(), baseStatisticsBean.getN());
        result.put(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.getName(), baseStatisticsBean.getXSum());
        result.put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.getName(), baseStatisticsBean.getXStandardDeviationSample());
        result.put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.getName(), baseStatisticsBean.getXStandardDeviationPop());
        result.put(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.getName(), baseStatisticsBean.getXVariance());
        result.put(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.getName(), baseStatisticsBean.getXAverage());
        return eventAdapterService.createMapFromValues(result, eventType);
    }
}