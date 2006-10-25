package net.esper.support.command.engine;

import net.esper.client.logstate.LogEntry;
import net.esper.client.logstate.LogKey;
import net.esper.client.logstate.LogEntrySerializer;

import java.util.Map;
import java.util.LinkedHashMap;

public class LogEntryContainer
{
    private Map<LogKey, LogEntry> logEntryRepository;

    public LogEntryContainer()
    {
        this.logEntryRepository = new LinkedHashMap<LogKey, LogEntry>();
    }

    public void add(LogEntry[] logEntries)
    {
        LogEntry[] deserialized = null;
        try
        {
            byte[] buffer = LogEntrySerializer.serialize(logEntries);
            deserialized = LogEntrySerializer.deserialize(buffer);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Error serializing log entry buffer", ex);
        }

        for (int i = 0; i < logEntries.length; i++)
        {
            LogKey key = deserialized[i].getKey();
            logEntryRepository.put(key, deserialized[i]);
        }
    }

    public LogEntry[] getAndClearLogEntries()
    {
        LogEntry[] entries = new LogEntry[logEntryRepository.size()];

        int count = 0;
        for (LogKey key : logEntryRepository.keySet())
        {
            LogEntry value = logEntryRepository.get(key);
            entries[count++] = value;
        }

        logEntryRepository.clear();
        
        return entries;
    }


}
