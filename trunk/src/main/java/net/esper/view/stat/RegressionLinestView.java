package net.esper.view.stat;

import net.esper.view.stat.RegressionBean;
import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;

/**
 * A view that calculates regression on two fields. The view uses internally a {@link RegressionBean}
 * instance for the calculations, it also returns this bean as the result.
 * This class accepts most of its behaviour from its parent, {@link net.esper.view.stat.BaseBivariateStatisticsView}. It adds
 * the usage of the regression bean and the appropriate schema.
 */
public final class RegressionLinestView extends BaseBivariateStatisticsView
{
    private EventType eventType;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public RegressionLinestView()
    {
        statisticsBean = new RegressionBean();
    }

    /**
     * Constructor.
     * @param xFieldName is the field name of the field providing X data points
     * @param yFieldName is the field name of the field providing X data points
     */
    public RegressionLinestView(String xFieldName, String yFieldName)
    {
        super(new RegressionBean(), xFieldName, yFieldName);
    }        

    public EventType getEventType()
    {
        if (eventType == null)
        {
            eventType = viewServiceContext.getEventAdapterService().addBeanType(RegressionBean.class.getName(), RegressionBean.class);
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

