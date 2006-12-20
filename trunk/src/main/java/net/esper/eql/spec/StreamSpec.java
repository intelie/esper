package net.esper.eql.spec;

import net.esper.view.ViewSpec;
import java.util.List;
import java.util.LinkedList;

/**
 * Abstract base specification for a stream, consists simply of an optional stream name and a list of views
 * on to of the stream.
 * <p>
 * Implementation classes for views and patterns add additional information defining the
 * stream of events.
 */
public abstract class StreamSpec
{
    private String optionalStreamName;
    private List<ViewSpec> viewSpecs = new LinkedList<ViewSpec>();

    /**
     * Ctor.
     * @param optionalStreamName - stream name, or null if none supplied
     * @param viewSpecs - specifies what view to use to derive data
     */
    public StreamSpec(String optionalStreamName, List<ViewSpec> viewSpecs)
    {
        this.optionalStreamName = optionalStreamName;
        this.viewSpecs.addAll(viewSpecs);
    }

    /**
     * Returns the name assigned.
     * @return stream name or null if not assigned
     */
    public String getOptionalStreamName()
    {
        return optionalStreamName;
    }

    /**
     * Returns view definitions to use to construct views to derive data on stream.
     * @return view defs
     */
    public List<ViewSpec> getViewSpecs()
    {
        return viewSpecs;
    }
}
