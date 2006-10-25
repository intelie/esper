package net.esper.persist;

import net.esper.client.logstate.LogEntryHandler;
import net.esper.client.logstate.LogEntry;
import net.esper.client.logstate.LogEntryType;
import net.esper.client.logstate.LogKey;

import java.util.*;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogServiceImpl implements LogService
{
    private final Set<LogContextNode> logEntries;
    private final LogEntryHandler logEntryHandler;
    private long sequenceNumber;

    public LogServiceImpl(LogEntryHandler logHandler)
    {
        this.logEntryHandler = logHandler;
        logEntries = new LinkedHashSet<LogContextNode>();
        sequenceNumber = 0;
    }

    public LogEntryHandler getLogEntryHandler()
    {
        return logEntryHandler;
    }

    public void add(LogContextNode logContextNode)
    {
        logEntries.add(logContextNode);
    }

    public void flush()
    {
        LogEntry[] logEntriesArr = new LogEntry[logEntries.size()];
        int count = 0;

        // Get the mementos from all accumulated callbacks
        for (LogContextNode node : logEntries)
        {
            sequenceNumber++;

            Object state = node.getState();
            if (!(state instanceof Serializable))
            {
                throw new IllegalStateException("State object not serializable:" + state.getClass());
            }
            Serializable serializable = (Serializable) state;

            LogKey flattenedKey = LogServiceUtil.getLogKeyForNode(node);

            LogEntry logEntry = new LogEntry(sequenceNumber, flattenedKey, node.getType(), serializable);
            logEntriesArr[count++] = logEntry;
        }
        if (count > 0)
        {
            logEntryHandler.write(logEntriesArr);
        }
        logEntries.clear();
    }

    private static Log log = LogFactory.getLog(LogServiceImpl.class);
}
