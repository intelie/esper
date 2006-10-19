package net.esper.timer;

/**
 * Service interface for repeated callbacks at regular intervals.
 */
public interface TimerService
{
    /**
     * Resolution in milliseconds of the internal clock.
     */
    public static int INTERNAL_CLOCK_RESOLUTION_MSEC = 100;

    /**
     * Set the callback method to invoke for clock ticks.
     * @param timerCallback is the callback
     */
    public void setCallback(TimerCallback timerCallback);

    /**
     * Start clock expecting callbacks at regular intervals and a fixed rate.
     * Catch-up callbacks are possible should the callback fall behind.
     */
    public void startInternalClock();

    /**
     * Stop internal clock.
     * @param warnIfNotStarted use true to indicate whether to warn if the clock is not started, use false to not warn
     * and expect the clock to be not started. 
     */
    public void stopInternalClock(boolean warnIfNotStarted);
}
