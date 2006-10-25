package net.esper.persist;

import net.esper.client.logstate.LogEntryHandler;

/**
 * Interface for saving changed state during runtime when events are evaluated.
 */
public interface LogService
{
    public LogEntryHandler getLogEntryHandler();

    public void add(LogContextNode logContextNode);

    public void flush();
}
