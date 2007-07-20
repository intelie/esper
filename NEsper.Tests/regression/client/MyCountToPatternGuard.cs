// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using net.esper.pattern.guard;
using net.esper.pattern;

namespace net.esper.regression.client
{
	public class MyCountToPatternGuard : GuardSupport
	{
	    private readonly int numCountTo;
	    private readonly Quitable quitable;

	    private int counter;

	    public MyCountToPatternGuard(int numCountTo, Quitable quitable)
	    {
	        this.numCountTo = numCountTo;
	        this.quitable = quitable;
	    }

	    public override void StartGuard()
	    {
	        counter = 0;
	    }

	    public override void StopGuard()
	    {
	        // No action required when a sub-expression quits, or when the pattern is stopped
	    }

	    public override bool Inspect(MatchedEventMap matchEvent)
	    {
	        counter++;
	        if (counter > numCountTo)
	        {
	            quitable.GuardQuit();
	            return false;
	        }
	        return true;
	    }
	}
} // End of namespace
