/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.collection;

import net.esper.event.EventBean;

/**
 * A general-purpose collection interface for collections updated by view data.
 * <p>
 * Views post delta-data in terms of new data (insert stream) events and old data (remove stream) event that
 * leave a window.
 */
public interface ViewUpdatedCollection
{
    /**
     * Accepts view insert and remove stream.
     * @param newData is the insert stream events or null if no data
     * @param oldData is the remove stream events or null if no data
     */
    public void update(EventBean[] newData, EventBean[] oldData);
}