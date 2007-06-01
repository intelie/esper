///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.events
{
	/// <summary>SPI for events for internal use.</summary>
	public interface EventBeanSPI : EventBean
	{
	    /// <summary>Returns the event id object.</summary>
	    /// <returns>event id</returns>
	    Object GetEventBeanId();
	}
} // End of namespace
