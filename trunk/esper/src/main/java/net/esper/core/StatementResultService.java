package net.esper.core;

import net.esper.event.EventBean;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProvider;

public interface StatementResultService
{
    public void setContext(EPStatement epStatement, EPServiceProvider epServiceProvider);

    // Called by SelectExprProcessor
    public boolean isMakeSynthetic();
    public boolean isMakeNatural();
    public Object[] getNatural(EventBean[] eventsPerStream, boolean isNewData);

    // Called by EPStatement
    public void updatedListeners(EPStatementListenerSet listenerSet);
    public void dispatchOnStop();
    public EventBean getLastIterableEvent();

    // Called by UpdateDispatchViewBase
    public void setUpdateListeners(EPStatementListenerSet updateListeners);

    // Called by OutputProcessView
    public void indicate(EventBean[] newData, EventBean[] oldData);
    public void execute();
}
