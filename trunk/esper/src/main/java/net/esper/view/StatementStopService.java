package net.esper.view;

public interface StatementStopService
{
    public void addSubscriber(StatementStopCallback callback);
    public void fireStatementStopped();
}
