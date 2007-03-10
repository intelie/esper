package net.esper.eql.spec;

import net.esper.view.ViewSpec;
import java.util.List;

/**
 * Specification for a stream, consists simply of an optional stream name and a list of views
 * on to of the stream.
 * <p>
 * Implementation classes for views and patterns add additional information defining the
 * stream of events.
 */
public interface StreamSpec
{
    /**
     * Returns the stream name, or null if undefined.
     * @return stream name
     */
    public String getOptionalStreamName();

    /**
     * Returns views definitions onto the stream
     * @return view defs
     */
    public List<ViewSpec> getViewSpecs();
}
