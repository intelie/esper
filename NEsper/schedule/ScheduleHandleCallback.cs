///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.core;

namespace net.esper.schedule
{
	/// <summary>Interface for scheduled callbacks.</summary>
	public interface ScheduleHandleCallback : ScheduleHandle
	{
	    /// <summary>
	    /// Callback that is invoked as indicated by a schedule added to the scheduling service.
	    /// </summary>
	    /// <param name="extensionServicesContext">
	    /// is a marker interface for providing custom extension services
	    /// passed to the triggered class
	    /// </param>
	    void ScheduledTrigger(ExtensionServicesContext extensionServicesContext);
	}
} // End of namespace
