package net.esper.core;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.StatementAwareUpdateListener;
import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.view.ViewSupport;
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
    protected ThreadLocal<LinkedList<EventBean[]>> lastNewEvents = new ThreadLocal<LinkedList<EventBean[]>>() {
        protected synchronized LinkedList<EventBean[]> initialValue() {
            return new LinkedList<EventBean[]>();
        }
    };
    /**
     * Buffer for holding dispatchable events.
     */
    protected ThreadLocal<LinkedList<EventBean[]>> lastOldEvents = new ThreadLocal<LinkedList<EventBean[]>>() {
        protected synchronized LinkedList<EventBean[]> initialValue() {
            return new LinkedList<EventBean[]>();
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
    }

    // Called by OutputProcessView
    public void indicate(EventBean[] newData, EventBean[] oldData)
    {
        if ((newData != null) && (newData.length != 0))
        {
            lastIterableEvent = newData[0];
            lastNewEvents.get().add(newData);
        }
        if ((oldData != null) && (oldData.length != 0))
        {
            lastOldEvents.get().add(oldData);
        }
    }

    public void execute()
    {
        EventBean[] newEvents = EventBeanUtility.flatten(lastNewEvents.get());
        EventBean[] oldEvents = EventBeanUtility.flatten(lastOldEvents.get());

        if (log.isDebugEnabled())
        {
            ViewSupport.dumpUpdateParams(".execute", newEvents, oldEvents);
        }

        for (UpdateListener listener : statementListenerSet.listeners)
        {
            listener.update(newEvents, oldEvents);
        }
        if (!(statementListenerSet.stmtAwareListeners.isEmpty()))
        {
            for (StatementAwareUpdateListener listener : statementListenerSet.getStmtAwareListeners())
            {
                listener.update(newEvents, oldEvents, epStatement, epServiceProvider);
            }
        }

        lastNewEvents.get().clear();
        lastOldEvents.get().clear();
    }

    /**
     * Dispatches when the statement is stopped any remaining results.
     */
    public void dispatchOnStop()
    {
        if ((lastNewEvents.get().size() > 0) || (lastOldEvents.get().size() > 0))
        {
            execute();
        }
        lastIterableEvent = null;
    }
}
