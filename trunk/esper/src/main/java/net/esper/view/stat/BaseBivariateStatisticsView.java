package net.esper.view.stat;

import java.util.Iterator;

import net.esper.collection.SingleEventIterator;
import net.esper.event.EventBean;
import net.esper.event.EventPropertyGetter;
import net.esper.view.*;
import net.esper.core.StatementContext;

/**
 * View for computing statistics that require 2 input variable arrays containing X and Y datapoints.
 * Subclasses compute correlation or regression values, for instance.
 */
public abstract class BaseBivariateStatisticsView extends ViewSupport
{
    /**
     * This bean can be overridden by subclasses providing extra values such as correlation, regression.
     */
    protected BaseStatisticsBean statisticsBean;

    private String fieldNameX;
    private String fieldNameY;
    private EventPropertyGetter fieldXGetter;
    private EventPropertyGetter fieldYGetter;

    /**
     * Services required by implementing classes.
     */
    protected StatementContext statementContext;

    /**
     * Constructor requires the name of the two fields to use in the parent view to compute the statistics.
     * @param statisticsBean is the base class prodiving sum of X and Y and squares for use by subclasses
     * @param fieldNameX is the name of the field within the parent view to get the X values from
     * @param fieldNameY is the name of the field within the parent view to get the Y values from
     * @param statementContext contains required view services
     */
    public BaseBivariateStatisticsView(StatementContext statementContext,
                                       BaseStatisticsBean statisticsBean,
                                       String fieldNameX,
                                       String fieldNameY)
    {
        this.statementContext = statementContext;
        this.statisticsBean = statisticsBean;
        this.fieldNameX = fieldNameX;
        this.fieldNameY = fieldNameY;
    }

    public final void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            fieldXGetter = parent.getEventType().getGetter(fieldNameX);
            fieldYGetter = parent.getEventType().getGetter(fieldNameY);
        }
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        // If we have child views, keep a reference to the old values, so we can fireStatementStopped them as old data event.
        BaseStatisticsBean oldValues = null;
        if (this.hasViews())
        {
            oldValues = (BaseStatisticsBean) statisticsBean.clone();
        }

        // add data points to the bean
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                double X = ((Number) fieldXGetter.get(newData[i])).doubleValue();
                double Y = ((Number) fieldYGetter.get(newData[i])).doubleValue();
                statisticsBean.addPoint(X, Y);
            }
        }

        // remove data points from the bean
        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                double X = ((Number) fieldXGetter.get(oldData[i])).doubleValue();
                double Y = ((Number) fieldYGetter.get(oldData[i])).doubleValue();
                statisticsBean.removePoint(X, Y);
            }
        }

        // If there are child view, fireStatementStopped update method
        if (this.hasViews())
        {
            // Make a copy of the current values since if we change the values subsequently, the handed-down
            // values should not change
            BaseStatisticsBean newValues = (BaseStatisticsBean) statisticsBean.clone();
            EventBean newValuesEvent = statementContext.getEventAdapterService().adapterForBean(newValues);
            EventBean oldValuesEvent = statementContext.getEventAdapterService().adapterForBean(oldValues);
            updateChildren(new EventBean[] {newValuesEvent}, new EventBean[] {oldValuesEvent});
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return new SingleEventIterator(statementContext.getEventAdapterService().adapterForBean(statisticsBean));
    }

    /**
     * Returns the field name of the field supplying X data points.
     * @return X field name
     */
    public final String getFieldNameX()
    {
        return fieldNameX;
    }

    /**
     * Returns the field name of the field supplying Y data points.
     * @return Y field name
     */
    public final String getFieldNameY()
    {
        return fieldNameY;
    }
}