package net.esper.timer;

import java.util.TimerTask;

/**
 * Timer task to simply invoke the callback when triggered.
 */
final class EQLTimerTask extends TimerTask
{
    private final TimerCallback callback;

    public EQLTimerTask(TimerCallback callback)
    {
        this.callback = callback;
    }

    public final void run()
    {
        callback.timerCallback();
    }
}
