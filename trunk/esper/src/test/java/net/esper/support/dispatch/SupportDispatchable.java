package net.esper.support.dispatch;

import net.esper.dispatch.Dispatchable;

import java.util.List;
import java.util.LinkedList;

public class SupportDispatchable implements Dispatchable
{
    private static List<SupportDispatchable> instanceList = new LinkedList<SupportDispatchable>();
    private int numExecuted;

    public void execute()
    {
        numExecuted++;
        instanceList.add(this);
    }

    public int getAndResetNumExecuted()
    {
        int val = numExecuted;
        numExecuted = 0;
        return val;
    }

    public static List<SupportDispatchable> getAndResetInstanceList()
    {
        List<SupportDispatchable> instances = instanceList;
        instanceList = new LinkedList<SupportDispatchable>();
        return instances;
    }
}
