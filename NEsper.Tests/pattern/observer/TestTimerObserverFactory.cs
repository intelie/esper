///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.pattern;
using net.esper.pattern.observer;
using net.esper.schedule;
using net.esper.support.pattern;

namespace net.esper.pattern.observer
{
	[TestFixture]
	public class TestTimerObserverFactory
	{
	    private PatternContext patternContext;

	    [SetUp]
	    public void SetUp()
	    {
	        patternContext = SupportPatternContextFactory.MakeContext();
	    }

	    [Test]
	    public void testIntervalWait()
	    {
	        TimerIntervalObserverFactory factory = new TimerIntervalObserverFactory();
	        factory.ObserverParameters = new Object[] {1};
	        EventObserver eventObserver = factory.MakeObserver(patternContext, null, null, null, null);

	        Assert.IsTrue(eventObserver is TimerIntervalObserver);
	    }
	}
} // End of namespace
