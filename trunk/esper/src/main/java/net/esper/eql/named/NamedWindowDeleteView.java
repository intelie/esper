package net.esper.eql.named;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.view.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

public class NamedWindowDeleteView extends ViewSupport implements StatementStopCallback
{
    private static final Log log = LogFactory.getLog(NamedWindowDeleteView.class);
    private EventType eventType;
    private final DeletionStrategy deletionStrategy;
    private final NamedWindowRootView removeStreamView;

    public NamedWindowDeleteView(StatementStopService statementStopService,
                                 DeletionStrategy deletionStrategy,
                                 NamedWindowRootView removeStreamView)
    {
        this.deletionStrategy = deletionStrategy;
        this.removeStreamView = removeStreamView;
        statementStopService.addSubscriber(this);
    }

    public void statementStopped()
    {
        log.debug(".statementStopped");
        removeStreamView.removeDeleter(deletionStrategy);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
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
        }
    }

    public void setParent(Viewable parent)
    {
        eventType = parent.getEventType();
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        throw new UnsupportedOperationException("On-delete expression does not support iteration");  
    }
}
