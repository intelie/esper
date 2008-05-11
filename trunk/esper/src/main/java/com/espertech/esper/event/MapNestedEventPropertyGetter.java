package com.espertech.esper.event;

import java.util.Map;
import java.util.Stack;

/**
 * A getter for querying Map-within-Map event properties.
 */
public class MapNestedEventPropertyGetter implements EventPropertyGetter
{
    private final Stack<String> accessPath;

    /**
     * Ctor.
     * @param accessPath is the properties to follow down into nested maps.
     */
    public MapNestedEventPropertyGetter(Stack<String> accessPath) {
        this.accessPath = new Stack<String>();
        this.accessPath.addAll(accessPath);
    }

    /**
     * Ctor.
     * @param accessPath is the properties to follow down into nested maps.
     * @param leaf the last property we are looking for in the final map
     */
    public MapNestedEventPropertyGetter(Stack<String> accessPath, String leaf) {
        this.accessPath = new Stack<String>();
        this.accessPath.addAll(accessPath);
        this.accessPath.add(leaf);
    }

    public Object get(EventBean obj)
    {
        // The underlying is expected to be a map
        if (!(obj.getUnderlying() instanceof Map))
        {
            throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                    "the underlying data object is not of type java.lang.Map");
        }

        Map map = (Map) obj.getUnderlying();
        Object value = null;

        // We are dealing with a nested map structure
        for (String next : accessPath)
        {
            value = map.get(next);
            if (value == null)
            {
                return null;
            }

            if (!(value instanceof Map))
            {
                return value;
            }

            map = (Map) value;
        }

        // If the map does not contain the key, this is allowed and represented as null
        return value;
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }
}
