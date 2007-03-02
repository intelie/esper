using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.filter;

namespace net.esper.support.filter
{
    public class SupportFilterSpecBuilder
    {
        public static FilterSpec build(EventType eventType, Object[] objects)
        {
            return new FilterSpec(eventType, buildList(objects));
        }

        public static IList<FilterSpecParam> buildList(Object[] objects)
        {
            IList<FilterSpecParam> filterParams = new List<FilterSpecParam>();

            int index = 0;
            while (objects.Length > index)
            {
                String propertyName = (String)objects[index++];
                FilterOperator filterOperator = (FilterOperator)objects[index++];

                if (!FilterOperatorHelper.IsRangeOperator(filterOperator))
                {
                    Object filterForConstant = objects[index++];
                    filterParams.Add(new FilterSpecParamConstant(propertyName, filterOperator, filterForConstant));
                }
                else
                {
                    double min = Convert.ToDouble(((ValueType)objects[index++]));
                    double max = Convert.ToDouble(((ValueType)objects[index++]));
                    filterParams.Add(new FilterSpecParamRange(propertyName, filterOperator, new RangeValueDouble(min), new RangeValueDouble(max)));
                }
            }

            return filterParams;
        }
    }
}
