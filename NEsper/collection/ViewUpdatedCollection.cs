///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.events;

namespace net.esper.collection
{


	/// <summary>
	/// A general-purpose collection interface for collections updated by view data.
	/// &lt;p&gt;
	/// Views post delta-data in terms of new data (insert stream) events and old data (remove stream) event that
	/// leave a window.
	/// </summary>
	public interface ViewUpdatedCollection
	{
	    /// <summary>Accepts view insert and remove stream.</summary>
	    /// <param name="newData">is the insert stream events or null if no data</param>
	    /// <param name="oldData">is the remove stream events or null if no data</param>
	    void Update(EventBean[] newData, EventBean[] oldData);
	}
} // End of namespace
