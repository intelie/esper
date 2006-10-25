package net.esper.support.persist;

import net.esper.persist.LogContextNode;
import net.esper.client.logstate.LogEntryType;

public class SupportLogContextNodeFactory
{
    public static <StateX> LogContextNode<StateX> createChild(StateX state)
    {
        LogContextNode<StateX> node = new LogContextNode<StateX>(null, LogEntryType.ENGINE, 0, 0, null, state);
        return node;
    }
}
