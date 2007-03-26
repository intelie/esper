// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.client
{
	/// <summary>
    /// Interface to iterate over events.
    /// </summary>
    
    public interface EPIterable : IEnumerable<EventBean>
    {
        /// <summary> Returns the type of events the iterable returns.</summary>
        /// <returns> event type of events the iterator returns
        /// </returns>

        EventType EventType { get; }
    }
}