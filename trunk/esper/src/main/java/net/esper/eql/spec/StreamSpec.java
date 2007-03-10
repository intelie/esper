package net.esper.eql.spec;

import net.esper.view.ViewSpec;
import java.util.List;

/**
 * Abstract base specification for a stream, consists simply of an optional stream name and a list of views
 * on to of the stream.
 * <p>
 * Implementation classes for views and patterns add additional information defining the
 * stream of events.
 */
public interface StreamSpec
{
    public String getOptionalStreamName();
    public List<ViewSpec> getViewSpecs();
}
