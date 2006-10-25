package net.esper.support.persist;

import net.esper.client.logstate.LogEntryHandler;
import net.esper.client.logstate.LogEntry;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;

public class SupportLogHandlerImpl implements LogEntryHandler
{
    private List<LogEntry> historical = new ArrayList<LogEntry>();
    private List<LogEntry[]> received = new LinkedList<LogEntry[]>();

    public SupportLogHandlerImpl()
    {
    }

    public void write(LogEntry[] logEntries)
    {
        received.add(logEntries);
        historical.addAll(Arrays.asList(logEntries));
    }

    public List<LogEntry[]> getReceived()
    {
        return received;
    }

    public LogEntry[] getHistorical()
    {
        return historical.toArray(new LogEntry[0]);
    }

    public void startTransaction()
    {
    }

    public void commit()
    {
    }

    public void resetReceived()
    {
        received.clear();
    }

    public void resetAll()
    {
        received.clear();
        historical.clear();
    }
}
