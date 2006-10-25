package net.esper.support.command.engine;

import net.esper.support.command.stmt.StmtLevelCommand;
import net.esper.client.logstate.LogEntry;

import java.util.LinkedHashMap;

import junit.framework.Assert;

public class LogReplayCmd implements EngineLevelCommand
{
    private String logContainerName;
    private String engineURI;

    public LogReplayCmd(String logContainerName, String engineURI)
    {
        this.logContainerName = logContainerName;
        this.engineURI = engineURI;
    }

    public void execute(EngineCommandWorkspace workspace, LinkedHashMap<Integer, StmtLevelCommand> commands)
    {
        EngineContainer engineContainer = workspace.getEngine(engineURI);
        LogEntryContainer logEntryContainer = workspace.getLogEntryContainer(logContainerName);

        // replay
        LogEntry[] logEntries = logEntryContainer.getAndClearLogEntries();
        engineContainer.getEpServiceProvider().replayLogs(logEntries);

        // make sure the replay itself didn't generate log entries
        Assert.assertEquals(0, engineContainer.getSupportLogHandlerImpl().getReceived().size());
    }

    public String toString()
    {
        return this.getClass().getSimpleName() +
            " logContainerName=" + logContainerName +
            " engineURI=" + engineURI;
    }
}
