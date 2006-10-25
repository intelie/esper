package net.esper.support.command.engine;

import java.util.Map;
import java.util.HashMap;

public class EngineCommandWorkspace
{
    private Map<String, EngineContainer> engines;
    private Map<String, LogEntryContainer> logEntryContainers;

    public EngineCommandWorkspace()
    {
        engines = new HashMap<String, EngineContainer>();
        logEntryContainers = new HashMap<String, LogEntryContainer>();
    }

    public void addEngine(String engineURI, EngineContainer container)
    {
        if (engines.containsKey(engineURI))
        {
            throw new IllegalStateException("Engine with URI '" + engineURI + "' already in collection");
        }
        engines.put(engineURI, container);
    }

    public EngineContainer getEngine(String engineURI)
    {
        if (!engines.containsKey(engineURI))
        {
            throw new IllegalStateException("Engine with URI '" + engineURI + "' not in collection");
        }
        return engines.get(engineURI);
    }

    public void addLogEntryContainer(String name, LogEntryContainer container)
    {
        if (logEntryContainers.containsKey(name))
        {
            throw new IllegalStateException("LogEntryContainer with name '" + name + "' already in collection");
        }
        logEntryContainers.put(name, container);
    }

    public LogEntryContainer getLogEntryContainer(String name)
    {
        if (!logEntryContainers.containsKey(name))
        {
            throw new IllegalStateException("LogEntryContainer with name '" + name + "' not in collection");
        }
        return logEntryContainers.get(name);
    }
}
