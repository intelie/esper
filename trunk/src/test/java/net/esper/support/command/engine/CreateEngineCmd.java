package net.esper.support.command.engine;

import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPServiceProvider;
import net.esper.client.Configuration;
import net.esper.support.persist.SupportLogHandlerImpl;
import net.esper.support.command.stmt.StmtLevelCommand;

import java.util.LinkedHashMap;
import java.util.Arrays;

public class CreateEngineCmd implements EngineLevelCommand
{
    private String engineURI;

    public CreateEngineCmd(String engineURI)
    {
        this.engineURI = engineURI;
    }

    public void execute(EngineCommandWorkspace workspace,
                        LinkedHashMap<Integer, StmtLevelCommand> commands)
    {
        SupportLogHandlerImpl logHandler = new SupportLogHandlerImpl();

        Configuration configuration = new Configuration();
        configuration.setLogEntryHandler(logHandler);

        EPServiceProvider engine = EPServiceProviderManager.getProvider(engineURI, configuration);
        engine.initialize();

        EngineContainer container = new EngineContainer(engine, logHandler);
        workspace.addEngine(engineURI, container);
    }

    public String toString()
    {
        return this.getClass().getSimpleName() +
            " engineURI=" + engineURI;
    }
}
