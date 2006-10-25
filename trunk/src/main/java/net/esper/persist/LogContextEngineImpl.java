package net.esper.persist;

import net.esper.client.logstate.LogEntryType;

public class LogContextEngineImpl implements LogContextEngine
{
    private LogContextNode<String> rootNode;

    public LogContextEngineImpl(String optionalEngineURI, LogService logService)
    {
        if (optionalEngineURI == null)
        {
            optionalEngineURI = "default";
        }
        this.rootNode = new LogContextNode<String>(logService, LogEntryType.ENGINE, 1, 0, null, optionalEngineURI);
    }

    public void setLogContext(LogContextNode rootNode)
    {
        this.rootNode = rootNode;
    }

    public LogContextNode getRootNode()
    {
        return rootNode;
    }
}
