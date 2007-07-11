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
using net.esper.support.events;
using net.esper.support.guard;
using net.esper.support.schedule;
using net.esper.support.view;

namespace net.esper.pattern.observer
{
	[TestFixture]
	public class TestTimerIntervalObserver
	{
	    private PatternContext context;
	    private TimerIntervalObserver observer;
	    private SchedulingServiceImpl scheduleService;
	    private SupportObserverEvaluator evaluator;
	    private MatchedEventMap beginState;

	    [SetUp]
	    public void SetUp()
	    {

	        beginState = new MatchedEventMapImpl();

	        scheduleService = new SchedulingServiceImpl();
	        StatementContext stmtContext = SupportStatementContextFactory.MakeContext(scheduleService);
	        context = new PatternContext(stmtContext, 1, null);

	        evaluator = new SupportObserverEvaluator();

	        observer =  new TimerIntervalObserver(1000, context, beginState, evaluator);
	    }

	    [Test]
	    public void testStartAndObserve()
	    {
	        scheduleService.Time = (0);
	        observer.StartObserve();
	        scheduleService.Time = (1000);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);
	        Assert.AreEqual(beginState, evaluator.GetAndClearMatchEvents()[0]);

	        // Test start again
	        observer.StartObserve();
	        scheduleService.Time = (1999);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);
	        Assert.AreEqual(0, evaluator.GetMatchEvents().Count);

	        scheduleService.Time = (2000);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);
	        Assert.AreEqual(beginState, evaluator.GetAndClearMatchEvents()[0]);
	    }

	    [Test]
	    public void testStartAndStop()
	    {
	        // Start then stop
	        scheduleService.Time = (0);
	        observer.StartObserve();
	        observer.StopObserve();
	        scheduleService.Time = (1000);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);
	        Assert.AreEqual(0, evaluator.GetAndClearMatchEvents().Count);

	        // Test start again
	        observer.StartObserve();
	        scheduleService.Time = (2500);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);
	        Assert.AreEqual(beginState, evaluator.GetAndClearMatchEvents()[0]);

	        observer.StopObserve();
	        observer.StartObserve();

	        scheduleService.Time = (3500);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);
	        Assert.AreEqual(beginState, evaluator.GetAndClearMatchEvents()[0]);
	    }

	    [Test]
	    public void testImmediateTrigger()
	    {
	        // Should fireStatementStopped right away, wait time set to zero
	        observer =  new TimerIntervalObserver(0, context, beginState, evaluator);

	        scheduleService.Time = (0);
	        observer.StartObserve();
	        Assert.AreEqual(beginState, evaluator.GetAndClearMatchEvents()[0]);
	        scheduleService.Time = (10000000);
	        SupportSchedulingServiceImpl.EvaluateSchedule(scheduleService);
	        Assert.AreEqual(0, evaluator.GetAndClearMatchEvents().Count);
	    }

	    [Test]
	    public void testInvalid()
	    {
	        try
	        {
	            observer.StartObserve();
	            observer.StartObserve();
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected exception
	        }
	    }

	}
} // End of namespace
