package net.esper.eql.expression;

import net.esper.view.ViewSpec;
import net.esper.filter.FilterSpec;

import java.util.List;
import java.util.LinkedList;

/**
 * Abstract base specification for a stream, consists simply of an optional stream name.
 * <p>
 * Implementation classes for views and patterns add additional information defining the
 * stream of events.
 */
public abstract class StreamSpec
{
    private String optionalStreamName;

    /**
     * Ctor.
     * @param optionalStreamName - stream name, or null if none supplied
     */
    public StreamSpec(String optionalStreamName)
    {
        this.optionalStreamName = optionalStreamName;
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
