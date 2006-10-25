package net.esper.client;

import net.esper.client.logstate.LogEntry;


/**
 * This class provides access to the EPRuntime and EPAdministrator implementations.
 */
public interface EPServiceProvider
{
    /**
     * Starts transaction on LogHandler provided to engine.
     */
    public void startTransaction();

    /**
     * Prepare commit. Accumulates log entries and passes these to a LogHandler provided to engine.
     */
    public void prepareCommit();

    /**
     * Commits transaction on LogHandler provided to engine.
     */
    public void commit();

    /**
     * Replay logs into the engine instance.
     * @param logEntries are the logs to replay
     */
    public void replayLogs(LogEntry[] logEntries);

    /**
     * Returns a class instance of EPRuntime.
     * @return an instance of EPRuntime
     */
    public EPRuntime getEPRuntime();

    /**
     * Returns a class instance of EPAdministrator.
     * @return an instance of EPAdministrator
     */
    public EPAdministrator getEPAdministrator();

    /**
     * Frees any resources associated with this runtime instance.
     * Stops and destroys any event filters, patterns, expressions, views.
     */
    public void initialize();
}
