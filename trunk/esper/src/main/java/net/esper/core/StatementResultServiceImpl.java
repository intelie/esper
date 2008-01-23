package net.esper.core;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.StatementAwareUpdateListener;
import net.esper.client.UpdateListener;
import net.esper.collection.Pair;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;

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

    public void setNaturalConditions(Class[] selectClauseTypes, String[] selectClauseColumnNames)
    {
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

        // TODO
        // log.info(".setUpdateListeners " + this.hashCode() + " Thread " + Thread.currentThread().getId() + " isMakeNatural=" + isMakeNatural + " isMakeSynthetic=" + isMakeSynthetic);
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
        // TODO
        // log.info(".execute " + this.hashCode() + " Thread " + Thread.currentThread().getId() + " isMakeNatural=" + isMakeNatural + " isMakeSynthetic=" + isMakeSynthetic);

        LinkedList<Pair<EventBean[], EventBean[]>> dispatches = lastResults.get();
        Pair<EventBean[], EventBean[]> events = EventBeanUtility.flattenList(dispatches);

        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".execute", events);
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
