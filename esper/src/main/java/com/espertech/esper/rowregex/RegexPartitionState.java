package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;

import java.util.ArrayList;
import java.util.List;

public class RegexPartitionState
{
    private RegexPartitionStateRandomAccessImpl randomAccess;
    private List<RegexNFAStateEntry> currentStates = new ArrayList<RegexNFAStateEntry>();
    private MultiKeyUntyped optionalKeys;
    private List<RegexNFAStateEntry> intervalCallbackItems;
    private boolean isCallbackScheduled;

    public RegexPartitionState(RegexPartitionStateRandomAccessImpl randomAccess, MultiKeyUntyped optionalKeys, boolean hasInterval)
    {
        this.randomAccess = randomAccess;
        this.optionalKeys = optionalKeys;

        if (hasInterval)
        {
            intervalCallbackItems = new ArrayList<RegexNFAStateEntry>();
        }
    }

    public RegexPartitionState(RegexPartitionStateRandomAccessGetter getter,
                               List<RegexNFAStateEntry> currentStates,
                               boolean hasInterval) {
        this(getter, currentStates, null, hasInterval);
    }

    public RegexPartitionState(RegexPartitionStateRandomAccessGetter getter, List<RegexNFAStateEntry> currentStates, MultiKeyUntyped optionalKeys, boolean hasInterval) {
        if (getter != null)
        {
            randomAccess = new RegexPartitionStateRandomAccessImpl(getter);
        }
        this.currentStates = currentStates;
        this.optionalKeys = optionalKeys;

        if (hasInterval)
        {
            intervalCallbackItems = new ArrayList<RegexNFAStateEntry>();
        }
    }

    public RegexPartitionStateRandomAccessImpl getRandomAccess() {
        return randomAccess;
    }

    public List<RegexNFAStateEntry> getCurrentStates() {
        return currentStates;
    }

    public void setCurrentStates(List<RegexNFAStateEntry> currentStates) {
        this.currentStates = currentStates;
    }

    public MultiKeyUntyped getOptionalKeys() {
        return optionalKeys;
    }

    public void removeEventFromPrev(EventBean[] oldEvents)
    {
        if (randomAccess != null)
        {
            randomAccess.remove(oldEvents);
        }
    }

    public void removeEventFromPrev(EventBean oldEvent)
    {
        if (randomAccess != null)
        {
            randomAccess.remove(oldEvent);
        }
    }

    public boolean removeEventFromState(EventBean oldEvent)
    {
        List<RegexNFAStateEntry> keepList = new ArrayList<RegexNFAStateEntry>();

        for (RegexNFAStateEntry entry : currentStates)
        {
            boolean keep = true;

            EventBean[] state = entry.getEventsPerStream();
            for (EventBean aState : state)
            {
                if (aState == oldEvent)
                {
                    keep = false;
                    break;
                }
            }

            if (keep)
            {
                MultimatchState[] multimatch = entry.getOptionalMultiMatches();
                if (multimatch != null)
                {
                    for (MultimatchState aMultimatch : multimatch)
                    {
                        if ((aMultimatch != null) && (aMultimatch.containsEvent(oldEvent)))
                        {
                            keep = false;
                            break;
                        }
                    }
                }
            }

            if (keep)
            {
                keepList.add(entry);
            }
        }

        if (randomAccess != null)
        {
            randomAccess.remove(oldEvent);
        }

        currentStates = keepList;
        return keepList.isEmpty();
    }

    public List<RegexNFAStateEntry> getCallbackItems()
    {
        return intervalCallbackItems;
    }

    public boolean isCallbackScheduled()
    {
        return isCallbackScheduled;
    }

    public void setCallbackScheduled(boolean callbackScheduled)
    {
        isCallbackScheduled = callbackScheduled;
    }

    public void addCallbackItem(RegexNFAStateEntry endState)
    {
        intervalCallbackItems.add(endState);
    }
}
