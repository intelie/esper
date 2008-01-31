package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.event.EventBean;

public interface StatementResultService
{
    /**
     * For initialization of the service to provide statement context.
     * @param epStatement the statement
     * @param epServiceProvider the engine instance
     * @param isInsertInto true if this is insert into
     * @param isPattern true if this is a pattern statement
     */
    public void setContext(EPStatement epStatement, EPServiceProvider epServiceProvider, boolean isInsertInto, boolean isPattern);

    /**
     * For initialize of the service providing select clause column types and names.
     * @param selectClauseTypes types of columns in the select clause
     * @param selectClauseColumnNames column names
     */
    public void setSelectClause(Class[] selectClauseTypes, String[] selectClauseColumnNames);
    
    /**
     * Returns true to indicate that synthetic events should be produced, for
     * use in select expression processing.
     * @return true to produce synthetic events
     */
    public boolean isMakeSynthetic();

    /**
     * Returns true to indicate that natural events should be produced, for
     * use in select expression processing.
     * @return true to produce natural (object[] column) events
     */
    public boolean isMakeNatural();

    /**
     * Dispatch the remaining results, if any, to listeners as the statement is about to be stopped.
     */
    public void dispatchOnStop();

    /**
     * Returns the last iterable event, for use by patterns since these are not iterable.
     * @return last event
     */
    public EventBean getLastIterableEvent();

    /**
     * Indicate a change in update listener.
     * @param updateListeners is the new listeners and subscriber
     */
    public void setUpdateListeners(EPStatementListenerSet updateListeners);

    /**
     * Stores for dispatching the statement results.
     * @param results is the insert and remove stream data
     */
    public void indicate(UniformPair<EventBean[]> results);

    /**
     * Execution of result indication.
     */
    public void execute();
}
