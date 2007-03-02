using System;
using System.Collections.Generic;

using net.esper.dispatch;

namespace net.esper.support.dispatch
{
	public class SupportDispatchable : Dispatchable
	{
    private static IList<SupportDispatchable> instanceList = new List<SupportDispatchable>();
    private int numExecuted;

    public void Execute()
    {
        numExecuted++;
        instanceList.Add(this);
    }

    public int getAndResetNumExecuted()
    {
        int val = numExecuted;
        numExecuted = 0;
        return val;
    }

    public static IList<SupportDispatchable> getAndResetInstanceList()
    {
        IList<SupportDispatchable> instances = instanceList;
        instanceList = new List<SupportDispatchable>();
        return instances;
    }
	}
}
