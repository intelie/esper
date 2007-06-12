// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;

namespace net.esper.client
{
	/// <summary>
    /// This class provides access to the EPRuntime and EPAdministrator implementations.
    /// </summary>

    public interface EPServiceProvider
	{
		/// <summary> Returns a class instance of EPRuntime.</summary>
		/// <returns> an instance of EPRuntime
		/// </returns>

        EPRuntime EPRuntime { get; }

		/// <summary> Returns a class instance of EPAdministrator.</summary>
		/// <returns> an instance of EPAdministrator
		/// </returns>

        EPAdministrator EPAdministrator { get; }

		/// <summary> Frees any resources associated with this runtime instance.
		/// Stops and destroys any event filters, patterns, expressions, views.
		/// </summary>

        void Initialize();

		/// <summary>Returns the provider URI, or null if this is the default provider.</summary>
		/// <returns>provider URI</returns>
		String URI { get ; }
	}
}
