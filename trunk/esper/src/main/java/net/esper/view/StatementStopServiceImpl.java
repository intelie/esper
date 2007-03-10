package net.esper.view;

import java.util.List;
import java.util.LinkedList;

public class StatementStopServiceImpl implements StatementStopService
{
    private List<StatementStopCallback> statementStopCallbacks;

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
