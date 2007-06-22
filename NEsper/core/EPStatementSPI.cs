///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.dispatch;
using net.esper.events;
using net.esper.view;

namespace net.esper.core
{
	/// <summary>
	/// Statement SPI for statements operations for state transitions and internal management.
	/// </summary>
	public interface EPStatementSPI : EPStatement
	{
	    /// <summary>Returns the statement id.</summary>
	    /// <returns>statement id</returns>
	    String StatementId
		{
			get;
		}

	    /// <summary>Set statement state.</summary>
	    EPStatementState CurrentState
		{
			set ;
		}

	    /// <summary>Sets the parent view.</summary>
	    Viewable ParentView
		{
			set ;
		}
	}
} // End of namespace
