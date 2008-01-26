package net.esper.core;

import net.esper.client.*;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.view.ViewSupport;
import net.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;

/**
 * Implements tracking of statement listeners and subscribers for a given statement
 * such as to efficiently dispatch in situations where 0, 1 or more listeners
 * are attached and/or 0 or 1 subscriber (such as iteration-only statement).
 */
public class StatementResultServiceImpl implements StatementResultService
{
    private static Log log = LogFactory.getLog(StatementResultServiceImpl.class);

    // Part of the statement context
    private EPStatement epStatement;
    private EPServiceProvider epServiceProvider;
    private boolean isInsertInto;
    private boolean isPattern;

    // For natural delivery derived out of select-clause expressions
    private Class[] selectClauseTypes;
    private String[] selectClauseColumnNames;

    // Listeners and subscribers and derived information
    private EPStatementListenerSet statementListenerSet;
    private boolean isMakeNatural;
    private boolean isMakeSynthetic;
    private ResultDeliveryStrategy statementResultNaturalStrategy;

    // For iteration over patterns
    private EventBean lastIterableEvent;

    /**
     * Buffer for holding dispatchable events.
     */
    protected ThreadLocal<LinkedList<Pair<EventBean[], EventBean[]>>> lastResults = new ThreadLocal<LinkedList<Pair<EventBean[], EventBean[]>>>() {
        protected synchronized LinkedList<Pair<EventBean[], EventBean[]>> initialValue() {
            return new LinkedList<Pair<EventBean[], EventBean[]>>();
        }
    };

    public void setContext(EPStatement epStatement, EPServiceProvider epServiceProvider, boolean isInsertInto, boolean isPattern)
    {
        this.epStatement = epStatement;
        this.epServiceProvider = epServiceProvider;
        this.isInsertInto = isInsertInto;
        this.isPattern = isPattern;
        isMakeSynthetic = isInsertInto || isPattern;
    }

    public void setSelectClause(Class[] selectClauseTypes, String[] selectClauseColumnNames)
    {
        if ((selectClauseTypes == null) || (selectClauseTypes.length == 0))
        {
            throw new IllegalArgumentException("Invalid null or zero-element list of select clause expression types");
        }
        if ((selectClauseColumnNames == null) || (selectClauseColumnNames.length == 0))
        {
            throw new IllegalArgumentException("Invalid null or zero-element list of select clause column names");
        }
        this.selectClauseTypes = selectClauseTypes;
        this.selectClauseColumnNames = selectClauseColumnNames;
    }

    public boolean isMakeSynthetic()
    {
        return isMakeSynthetic;
    }

    public boolean isMakeNatural()
    {
        return isMakeNatural;
    }

    public EventBean getLastIterableEvent()
    {
        return lastIterableEvent;
    }

    public void setUpdateListeners(EPStatementListenerSet statementListenerSet)
    {
        this.statementListenerSet = statementListenerSet;

        isMakeNatural = statementListenerSet.getSubscriber() != null;
        isMakeSynthetic = !(statementListenerSet.getListeners().isEmpty() && statementListenerSet.getStmtAwareListeners().isEmpty())
                || isPattern || isInsertInto;

        if (statementListenerSet.getSubscriber() == null)
        {
            statementResultNaturalStrategy = null;
            isMakeNatural = false;
            return;
        }

        statementResultNaturalStrategy = ResultDeliveryStrategyFactory.create(statementListenerSet.getSubscriber(),
                selectClauseTypes, selectClauseColumnNames);
        isMakeNatural = true;
    }

    // Called by OutputProcessView
    public void indicate(Pair<EventBean[], EventBean[]> results)
    {
        if (results != null)
        {
            if ((results.getFirst() != null) && (results.getFirst().length != 0))
            {
                lastResults.get().add(results);
                lastIterableEvent = results.getFirst()[0];
            }
            else if ((results.getSecond() != null) && (results.getSecond().length != 0))
            {
                lastResults.get().add(results);
            }
        }
    }

    public void execute()
    {
        LinkedList<Pair<EventBean[], EventBean[]>> dispatches = lastResults.get();
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".execute dispatches: " + dispatches.size());
        }

        Pair<EventBean[], EventBean[]> events = EventBeanUtility.flattenList(dispatches);

        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".execute", events);
        }

        if (statementResultNaturalStrategy != null)
        {
            statementResultNaturalStrategy.execute(events);
        }

        EventBean[] newEventArr = events != null ? events.getFirst() : null;
        EventBean[] oldEventArr = events != null ? events.getSecond() : null;

        for (UpdateListener listener : statementListenerSet.listeners)
        {
            listener.update(newEventArr, oldEventArr);
        }
        if (!(statementListenerSet.stmtAwareListeners.isEmpty()))
        {
            for (StatementAwareUpdateListener listener : statementListenerSet.getStmtAwareListeners())
            {
                listener.update(newEventArr, oldEventArr, epStatement, epServiceProvider);
            }
        }

        dispatches.clear();
    }

    /**
     * Dispatches when the statement is stopped any remaining results.
     */
    public void dispatchOnStop()
    {
        lastIterableEvent = null;
        LinkedList<Pair<EventBean[], EventBean[]>> dispatches = lastResults.get();
        if (dispatches.isEmpty())
        {
            return;
        }
        execute();
    }
}
