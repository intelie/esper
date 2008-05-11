package com.espertech.esper.epl.join.table;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Index that organizes events by the event property values into hash buckets. Based on a HashMap
 * with {@link com.espertech.esper.collection.MultiKeyUntyped} keys that store the property values.
 * <p>
 * Performs coercion of the index keys before storing the keys.
 * <p>
 * Takes a list of property names as parameter. Doesn't care which event type the events have as long as the properties
 * exist. If the same event is added twice, the class throws an exception on add.
 */
public class PropertyIndTableCoerceAdd extends PropertyIndexedEventTable
{
    private static Log log = LogFactory.getLog(PropertyIndTableCoerceAdd.class);
    private final Class[] coercionTypes;

    /**
     * Ctor.
     * @param streamNum is the stream number of the indexed stream
     * @param eventType is the event type of the indexed stream
     * @param propertyNames are the property names to get property values
     * @param coercionType are the classes to coerce indexed values to
     */
    public PropertyIndTableCoerceAdd(int streamNum, EventType eventType, String[] propertyNames, Class[] coercionType)
    {
        super(streamNum, eventType, propertyNames);
        this.coercionTypes = coercionType;
    }

    protected MultiKeyUntyped getMultiKey(EventBean event)
    {
        Object[] keyValues = new Object[propertyGetters.length];
        for (int i = 0; i < propertyGetters.length; i++)
        {
            Object value = propertyGetters[i].get(event);
            Class coercionType = coercionTypes[i];
            if ((value != null) && (!value.getClass().equals(coercionType)))
            {
                if (value instanceof Number)
                {
                    value = JavaClassHelper.coerceBoxed((Number) value, coercionTypes[i]);
                }
            }
            keyValues[i] = value;
        }
        return new MultiKeyUntyped(keyValues);
    }
}
