/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.timer;

/**
 * Callback interface for a time provider that triggers at scheduled intervals.
 */
public interface TimerCallback
{
    /**
     * Invoked by the internal clocking service at regular intervals.
     */
    public void timerCallback();
}
