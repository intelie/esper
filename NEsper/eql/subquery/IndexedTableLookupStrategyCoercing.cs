///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.eql.join.table;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.subquery
{
    /// <summary>
    /// Index lookup strategy that coerces the key values before performing a lookup.
    /// </summary>
    public class IndexedTableLookupStrategyCoercing : IndexedTableLookupStrategy
    {
        private Type[] coercionTypes;

        /// <summary>Ctor.</summary>
        /// <param name="eventTypes">is the event type per stream</param>
        /// <param name="streamNumbers">is the stream numbers to get keys from</param>
        /// <param name="properties">is the property names</param>
        /// <param name="index">is the table to look into</param>
        /// <param name="coercionTypes">is the types to coerce to before lookup</param>
        public IndexedTableLookupStrategyCoercing(EventType[] eventTypes, int[] streamNumbers, String[] properties, PropertyIndexedEventTable index, Type[] coercionTypes)
            : base(eventTypes, streamNumbers, properties, index)
        {
            this.coercionTypes = coercionTypes;
        }

        protected override Object[] GetKeys(EventBean[] eventsPerStream)
        {
            Object[] keyValues = new Object[propertyGetters.Length];
            for (int i = 0; i < propertyGetters.Length; i++)
            {
                int streamNum = streamNumbers[i];
                EventBean _event = eventsPerStream[streamNum];
                Object value = propertyGetters[i].Get(_event);

                Type coercionType = coercionTypes[i];
                if ((value != null) && (value.GetType() != coercionType))
                {
                    if (value is Number)
                    {
                        value = TypeHelper.CoerceBoxed((Number)value, coercionTypes[i]);
                    }
                }

                keyValues[i] = value;
            }
            return keyValues;
        }
    }
}
