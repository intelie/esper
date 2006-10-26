package net.esper.schedule;

import junit.framework.TestCase;
import net.esper.support.schedule.SupportScheduleCallback;

import java.util.Calendar;

public class TestSchedulingServiceImpl extends TestCase
{
    private SchedulingServiceImpl service;

    private ScheduleSlot slots[][];
    private SupportScheduleCallback callbacks[];

    public void setUp()
    {
        service = new SchedulingServiceImpl();

        // 2-by-2 table of buckets and slots
        ScheduleBucket[] buckets = new ScheduleBucket[3];
        slots = new ScheduleSlot[buckets.length][2];
        for (int i = 0; i < buckets.length; i++)
        {
            buckets[i] = service.allocateBucket();
            slots[i] = new ScheduleSlot[2];
            for (int j = 0; j < slots[i].length; j++)
            {
                slots[i][j] = buckets[i].allocateSlot();
            }
        }

        callbacks = new SupportScheduleCallback[5];
        for (int i= 0; i < callbacks.length; i++)
        {
            callbacks[i] = new SupportScheduleCallback();
        }
    }

    public void testTrigger()
    {
        long startTime = 0;

        service.setTime(0);

        // Add callbacks
        service.add(20, callbacks[3], slots[1][1]);
        service.add(20, callbacks[2], slots[1][0]);
        service.add(20, callbacks[1], slots[0][1]);
        service.add(21, callbacks[0], slots[0][0]);

        // Evaluate before the within time, expect not results
        startTime += 19;
        service.setTime(startTime);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        // Evaluate exactly on the within time, expect a result
        startTime += 1;
        service.setTime(startTime);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 1, 2, 3, 0});

        // Evaluate after already evaluated once, no result
        startTime += 1;
        service.setTime(startTime);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {4, 0, 0, 0, 0});

        startTime += 1;
        service.setTime(startTime);
        service.evaluate();
        assertEquals(0, callbacks[3].clearAndGetOrderTriggered());

        // Adding the same callback more than once should cause an exception
        service.add(20, callbacks[0], slots[0][0]);
        try
        {
            service.add(28, callbacks[0], slots[0][0]);
            fail();
        }
        catch (ScheduleServiceException ex)
        {
            // Expected exception
        }
        service.remove(callbacks[0], slots[0][0]);

        service.add(20, callbacks[2], slots[1][0]);
        service.add(25, callbacks[1], slots[0][1]);
        service.remove(callbacks[1], slots[0][1]);
        service.add(21, callbacks[0], slots[0][0]);
        service.add(21, callbacks[3], slots[1][1]);
        service.add(20, callbacks[1], slots[0][1]);
        SupportScheduleCallback.setCallbackOrderNum(0);

        startTime += 20;
        service.setTime(startTime);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 1, 2, 0, 0});

        startTime += 1;
        service.setTime(startTime);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {3, 0, 0, 4, 0});

        service.setTime(startTime + Integer.MAX_VALUE);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});
    }

    public void testWaitAndSpecTogether()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2004, 11, 9, 15, 27, 10);
        calendar.set(Calendar.MILLISECOND, 500);
        long startTime = calendar.getTimeInMillis();

        service.setTime(startTime);

        // Add a specification
        ScheduleSpec spec = new ScheduleSpec();
        spec.addValue(ScheduleUnit.MONTHS, 12);
        spec.addValue(ScheduleUnit.DAYS_OF_MONTH, 9);
        spec.addValue(ScheduleUnit.HOURS, 15);
        spec.addValue(ScheduleUnit.MINUTES, 27);
        spec.addValue(ScheduleUnit.SECONDS, 20);

        service.add(spec, callbacks[3], slots[1][1]);

        spec.addValue(ScheduleUnit.SECONDS, 15);
        service.add(spec, callbacks[4], slots[2][0]);

        // Add some more callbacks
        service.add(5000, callbacks[0], slots[0][0]);
        service.add(10000, callbacks[1], slots[0][1]);
        service.add(15000, callbacks[2], slots[1][0]);

        // Now send a times reflecting various seconds later and check who got a callback
        service.setTime(startTime + 1000);
        SupportScheduleCallback.setCallbackOrderNum(0);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        service.setTime(startTime + 2000);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        service.setTime(startTime + 4000);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        service.setTime(startTime + 5000);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {1, 0, 0, 0, 2});

        service.setTime(startTime + 9000);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        service.setTime(startTime + 10000);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 3, 0, 4, 0});

        service.setTime(startTime + 11000);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});

        service.setTime(startTime + 15000);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 5, 0, 0});

        service.setTime(startTime + Integer.MAX_VALUE);
        service.evaluate();
        checkCallbacks(callbacks, new Integer[] {0, 0, 0, 0, 0});
    }

    public void testIncorrectRemove()
    {
        SchedulingServiceImpl evaluator = new SchedulingServiceImpl();
        SupportScheduleCallback callback = new SupportScheduleCallback();

        try
        {
            evaluator.remove(callback, null);
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
            assertEquals((int) results[i], (int) callbacks[i].clearAndGetOrderTriggered());
        }
    }

}
