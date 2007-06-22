// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;

using net.esper.events;

namespace net.esper.client
{
    /// <summary>
    /// Defines an interface to notify of new and old events.
    /// </summary>

    public interface UpdateListener
    {
        void Update(EventBean[] newEvents, EventBean[] oldEvents);
    }

    /// <summary>
    /// Defines a delegate that is notified of new and old events.
    /// </summary>
    /// <param name="newEvents"></param>
    /// <param name="oldEvents"></param>
	
    public delegate void UpdateEventHandler(EventBean[] newEvents, EventBean[] oldEvents);
}