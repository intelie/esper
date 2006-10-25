package net.esper.support.command.engine;

import net.esper.support.command.stmt.StmtLevelCommand;
import net.esper.support.persist.SupportLogHandlerImpl;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

import java.util.LinkedHashMap;

public class CreateLogEntryContainerCmd implements EngineLevelCommand
{
    private String logEntryContainerName;

    public CreateLogEntryContainerCmd(String logEntryContainerName)
    {
        this.logEntryContainerName = logEntryContainerName;
    }

    public void execute(EngineCommandWorkspace workspace,
                        LinkedHashMap<Integer, StmtLevelCommand> commands)
    {
        workspace.addLogEntryContainer(logEntryContainerName, new LogEntryContainer());
    }

    public String toString()
    {
        return this.getClass().getSimpleName() +
            " name=" + logEntryContainerName;
    }
}
