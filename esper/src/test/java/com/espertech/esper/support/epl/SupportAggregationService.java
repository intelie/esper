/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.epl;

import com.espertech.esper.epl.agg.AggregationService;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.collection.MultiKeyUntyped;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

public class SupportAggregationService implements AggregationService
{
    private List<Pair<EventBean[], MultiKeyUntyped>> leaveList = new LinkedList<Pair<EventBean[], MultiKeyUntyped>>();
    private List<Pair<EventBean[], MultiKeyUntyped>> enterList = new LinkedList<Pair<EventBean[], MultiKeyUntyped>>();

    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow, ExprEvaluatorContext exprEvaluatorContext)
    {
        enterList.add(new Pair<EventBean[], MultiKeyUntyped>(eventsPerStream, optionalGroupKeyPerRow));
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow, ExprEvaluatorContext exprEvaluatorContext)
    {
        leaveList.add(new Pair<EventBean[], MultiKeyUntyped>(eventsPerStream, optionalGroupKeyPerRow));
    }

    public List<Pair<EventBean[], MultiKeyUntyped>> getLeaveList()
    {
        return leaveList;
    }

    public List<Pair<EventBean[], MultiKeyUntyped>> getEnterList()
    {
        return enterList;
    }

    public void setCurrentAccess(MultiKeyUntyped groupKey)
    {
    }

    public Object getValue(int column)
    {
        return null;
    }

    public Collection<EventBean> getCollection(int column) {
        return null;
    }

    public void clearResults()
    {
    }

    public EventBean getEventBean(int column) {
        return null;
    }
}
