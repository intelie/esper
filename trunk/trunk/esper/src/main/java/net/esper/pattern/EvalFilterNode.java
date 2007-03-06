package net.esper.pattern;

import net.esper.filter.FilterSpec;
import net.esper.eql.spec.FilterSpecRaw;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * This class represents a filter of events in the evaluation tree representing any event expressions.
 */
public final class EvalFilterNode extends EvalNode
{
    private final FilterSpecRaw rawFilterSpec;
    private final String eventAsName;
    private FilterSpec filterSpec;

    public final EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".newState");
        }

        if (!getChildNodes().isEmpty())
        {
            throw new IllegalStateException("Expected number of child nodes incorrect, expected no child nodes, found "
                    + getChildNodes().size());
        }

        return new EvalFilterStateNode(parentNode, filterSpec, eventAsName, beginState, context);
    }

    /**
     * Constructor.
     * @param filterSpecification specifies the filter properties
     * @param eventAsName is the name to use for adding matching events to the MatchedEventMap
     * table used when indicating truth value of true.
     */
    public EvalFilterNode(FilterSpecRaw filterSpecification,
                                String eventAsName)
    {
        this.rawFilterSpec = filterSpecification;
        this.eventAsName = eventAsName;
    }

    public FilterSpecRaw getRawFilterSpec()
    {
        return rawFilterSpec;
    }

    /**
     * Returns filter specification.
     * @return filter definition
     */
    public final FilterSpec getFilterSpec()
    {
        return filterSpec;
    }

    /**
     * Returns the tag for any matching events to this filter, or null since tags are optional.
     * @return tag string for event
     */
    public final String getEventAsName()
    {
        return eventAsName;
    }

    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend"})
    public final String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("EvalFilterNode rawFilterSpec=" + this.rawFilterSpec);
        buffer.append(" filterSpec=" + this.filterSpec);
        buffer.append(" eventAsName=" + this.eventAsName);
        return buffer.toString();
    }

    private static final Log log = LogFactory.getLog(EvalFilterNode.class);
}
