package net.esper.view.stat;

import net.esper.event.EventType;
import net.esper.view.StatementServiceContext;
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
     * @param statementServiceContext contains required view services
     */
    public CorrelationView(StatementServiceContext statementServiceContext, String xFieldName, String yFieldName)
    {
        super(statementServiceContext, new CorrelationBean(), xFieldName, yFieldName);
    }

    public View cloneView(StatementServiceContext statementServiceContext)
    {
        return new CorrelationView(statementServiceContext, this.getFieldNameX(), this.getFieldNameY());
    }

    public EventType getEventType()
    {
        if (eventType == null)
        {
            eventType = createEventType(statementServiceContext);
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
     * @param statementServiceContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(StatementServiceContext statementServiceContext)
    {
        return statementServiceContext.getEventAdapterService().addBeanType(CorrelationBean.class.getName(), CorrelationBean.class);
    }
}

