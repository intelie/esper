package net.esper.eql.named;

import net.esper.collection.ArrayEventIterator;
import net.esper.collection.MultiKey;
import net.esper.collection.Pair;
import net.esper.core.EPStatementHandle;
import net.esper.core.InternalEventRouter;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.StatementStopService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * View for the on-select statement that handles selecting events from a named window.
 */
public class NamedWindowOnSelectView extends NamedWindowOnExprBaseView
{
    private static final Log log = LogFactory.getLog(NamedWindowOnSelectView.class);

    private final InternalEventRouter internalEventRouter;
    private final ResultSetProcessor optionalResultSetProcessor;
    private final EPStatementHandle statementHandle;
    private EventBean[] lastResult;
    private Set<MultiKey<EventBean>> oldEvents = new HashSet<MultiKey<EventBean>>();

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param lookupStrategy for handling trigger events to determine deleted events
     * @param rootView the named window root view
     * @param internalEventRouter for insert-into behavior
     * @param optionalResultSetProcessor for processing aggregation, having and ordering
     * @param statementHandle required for routing events
     */
    public NamedWindowOnSelectView(StatementStopService statementStopService,
                                   LookupStrategy lookupStrategy,
                                   NamedWindowRootView rootView,
                                   InternalEventRouter internalEventRouter,
                                   ResultSetProcessor optionalResultSetProcessor,
                                   EPStatementHandle statementHandle)
    {
        super(statementStopService, lookupStrategy, rootView);
        this.internalEventRouter = internalEventRouter;
        this.optionalResultSetProcessor = optionalResultSetProcessor;
        this.statementHandle = statementHandle;
    }

    public void handleMatching(EventBean[] triggerEvents, EventBean[] matchingEvents)
    {
        EventBean[] newData = null;

        if (optionalResultSetProcessor != null)
        {
            // clear state from prior results
            optionalResultSetProcessor.clear();

            // build join result
            Set<MultiKey<EventBean>> newEvents = new HashSet<MultiKey<EventBean>>();
            for (int i = 0; i < triggerEvents.length; i++)
            {
                EventBean triggerEvent = triggerEvents[0];
                if (matchingEvents != null)
                {
                    for (int j = 0; j < matchingEvents.length; j++)
                    {
                        EventBean[] eventsPerStream = new EventBean[2];
                        eventsPerStream[0] = matchingEvents[j];
                        eventsPerStream[1] = triggerEvent;
                        newEvents.add(new MultiKey<EventBean>(eventsPerStream));
                    }
                }
            }
            
            // process matches
            Pair<EventBean[], EventBean[]> pair = optionalResultSetProcessor.processJoinResult(newEvents, oldEvents);
            newData = pair.getFirst();
        }
        else
        {
            newData = matchingEvents;
        }

        if (internalEventRouter != null)
        {
            internalEventRouter.route(newData, statementHandle);
        }

        // The on-select listeners receive the events selected
        if ((newData != null) && (newData.length > 0))
        {
            updateChildren(newData, null);
        }
        lastResult = newData;
    }

    public EventType getEventType()
    {
        if (optionalResultSetProcessor != null)
        {
            return optionalResultSetProcessor.getResultEventType();
        }
        else
        {
            return namedWindowEventType;
        }
    }

    public Iterator<EventBean> iterator()
    {
        return new ArrayEventIterator(lastResult);
    }
}
