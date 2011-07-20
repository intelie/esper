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

/**
 * Service for holding partition state.
 */
public interface RegexPartitionStateRepo
{
    /**
     * Return state for key or create state if not found.
     * @param key to look up
     * @return state
     */
    public RegexPartitionState getState(MultiKeyUntyped key);

    /**
     * Return state for event or create state if not found.
     * @param event to look up
     * @param isCollect true if a collection of unused state can occur
     * @return state
     */
    public RegexPartitionState getState(EventBean event, boolean isCollect);

    /**
     * Remove old events from the state, applicable for "prev" function and partial NFA state.
     * @param events to remove
     * @param isEmpty indicator if there are not matches
     * @param found indicator if any partial matches exist to be deleted
     */
    public void removeOld(EventBean[] events, boolean isEmpty, boolean found[]);

    /**
     * Copy state for iteration.
     * @return copied state
     */
    public RegexPartitionStateRepo copyForIterate();

    public void removeState(MultiKeyUntyped partitionKey);
}
