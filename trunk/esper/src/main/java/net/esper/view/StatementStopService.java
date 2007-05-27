package net.esper.view;

/**
 * Provides statement resources with the means to register a callback and be informed when a statement stopped
 * and resources for the statement must be release.
 */
public interface StatementStopService
{
    /**
     * Add a callback to perform for a stop of a statement.
     * @param callback is the callback function
     */
    public void addSubscriber(StatementStopCallback callback);

    /**
     * Used by the engine to indicate a statement stopped, invoking any callbacks registered.
     */
    public void fireStatementStopped();
}
