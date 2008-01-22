package net.esper.core;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.StatementAwareUpdateListener;
import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.view.ViewSupport;
import net.esper.collection.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;

public class StatementResultServiceImpl implements StatementResultService
{
    private static Log log = LogFactory.getLog(StatementResultServiceImpl.class);
    private EPStatement epStatement;
    private EPServiceProvider epServiceProvider;
    private EPStatementListenerSet statementListenerSet;
    private EventBean lastIterableEvent;

    private boolean isMakeNatural;
    private boolean isMakeSynthetic;

    /**
     * Buffer for holding dispatchable events.
     */
    protected ThreadLocal<LinkedList<Pair<EventBean[], EventBean[]>>> lastResults = new ThreadLocal<LinkedList<Pair<EventBean[], EventBean[]>>>() {
        protected synchronized LinkedList<Pair<EventBean[], EventBean[]>> initialValue() {
            return new LinkedList<Pair<EventBean[], EventBean[]>>();
        }
    };

    public void setContext(EPStatement epStatement, EPServiceProvider epServiceProvider)
    {
        this.epStatement = epStatement;
        this.epServiceProvider = epServiceProvider;
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

    public void setUpdateListeners(EPStatementListenerSet statementListenerSet, boolean isPatternStmt)
    {
        this.statementListenerSet = statementListenerSet;

        isMakeNatural = statementListenerSet.getSubscriber() != null;
        isMakeSynthetic = !(statementListenerSet.getListeners().isEmpty() && statementListenerSet.getStmtAwareListeners().isEmpty()) || isPatternStmt;

        // TODO
        log.info(".setUpdateListeners " + this.hashCode() + " Thread " + Thread.currentThread().getId() + " isMakeNatural=" + isMakeNatural + " isMakeSynthetic=" + isMakeSynthetic);
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
        log.info(".execute " + this.hashCode() + " Thread " + Thread.currentThread().getId() + " isMakeNatural=" + isMakeNatural + " isMakeSynthetic=" + isMakeSynthetic);

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
