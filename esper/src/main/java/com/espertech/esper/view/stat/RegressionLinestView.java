/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.view.CloneableView;
import com.espertech.esper.view.View;
import com.espertech.esper.view.ViewFieldEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * A view that calculates regression on two fields. The view uses internally a {@link RegressionBean}
 * instance for the calculations, it also returns this bean as the result.
 * This class accepts most of its behaviour from its parent, {@link com.espertech.esper.view.stat.BaseBivariateStatisticsView}. It adds
 * the usage of the regression bean and the appropriate schema.
 */
public final class RegressionLinestView extends BaseBivariateStatisticsView implements CloneableView
{
    /**
     * Constructor.
     * @param xFieldName is the field name of the field providing X data points
     * @param yFieldName is the field name of the field providing X data points
     * @param statementContext contains required view services
     */
    public RegressionLinestView(StatementContext statementContext, ExprNode xFieldName, ExprNode yFieldName, EventType eventType, StatViewAdditionalProps additionalProps)
    {
        super(statementContext, new RegressionBean(), xFieldName, yFieldName, eventType, additionalProps);
    }

    public View cloneView(StatementContext statementContext)
    {
        return new RegressionLinestView(statementContext, this.getExpressionX(), this.getExpressionY(), eventType, additionalProps);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public String toString()
    {
        return this.getClass().getName() +
                " fieldX=" + this.getExpressionX() +
                " fieldY=" + this.getExpressionY();
    }

    protected EventBean populateMap(BaseStatisticsBean baseStatisticsBean,
                                         EventAdapterService eventAdapterService,
                                         EventType eventType)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        RegressionBean rb = (RegressionBean) baseStatisticsBean;
        result.put(ViewFieldEnum.REGRESSION__SLOPE.getName(), rb.getSlope());
        result.put(ViewFieldEnum.REGRESSION__YINTERCEPT.getName(), rb.getYIntercept());
        addProperties(result);
        return eventAdapterService.adaptorForTypedMap(result, eventType);
    }

    /**
     * Creates the event type for this view.
     * @param statementContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(StatementContext statementContext, StatViewAdditionalProps additionalProps)
    {
        Map<String, Object> eventTypeMap = new HashMap<String, Object>();
        eventTypeMap.put(ViewFieldEnum.REGRESSION__SLOPE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__YINTERCEPT.getName(), Double.class);
        StatViewAdditionalProps.addCheckDupProperties(eventTypeMap, additionalProps,
                ViewFieldEnum.REGRESSION__SLOPE, ViewFieldEnum.REGRESSION__YINTERCEPT);
        return statementContext.getEventAdapterService().createAnonymousMapType(eventTypeMap);
    }
}

