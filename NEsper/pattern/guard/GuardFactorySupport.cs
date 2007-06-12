///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.pattern.guard
{
	/// <summary>
	/// Abstract class for applications to extend to implement pattern guard objects.
	/// </summary>
	public abstract class GuardFactorySupport : GuardFactory
	{
        /// <summary>
        /// Sets the guard object parameters.
        /// </summary>
        /// <value></value>
        /// <throws>GuardParameterException thrown to indicate a parameter problem</throws>
		abstract public IList<object> GuardParameters { set ; }

        /// <summary>
        /// Constructs a guard instance.
        /// </summary>
        /// <param name="context">services for use by guard</param>
        /// <param name="quitable">to use for indicating the guard has quit</param>
        /// <param name="stateNodeId">a node id for the state object</param>
        /// <param name="guardState">state node for guard</param>
        /// <returns>guard instance</returns>
		abstract public Guard MakeGuard(PatternContext context, Quitable quitable, object stateNodeId, object guardState);
	}
} // End of namespace
