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
