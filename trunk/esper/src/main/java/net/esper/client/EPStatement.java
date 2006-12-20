package net.esper.client;

/**
 * Interface to add and remove update listeners receiving events for an EP statement.
 */
public interface EPStatement extends EPListenable, EPIterable
{
    /**
     * Start the statement.
     */
    public void start();

    /**
     * Stop the statement.
     */
    public void stop();

    /**
     * Returns the underlying expression text or XML.
     * @return expression text
     */
    public String getText();
}

