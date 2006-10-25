package net.esper.support.command.stmt;

import net.esper.client.EPServiceProvider;

import java.util.Arrays;

public class SendEventCmd implements StmtLevelCommand
{
    private Object[] events;

    public SendEventCmd(Object event)
    {
        this.events = new Object[] {event};
    }

    public SendEventCmd(Object[] events)
    {
        this.events = events;
    }

    public void execute(EPServiceProvider engine)
    {
        for (int i = 0; i < events.length; i++)
        {
            engine.getEPRuntime().sendEvent(events[i]);
        }
    }

    public String toString()
    {
        return this.getClass().getSimpleName() +
            " events=" + Arrays.toString(events);
    }
}
