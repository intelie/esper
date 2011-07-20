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

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

import com.espertech.esper.collection.MultiKeyUntyped;

public class TestFilterSpecParamIn extends TestCase
{
    private FilterSpecParamIn values;

    public void testEquals()
    {
        values = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, getList(new Object[] {"A", "B"}));
        FilterSpecParamIn values2 = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, getList(new Object[] {"A"}));
        FilterSpecParamIn values3 = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, getList(new Object[] {"A", "B"}));
        FilterSpecParamIn values4 = new FilterSpecParamIn("a", FilterOperator.IN_LIST_OF_VALUES, getList(new Object[] {"A", "C"}));

        assertFalse(values.equals(new FilterSpecParamConstant("a", FilterOperator.EQUAL, "a")));
        assertFalse(values.equals(values2));
        assertTrue(values.equals(values3));
        assertFalse(values.equals(values4));
    }

    private List<FilterSpecParamInValue> getList(Object[] keys)
    {
        List<FilterSpecParamInValue> list = new LinkedList<FilterSpecParamInValue>();
        for (int i = 0; i < keys.length; i++)
        {
            list.add(new InSetOfValuesConstant(keys[i]));
        }
        return list;
    }
}
