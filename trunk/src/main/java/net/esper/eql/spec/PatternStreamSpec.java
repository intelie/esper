package net.esper.eql.spec;

import net.esper.pattern.EvalNode;
import net.esper.event.EventType;
import net.esper.view.ViewSpec;
import net.esper.eql.spec.StreamSpec;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Specification for building an event stream out of a pattern statement and views staggered onto the
 * pattern statement.
 * <p>
 * The pattern statement is represented by the top EvalNode evaluation node.
 * A pattern statement contains tagged events (i.e. a=A -> b=B).
 * Thus the resulting event type is has properties "a" and "b" of the type of A and B.
 */
public class PatternStreamSpec extends StreamSpec
{
    private final EvalNode evalNode;
    private final Map<String, EventType> taggedEventTypes;       // Stores types for filters with tags

    /**
     * Ctor.
     * @param evalNode - pattern evaluation node representing pattern statement
     * @param viewSpecs - specifies what view to use to derive data
     * @param taggedEventTypes - event tags and their types as specified in the pattern, copied to allow original collection to change
     * @param optionalStreamName - stream name, or null if none supplied
     */
    public PatternStreamSpec(EvalNode evalNode, Map<String, EventType> taggedEventTypes, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
        this.evalNode = evalNode;

        Map<String, EventType> copy = new HashMap<String, EventType>();
        copy.putAll(taggedEventTypes);
        this.taggedEventTypes = copy;
    }

    public EvalNode getEvalNode()
    {
        return evalNode;
    }

    public Map<String, EventType> getTaggedEventTypes()
    {
        return taggedEventTypes;
    }
}
