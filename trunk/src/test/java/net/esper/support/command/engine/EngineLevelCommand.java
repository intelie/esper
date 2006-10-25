package net.esper.support.command.engine;

import net.esper.support.command.stmt.StmtLevelCommand;

import java.util.LinkedHashMap;

public interface EngineLevelCommand
{
    public void execute(EngineCommandWorkspace workspace,
                        LinkedHashMap<Integer, StmtLevelCommand> commands);
}
