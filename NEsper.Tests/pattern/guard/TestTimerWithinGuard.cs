///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.core;
using net.esper.compat;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.support.guard;
using net.esper.support.schedule;
using net.esper.support.view;

namespace net.esper.pattern.guard
{
	[TestFixture]
	public class TestTimerWithinGuard
	{
	    private TimerWithinGuard guard;
	    private SchedulingService scheduleService;
	    private SupportQuitable quitable;

	    [SetUp]
	    public void SetUp()
	    {
	        StatementContext stmtContext = SupportStatementContextFactory.MakeContext(new SchedulingServiceImpl());
	        PatternContext context = new PatternContext(stmtContext, 1, null);
	        scheduleService = stmtContext.SchedulingService;

	        quitable = new SupportQuitable();

	        guard =  new TimerWithinGuard(1000, context, quitable);
	    }

	    [Test]
	    public void testInspect()
	    {
	        Assert.IsTrue(guard.Inspect(null));
	    }

	    /**
	     * Make sure the timer calls guardQuit after the set time period
	     */
	    [Test]
	    public void testStartAndTrigger()
	    {
	        scheduleService.Time = (0);

	        guard.StartGuard();

	        Assert.AreEqual(0, quitable.GetAndResetQuitCounter());

	        scheduleService.Time = (1000);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);

	        Assert.AreEqual(1, quitable.GetAndResetQuitCounter());
	    }

	    [Test]
	    public void testStartAndStop()
	    {
	        scheduleService.Time = (0);

	        guard.StartGuard();

	        guard.StopGuard();

	        scheduleService.Time = (1001);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);

	        Assert.AreEqual(0, quitable.GetAndResetQuitCounter());
	    }

	    [Test]
	    public void testInvalid()
	    {
	        try
	        {
	            guard.StartGuard();
	            guard.StartGuard();
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected exception
	        }
	    }
	}
} // End of namespace
