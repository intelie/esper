package net.esper.eql.named;

import net.esper.core.EPStatementHandle;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.StatementStopService;
import net.esper.view.ViewSupport;

import java.util.*;

public class NamedWindowTailView extends ViewSupport implements Iterable<EventBean>
{
    private final EventType eventType;
    private final NamedWindowRootView namedWindowRootView;
    private final NamedWindowService namedWindowService;
    private final Map<EPStatementHandle, List<NamedWindowConsumerView>> consumers;

    public NamedWindowTailView(EventType eventType, NamedWindowService namedWindowService, NamedWindowRootView namedWindowRootView)
    {
        this.eventType = eventType;
        this.namedWindowService = namedWindowService;
        consumers = new HashMap<EPStatementHandle, List<NamedWindowConsumerView>>();
        this.namedWindowRootView = namedWindowRootView; 
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        // Only old data needs to be removed
        if (oldData != null)
        {
            namedWindowRootView.removeOldData(oldData);
        }

        // Post to child views
        updateChildren(newData, oldData);        

        // Add to dispatch list for later result dispatch by runtime
        NamedWindowDeltaData delta = new NamedWindowDeltaData(newData, oldData);
        namedWindowService.addDispatch(delta, consumers);
    }

    public NamedWindowConsumerView addConsumer(EPStatementHandle statementHandle, StatementStopService statementStopService)
    {
        // Construct consumer view, allow a callback to this view to remove the consumer
        NamedWindowConsumerView consumerView = new NamedWindowConsumerView(eventType, statementStopService, this);

        // Keep a list of consumer views per statement to accomodate joins and subqueries
        List<NamedWindowConsumerView> viewsPerStatements = consumers.get(statementHandle);
        if (viewsPerStatements == null)
        {
            viewsPerStatements = new ArrayList<NamedWindowConsumerView>();
            consumers.put(statementHandle, viewsPerStatements);
        }
        viewsPerStatements.add(consumerView);

        return consumerView;
    }

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
        return this.parent.iterator();
    }

    public void destroy()
    {
        consumers.clear();        
    }
}
