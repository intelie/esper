///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.filter;
using net.esper.support.events;

namespace net.esper.support.filter
{
	public class SupportFilterSpecBuilder
	{
	    public static FilterSpecCompiled Build(EventType eventType, Object[] objects)
	    {
	        String eventTypeId = SupportEventAdapterService.GetService().GetIdByType(eventType);
	        String eventTypeAlias = SupportEventAdapterService.GetService().GetAliasById(eventTypeId);
	        return new FilterSpecCompiled(eventType, BuildList(objects));
	    }

	    public static FilterSpecCompiled Build(String eventTypeId, String eventTypeAlias, Object[] objects)
	    {
	        EventType eventType = SupportEventAdapterService.GetService().GetTypeById(eventTypeId);
	        return new FilterSpecCompiled(eventType, BuildList(objects));
	    }

	    public static List<FilterSpecParam> BuildList(Object[] objects)
	    {
	        List<FilterSpecParam> filterParams = new List<FilterSpecParam>();

	        int index = 0;
	        while (objects.Length > index)
	        {
	            String propertyName = (String) objects[index++];
	            FilterOperator filterOperator = (FilterOperator) objects[index++];

	            if (!FilterOperatorHelper.IsRangeOperator(filterOperator))
	            {
	                Object filterForConstant = objects[index++];
	                filterParams.Add(new FilterSpecParamConstant(propertyName, filterOperator, filterForConstant));
	            }
	            else
	            {
	                double min = Convert.ToDouble(objects[index++]);
	                double max = Convert.ToDouble(objects[index++]);
	                filterParams.Add(new FilterSpecParamRange(propertyName, filterOperator,
	                        new RangeValueDouble(min),
	                        new RangeValueDouble(max)));
	            }
	        }

	        return filterParams;
	    }
	}

} // End of namespace
