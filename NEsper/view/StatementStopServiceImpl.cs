///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.view
{
	/// <summary>
	/// Provides subscription list for statement stop callbacks.
	/// </summary>
	public class StatementStopServiceImpl : StatementStopService
	{
	    private IList<StatementStopCallback> statementStopCallbacks;

	    /// <summary>ctor.</summary>
	    public StatementStopServiceImpl()
	    {
	        statementStopCallbacks = new List<StatementStopCallback>();
	    }

        /// <summary>
        /// Add a callback to perform for a stop of a statement.
        /// </summary>
        /// <param name="callback">is the callback function</param>
	    public void AddSubscriber(StatementStopCallback callback)
	    {
	        statementStopCallbacks.Add(callback);
	    }

        /// <summary>
        /// Used by the engine to indicate a statement stopped, invoking any callbacks registered.
        /// </summary>
	    public void FireStatementStopped()
	    {
	        foreach (StatementStopCallback statementStopCallback in statementStopCallbacks)
	        {
	            statementStopCallback();
	        }
	    }
	}
} // End of namespace
