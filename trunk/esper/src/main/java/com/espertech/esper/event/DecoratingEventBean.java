package com.espertech.esper.event;

import com.espertech.esper.collection.Pair;

import java.util.Map;

/**
 * Interface for event types that provide decorating event properties as a name-value map.
 */
public interface DecoratingEventBean
{
    /**
     * Returns decorating properties.
     * @return property name and values
     */
    public Map<String, Object> getDecoratingProperties();
}
