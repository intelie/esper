/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;

import java.util.ArrayList;

/**
 * State for when no partitions (single partition) is required.
 */
public class RegexPartitionStateRepoNoGroup implements RegexPartitionStateRepo
{
    private final RegexPartitionState singletonState;
    private final boolean hasInterval;

    /**
     * Ctor.
     * @param singletonState state
     * @param hasInterval true for interval
     */
    public RegexPartitionStateRepoNoGroup(RegexPartitionState singletonState, boolean hasInterval)
    {
        this.singletonState = singletonState;
        this.hasInterval = hasInterval;
    }

    /**
     * Ctor.
     * @param getter "prev" getter
     * @param hasInterval true for interval
     */
    public RegexPartitionStateRepoNoGroup(RegexPartitionStateRandomAccessGetter getter, boolean hasInterval)
    {
        singletonState = new RegexPartitionState(getter, new ArrayList<RegexNFAStateEntry>(), hasInterval);
        this.hasInterval = hasInterval;
    }

    public void removeState(MultiKeyUntyped partitionKey) {
        // not an operation
    }

    /**
     * Copy state for iteration.
     * @return copy
     */
    public RegexPartitionStateRepo copyForIterate()
    {
        RegexPartitionState state = new RegexPartitionState(singletonState.getRandomAccess(), null, hasInterval);
        return new RegexPartitionStateRepoNoGroup(state, hasInterval);
    }

    public void removeOld(EventBean[] oldEvents, boolean isEmpty, boolean[] found)
    {
        if (isEmpty)
        {
            singletonState.getCurrentStates().clear();
        }
        else
        {
            for (EventBean oldEvent : oldEvents)
            {
                singletonState.removeEventFromState(oldEvent);
            }
        }
        singletonState.removeEventFromPrev(oldEvents);
    }

    public RegexPartitionState getState(EventBean event, boolean collect)
    {
        return singletonState;
    }

    public RegexPartitionState getState(MultiKeyUntyped key)
    {
        return singletonState;
    }
}