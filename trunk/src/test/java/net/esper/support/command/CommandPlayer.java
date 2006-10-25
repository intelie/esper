package net.esper.support.command;

import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPServiceProvider;
import net.esper.support.command.stmt.StmtLevelCommand;
import net.esper.support.command.engine.EngineLevelCommand;
import net.esper.support.command.engine.EngineCommandWorkspace;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommandPlayer
{
    public static void playStatements(LinkedHashMap<Integer, StmtLevelCommand> commands)
    {
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
        engine.initialize();

        for (StmtLevelCommand stmtLevelCommand : commands.values())
        {
            log.info(".playStatements Playing command " + stmtLevelCommand);
            stmtLevelCommand.execute(engine);
        }
    }

    public static void playEngine(LinkedHashMap<Integer, StmtLevelCommand> statementCommands, List<EngineLevelCommand> engineLevelCommands)
    {
        EngineCommandWorkspace workspace = new EngineCommandWorkspace();

        for (EngineLevelCommand engineLevelCommand : engineLevelCommands)
        {
            log.info(".playEngine Playing command " + engineLevelCommand);
            engineLevelCommand.execute(workspace, statementCommands);
        }
    }

    private static final Log log = LogFactory.getLog(CommandPlayer.class);
}
