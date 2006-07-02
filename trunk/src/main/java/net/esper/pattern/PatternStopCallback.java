package net.esper.pattern;

/**
 * Interface for executing a stop on an active event expression.
 */
public interface PatternStopCallback
{
    /**
     * Method to stop the event expression.
     */
    public void stop();
}
