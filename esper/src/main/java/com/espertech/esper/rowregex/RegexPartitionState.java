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
import java.util.List;

/**
 * All current state holding partial NFA matches.
 */
public class RegexPartitionState
{
    private RegexPartitionStateRandomAccessImpl randomAccess;
    private List<RegexNFAStateEntry> currentStates = new ArrayList<RegexNFAStateEntry>();
    private MultiKeyUntyped optionalKeys;
    private List<RegexNFAStateEntry> intervalCallbackItems;
    private boolean isCallbackScheduled;

    /**
     * Ctor.
     * @param randomAccess for handling "prev" functions, if any
     * @param optionalKeys keys for "partition", if any
     * @param hasInterval true if an interval is provided
     */
    public RegexPartitionState(RegexPartitionStateRandomAccessImpl randomAccess, MultiKeyUntyped optionalKeys, boolean hasInterval)
    {
        this.randomAccess = randomAccess;
        this.optionalKeys = optionalKeys;

        if (hasInterval)
        {
            intervalCallbackItems = new ArrayList<RegexNFAStateEntry>();
        }
    }

    /**
     * Ctor.
     * @param getter for "prev" access
     * @param currentStates existing state
     * @param hasInterval true for interval
     */
    public RegexPartitionState(RegexPartitionStateRandomAccessGetter getter,
                               List<RegexNFAStateEntry> currentStates,
                               boolean hasInterval) {
        this(getter, currentStates, null, hasInterval);
    }

    /**
     * Ctor.
     * @param getter for "prev" access
     * @param currentStates existing state
     * @param optionalKeys partition keys if any
     * @param hasInterval true for interval
     */
    public RegexPartitionState(RegexPartitionStateRandomAccessGetter getter,
                               List<RegexNFAStateEntry> currentStates,
                               MultiKeyUntyped optionalKeys,
                               boolean hasInterval) {
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

    /**
     * Returns the random access for "prev".
     * @return access
     */
    public RegexPartitionStateRandomAccessImpl getRandomAccess() {
        return randomAccess;
    }

    /**
     * Returns partial matches.
     * @return state
     */
    public List<RegexNFAStateEntry> getCurrentStates() {
        return currentStates;
    }

    /**
     * Sets partial matches.
     * @param currentStates state to set
     */
    public void setCurrentStates(List<RegexNFAStateEntry> currentStates) {
        this.currentStates = currentStates;
    }

    /**
     * Returns partition keys, if any.
     * @return keys
     */
    public MultiKeyUntyped getOptionalKeys() {
        return optionalKeys;
    }

    /**
     * Remove an event from random access for "prev".
     * @param oldEvents to remove
     */
    public void removeEventFromPrev(EventBean[] oldEvents)
    {
        if (randomAccess != null)
        {
            randomAccess.remove(oldEvents);
        }
    }

    /**
     * Remove an event from random access for "prev".
     * @param oldEvent to remove
     */
    public void removeEventFromPrev(EventBean oldEvent)
    {
        if (randomAccess != null)
        {
            randomAccess.remove(oldEvent);
        }
    }

    /**
     * Remove an event from state.
     * @param oldEvent to remove
     * @return true for removed, false for not found
     */
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

    /**
     * Returns the interval states, if any.
     * @return interval states
     */
    public List<RegexNFAStateEntry> getCallbackItems()
    {
        return intervalCallbackItems;
    }

    /**
     * Returns indicator if callback is schedule.
     * @return scheduled indicator
     */
    public boolean isCallbackScheduled()
    {
        return isCallbackScheduled;
    }

    /**
     * Returns indicator if callback is schedule.
     * @param callbackScheduled true if scheduled
     */
    public void setCallbackScheduled(boolean callbackScheduled)
    {
        isCallbackScheduled = callbackScheduled;
    }

    /**
     * Add a callback item for intervals.
     * @param endState to add
     */
    public void addCallbackItem(RegexNFAStateEntry endState)
    {
        intervalCallbackItems.add(endState);
    }
}
