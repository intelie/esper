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

package com.espertech.esper.support.filter;

import com.espertech.esper.filter.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.Collection;

public class SupportFilterParamIndex extends FilterParamIndexPropBase
{
    public SupportFilterParamIndex()
    {
        super("intPrimitive", FilterOperator.EQUAL, SupportEventTypeFactory.createBeanType(SupportBean.class));
    }

    protected EventEvaluator get(Object expressionValue)
    {
        return null;
    }

    protected void put(Object expressionValue, EventEvaluator evaluator)
    {
    }

    protected boolean remove(Object expressionValue)
    {
        return true;
    }

    protected int size()
    {
        return 0;
    }

    protected ReadWriteLock getReadWriteLock()
    {
        return null;
    }

    public void matchEvent(EventBean event, Collection<FilterHandle> matches, ExprEvaluatorContext exprEvaluatorContext)
    {
    }
}
