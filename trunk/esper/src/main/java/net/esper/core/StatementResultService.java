package net.esper.core;

import net.esper.event.EventBean;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProvider;
import net.esper.collection.Pair;

public interface StatementResultService
{
    // Called by StatementLifecycleSvcImpl 
    public void setContext(EPStatement epStatement, EPServiceProvider epServiceProvider, boolean isInsertInto, boolean isPattern);

    // Called by SelectExprProcessorFactory
    public void setSelectClause(Class[] selectClauseTypes, String[] selectClauseColumnNames);
    
    // Called by SelectExprProcessor
    public boolean isMakeSynthetic();
    public boolean isMakeNatural();

    // Called by EPStatement
    public void dispatchOnStop();
    public EventBean getLastIterableEvent();

    // Called by UpdateDispatchViewBase
    public void setUpdateListeners(EPStatementListenerSet updateListeners);

    // Called by OutputProcessView
    public void indicate(Pair<EventBean[], EventBean[]> results);
    public void execute();
}
