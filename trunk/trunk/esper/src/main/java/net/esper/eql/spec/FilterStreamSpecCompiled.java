package net.esper.eql.spec;

import net.esper.filter.FilterSpec;
import net.esper.view.ViewSpec;
import net.esper.eql.spec.StreamSpecBase;

import java.util.List;

/**
 * Specification for building an event stream out of a filter for events (supplying type and basic filter criteria)
 * and views onto these events which are staggered onto each other to supply a final stream of events.
 */
public class FilterStreamSpecCompiled extends StreamSpecBase implements StreamSpecCompiled
{
    private FilterSpec filterSpec;

    /**
     * Ctor.
     * @param filterSpec - specifies what events we are interested in.
     * @param viewSpecs - specifies what view to use to derive data
     * @param optionalStreamName - stream name, or null if none supplied
     */
    public FilterStreamSpecCompiled(FilterSpec filterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
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
