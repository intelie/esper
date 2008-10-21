/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat;

import com.espertech.esper.event.EventType;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.view.CloneableView;
import com.espertech.esper.view.View;

/**
 * A view that calculates correlation on two fields. The view uses internally a {@link CorrelationBean}
 * instance for the calculations, it also returns this bean as the result.
 * This class accepts most of its behaviour from its parent, {@link com.espertech.esper.view.stat.BaseBivariateStatisticsView}. It adds
 * the usage of the correlation bean and the appropriate schema.
 */
public final class CorrelationView extends BaseBivariateStatisticsView implements CloneableView
{
    private EventType eventType;

    /**
     * Constructor.
     * @param xFieldName is the field name of the field providing X data points
     * @param yFieldName is the field name of the field providing X data points
     * @param statementContext contains required view services
     */
    public CorrelationView(StatementContext statementContext, String xFieldName, String yFieldName)
    {
        super(statementContext, new CorrelationBean(), xFieldName, yFieldName);
    }

    public View cloneView(StatementContext statementContext)
    {
        return new CorrelationView(statementContext, this.getFieldNameX(), this.getFieldNameY());
    }

    public EventType getEventType()
    {
        if (eventType == null)
        {
            eventType = createEventType(statementContext);
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
     * @param statementContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(StatementContext statementContext)
    {
        return statementContext.getEventAdapterService().addBeanType(CorrelationBean.class.getName(), CorrelationBean.class, false);
    }
}


