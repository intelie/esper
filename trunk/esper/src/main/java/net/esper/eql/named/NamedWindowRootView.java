package net.esper.eql.named;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.View;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.spec.OnDeleteDesc;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NamedWindowRootView extends ViewSupport
{
    private static final Log log = LogFactory.getLog(NamedWindowRootView.class);

    private EventType eventType;
    private final EventTable table;

    public NamedWindowRootView()
    {
        table = new UnindexedEventTable(0);
    }

    // Called by tail view to indicate that the view removed data
    public void removeOldData(EventBean[] oldData)
    {
        table.remove(oldData);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        // Add to index: TODO is multiple tables
        table.add(newData);
        table.remove(oldData);

        // Update child views
        updateChildren(newData, oldData);
    }

    public NamedWindowDeleteView addDeleter(OnDeleteDesc onDeleteDesc)
    {
        View removeStreamView = this;
        DeletionStrategyImpl strategy = new DeletionStrategyImpl(table, onDeleteDesc.getJoinExpr(), removeStreamView);
        return new NamedWindowDeleteView(strategy);
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        eventType = parent.getEventType();
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return null; 
    }
}
