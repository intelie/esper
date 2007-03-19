/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.observer;

/**
 * Observers observe and indicate other external events such as timing events.
 */
public interface EventObserver
{
    /**
     * Start observing.
     */
    public void startObserve();

    /**
     * Stop observing.
     */
    public void stopObserve();
}
