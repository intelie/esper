package com.espertech.esper.support.filter;

import com.espertech.esper.event.EventType;
import com.espertech.esper.filter.*;

import java.util.LinkedList;
import java.util.List;

public class SupportFilterSpecBuilder
{
    public static FilterSpecCompiled build(EventType eventType, Object[] objects)
    {
        return new FilterSpecCompiled(eventType, buildList(objects));
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
                        new RangeValueDouble(max)));
            }
        }

        return filterParams;
    }
}

