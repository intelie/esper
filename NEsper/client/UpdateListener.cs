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
	
    public delegate void UpdateListener(EventBean[] newEvents, EventBean[] oldEvents);
}