/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.MethodResolutionService;

public class ExprAccessAggNodeFactory implements AggregationMethodFactory
{
    private final AggregationAccessType accessType;
    private final Class resultType;
    private final int streamNum;
    private final ExprEvaluator childNode;
    private final boolean istreamOnly;
    private final boolean ondemandQuery;

    public ExprAccessAggNodeFactory(AggregationAccessType accessType, Class resultType, int streamNum, ExprEvaluator childNode, boolean istreamOnly, boolean ondemandQuery)
    {
        this.accessType = accessType;
        this.resultType = resultType;
        this.streamNum = streamNum;
        this.childNode = childNode;
        this.istreamOnly = istreamOnly;
        this.ondemandQuery = ondemandQuery;
    }

    public Class getResultType()
    {
        return resultType;
    }

    public AggregationSpec getSpec(boolean isMatchRecognize)
    {
        // For match-recognize we don't use the access functions
        if (isMatchRecognize) {
            return null;
        }

        // on-demand query allow window access type
        if (ondemandQuery && accessType == AggregationAccessType.WINDOW) {
            return new AggregationSpec(streamNum);
        }

        // no remove stream, use first-ever and last-ever functions
        if (istreamOnly || ondemandQuery) {
            return null;
        }
        return new AggregationSpec(streamNum);
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        if (accessType == AggregationAccessType.FIRST) {
            return methodResolutionService.makeFirstEverValueAggregator(resultType);
        }
        else if (accessType == AggregationAccessType.LAST) {
            return methodResolutionService.makeLastEverValueAggregator(resultType);
        }
        throw new RuntimeException("Window aggregation function is not available");
    }

    public AggregationAccessor getAccessor()
    {
        if (accessType == AggregationAccessType.FIRST) {
            return new AggregationAccessorFirst(streamNum, childNode);
        }
        else if (accessType == AggregationAccessType.LAST) {
            return new AggregationAccessorLast(streamNum, childNode);
        }
        else if (accessType == AggregationAccessType.WINDOW) {
            return new AggregationAccessorAll(streamNum, childNode);
        }
        throw new IllegalStateException("Access type is undefined or not known as code '" + accessType + "'");
    }
}