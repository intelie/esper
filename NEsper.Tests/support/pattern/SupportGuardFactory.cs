// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.pattern.guard;
using net.esper.pattern;

namespace net.esper.support.pattern
{
	public class SupportGuardFactory : GuardFactory
	{
	    public Guard MakeGuard(PatternContext context, Quitable quitable, Object stateObjectId, Object guardState)
	    {
	        return null;  //To change body of implemented methods use File | Settings | File Templates.
	    }
		
		public virtual IList<object> GuardParameters {
	    	set {}
		}
	}
} // End of namespace
