package net.esper.support.filter;

import net.esper.filter.*;
import net.esper.event.EventType;

import java.util.List;
import java.util.LinkedList;

public class SupportFilterSpecBuilder
{
    public static FilterSpec build(EventType eventType, Object[] objects)
    {
        return new FilterSpec(eventType, buildList(objects));
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

