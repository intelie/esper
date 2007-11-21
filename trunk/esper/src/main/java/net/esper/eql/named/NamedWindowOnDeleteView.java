package net.esper.eql.named;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.*;
import net.esper.collection.ArrayEventIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

public class NamedWindowOnDeleteView extends NamedWindowOnExprBaseView
{
    private static final Log log = LogFactory.getLog(NamedWindowOnDeleteView.class);
    private EventBean[] lastResult;

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param lookupStrategy for handling trigger events to determine deleted events
     * @param removeStreamView to indicate which events to delete
     */
    public NamedWindowOnDeleteView(StatementStopService statementStopService,
                                 LookupStrategy lookupStrategy,
                                 NamedWindowRootView removeStreamView)
    {
        super(statementStopService, lookupStrategy, removeStreamView);
    }

    public void handleMatching(EventBean[] matchingEvents)
    {
        if ((matchingEvents != null) && (matchingEvents.length > 0))
        {
            // Events to delete are indicated via old data
            this.rootView.update(null, matchingEvents);

            // The on-delete listeners receive the events deleted
            updateChildren(matchingEvents, null);
        }

        // Keep the last delete records
        lastResult = matchingEvents;
    }

    public EventType getEventType()
    {
        return namedWindowEventType;
    }

    public Iterator<EventBean> iterator()
    {
        return new ArrayEventIterator(lastResult);
    }
}
