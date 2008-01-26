package net.esper.eql.named;

import net.esper.collection.ArrayEventIterator;
import net.esper.collection.NullIterator;
import net.esper.core.EPStatementHandle;
import net.esper.core.StatementResultService;
import net.esper.eql.expression.ExprNode;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.StatementStopService;
import net.esper.view.ViewSupport;

import java.util.*;

/**
 * This view is hooked into a named window's view chain as the last view and handles dispatching of named window
 * insert and remove stream results via {@link NamedWindowService} to consuming statements.
 */
public class NamedWindowTailView extends ViewSupport implements Iterable<EventBean>
{
    private final static Iterator<EventBean> nullIterator = new NullIterator<EventBean>();
    private final EventType eventType;
    private final NamedWindowRootView namedWindowRootView;
    private final NamedWindowService namedWindowService;
    private transient Map<EPStatementHandle, List<NamedWindowConsumerView>> consumers;
    private final EPStatementHandle createWindowStmtHandle;
    private final StatementResultService statementResultService;

    /**
     * Ctor.
     * @param eventType the event type of the named window
     * @param namedWindowService the service for dispatches to consumers for hooking into the dispatch loop
     * @param namedWindowRootView the root data window view for indicating remove stream events to be removed from possible on-delete indexes
     * @param createWindowStmtHandle statement handle for the statement that created the named window, for safe iteration
     * @param statementResultService
     */
    public NamedWindowTailView(EventType eventType, NamedWindowService namedWindowService, NamedWindowRootView namedWindowRootView, EPStatementHandle createWindowStmtHandle, StatementResultService statementResultService)
    {
        this.eventType = eventType;
        this.namedWindowService = namedWindowService;
        consumers = new HashMap<EPStatementHandle, List<NamedWindowConsumerView>>();
        this.namedWindowRootView = namedWindowRootView;
        this.createWindowStmtHandle = createWindowStmtHandle;
        this.statementResultService = statementResultService;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        // Only old data needs to be removed
        if (oldData != null)
        {
            namedWindowRootView.removeOldData(oldData);
        }

        // Post to child views, only if there are listeners or subscribers
        if (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic())
        {
            updateChildren(newData, oldData);
        }

        // Add to dispatch list for later result dispatch by runtime
        NamedWindowDeltaData delta = new NamedWindowDeltaData(newData, oldData);
        namedWindowService.addDispatch(delta, consumers);
    }

    /**
     * Adds a consumer view keeping the consuming statement's handle and lock to coordinate dispatches.
     * @param statementHandle the statement handle
     * @param statementStopService for when the consumer stops, to unregister the consumer
     * @param filterList is a list of filter expressions
     * @return consumer representative view
     */
    public NamedWindowConsumerView addConsumer(List<ExprNode> filterList, EPStatementHandle statementHandle, StatementStopService statementStopService)
    {
        // Construct consumer view, allow a callback to this view to remove the consumer
        NamedWindowConsumerView consumerView = new NamedWindowConsumerView(filterList, eventType, statementStopService, this);

        // Keep a list of consumer views per statement to accomodate joins and subqueries
        List<NamedWindowConsumerView> viewsPerStatements = consumers.get(statementHandle);
        if (viewsPerStatements == null)
        {
            viewsPerStatements = new ArrayList<NamedWindowConsumerView>();

            // avoid concurrent modification as a thread may currently iterate over consumers as its dispatching
            // without the engine lock
            Map<EPStatementHandle, List<NamedWindowConsumerView>> newConsumers = new HashMap<EPStatementHandle, List<NamedWindowConsumerView>>();
            newConsumers.putAll(consumers);
            newConsumers.put(statementHandle, viewsPerStatements);
            consumers = newConsumers;
        }
        viewsPerStatements.add(consumerView);

        return consumerView;
    }

    /**
     * Called by the consumer view to indicate it was stopped or destroyed, such that the
     * consumer can be deregistered and further dispatches disregard this consumer.
     * @param namedWindowConsumerView is the consumer representative view
     */
    public void removeConsumer(NamedWindowConsumerView namedWindowConsumerView)
    {
        EPStatementHandle handleRemoved = null;
        // Find the consumer view
        for (Map.Entry<EPStatementHandle, List<NamedWindowConsumerView>> entry : consumers.entrySet())
        {
            boolean foundAndRemoved = entry.getValue().remove(namedWindowConsumerView);
            // Remove the consumer view
            if ((foundAndRemoved) && (entry.getValue().size() == 0))
            {
                // Remove the handle if this list is now empty
                handleRemoved = entry.getKey();
            }
            break;
        }
        if (handleRemoved != null)
        {
            consumers.remove(handleRemoved);
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        createWindowStmtHandle.getStatementLock().acquireLock(null);
        try
        {
            Iterator<EventBean> it = parent.iterator();
            if (!it.hasNext())
            {
                return nullIterator;
            }
            ArrayList<EventBean> list = new ArrayList<EventBean>();
            while (it.hasNext())
            {
                list.add(it.next());
            }
            return new ArrayEventIterator(list.toArray(new EventBean[0]));
        }
        finally
        {
            createWindowStmtHandle.getStatementLock().releaseLock(null);
        }
    }

    /**
     * Destroy the view.
     */
    public void destroy()
    {
        consumers.clear();        
    }
}
