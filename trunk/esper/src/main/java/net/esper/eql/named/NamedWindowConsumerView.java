package net.esper.eql.named;

import net.esper.collection.SingleEventIterator;
import net.esper.collection.OneEventLinkedList;
import net.esper.eql.expression.ExprNode;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.view.StatementStopCallback;
import net.esper.view.StatementStopService;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a consumer of a named window that selects from a named window via a from-clause.
 * <p>
 * The view simply dispatches directly to child views, and keeps the last new event for iteration.
 */
public class NamedWindowConsumerView extends ViewSupport implements StatementStopCallback
{
    private static final Log log = LogFactory.getLog(NamedWindowConsumerView.class);
    private final List<ExprNode> filterList;
    private final EventType eventType;
    private final NamedWindowTailView tailView;
    private EventBean lastEvent;
    private EventBean[] eventPerStream = new EventBean[1];

    /**
     * Ctor.
     * @param eventType the event type of the named window
     * @param statementStopService for registering a callback when the view stopped, to unregister the statement as a consumer
     * @param tailView to indicate when the consumer stopped to remove the consumer
     * @param filterList is a list of filter expressions
     */
    public NamedWindowConsumerView(List<ExprNode> filterList,
                                   EventType eventType,
                                   StatementStopService statementStopService,
                                   NamedWindowTailView tailView)
    {
        this.filterList = filterList;
        this.eventType = eventType;
        this.tailView = tailView;
        statementStopService.addSubscriber(this);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        // if we have a filter for the named window,
        if (!filterList.isEmpty())
        {
            newData = passFilter(newData, true);
            oldData = passFilter(oldData, false);
        }
        
        if ((newData != null) && (newData.length > 0))
        {
            lastEvent = newData[newData.length - 1];
        }

        if ((newData != null) || (oldData != null))
        {
            updateChildren(newData, oldData);
        }
    }

    private EventBean[] passFilter(EventBean[] eventData, boolean isNewData)
    {
        if ((eventData == null) || (eventData.length == 0))
        {
            return null;
        }
        
        OneEventLinkedList filtered = null;
        for (EventBean event : eventData)
        {
            eventPerStream[0] = event;
            boolean pass = true;
            for (ExprNode filter : filterList)
            {
                Boolean result = (Boolean) filter.evaluate(eventPerStream, isNewData);
                if ((result != null) && (!result))
                {
                    pass = false;
                    break;
                }
            }

            if (pass)
            {
                if (filtered == null)
                {
                    filtered = new OneEventLinkedList();
                }
                filtered.add(event);
            }
        }

        if (filtered == null)
        {
            return null;
        }
        return filtered.toArray();
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return new FilteredEventIterator(filterList, tailView.iterator()); 
    }

    public void statementStopped()
    {
        tailView.removeConsumer(this);
    }
}
