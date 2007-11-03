package net.esper.eql.named;

import net.esper.core.EPStatementHandle;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.spec.OnDeleteDesc;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.View;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.StatementStopService;

import java.util.*;

public class NamedWindowDeltaView extends ViewSupport
{
    private final EventType eventType;
    private final View optionalRootView;
    private final NamedWindowService namedWindowService;
    private final Map<EPStatementHandle, List<NamedWindowConsumerView>> consumers;

    private final EventTable table;

    public NamedWindowDeltaView(EventType eventType, NamedWindowService namedWindowService, Viewable eventStream, StatementStopService statementStopService)
    {
        this.eventType = eventType;
        this.namedWindowService = namedWindowService;
        consumers = new HashMap<EPStatementHandle, List<NamedWindowConsumerView>>();
        table = new UnindexedEventTable(0);

        if (eventStream instanceof View)
        {
            optionalRootView = (View) eventStream;
        }
        else
        {
            optionalRootView = null;
        }
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

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        // Keep for later dispatch
        NamedWindowDeltaData delta = new NamedWindowDeltaData(newData, oldData);
        namedWindowService.addDispatch(delta, consumers);

        // Update child views
        updateChildren(newData, oldData);

        // Add new data to tables, TODO: multiple tables
        table.add(newData);
        table.remove(oldData);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return null;  // TODO
    }

    public NamedWindowDeleteView addDeleter(OnDeleteDesc onDeleteDesc)
    {
        View removeStreamView = this;
        if (optionalRootView != null)
        {
            removeStreamView = optionalRootView;
        }
        DeletionStrategyImpl strategy = new DeletionStrategyImpl(table, onDeleteDesc.getJoinExpr(), removeStreamView);
        return new NamedWindowDeleteView(strategy);
    }

}
