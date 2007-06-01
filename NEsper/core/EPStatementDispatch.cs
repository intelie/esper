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
	/// Interface for statement-level dispatch.
	/// <p>
	/// Relevant when a statements callbacks have completed and the join processing must take place.
	/// </summary>
	public interface EPStatementDispatch
	{
	    /// <summary>Execute dispatch.</summary>
	    void Execute();
	}
} // End of namespace
