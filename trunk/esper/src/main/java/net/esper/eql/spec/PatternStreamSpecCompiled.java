/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.pattern.EvalNode;
import net.esper.event.EventType;
import net.esper.eql.spec.ViewSpec;

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
public class PatternStreamSpecCompiled extends StreamSpecBase implements StreamSpecCompiled
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
    public PatternStreamSpecCompiled(EvalNode evalNode, Map<String, EventType> taggedEventTypes, List<ViewSpec> viewSpecs, String optionalStreamName)
    {
        super(optionalStreamName, viewSpecs);
        this.evalNode = evalNode;

        Map<String, EventType> copy = new HashMap<String, EventType>();
        copy.putAll(taggedEventTypes);
        this.taggedEventTypes = copy;
    }

    /**
     * Returns the pattern expression evaluation node for the top pattern operator.
     * @return parent pattern expression node
     */
    public EvalNode getEvalNode()
    {
        return evalNode;
    }

    /**
     * Returns event types tagged in the pattern expression.
     * @return map of tag and event type tagged in pattern expression
     */
    public Map<String, EventType> getTaggedEventTypes()
    {
        return taggedEventTypes;
    }
}
