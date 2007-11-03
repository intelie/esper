package net.esper.eql.named;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

public class NamedWindowDeleteView extends ViewSupport
{
    private static final Log log = LogFactory.getLog(NamedWindowDeleteView.class);
    private EventType eventType;
    private final DeletionStrategy deletionStrategy;

    public NamedWindowDeleteView(DeletionStrategy deletionStrategy)
    {
        this.deletionStrategy = deletionStrategy;
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
        deletionStrategy.matchedDelete(newData);
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
        return null;  // TODO
    }
}
