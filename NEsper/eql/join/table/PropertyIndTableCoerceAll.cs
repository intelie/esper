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
using net.esper.compat;

using net.esper.events;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.join.table
{
	/// <summary>
	/// Index that organizes events by the event property values into hash buckets. Based on a HashMap
	/// with {@link net.esper.collection.MultiKeyUntyped} keys that store the property values.
	/// <p>
	/// Performs coercion of the index keys before storing the keys, and coercion of the lookup keys before lookup.
	/// <p>
	/// Takes a list of property names as parameter. Doesn't care which event type the events have as long as the properties
	/// exist. If the same event is added twice, the class throws an exception on add.
	/// </summary>
	public class PropertyIndTableCoerceAll : PropertyIndTableCoerceAdd
	{
	    private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	    
	    private readonly Type[] coercionTypes;

	    /// <summary>Ctor.</summary>
	    /// <param name="streamNum">is the stream number of the indexed stream</param>
	    /// <param name="eventType">is the event type of the indexed stream</param>
	    /// <param name="propertyNames">are the property names to get property values</param>
	    /// <param name="coercionType">are the classes to coerce indexed values to</param>
	    public PropertyIndTableCoerceAll(int streamNum, EventType eventType, String[] propertyNames, Type[] coercionType)
	        : base(streamNum, eventType, propertyNames, coercionType)
	    {
	        this.coercionTypes = coercionType;
	    }

	    /// <summary>
	    /// Returns the set of events that have the same property value as the given event.
	    /// </summary>
	    /// <param name="keys">to compare against</param>
	    /// <returns>
	    /// set of events with property value, or null if none found (never returns zero-sized set)
	    /// </returns>
	    public override Set<EventBean> Lookup(Object[] keys)
	    {
	        for (int i = 0; i < keys.Length; i++)
	        {
	            Type coercionType = coercionTypes[i];
	            Object key = keys[i];
	            if ((key != null) && (!key.GetType() == coercionType))
	            {
	                if (key is Number)
	                {
	                    key = TypeHelper.CoerceBoxed((Number) key, coercionTypes[i]);
	                    keys[i] = key;
	                }
	            }
	        }
	        
	        MultiKeyUntyped _key = new MultiKeyUntyped(keys);
	        Set<EventBean> events = propertyIndex.Get(_key);
	        return events;
	    }

	}
} // End of namespace
