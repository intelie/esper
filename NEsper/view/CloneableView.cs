///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using net.esper.core;

namespace net.esper.view
{
	/// <summary>
	/// Views that can work under a group-by must be able to duplicate and are required to implement this interface.
	/// </summary>
	public interface CloneableView
	{
	    /// <summary>
	    /// Duplicates the view.
	    /// <p>
	    /// Expected to return a same view in initialized state for grouping.
	    /// </p>
	    /// </summary>
	    /// <param name="statementContext">is services for the view</param>
	    /// <returns>duplicated view</returns>
	    View CloneView(StatementContext statementContext);
	}
} // End of namespace
