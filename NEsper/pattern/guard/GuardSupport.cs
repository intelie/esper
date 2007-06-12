///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.pattern.guard
{
	/// <summary>
	/// Abstract class for applications to extend to implement a pattern guard.
	/// </summary>
	public abstract class GuardSupport : Guard
	{
        /// <summary>
        /// Start the guard operation.
        /// </summary>
		abstract public void StartGuard();

        /// <summary>
        /// Called when sub-expression quits, or when the pattern Stopped.
        /// </summary>
		abstract public void StopGuard();

        /// <summary>
        /// Returns true if inspection shows that the match events can pass, or false to not pass.
        /// </summary>
        /// <param name="matchEvent">is the map of matching events</param>
        /// <returns>true to pass, false to not pass</returns>
		abstract public bool Inspect(MatchedEventMap matchEvent);
	}
} // End of namespace
