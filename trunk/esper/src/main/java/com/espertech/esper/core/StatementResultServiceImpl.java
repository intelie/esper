package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements tracking of statement listeners and subscribers for a given statement
 * such as to efficiently dispatch in situations where 0, 1 or more listeners
 * are attached and/or 0 or 1 subscriber (such as iteration-only statement).
 */
public class StatementResultServiceImpl implements StatementResultService
{
    private static Log log = LogFactory.getLog(StatementResultServiceImpl.class);

    // Part of the statement context
    private EPStatementSPI epStatement;
    private EPServiceProvider epServiceProvider;
    private boolean isInsertInto;
    private boolean isPattern;
    private StatementLifecycleSvc statementLifecycleSvc;

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
    protected ThreadLocal<ArrayDequeJDK6Backport<UniformPair<EventBean[]>>> lastResults = new ThreadLocal<ArrayDequeJDK6Backport<UniformPair<EventBean[]>>>() {
        protected synchronized ArrayDequeJDK6Backport<UniformPair<EventBean[]>> initialValue() {
            return new ArrayDequeJDK6Backport<UniformPair<EventBean[]>>();
        }
    };

    /**
     * Ctor.
     * @param statementLifecycleSvc handles persistence for statements
     */
    public StatementResultServiceImpl(StatementLifecycleSvc statementLifecycleSvc)
    {
        log.debug(".ctor");
        this.statementLifecycleSvc = statementLifecycleSvc;
    }

    public void setContext(EPStatementSPI epStatement, EPServiceProvider epServiceProvider,
                           boolean isInsertInto, boolean isPattern)
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
        // indicate that listeners were updated for potential persistence of listener set, once the statement context is known
        if (epStatement != null)
        {
            this.statementLifecycleSvc.updatedListeners(epStatement.getStatementId(), epStatement.getName(), statementListenerSet);
        }

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
    public void indicate(UniformPair<EventBean[]> results)
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
        ArrayDequeJDK6Backport<UniformPair<EventBean[]>> dispatches = lastResults.get();
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".execute dispatches: " + dispatches.size());
        }

        UniformPair<EventBean[]> events = EventBeanUtility.flattenList(dispatches);

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
        ArrayDequeJDK6Backport<UniformPair<EventBean[]>> dispatches = lastResults.get();
        if (dispatches.isEmpty())
        {
            return;
        }
        execute();
    }
}
