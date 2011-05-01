/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.lookup;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTableSingle;
import com.espertech.esper.event.EventBeanUtility;

/**
 * Index lookup strategy that coerces the key values before performing a lookup.
 */
public class SubordIndexedTableLookupStrategySingleCoercing extends SubordIndexedTableLookupStrategySingleExpr
{
    private Class coercionType;

    /**
     * Ctor.
     * @param index is the table to look into
     */
    public SubordIndexedTableLookupStrategySingleCoercing(boolean isNWOnTrigger, int streamCountOuter, SubordPropHashKey hashKey, PropertyIndexedEventTableSingle index, Class coercionType) {
        super(isNWOnTrigger, streamCountOuter, hashKey, index);
        this.coercionType = coercionType;
    }

    protected Object getKey(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        Object key = super.getKey(eventsPerStream, context);
        return EventBeanUtility.coerce(key, coercionType);
    }
}
