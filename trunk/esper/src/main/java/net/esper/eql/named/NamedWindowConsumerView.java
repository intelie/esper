package net.esper.eql.named;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.StatementStopService;
import net.esper.view.StatementStopCallback;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.collection.SingleEventIterator;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents a consumer of a named window that selects from a named window via a from-clause.
 * <p>
 * The view simply dispatches directly to child views, and keeps the last new event for iteration.
 */
public class NamedWindowConsumerView extends ViewSupport implements StatementStopCallback
{
    private static final Log log = LogFactory.getLog(NamedWindowConsumerView.class);
    private EventType eventType;
    private NamedWindowTailView tailView;
    private EventBean lastEvent;

    /**
     * Ctor.
     * @param eventType the event type of the named window
     * @param statementStopService for registering a callback when the view stopped, to unregister the statement as a consumer
     * @param tailView to indicate when the consumer stopped to remove the consumer
     */
    public NamedWindowConsumerView(EventType eventType,
                                   StatementStopService statementStopService,
                                   NamedWindowTailView tailView)
    {
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

        if ((newData != null) && (newData.length > 0))
        {
            lastEvent = newData[newData.length - 1];
        }

        updateChildren(newData, oldData);
    }

    public void setParent(Viewable parent)
    {
        eventType = parent.getEventType();
        super.setParent(parent);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return new SingleEventIterator(lastEvent);  // May reach this iterator if a consumer does not declare a window itself 
    }

    public void statementStopped()
    {
        tailView.removeConsumer(this);
    }
}
