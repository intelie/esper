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
	/// <summary>
    /// Event type SPI for internal use.
    /// </summary>
	
    public interface EventTypeSPI : EventType
	{
	    /// <summary>Returns the type's event type id.</summary>
	    /// <returns>type id</returns>
        String EventTypeId { get; }
	}
} // End of namespace
