package com.espertech.esper.epl.spec;

import java.util.List;

/**
 * Specification for a pattern observer object consists of a namespace, name and object parameters. 
 */
public final class PatternObserverSpec extends ObjectSpec
{
    /**
     * Constructor.
     * @param namespace if the namespace the object is in
     * @param objectName is the name of the object
     * @param objectParameters is a list of values representing the object parameters
     */
    public PatternObserverSpec(String namespace, String objectName, List<Object> objectParameters)
    {
        super(namespace, objectName, objectParameters);
    }
}
