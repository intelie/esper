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

import com.espertech.esper.client.EventType;
import com.espertech.esper.filter.*;

import java.util.LinkedList;
import java.util.List;

public class SupportFilterSpecBuilder
{
    public static FilterSpecCompiled build(EventType eventType, Object[] objects)
    {
        return new FilterSpecCompiled(eventType, "SomeAliasNameForType", buildList(objects), null);
    }

    public static List<FilterSpecParam> buildList(Object[] objects)
    {
        List<FilterSpecParam> filterParams = new LinkedList<FilterSpecParam>();

        int index = 0;
        while (objects.length > index)
        {
            String propertyName = (String) objects[index++];
            FilterOperator filterOperator = (FilterOperator) objects[index++];

            if (!(filterOperator.isRangeOperator()))
            {
                Object filterForConstant = objects[index++];
                filterParams.add(new FilterSpecParamConstant(propertyName, filterOperator, filterForConstant));
            }
            else
            {
                double min = ((Number) objects[index++]).doubleValue();
                double max = ((Number) objects[index++]).doubleValue();
                filterParams.add(new FilterSpecParamRange(propertyName, filterOperator,
                        new RangeValueDouble(min),
                        new RangeValueDouble(max), Double.class));
            }
        }

        return filterParams;
    }
}


