package net.esper.schedule;

import junit.framework.TestCase;

import java.util.Calendar;

import net.esper.support.schedule.SupportScheduleCallback;

public class TestSchedulingServiceImpl extends TestCase
{
    public void testTrigger()
    {
        SchedulingServiceImpl evaluator = new SchedulingServiceImpl();

        long startTime = 0;

        SupportScheduleCallback callbackOne = new SupportScheduleCallback();
        SupportScheduleCallback callbackTwo = new SupportScheduleCallback();
        SupportScheduleCallback callbackThree = new SupportScheduleCallback();

        evaluator.setTime(0);

        // Add 3 callbacks
        evaluator.add(20, callbackOne);
        evaluator.add(20, callbackTwo);
        evaluator.add(21, callbackThree);

        // Evaluate before the within time, expect not results
        startTime += 19;
        evaluator.setTime(startTime);
        evaluator.evaluate();
        assertTrue(callbackOne.clearAndGetCount() == 0);
        assertTrue(callbackTwo.clearAndGetCount() == 0);

        // Evaluate exactly on the within time, expect a result
        startTime += 1;
        evaluator.setTime(startTime);
        evaluator.evaluate();
        assertTrue(callbackOne.clearAndGetCount() == 1);
        assertTrue(callbackTwo.clearAndGetCount() == 1);
        assertTrue(callbackThree.clearAndGetCount() == 0);

        // Evaluate after already evaluated once, no result
        startTime += 1;
        evaluator.setTime(startTime);
        evaluator.evaluate();
        assertTrue(callbackOne.clearAndGetCount() == 0);
        assertTrue(callbackTwo.clearAndGetCount() == 0);
        assertTrue(callbackThree.clearAndGetCount() == 1);

        startTime += 1;
        evaluator.setTime(startTime);
        evaluator.evaluate();
        assertTrue(callbackThree.clearAndGetCount() == 0);

        // Add some more callbacks

        // Adding the same callback more than once should cause an exception
        evaluator.add(20, callbackOne);
        try
        {
            evaluator.add(28, callbackOne);
        }
        catch (ScheduleServiceException ex)
        {
            // Expected exception
        }

        evaluator.add(20, callbackTwo);
        evaluator.add(25, callbackThree);
        evaluator.remove(callbackThree);
        evaluator.add(21, callbackThree);

        startTime += 20;
        evaluator.setTime(startTime);
        evaluator.evaluate();
        assertTrue(callbackOne.clearAndGetCount() == 1);
        assertTrue(callbackTwo.clearAndGetCount() == 1);
        assertTrue(callbackThree.clearAndGetCount() == 0);

        startTime += 1;
        evaluator.setTime(startTime);
        evaluator.evaluate();
        assertTrue(callbackOne.clearAndGetCount() == 0);
        assertTrue(callbackTwo.clearAndGetCount() == 0);
        assertTrue(callbackThree.clearAndGetCount() == 1);

        evaluator.setTime(startTime + Integer.MAX_VALUE);
        evaluator.evaluate();
        assertTrue(callbackOne.clearAndGetCount() == 0);
        assertTrue(callbackTwo.clearAndGetCount() == 0);
        assertTrue(callbackThree.clearAndGetCount() == 0);
    }

    public void testWaitAndSpecTogether()
    {
        SchedulingServiceImpl evaluator = new SchedulingServiceImpl();

        SupportScheduleCallback callbacks[] = new SupportScheduleCallback[5];
        for (int i = 0; i < callbacks.length; i++)
        {
            callbacks[i] = new SupportScheduleCallback();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(2004, 11, 9, 15, 27, 10);
        calendar.set(Calendar.MILLISECOND, 500);
        long startTime = calendar.getTimeInMillis();

        evaluator.setTime(startTime);

        // Add a callback
        evaluator.add(5000, callbacks[0]);
        evaluator.add(10000, callbacks[1]);
        evaluator.add(15000, callbacks[2]);

        // Add a specification
        ScheduleSpec spec = new ScheduleSpec();
        spec.addValue(ScheduleUnit.MONTHS, 12);
        spec.addValue(ScheduleUnit.DAYS_OF_MONTH, 9);
        spec.addValue(ScheduleUnit.HOURS, 15);
        spec.addValue(ScheduleUnit.MINUTES, 27);
        spec.addValue(ScheduleUnit.SECONDS, 20);

        evaluator.add(spec, callbacks[3]);

        spec.addValue(ScheduleUnit.SECONDS, 15);
        evaluator.add(spec, callbacks[4]);

        // Now send a times reflecting various seconds later and check who got a callback
        evaluator.setTime(startTime + 1000);
        evaluator.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        evaluator.setTime(startTime + 2000);
        evaluator.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        evaluator.setTime(startTime + 4000);
        evaluator.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        evaluator.setTime(startTime + 5000);
        evaluator.evaluate();
        checkCallbacks(callbacks, new Integer[] {1, 0, 0, 0, 1});

        evaluator.setTime(startTime + 9000);
        evaluator.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        evaluator.setTime(startTime + 10000);
        evaluator.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 1, 0, 1, 0});

        evaluator.setTime(startTime + 11000);
        evaluator.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});
    }

    public void testIncorrectRemove()
    {
        SchedulingServiceImpl evaluator = new SchedulingServiceImpl();
        SupportScheduleCallback callback = new SupportScheduleCallback();

        try
        {
            evaluator.remove(callback);
            assertTrue(false);
        }
        catch (ScheduleServiceException ex)
        {
            // Expected exception
        }
    }

    private void checkCallbacks(SupportScheduleCallback callbacks[], Integer[] results)
    {
        assertTrue(callbacks.length == results.length);

        for (int i = 0; i < callbacks.length; i++)
        {
            assertTrue(callbacks[i].clearAndGetCount() == results[i]);
        }
    }

}
