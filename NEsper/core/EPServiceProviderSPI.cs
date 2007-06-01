///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.client;
using net.esper.events;
using net.esper.filter;
using net.esper.schedule;

namespace net.esper.core
{
	/// <summary>
	/// A service provider interface that makes available internal engine services.
	/// </summary>
	public interface EPServiceProviderSPI : EPServiceProvider
	{
	    /// <summary>Get the EventAdapterService for this engine.</summary>
	    /// <returns>the EventAdapterService</returns>
        EventAdapterService EventAdapterService { get; }

	    /// <summary>Get the SchedulingService for this engine.</summary>
	    /// <returns>the SchedulingService</returns>
        SchedulingService SchedulingService { get; }

	    /// <summary>Returns the filter service.</summary>
	    /// <returns>filter service</returns>
        FilterService FilterService { get ; }

	    /// <summary>
	    /// Returns the engine environment context for engine-external resources such as adapters.
	    /// </summary>
	    /// <returns>engine environment context</returns>
        Context EnvContext { get; }
	}
} // End of namespace
