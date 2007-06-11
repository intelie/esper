///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.eql.spec;

namespace net.esper.view
{
	/// <summary>
	/// Factory service for resolving view names and for creating view instances based on a view specification including view name and namespace.
	/// </summary>
	public interface ViewResolutionService
	{
	    /// <summary>
	    /// Instantiates a {@link ViewFactory} based on the view namespace and name stored in the view spec.
	    /// <para>
	    /// Does not actually use the view factory object created.
	    /// </para>
	    /// </summary>
	    /// <param name="spec">contains view name and namespace</param>
	    /// <returns>{@link ViewFactory} instance</returns>
	    /// <throws>ViewProcessingException if the view namespace or name cannot resolve</throws>
	    ViewFactory Create(ViewSpec spec);
	}
}
