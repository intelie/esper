///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.events;

namespace net.esper.filter
{
	/// <summary>
	/// Interface for a callback method to be called when an event matches a filter specification. Provided
	/// as a convenience for use as a filter handle for registering with the {@link FilterService}.
	/// </summary>

	public interface FilterHandleCallback : FilterHandle
	{
	    /// <summary>
	    /// Indicate that an event was evaluated by the {@link net.esper.filter.FilterService}
	    /// which matches the filter specification {@link net.esper.filter.FilterSpecCompiled} associated with this callback.
	    /// </summary>
	    /// <param name="_event">the event received that matches the filter specification</param>
	    void MatchFound(EventBean _event);
	}
} // End of namespace
