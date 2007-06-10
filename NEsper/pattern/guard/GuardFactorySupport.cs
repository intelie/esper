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
		abstract public IList<object> GuardParameters { set ; }
		
		abstract public Guard MakeGuard(PatternContext context, Quitable quitable, object stateNodeId, object guardState);
	}
} // End of namespace
