package net.esper.eql.expression;

import net.esper.filter.FilterSpec;
import net.esper.view.ViewSpec;

import java.util.List;
import java.util.LinkedList;

/**
 * Specification for building an event stream out of a filter for events (supplying type and basic filter criteria)
 * and views onto these events which are staggered onto each other to supply a final stream of events.
 */
public class FilterStreamSpec extends StreamSpec
{
    private FilterSpec filterSpec;

    /**
     * Ctor.
     * @param filterSpec - specifies what events we are interested in.
     * @param viewSpecs - specifies what view to use to derive data
     * @param optionalStreamName - stream name, or null if none supplied
     */
    public FilterStreamSpec(FilterSpec filterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
        this.filterSpec = filterSpec;
    }

    /**
     * Returns filter specification for which events the stream will getSelectListEvents.
     * @return filter spec
     */
    public FilterSpec getFilterSpec()
    {
        return filterSpec;
    }
}
