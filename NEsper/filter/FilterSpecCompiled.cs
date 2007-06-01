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
using net.esper.pattern;

namespace net.esper.filter
{
	/// <summary>
	/// Contains the filter criteria to sift through events. The filter criteria are the event class to look for and
	/// a set of parameters (attribute names, operators and constant/range values).
	/// </summary>
	public sealed class FilterSpecCompiled
	{
	    private readonly EventType eventType;
	    private readonly List<FilterSpecParam> parameters;

	    /// <summary>
	    /// Constructor - validates parameter list against event type, throws exception if invalid
	    /// property names or mismatcing filter operators are found.
	    /// </summary>
	    /// <param name="eventType">is the event type</param>
	    /// <param name="parameters">is a list of filter parameters</param>
	    /// <throws>IllegalArgumentException if validation invalid</throws>
	    public FilterSpecCompiled(EventType eventType, List<FilterSpecParam> parameters)
	    {
	        this.eventType = eventType;
	        this.parameters = parameters;
	    }

	    /// <summary>Returns type of event to filter for.</summary>
	    /// <returns>event type</returns>
	    public EventType EventType
	    {
            get { return eventType; }
	    }

	    /// <summary>Returns list of filter parameters.</summary>
	    /// <returns>list of filter params</returns>
	    public List<FilterSpecParam> Parameters
	    {
            get { return parameters; }
	    }

	    /// <summary>
	    /// Returns the values for the filter, using the supplied result events to ask filter parameters
	    /// for the value to filter for.
	    /// </summary>
	    /// <param name="matchedEvents">
	    /// contains the result events to use for determining filter values
	    /// </param>
	    /// <returns>filter values</returns>
	    public FilterValueSet GetValueSet(MatchedEventMap matchedEvents)
	    {
	        List<FilterValueSetParam> valueList = new LinkedList<FilterValueSetParam>();

	        // Ask each filter specification parameter for the actual value to filter for
	        foreach (FilterSpecParam specParam in parameters)
	        {
	            Object filterForValue = specParam.GetFilterValue(matchedEvents);

	            FilterValueSetParam valueParam = new FilterValueSetParamImpl(specParam.PropertyName,
	                    specParam.FilterOperator, filterForValue);
	            valueList.Add(valueParam);
	        }
	        return new FilterValueSetImpl(eventType, valueList);
	    }

	    public override String ToString()
	    {
	        StringBuilder buffer = new StringBuilder();
	        buffer.Append("FilterSpecCompiled type=" + this.eventType);
	        buffer.Append(" parameters=" + Arrays.ToString(parameters.ToArray()));
	        return buffer.ToString();
	    }

	    public override bool Equals(Object obj)
	    {
	        if (this == obj)
	        {
	            return true;
	        }

	        if (!(obj is FilterSpecCompiled))
	        {
	            return false;
	        }

	        FilterSpecCompiled other = (FilterSpecCompiled) obj;

	        if (this.eventType != other.eventType)
	        {
	            return false;
	        }
	        if (this.parameters.Size() != other.parameters.Size())
	        {
	            return false;
	        }

	        IEnumerator<FilterSpecParam> iterOne = parameters.GetEnumerator();
            IEnumerator<FilterSpecParam> iterOther = other.parameters.GetEnumerator();
            while (iterOne.MoveNext())
            {
                if (!iterOther.MoveNext())
                {
                    return false;
                }
                else if (Object.Equals(iterOne.Current, iterOther.Current))
                {
                    return false;
                }
            }

	        return true;
	    }

	    public override int GetHashCode()
	    {
	        int hashCode = eventType.GetHashCode();
	        foreach (FilterSpecParam param in parameters)
	        {
	            hashCode = hashCode ^ param.PropertyName.GetHashCode();
	        }
	        return hashCode;
	    }
	}
} // End of namespace
