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
