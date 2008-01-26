package net.esper.eql.lookup;

import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;

/**
 * Index lookup strategy that coerces the key values before performing a lookup.
 */
public class IndexedTableLookupStrategyCoercing extends IndexedTableLookupStrategy
{
    private Class[] coercionTypes;

    /**
     * Ctor.
     * @param eventTypes is the event type per stream
     * @param streamNumbers is the stream numbers to get keys from
     * @param properties is the property names
     * @param index is the table to look into
     * @param coercionTypes is the types to coerce to before lookup
     */
    public IndexedTableLookupStrategyCoercing(EventType[] eventTypes, int[] streamNumbers, String[] properties, PropertyIndexedEventTable index, Class[] coercionTypes)
    {
        super(eventTypes, streamNumbers, properties, index);
        this.coercionTypes = coercionTypes;
    }

    protected Object[] getKeys(EventBean[] eventsPerStream)
    {
        Object[] keyValues = new Object[propertyGetters.length];
        for (int i = 0; i < propertyGetters.length; i++)
        {
            int streamNum = streamNumbers[i];
            EventBean event = eventsPerStream[streamNum];
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
        return keyValues;
    }
}
