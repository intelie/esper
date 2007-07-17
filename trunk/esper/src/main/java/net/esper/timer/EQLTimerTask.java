/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.timer;

import java.util.TimerTask;

/**
 * Timer task to simply invoke the callback when triggered.
 */
final class EQLTimerTask extends TimerTask
{
    private final TimerCallback callback;
    private boolean isCancelled;

    public EQLTimerTask(TimerCallback callback)
    {
        this.callback = callback;
    }

    public final void run()
    {
        if (!isCancelled)
        {
            callback.timerCallback();
        }
    }

    public void setCancelled(boolean cancelled)
    {
        isCancelled = cancelled;
    }
}
