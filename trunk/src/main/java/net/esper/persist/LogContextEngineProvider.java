package net.esper.persist;

import net.esper.client.logstate.LogEntryHandler;

public class LogContextEngineProvider
{
    public static LogContextEngine newLogContext(LogService logService, String optionalEngineURI)
    {
        return new LogContextEngineImpl(optionalEngineURI, logService);
    }

    public static LogService newLogService(LogEntryHandler mementoCaretaker)
    {
        return new LogServiceImpl(mementoCaretaker);
    }
}
