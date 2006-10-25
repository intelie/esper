package net.esper.support.command.engine;

import net.esper.support.command.stmt.StmtLevelCommand;
import net.esper.client.EPServiceProvider;
import net.esper.client.logstate.LogEntry;

import java.util.LinkedHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransactionCmd implements EngineLevelCommand
{
    private String engineURI;
    private int beginIndex;
    private int endIndex;
    private String logContainerName;

    public TransactionCmd(String engineURI, int beginIndex, int endIndex, String logContainerName)
    {
        this.engineURI = engineURI;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.logContainerName = logContainerName;
    }

    public void execute(EngineCommandWorkspace workspace, LinkedHashMap<Integer, StmtLevelCommand> commands)
    {
        EngineContainer engineContainer = workspace.getEngine(engineURI);
        LogEntryContainer logEntryContainer = workspace.getLogEntryContainer(logContainerName);

        EPServiceProvider engine = engineContainer.getEpServiceProvider();
        engine.startTransaction();

        // play back commands
        for (int i = beginIndex; i <= endIndex; i++)
        {
            StmtLevelCommand command = commands.get(i);
            if (command == null)
            {
                continue;
            }

            log.info(".execute Playing statement command " + command);
            command.execute(engine);
        }

        // obtain log entries
        engine.prepareCommit();
        LogEntry[] logEntries = workspace.getEngine(engineURI).getSupportLogHandlerImpl().getHistorical();
        engine.commit();
        engineContainer.getSupportLogHandlerImpl().resetAll();

        // add log entries to our own container
        logEntryContainer.add(logEntries);
    }

    public String toString()
    {
        return this.getClass().getSimpleName() +
            " engineURI=" + engineURI +
            " beginIndex=" + beginIndex +
            " endIndex=" + endIndex +
            " logContainerName=" + logContainerName;
    }

    private static Log log = LogFactory.getLog(TransactionCmd.class);
}
