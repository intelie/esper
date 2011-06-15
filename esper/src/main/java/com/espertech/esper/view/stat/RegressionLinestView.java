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
 * A view that calculates regression on two fields. The view uses internally a {@link BaseStatisticsBean}
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
        super(statementContext, xFieldName, yFieldName, eventType, additionalProps);
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

    public EventBean populateMap(BaseStatisticsBean baseStatisticsBean,
                                         EventAdapterService eventAdapterService,
                                         EventType eventType,
                                         StatViewAdditionalProps additionalProps,
                                         Object[] decoration)
    {
        return doPopulateMap(baseStatisticsBean,eventAdapterService,eventType,additionalProps,decoration);
    }

    public static EventBean doPopulateMap(BaseStatisticsBean baseStatisticsBean,
                                         EventAdapterService eventAdapterService,
                                         EventType eventType,
                                         StatViewAdditionalProps additionalProps,
                                         Object[] decoration)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ViewFieldEnum.REGRESSION__SLOPE.getName(), baseStatisticsBean.getSlope());
        result.put(ViewFieldEnum.REGRESSION__YINTERCEPT.getName(), baseStatisticsBean.getYIntercept());
        result.put(ViewFieldEnum.REGRESSION__XAVERAGE.getName(), baseStatisticsBean.getXAverage());
        result.put(ViewFieldEnum.REGRESSION__XSTANDARDDEVIATIONPOP.getName(), baseStatisticsBean.getXStandardDeviationPop());
        result.put(ViewFieldEnum.REGRESSION__XSTANDARDDEVIATIONSAMPLE.getName(), baseStatisticsBean.getXStandardDeviationSample());
        result.put(ViewFieldEnum.REGRESSION__XSUM.getName(), baseStatisticsBean.getXSum());
        result.put(ViewFieldEnum.REGRESSION__XVARIANCE.getName(), baseStatisticsBean.getXVariance());
        result.put(ViewFieldEnum.REGRESSION__YAVERAGE.getName(), baseStatisticsBean.getYAverage());
        result.put(ViewFieldEnum.REGRESSION__YSTANDARDDEVIATIONPOP.getName(), baseStatisticsBean.getYStandardDeviationPop());
        result.put(ViewFieldEnum.REGRESSION__YSTANDARDDEVIATIONSAMPLE.getName(), baseStatisticsBean.getYStandardDeviationSample());
        result.put(ViewFieldEnum.REGRESSION__YSUM.getName(), baseStatisticsBean.getYSum());
        result.put(ViewFieldEnum.REGRESSION__YVARIANCE.getName(), baseStatisticsBean.getYVariance());
        result.put(ViewFieldEnum.REGRESSION__DATAPOINTS.getName(), baseStatisticsBean.getDataPoints());
        result.put(ViewFieldEnum.REGRESSION__N.getName(), baseStatisticsBean.getN());
        result.put(ViewFieldEnum.REGRESSION__SUMX.getName(), baseStatisticsBean.getSumX());
        result.put(ViewFieldEnum.REGRESSION__SUMXSQ.getName(), baseStatisticsBean.getSumXSq());
        result.put(ViewFieldEnum.REGRESSION__SUMXY.getName(), baseStatisticsBean.getSumXY());
        result.put(ViewFieldEnum.REGRESSION__SUMY.getName(), baseStatisticsBean.getSumY());
        result.put(ViewFieldEnum.REGRESSION__SUMYSQ.getName(), baseStatisticsBean.getSumYSq());
        if (additionalProps != null) {
            additionalProps.addProperties(result, decoration);
        }
        return eventAdapterService.adaptorForTypedMap(result, eventType);
    }

    /**
     * Creates the event type for this view.
     * @param statementContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(StatementContext statementContext, StatViewAdditionalProps additionalProps, int streamNum)
    {
        Map<String, Object> eventTypeMap = new HashMap<String, Object>();
        eventTypeMap.put(ViewFieldEnum.REGRESSION__SLOPE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__YINTERCEPT.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__XAVERAGE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__XSTANDARDDEVIATIONPOP.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__XSTANDARDDEVIATIONSAMPLE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__XSUM.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__XVARIANCE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__YAVERAGE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__YSTANDARDDEVIATIONPOP.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__YSTANDARDDEVIATIONSAMPLE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__YSUM.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__YVARIANCE.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__DATAPOINTS.getName(), Long.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__N.getName(), Long.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__SUMX.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__SUMXSQ.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__SUMXY.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__SUMY.getName(), Double.class);
        eventTypeMap.put(ViewFieldEnum.REGRESSION__SUMYSQ.getName(), Double.class);
        StatViewAdditionalProps.addCheckDupProperties(eventTypeMap, additionalProps,
                ViewFieldEnum.REGRESSION__SLOPE, ViewFieldEnum.REGRESSION__YINTERCEPT);
        String outputEventTypeName = statementContext.getStatementId() + "_regview_" + streamNum;
        return statementContext.getEventAdapterService().createAnonymousMapType(outputEventTypeName, eventTypeMap);
    }
}
