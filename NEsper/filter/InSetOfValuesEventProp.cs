///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.events;
using net.esper.pattern;
using net.esper.util;

namespace net.esper.filter
{
	/// <summary>Event property value in a list of values following an in-keyword.</summary>
	public class InSetOfValuesEventProp : FilterSpecParamInValue
	{
	    private readonly String resultEventAsName;
	    private readonly String resultEventProperty;
	    private readonly bool isMustCoerce;
        private readonly Type coercionType;

	    /// <summary>Ctor.</summary>
	    /// <param name="resultEventAsName">is the event tag</param>
	    /// <param name="resultEventProperty">is the event property name</param>
	    /// <param name="isMustCoerce">
	    /// indicates on whether numeric coercion must be performed
	    /// </param>
	    /// <param name="coercionType">indicates the numeric coercion type to use</param>
	    public InSetOfValuesEventProp(String resultEventAsName, String resultEventProperty, bool isMustCoerce, Type coercionType)
	    {
	        this.resultEventAsName = resultEventAsName;
	        this.resultEventProperty = resultEventProperty;
	        this.coercionType = coercionType;
	        this.isMustCoerce = isMustCoerce;
	    }

	    public Object GetFilterValue(MatchedEventMap matchedEvents)
	    {
	        EventBean _event = matchedEvents.GetMatchingEvent(resultEventAsName);
	        if (_event == null)
	        {
	            throw new IllegalStateException("Matching event named " +
	                    '\'' + resultEventAsName + "' not found in event result set");
	        }

	        Object value = _event.Get(resultEventProperty);

	        // Coerce if necessary
	        if (isMustCoerce)
	        {
	            value = TypeHelper.CoerceBoxed((Number) value, coercionType);
	        }
	        return value;
	    }

	    /// <summary>Returns the tag used for the event property.</summary>
	    /// <returns>tag</returns>
	    public String GetResultEventAsName()
	    {
	        return resultEventAsName;
	    }

	    /// <summary>Returns the event property name.</summary>
	    /// <returns>property name</returns>
	    public String GetResultEventProperty()
	    {
	        return resultEventProperty;
	    }

	    public override String ToString()
	    {
	        return "resultEventProp=" + resultEventAsName + '.' + resultEventProperty;
	    }

	    public override bool Equals(Object obj)
	    {
	        if (this == obj)
	        {
	            return true;
	        }

	        if (!(obj is InSetOfValuesEventProp))
	        {
	            return false;
	        }

	        InSetOfValuesEventProp other = (InSetOfValuesEventProp) obj;
	        if ( (other.resultEventAsName.Equals(this.resultEventAsName)) &&
	             (other.resultEventProperty.Equals(this.resultEventProperty)))
	        {
	            return true;
	        }

	        return false;
	    }
	}
} // End of namespace
