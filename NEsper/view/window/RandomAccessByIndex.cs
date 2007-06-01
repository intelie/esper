///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.events;

namespace net.esper.view.window
{


	/// <summary>
	/// Random access interface to insert stream and remove stream data based on an index.
	/// </summary>
	public interface RandomAccessByIndex
	{
	    /// <summary>Returns an new data event given an index.</summary>
	    /// <param name="index">to return new data for</param>
	    /// <returns>new data event</returns>
	    EventBean GetNewData(int index);

	    /// <summary>Returns an old data event given an index.</summary>
	    /// <param name="index">to return old data for</param>
	    /// <returns>old data event</returns>
	    EventBean GetOldData(int index);
	}
} // End of namespace
