package net.esper.eql.named;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.StatementStopService;
import net.esper.view.StatementStopCallback;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.util.ExecutionPathDebugLog;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Dispatch view dispatches to child views, which is always just 1.
 * TODO
 */
public class NamedWindowConsumerView extends ViewSupport implements StatementStopCallback
{
    private static final Log log = LogFactory.getLog(NamedWindowConsumerView.class);
    private EventType eventType;
    private NamedWindowDeltaView deltaView;

    public NamedWindowConsumerView(EventType eventType,
                                   StatementStopService statementStopService,
                                   NamedWindowDeltaView deltaView)
    {
        this.eventType = eventType;
        this.deltaView = deltaView;
        statementStopService.addSubscriber(this);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
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
        return null;  // TODO
    }

    public void statementStopped()
    {
        deltaView.removeConsumer(this);
    }
}
