package net.esper.support.command.engine;

import net.esper.client.EPServiceProvider;
import net.esper.support.persist.SupportLogHandlerImpl;

public class EngineContainer
{
    private EPServiceProvider epServiceProvider;
    private SupportLogHandlerImpl supportLogHandlerImpl;

    public EngineContainer(EPServiceProvider epServiceProvider, SupportLogHandlerImpl supportLogHandlerImpl)
    {
        this.epServiceProvider = epServiceProvider;
        this.supportLogHandlerImpl = supportLogHandlerImpl;
    }

    public EPServiceProvider getEpServiceProvider()
    {
        return epServiceProvider;
    }

    public SupportLogHandlerImpl getSupportLogHandlerImpl()
    {
        return supportLogHandlerImpl;
    }
}
