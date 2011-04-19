/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.lookup;

import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.util.JavaClassHelper;

import java.util.List;

/**
 * Index lookup strategy that coerces the key values before performing a lookup.
 */
public class SubordIndexedTableLookupStrategyCoercing extends SubordIndexedTableLookupStrategyExpr
{
    private Class[] coercionTypes;

    public SubordIndexedTableLookupStrategyCoercing(boolean isNWOnTrigger, int numStreamsOuter, List<SubordPropHashKey> hashKeys, PropertyIndexedEventTable index, Class[] coercionTypes) {
        super(isNWOnTrigger, numStreamsOuter, hashKeys, index);
        this.coercionTypes = coercionTypes;
    }

    protected Object[] getKeys(EventBean[] eventsPerStream, ExprEvaluatorContext context) {
        Object[] keys = super.getKeys(eventsPerStream, context);
        for (int i = 0; i < keys.length; i++)
        {
            Object value = keys[i];

            Class coercionType = coercionTypes[i];
            if ((value != null) && (!value.getClass().equals(coercionType)))
            {
                if (value instanceof Number)
                {
                    value = JavaClassHelper.coerceBoxed((Number) value, coercionTypes[i]);
                }
                keys[i] = value;
            }
        }
        return keys;
    }
}
