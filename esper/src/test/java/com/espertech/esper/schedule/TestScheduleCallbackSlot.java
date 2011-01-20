package com.espertech.esper.schedule;

import junit.framework.TestCase;

public class TestScheduleCallbackSlot extends TestCase
{
    public void testCompare()
    {
        ScheduleSlot slots[] = new ScheduleSlot[10];
        slots[0] = new ScheduleSlot(1, 1);
        slots[1] = new ScheduleSlot(1, 2);
        slots[2] = new ScheduleSlot(2, 1);
        slots[3] = new ScheduleSlot(2, 2);

        assertEquals(-1, slots[0].compareTo(slots[1]));
        assertEquals(1, slots[1].compareTo(slots[0]));
        assertEquals(0, slots[0].compareTo(slots[0]));

        assertEquals(-1, slots[0].compareTo(slots[2]));
        assertEquals(-1, slots[1].compareTo(slots[2]));
        assertEquals(1, slots[2].compareTo(slots[0]));
        assertEquals(1, slots[2].compareTo(slots[1]));
    }
}
