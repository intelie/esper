///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.compat;
using NUnit.Framework;

using net.esper.support.schedule;

namespace net.esper.schedule
{
	[TestFixture]
	public class TestSchedulingServiceImpl
	{
	    private SchedulingServiceImpl service;

	    private ScheduleSlot[,] slots;
	    private SupportScheduleCallback[] callbacks;

	    private const int numSlotsPerBucket = 2;

	    [SetUp]
	    public void SetUp()
	    {
	        service = new SchedulingServiceImpl();

	        // 2-by-2 table of buckets and slots
	        ScheduleBucket[] buckets = new ScheduleBucket[3];
	        slots = new ScheduleSlot[buckets.Length,numSlotsPerBucket];
	        for (int i = 0; i < buckets.Length; i++)
	        {
	            buckets[i] = service.AllocateBucket();
	            //slots[i] = new ScheduleSlot[2];
                for (int j = 0; j < numSlotsPerBucket; j++)
	            {
	                slots[i,j] = buckets[i].AllocateSlot();
	            }
	        }

	        callbacks = new SupportScheduleCallback[5];
	        for (int i= 0; i < callbacks.Length; i++)
	        {
	            callbacks[i] = new SupportScheduleCallback();
	        }
	    }

	    [Test]
	    public void testAddTwice()
	    {
	        service.Add(100, callbacks[0], slots[0,0]);
	        try
	        {
	            service.Add(100, callbacks[0], slots[0,0]);
	            Assert.Fail();
	        }
	        catch (ScheduleHandleExistsException ex)
	        {
	            // expected
	        }

	        service.Add(new ScheduleSpec(), callbacks[1], slots[0,0]);
	        try
	        {
	            service.Add(new ScheduleSpec(), callbacks[1], slots[0,0]);
	            Assert.Fail();
	        }
	        catch (ScheduleHandleExistsException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testTrigger()
	    {
	        long startTime = 0;

            service.Time = (0);

	        // Add callbacks
	        service.Add(20, callbacks[3], slots[1,1]);
	        service.Add(20, callbacks[2], slots[1,0]);
	        service.Add(20, callbacks[1], slots[0,1]);
	        service.Add(21, callbacks[0], slots[0,0]);

	        // Evaluate before the within time, expect not results
	        startTime += 19;
            service.Time = (startTime);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});

	        // Evaluate exactly on the within time, expect a result
	        startTime += 1;
            service.Time = (startTime);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 1, 2, 3, 0});

	        // Evaluate after already evaluated once, no result
	        startTime += 1;
            service.Time = (startTime);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {4, 0, 0, 0, 0});

	        startTime += 1;
            service.Time = (startTime);
	        EvaluateSchedule();
	        Assert.AreEqual(0, callbacks[3].ClearAndGetOrderTriggered());

	        // Adding the same callback more than once should cause an exception
	        service.Add(20, callbacks[0], slots[0,0]);
	        try
	        {
	            service.Add(28, callbacks[0], slots[0,0]);
	            Assert.Fail();
	        }
	        catch (ScheduleServiceException ex)
	        {
	            // Expected exception
	        }
	        service.Remove(callbacks[0], slots[0,0]);

	        service.Add(20, callbacks[2], slots[1,0]);
	        service.Add(25, callbacks[1], slots[0,1]);
	        service.Remove(callbacks[1], slots[0,1]);
	        service.Add(21, callbacks[0], slots[0,0]);
	        service.Add(21, callbacks[3], slots[1,1]);
	        service.Add(20, callbacks[1], slots[0,1]);
	        SupportScheduleCallback.SetCallbackOrderNum(0);

	        startTime += 20;
            service.Time = (startTime);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 1, 2, 0, 0});

	        startTime += 1;
            service.Time = (startTime);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {3, 0, 0, 4, 0});

            service.Time = (startTime + Int32.MaxValue);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});
	    }

	    [Test]
	    public void testWaitAndSpecTogether()
	    {
	        DateTime calendar = new DateTime(2004, 11, 9, 15, 27, 10, 500);

	        long startTime = DateTimeHelper.TimeInMillis(calendar);

	        service.Time = startTime;
                
	        // Add a specification
	        ScheduleSpec spec = new ScheduleSpec();
	        spec.AddValue(ScheduleUnit.MONTHS, 12);
	        spec.AddValue(ScheduleUnit.DAYS_OF_MONTH, 9);
	        spec.AddValue(ScheduleUnit.HOURS, 15);
	        spec.AddValue(ScheduleUnit.MINUTES, 27);
	        spec.AddValue(ScheduleUnit.SECONDS, 20);

	        service.Add(spec, callbacks[3], slots[1,1]);

	        spec.AddValue(ScheduleUnit.SECONDS, 15);
	        service.Add(spec, callbacks[4], slots[2,0]);

	        // Add some more callbacks
	        service.Add(5000, callbacks[0], slots[0,0]);
	        service.Add(10000, callbacks[1], slots[0,1]);
	        service.Add(15000, callbacks[2], slots[1,0]);

	        // Now send a times reflecting various seconds later and check who got a callback
	        service.Time = (startTime + 1000);
	        SupportScheduleCallback.SetCallbackOrderNum(0);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});

	        service.Time = (startTime + 2000);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});

	        service.Time = (startTime + 4000);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});

	        service.Time = (startTime + 5000);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {1, 0, 0, 0, 2});

	        service.Time = (startTime + 9000);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});

            service.Time = (startTime + 10000);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 3, 0, 4, 0});

            service.Time = (startTime + 11000);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});

            service.Time = (startTime + 15000);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 5, 0, 0});

            service.Time = (startTime + Int32.MaxValue);
	        EvaluateSchedule();
	        CheckCallbacks(callbacks, new int[] {0, 0, 0, 0, 0});
	    }

	    [Test]
	    public void testIncorrectRemove()
	    {
	        SchedulingServiceImpl evaluator = new SchedulingServiceImpl();
	        SupportScheduleCallback callback = new SupportScheduleCallback();
	        evaluator.Remove(callback, null);
	    }

	    private void CheckCallbacks(SupportScheduleCallback[] callbacks, int[] results)
	    {
	        Assert.IsTrue(callbacks.Length == results.Length);

	        for (int i = 0; i < callbacks.Length; i++)
	        {
	            Assert.AreEqual((int) results[i], (int) callbacks[i].ClearAndGetOrderTriggered());
	        }
	    }

	    private void EvaluateSchedule()
	    {
	        IList<ScheduleHandle> handles = new List<ScheduleHandle>();
	        service.Evaluate(handles);

	        foreach (ScheduleHandle handle in handles)
	        {
	            ScheduleHandleCallback cb = (ScheduleHandleCallback) handle;
	            cb.ScheduledTrigger(null);
	        }
	    }
	}
} // End of namespace
