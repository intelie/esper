// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.pattern;
using net.esper.pattern.guard;

namespace net.esper.regression.client
{
	public class MyCountToPatternGuardFactory : GuardFactorySupport
	{
	    private int numCountTo;

		public override IList<object> GuardParameters
		{
			set {
		        if (value.Count != 1)
		        {
		            throw new GuardParameterException("Count-to guard takes a single integer parameter");
		        }		        
		        if (!(value[0] is int))
		        {
		            throw new GuardParameterException("Count-to guard takes a single integer parameter");
		        }
		        numCountTo = Convert.ToInt32(value[0]);
		    }
	    }

	    public override Guard MakeGuard(PatternContext context, Quitable quitable, Object stateNodeId, Object guardState)
	    {
	        return new MyCountToPatternGuard(numCountTo, quitable);
	    }
	}
} // End of namespace
