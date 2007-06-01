///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.eql.join.table;
using net.esper.events;

namespace net.esper.eql.subquery
{
    /// <summary>Index lookup strategy for subqueries.</summary>
    public class IndexedTableLookupStrategy : SubqueryTableLookupStrategy
    {
        private readonly String[] properties;

        /// <summary>Stream numbers to get key values from.</summary>
        protected readonly int[] streamNumbers;

        /// <summary>Index to look up in.</summary>
        protected readonly PropertyIndexedEventTable index;

        /// <summary>Getters to use to get key values.</summary>
        protected readonly EventPropertyGetter[] propertyGetters;

        /// <summary>Ctor.</summary>
        /// <param name="eventTypes">is the event types per stream</param>
        /// <param name="streamNumbers">is the stream number per stream</param>
        /// <param name="properties">is the key properties</param>
        /// <param name="index">is the table carrying the data to lookup into</param>
        public IndexedTableLookupStrategy(EventType[] eventTypes, int[] streamNumbers, String[] properties, PropertyIndexedEventTable index)
        {
            this.streamNumbers = streamNumbers;
            this.properties = properties;
            this.index = index;

            propertyGetters = new EventPropertyGetter[properties.length];
            for (int i = 0; i < streamNumbers.length; i++)
            {
                int streamNumber = streamNumbers[i];
                String property = properties[i];
                EventType eventType = eventTypes[streamNumber];
                propertyGetters[i] = eventType.GetGetter(property);

                if (propertyGetters[i] == null)
                {
                    throw new IllegalArgumentException("Property named '" + properties[i] + "' is invalid for type " + eventType);
                }
            }
        }

        /// <summary>Returns properties to use from lookup event to look up in index.</summary>
        /// <returns>properties to use from lookup event</returns>
        public String[] GetProperties()
        {
            return properties;
        }

        /// <summary>Returns index to look up in.</summary>
        /// <returns>index to use</returns>
        public PropertyIndexedEventTable GetIndex()
        {
            return index;
        }

        public Set<EventBean> Lookup(EventBean[] eventsPerStream)
        {
            Object[] keys = GetKeys(eventsPerStream);
            return index.Lookup(keys);
        }

        /// <summary>Get the index lookup keys.</summary>
        /// <param name="eventsPerStream">is the events for each stream</param>
        /// <returns>key object</returns>
        protected Object[] GetKeys(EventBean[] eventsPerStream)
        {
            Object[] keyValues = new Object[propertyGetters.length];
            for (int i = 0; i < propertyGetters.length; i++)
            {
                int streamNum = streamNumbers[i];
                EventBean _event = eventsPerStream[streamNum];
                keyValues[i] = propertyGetters[i].Get(_event);
            }
            return keyValues;
        }

        public override String ToString()
        {
            return "IndexedTableLookupStrategy indexProps=" + Arrays.ToString(properties) +
                    " index=(" + index + ')';
        }
    }
} // End of namespace
