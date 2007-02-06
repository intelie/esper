package net.esper.view.stat;

import net.esper.event.EventType;
import net.esper.view.ViewServiceContext;
import net.esper.view.CloneableView;
import net.esper.view.View;

/**
 * A view that calculates correlation on two fields. The view uses internally a {@link CorrelationBean}
 * instance for the calculations, it also returns this bean as the result.
 * This class accepts most of its behaviour from its parent, {@link net.esper.view.stat.BaseBivariateStatisticsView}. It adds
 * the usage of the correlation bean and the appropriate schema.
 */
public final class CorrelationView extends BaseBivariateStatisticsView implements CloneableView
{
    private EventType eventType;

    /**
     * Constructor.
     * @param xFieldName is the field name of the field providing X data points
     * @param yFieldName is the field name of the field providing X data points
     * @param viewServiceContext contains required view services
     */
    public CorrelationView(ViewServiceContext viewServiceContext, String xFieldName, String yFieldName)
    {
        super(viewServiceContext, new CorrelationBean(), xFieldName, yFieldName);
    }

    public View cloneView(ViewServiceContext viewServiceContext)
    {
        return new CorrelationView(viewServiceContext, this.getFieldNameX(), this.getFieldNameY());
    }

    public EventType getEventType()
    {
        if (eventType == null)
        {
            eventType = createEventType(viewServiceContext);
        }
        return eventType;
    }

    public String toString()
    {
        return this.getClass().getName() +
                " fieldX=" + this.getFieldNameX() +
                " fieldY=" + this.getFieldNameY();
    }

    /**
     * Creates the event type for this view.
     * @param viewServiceContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(ViewServiceContext viewServiceContext)
    {
        return viewServiceContext.getEventAdapterService().addBeanType(CorrelationBean.class.getName(), CorrelationBean.class);
    }
}

