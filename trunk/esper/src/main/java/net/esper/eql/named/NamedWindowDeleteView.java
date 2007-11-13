package net.esper.eql.named;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.view.*;
import net.esper.collection.SingleEventIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

/**
 * View for the on-delete statement that handles removing events from a named window.
 */
public class NamedWindowDeleteView extends ViewSupport implements StatementStopCallback
{
    private static final Log log = LogFactory.getLog(NamedWindowDeleteView.class);
    private final EventType eventType;
    private final DeletionStrategy deletionStrategy;
    private final NamedWindowRootView removeStreamView;
    private EventBean lastEvent;

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param deletionStrategy for handling trigger events to determine deleted events
     * @param removeStreamView to indicate which events to delete
     */
    public NamedWindowDeleteView(StatementStopService statementStopService,
                                 DeletionStrategy deletionStrategy,
                                 NamedWindowRootView removeStreamView)
    {
        this.deletionStrategy = deletionStrategy;
        this.removeStreamView = removeStreamView;
        statementStopService.addSubscriber(this);
        eventType = removeStreamView.getEventType();
    }

    public void statementStopped()
    {
        log.debug(".statementStopped");
        removeStreamView.removeDeleter(deletionStrategy);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if (newData == null)
        {
            return;
        }

        EventBean[] eventsToDelete = deletionStrategy.determineRemoveStream(newData);
        if ((eventsToDelete != null) && (eventsToDelete.length > 0))
        {
            removeStreamView.update(null, eventsToDelete);
            updateChildren(eventsToDelete, null);            
            lastEvent = eventsToDelete[eventsToDelete.length - 1];
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return new SingleEventIterator(lastEvent);  
    }
}
