/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

/**
 * Interface to add and remove update listeners.
 */
public interface EPListenable
{
    /**
     * Add an listener that observes events.
     * @param listener to add
     */
    public void addListener(UpdateListener listener);

    /**
     * Remove an listener that observes events.
     * @param listener to remove
     */
    public void removeListener(UpdateListener listener);

    /**
     * Remove all listeners.
     */
    public void removeAllListeners();
}

