package net.esper.client.logstate;

/**
 * Interface for a hander that receives log entries and handles log write transaction boundaries.
 * <p>
 * The <pre>startTransaction</pre> method is invoked on epServiceProvider.startTransaction
 * <p>
 * The <pre>write</pre> method is invoked on epServiceProvider.prepareCommit
 * <p>
 * The <pre>commit</pre> method is invoked on epServiceProvider.commit
 * that
 */
public interface LogEntryHandler
{    
    public void startTransaction();
    public void write(LogEntry[] logEntries);
    public void commit();
}
