package net.esper.view.stat;

import net.esper.event.EventType;

/**
 * A view that calculates correlation on two fields. The view uses internally a {@link CorrelationBean}
 * instance for the calculations, it also returns this bean as the result.
 * This class accepts most of its behaviour from its parent, {@link net.esper.view.stat.BaseBivariateStatisticsView}. It adds
 * the usage of the correlation bean and the appropriate schema.
 */
public final class CorrelationView extends BaseBivariateStatisticsView
{
    private EventType eventType;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public CorrelationView()
    {
        statisticsBean = new CorrelationBean();
    }

    /**
     * Constructor.
     * @param xFieldName is the field name of the field providing X data points
     * @param yFieldName is the field name of the field providing X data points
     */
    public CorrelationView(String xFieldName, String yFieldName)
    {
        super(new CorrelationBean(), xFieldName, yFieldName);
    }

    public EventType getEventType()
    {
        if (eventType == null)
        {
            eventType = viewServiceContext.getEventAdapterService().addBeanType(CorrelationBean.class.getName(), CorrelationBean.class);
        }
        return eventType;
    }

    public String toString()
    {
        return this.getClass().getName() +
                " fieldX=" + this.getFieldNameX() +
                " fieldY=" + this.getFieldNameY();
    }
}

