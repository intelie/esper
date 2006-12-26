package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.filter.FilterCallback;
import net.esper.filter.FilterSpec;
import net.esper.filter.FilterValueSet;
import net.esper.event.EventBean;

/**
 * This class contains the state of a single filter expression in the evaluation state tree.
 */
public final class EvalFilterStateNode extends EvalStateNode implements FilterCallback
{
    private final FilterSpec filterSpec;
    private final String eventAsName;
    private final MatchedEventMap beginState;
    private final PatternContext context;

    private boolean isStarted;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param filterSpec is the filter definition
     * @param eventAsName is the name to use to store the event
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required
     */
    public EvalFilterStateNode(Evaluator parentNode,
                                     FilterSpec filterSpec,
                                     String eventAsName,
                                     MatchedEventMap beginState,
                                     PatternContext context)
    {
        super(parentNode);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        this.filterSpec = filterSpec;
        this.eventAsName = eventAsName;
        this.beginState = beginState;
        this.context = context;
    }

    public final void start()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting filter expression");
        }

        if (isStarted)
        {
            throw new IllegalStateException("Filter state node already active");
        }

        // Start the filter
        isStarted = true;
        startFiltering();
    }

    public final void quit()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".guardQuit Stop filter expression");
        }

        isStarted = false;
        stopFiltering();
    }

    private void evaluateTrue(MatchedEventMap event, boolean isQuitted)
    {
        this.getParentEvaluator().evaluateTrue(event, this, isQuitted);
    }

    public final void matchFound(EventBean event)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".matchFound Filter node received match");
        }

        if (!isStarted)
        {
            log.debug(".matchFound Match ignored, filter was stopped");
            return;
        }

        MatchedEventMap passUp = beginState.shallowCopy();

        // Add event itself to the match event structure if a tag was provided
        if (eventAsName != null)
        {
            passUp.add(eventAsName, event);
        }

        // Explanation for the type cast...
        // Each state node stops listening if it resolves to true, and all nodes newState
        // new listeners again. However this would be a performance drain since
        // and expression such as "on all b()" would remove the listener for b() for every match
        // and the all node would newState a new listener. The remove operation and the add operation
        // therefore don't take place if the EvalEveryStateNode node sits on top of a EvalFilterStateNode node.
        boolean isQuitted = false;
        if (!(this.getParentEvaluator() instanceof EvalEveryStateNode))
        {
            stopFiltering();
            isQuitted = true;
        }

        this.evaluateTrue(passUp, isQuitted);
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        return data;
    }

    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend"})
    public final String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("EvalFilterStateNode spec=" + this.filterSpec);
        buffer.append(" tag=" + this.filterSpec);
        return buffer.toString();
    }

    private void startFiltering()
    {
        FilterValueSet filterValues = filterSpec.getValueSet(beginState);
        context.getFilterService().add(filterValues, this);
    }

    private void stopFiltering()
    {
        context.getFilterService().remove(this);
    }

    private static final Log log = LogFactory.getLog(EvalFilterStateNode.class);
}
