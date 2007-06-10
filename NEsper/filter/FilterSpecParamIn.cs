///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.collection;
using net.esper.pattern;

namespace net.esper.filter
{
    /// <summary>
	/// This class represents a 'in' filter parameter in an {@link net.esper.filter.FilterSpecCompiled} filter specification.
	/// &lt;p&gt;
	/// The 'in' checks for a list of values.
	/// </summary>
	public sealed class FilterSpecParamIn : FilterSpecParam
	{
	    private readonly List<FilterSpecParamInValue> listOfValues;
	    private MultiKeyUntyped inListConstantsOnly;

	    /// <summary>Ctor.</summary>
	    /// <param name="propertyName">is the event property name</param>
	    /// <param name="filterOperator">is expected to be the IN-list operator</param>
	    /// <param name="listofValues">is a list of constants and event property names</param>
	    /// <throws>ArgumentException for illegal args</throws>
	    public FilterSpecParamIn(String propertyName,
	                             FilterOperator filterOperator,
	                             List<FilterSpecParamInValue> listofValues)
	    	: base(propertyName, filterOperator)
	    {
	        this.listOfValues = listofValues;

	        bool isAllConstants = false;
	        foreach (FilterSpecParamInValue value in listofValues)
	        {
	            if (value is InSetOfValuesEventProp)
	            {
	                isAllConstants = false;
	                break;
	            }
	        }

	        if (isAllConstants)
	        {
	            Object[] constants = new Object[listOfValues.Count];
	            int count = 0;
	            foreach (FilterSpecParamInValue valuePlaceholder in listOfValues)
	            {
	                constants[count++] = valuePlaceholder.GetFilterValue(null);
	            }
	            inListConstantsOnly = new MultiKeyUntyped(constants);
	        }

	        if ((filterOperator != FilterOperator.IN_LIST_OF_VALUES) && ((filterOperator != FilterOperator.NOT_IN_LIST_OF_VALUES)))
	        {
	            throw new ArgumentException("Illegal filter operator " + filterOperator + " supplied to " +
	                    "in-values filter parameter");
	        }
	    }

	    public override Object GetFilterValue(MatchedEventMap matchedEvents)
	    {
	        // If the list of values consists of all-constants and no event properties, then use cached version
	        if (inListConstantsOnly != null)
	        {
	            return inListConstantsOnly;
	        }

	        // Determine actual values since the in-list of values contains one or more event properties
	        Object[] actualValues = new Object[listOfValues.Count];
	        int count = 0;
	        foreach (FilterSpecParamInValue valuePlaceholder in listOfValues)
	        {
	            actualValues[count++] = valuePlaceholder.GetFilterValue(matchedEvents);
	        }
	        return new MultiKeyUntyped(actualValues);
	    }

	    /// <summary>Returns the list of values we are asking to match.</summary>
	    /// <returns>list of filter values</returns>
	    public List<FilterSpecParamInValue> GetListOfValues()
	    {
	        return listOfValues;
	    }

	    public override String ToString()
	    {
	        return baseToString() + "  in=(listOfValues=" + listOfValues.ToString() + ')';
	    }

	    public override bool Equals(Object obj)
	    {
	        if (this == obj)
	        {
	            return true;
	        }

	        if (!(obj is FilterSpecParamIn))
	        {
	            return false;
	        }

	        FilterSpecParamIn other = (FilterSpecParamIn) obj;
	        if (!base.Equals(other))
	        {
	            return false;
	        }

	        if (listOfValues.Count != other.listOfValues.Count)
	        {
	            return false;
	        }

	        if (!(Arrays.DeepEquals(listOfValues.ToArray(), other.listOfValues.ToArray())))
	        {
	            return false;
	        }
	        return true;
	    }
	    
		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
	}
} // End of namespace
