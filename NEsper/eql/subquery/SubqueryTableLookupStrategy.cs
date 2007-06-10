///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.subquery
{
    /// <summary>
    /// Strategy for looking up, in some sort of table or index, or a set of events, potentially based on the
    /// events properties, and returning a set of matched events.
    /// </summary>
    public interface SubqueryTableLookupStrategy
    {
        /// <summary>
        /// Returns matched events for a set of events to look up for. Never returns an empty result set,
        /// always returns null to indicate no results.
        /// </summary>
        /// <param name="events">to look up</param>
        /// <returns>set of matching events, or null if none matching</returns>
        Set<EventBean> Lookup(EventBean[] events);
    }
}
