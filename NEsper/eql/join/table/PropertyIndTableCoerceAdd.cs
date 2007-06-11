///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.collection;
using net.esper.events;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.join.table
{
	/// <summary>
	/// Index that organizes events by the event property values into hash buckets. Based on a HashMap
	/// with {@link net.esper.collection.MultiKeyUntyped} keys that store the property values.
	/// <p>
	/// Performs coercion of the index keys before storing the keys.
	/// <p>
	/// Takes a list of property names as parameter. Doesn't care which event type the events have as long as the properties
	/// exist. If the same event is added twice, the class throws an exception on add.
	/// </summary>
	public class PropertyIndTableCoerceAdd : PropertyIndexedEventTable
	{
	    private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	    private readonly Type[] coercionTypes;

	    /// <summary>Ctor.</summary>
	    /// <param name="streamNum">is the stream number of the indexed stream</param>
	    /// <param name="eventType">is the event type of the indexed stream</param>
	    /// <param name="propertyNames">are the property names to get property values</param>
	    /// <param name="coercionType">are the classes to coerce indexed values to</param>
	    public PropertyIndTableCoerceAdd(int streamNum, EventType eventType, String[] propertyNames, Type[] coercionType)
			: base(streamNum, eventType, propertyNames)
	    {
	        this.coercionTypes = coercionType;
	    }

	    protected override MultiKeyUntyped GetMultiKey(EventBean _event)
	    {
	        Object[] keyValues = new Object[propertyGetters.Length];
	        for (int i = 0; i < propertyGetters.Length; i++)
	        {
                Object value = propertyGetters[i].GetValue(_event);
                Type coercionType = coercionTypes[i];
	            if ((value != null) && (value.GetType() != coercionType))
	            {
	                if (TypeHelper.IsNumericValue(value))
	                {
	                    value = TypeHelper.CoerceBoxed(value, coercionTypes[i]);
	                }
	            }
	            keyValues[i] = value;
	        }
	        return new MultiKeyUntyped(keyValues);
	    }
	}
} // End of namespace
