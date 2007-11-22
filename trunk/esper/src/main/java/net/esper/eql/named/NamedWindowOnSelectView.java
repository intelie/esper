package net.esper.eql.named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.view.StatementStopService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.core.InternalEventRouter;
import net.esper.core.EPStatementHandle;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.collection.Pair;
import net.esper.collection.ArrayEventIterator;

import java.util.Iterator;

public class NamedWindowOnSelectView extends NamedWindowOnExprBaseView
{
    private static final Log log = LogFactory.getLog(NamedWindowOnSelectView.class);

    private final InternalEventRouter internalEventRouter;
    private final ResultSetProcessor optionalResultSetProcessor;
    private final EPStatementHandle statementHandle;
    private EventBean[] lastResult;

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param lookupStrategy for handling trigger events to determine deleted events
     * @param rootView the named window root view
     * @param internalEventRouter
     * @param optionalResultSetProcessor
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

    public void handleMatching(EventBean[] matchingEvents)
    {
        EventBean[] newData = null;

        if (optionalResultSetProcessor != null)
        {
            // clear state from prior results
            optionalResultSetProcessor.clear();

            // process matches
            Pair<EventBean[], EventBean[]> pair = optionalResultSetProcessor.processViewResult(matchingEvents, null);
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
