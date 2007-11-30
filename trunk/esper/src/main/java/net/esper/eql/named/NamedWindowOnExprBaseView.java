package net.esper.eql.named;

import net.esper.view.ViewSupport;
import net.esper.view.StatementStopCallback;
import net.esper.view.StatementStopService;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * View for the on-delete statement that handles removing events from a named window.
 */
public abstract class NamedWindowOnExprBaseView extends ViewSupport implements StatementStopCallback
{
    private static final Log log = LogFactory.getLog(NamedWindowOnExprBaseView.class);

    /**
     * The event type of the events hosted in the named window.
     */
    protected final EventType namedWindowEventType;
    private final LookupStrategy lookupStrategy;

    /**
     * The root view accepting removals (old data).
     */
    protected final NamedWindowRootView rootView;

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param lookupStrategy for handling trigger events to determine deleted events
     * @param rootView to indicate which events to delete
     */
    public NamedWindowOnExprBaseView(StatementStopService statementStopService,
                                 LookupStrategy lookupStrategy,
                                 NamedWindowRootView rootView)
    {
        this.lookupStrategy = lookupStrategy;
        this.rootView = rootView;
        statementStopService.addSubscriber(this);
        namedWindowEventType = rootView.getEventType();
    }

    /**
     * Implemented by on-trigger views to action on the combination of trigger and matching events in the named window.
     * @param triggerEvents is the trigger events (usually 1)
     * @param matchingEvents is the matching events retrieved via lookup strategy
     */
    public abstract void handleMatching(EventBean[] triggerEvents, EventBean[] matchingEvents);
    
    public void statementStopped()
    {
        log.debug(".statementStopped");
        rootView.removeOnExpr(lookupStrategy);
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

        // Determine via the lookup strategy a subset of events to process
        EventBean[] eventsFound = lookupStrategy.lookup(newData);

        // Let the implementation handle the delete or
        handleMatching(newData, eventsFound);
    }
}