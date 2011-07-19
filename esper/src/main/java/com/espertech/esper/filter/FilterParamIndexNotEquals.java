/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Index for filter parameter constants to match using the equals (=) operator.
 * The implementation is based on a regular HashMap.
 */
public final class FilterParamIndexNotEquals extends FilterParamIndexNotEqualsBase
{
    public FilterParamIndexNotEquals(String propertyName, EventType eventType) {
        super(propertyName, FilterOperator.NOT_EQUAL, eventType);
    }

    public final void matchEvent(EventBean eventBean, Collection<FilterHandle> matches, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object attributeValue = this.getGetter().get(eventBean);
        if (attributeValue == null) {   // null cannot match any other value, not even null (use "is" or "is not", i.e. null != null returns null)
            return;
        }

        // Look up in hashtable
        constantsMapRWLock.readLock().lock();

        for(Map.Entry<Object, EventEvaluator> entry : constantsMap.entrySet())
        {
            if (entry.getKey() == null)
            {
                continue;   // null-value cannot match, not even null (use "is" or "is not", i.e. null != null returns null)
            }

            if (!entry.getKey().equals(attributeValue))
            {
                entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
            }
        }

        constantsMapRWLock.readLock().unlock();
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexNotEquals.class);
}
