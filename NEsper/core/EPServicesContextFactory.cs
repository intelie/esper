///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.core
{
	/// <summary>
	/// Interface for a factory class to provide services in a services context for an engine instance.
	/// </summary>
	public interface EPServicesContextFactory
	{
	    /// <summary>Factory method for a new set of engine services.</summary>
	    /// <param name="engineURI">
	    /// is the URI for the engine or null if this is the default engine
	    /// </param>
	    /// <param name="configurationSnapshot">
	    /// is a snapshot of configs at the time of engine creation
	    /// </param>
	    /// <returns>services context</returns>
	    EPServicesContext CreateServicesContext(String engineURI, ConfigurationSnapshot configurationSnapshot);
	}
} // End of namespace
