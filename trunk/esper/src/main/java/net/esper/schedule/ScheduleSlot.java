/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.schedule;

import net.esper.util.MetaDefItem;

/**
 * This class is a slot in a {@link ScheduleBucket} for sorting schedule service callbacks.
 */
public class ScheduleSlot implements Comparable<ScheduleSlot>, MetaDefItem
{
    private int bucketNum;
    private int slotNum;

    /**
     * Ctor.
     * @param bucketNum is the number of the bucket the slot belongs to
     * @param slotNum is the slot number for ordering within the bucket
     */
    public ScheduleSlot(int bucketNum, int slotNum)
    {
        this.bucketNum = bucketNum;
        this.slotNum = slotNum;
    }

    public int compareTo(ScheduleSlot scheduleCallbackSlot)
    {
        if (this.bucketNum > scheduleCallbackSlot.bucketNum)
        {
            return 1;
        }
        if (this.bucketNum < scheduleCallbackSlot.bucketNum)
        {
            return -1;
        }
        if (this.slotNum > scheduleCallbackSlot.slotNum)
        {
            return 1;
        }
        if (this.slotNum < scheduleCallbackSlot.slotNum)
        {
            return -1;
        }

        return 0;
    }
}