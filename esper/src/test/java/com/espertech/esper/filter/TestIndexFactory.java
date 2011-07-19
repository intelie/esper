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

package com.espertech.esper.filter;

import junit.framework.TestCase;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.client.EventType;

public class TestIndexFactory extends TestCase
{
    EventType eventType;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public void testCreateIndex()
    {
        // Create a "greater" index
        FilterParamIndexBase index = IndexFactory.createIndex(eventType, "intPrimitive", FilterOperator.GREATER);

        assertTrue(index != null);
        assertTrue(index instanceof FilterParamIndexCompare);
        assertTrue(((FilterParamIndexCompare)index).getPropertyName().equals("intPrimitive"));
        assertTrue(index.getFilterOperator() == FilterOperator.GREATER);

        // Create an "equals" index
        index = IndexFactory.createIndex(eventType, "string", FilterOperator.EQUAL);

        assertTrue(index != null);
        assertTrue(index instanceof FilterParamIndexEquals);
        assertTrue(((FilterParamIndexEquals)index).getPropertyName().equals("string"));
        assertTrue(index.getFilterOperator() == FilterOperator.EQUAL);

        // Create an "not equals" index
        index = IndexFactory.createIndex(eventType, "string", FilterOperator.NOT_EQUAL);

        assertTrue(index != null);
        assertTrue(index instanceof FilterParamIndexNotEquals);
        assertTrue(((FilterParamIndexNotEquals)index).getPropertyName().equals("string"));
        assertTrue(index.getFilterOperator() == FilterOperator.NOT_EQUAL);

        // Create a range index
        index = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.RANGE_CLOSED);
        assertTrue(index instanceof FilterParamIndexDoubleRange);
        index = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.NOT_RANGE_CLOSED);
        assertTrue(index instanceof FilterParamIndexDoubleRangeInverted);

        // Create a in-index
        index = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.IN_LIST_OF_VALUES);
        assertTrue(index instanceof FilterParamIndexIn);
        index = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.NOT_IN_LIST_OF_VALUES);
        assertTrue(index instanceof FilterParamIndexNotIn);

        // Create a boolean-expression-index
        index = IndexFactory.createIndex(eventType, "boolean", FilterOperator.BOOLEAN_EXPRESSION);
        assertTrue(index instanceof FilterParamIndexBooleanExpr);
        index = IndexFactory.createIndex(eventType, "boolean", FilterOperator.BOOLEAN_EXPRESSION);
        assertTrue(index instanceof FilterParamIndexBooleanExpr);
    }
}

