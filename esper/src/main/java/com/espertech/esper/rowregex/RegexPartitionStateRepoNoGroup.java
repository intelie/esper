package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;

import java.util.ArrayList;

public class RegexPartitionStateRepoNoGroup implements RegexPartitionStateRepo
{
    private final RegexPartitionState singletonState;
    private final boolean hasInterval;

    public RegexPartitionStateRepoNoGroup(RegexPartitionState singletonState, boolean hasInterval)
    {
        this.singletonState = singletonState;
        this.hasInterval = hasInterval;
    }

    public RegexPartitionStateRepoNoGroup(RegexPartitionStateRandomAccessGetter getter, boolean hasInterval)
    {
        singletonState = new RegexPartitionState(getter, new ArrayList<RegexNFAStateEntry>(), hasInterval);
        this.hasInterval = hasInterval;
    }

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