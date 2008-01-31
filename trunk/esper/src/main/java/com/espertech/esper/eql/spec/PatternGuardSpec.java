package com.espertech.esper.eql.spec;

import java.util.List;

/**
 * Specification for a pattern guard object consists of a namespace, name and guard object parameters. 
 */
public final class PatternGuardSpec extends ObjectSpec
{
    /**
     * Constructor.
     * @param namespace if the namespace the object is in
     * @param objectName is the name of the object
     * @param objectParameters is a list of values representing the object parameters
     */
    public PatternGuardSpec(String namespace, String objectName, List<Object> objectParameters)
    {
        super(namespace, objectName, objectParameters);
    }
}
