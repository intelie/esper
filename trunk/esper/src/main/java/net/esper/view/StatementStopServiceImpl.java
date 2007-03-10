package net.esper.view;

import java.util.List;
import java.util.LinkedList;

/**
 * Provides subscription list for statement stop callbacks.
 */
public class StatementStopServiceImpl implements StatementStopService
{
    private List<StatementStopCallback> statementStopCallbacks;

    /**
     * ctor.
     */
    public StatementStopServiceImpl()
    {
        statementStopCallbacks = new LinkedList<StatementStopCallback>();
    }

    public void addSubscriber(StatementStopCallback callback)
    {
        statementStopCallbacks.add(callback);
    }

    public void fireStatementStopped()
    {
        for (StatementStopCallback statementStopCallback : statementStopCallbacks)
        {
            statementStopCallback.statementStopped();
        }
    }
}
