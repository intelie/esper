package com.espertech.esper.core;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.NaturalEventBean;
import com.espertech.esper.collection.Pair;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Converts column results into a Map of key-value pairs.
 */
public class DeliveryConvertorMap implements DeliveryConvertor
{
    private final String[] columnNames;

    /**
     * Ctor.
     * @param columnNames the names for columns
     */
    public DeliveryConvertorMap(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public Object[] convertRow(Object[] columns) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < columns.length; i++)
        {
            map.put(columnNames[i], columns[i]);
        }
        return new Object[] {map};
    }
}
