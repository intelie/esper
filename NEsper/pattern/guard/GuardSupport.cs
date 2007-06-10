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
		abstract public void StartGuard();
		
		abstract public void StopGuard();
		
		abstract public bool Inspect(MatchedEventMap matchEvent);
	}
} // End of namespace
