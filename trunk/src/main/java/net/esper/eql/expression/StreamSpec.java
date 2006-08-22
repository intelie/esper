package net.esper.eql.expression;

import net.esper.view.ViewSpec;
import net.esper.filter.FilterSpec;

import java.util.List;
import java.util.LinkedList;

/**
 * Specification for building a stream.
 */
public class StreamSpec
{
    private FilterSpec filterSpec;
    private List<ViewSpec> viewSpecs = new LinkedList<ViewSpec>();
    private String optionalStreamName;

    /**
     * Ctor.
     * @param filterSpec - specifies what events we are interested in.
     * @param viewSpecs - specifies what view to use to derive data
     * @param optionalStreamName - stream name, or null if none supplied
     */
    public StreamSpec(FilterSpec filterSpec, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        this.filterSpec = filterSpec;
        this.viewSpecs = viewSpecs;
        this.optionalStreamName = optionalStreamName;
    }

    /**
     * Returns filter specification for which events the stream will getSelectListEvents.
     * @return filter spec
     */
    public FilterSpec getFilterSpec()
    {
        return filterSpec;
    }

    /**
     * Returns view definitions to use to construct views to derive data on stream.
     * @return view defs
     */
    public List<ViewSpec> getViewSpecs()
    {
        return viewSpecs;
    }

    /**
     * Returns the name assigned.
     * @return stream name or null if not assigned
     */
    public String getOptionalStreamName()
    {
        return optionalStreamName;
    }
}
