package net.esper.core;

import net.esper.event.EventBean;
import net.esper.event.NaturalEventBean;
import net.esper.collection.Pair;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StatementResultNaturalStrategyMap implements StatementResultNaturalStrategy
{
    private static Log log = LogFactory.getLog(StatementResultNaturalStrategyImpl.class);
    private final Object subscriber;
    private final FastMethod fastMethod;
    private final String[] columnNames;

    public StatementResultNaturalStrategyMap(Object subscriber, Method method, String[] columnNames)
    {
        this.subscriber = subscriber;
        FastClass fastClass = FastClass.create(subscriber.getClass());
        this.fastMethod = fastClass.getMethod(method);
        this.columnNames = columnNames;
    }

    public void execute(Pair<EventBean[], EventBean[]> result)
    {
        Map[] newData = convert(result.getFirst());
        Map[] oldData = convert(result.getSecond());

        try {
            fastMethod.invoke(subscriber, new Object[] {newData, oldData});
        }
        catch (InvocationTargetException e) {
            log.error(e);
            // TODO
        }
    }

    private Map[] convert(EventBean[] events)
    {
        if ((events == null) || (events.length == 0))
        {
            return null;
        }

        Map[] result = new Map[events.length];
        int length = 0;
        for (int i = 0; i < result.length; i++)
        {
            if (events[i] instanceof NaturalEventBean)
            {
                NaturalEventBean natural = (NaturalEventBean) events[i];
                result[length] = convert(natural);
                length++;
            }
        }

        if (length == 0)
        {
            return null;
        }
        if (length != events.length)
        {
            Map[] reduced = new Map[length];
            System.arraycopy(result, 0, reduced, 0, length);
            result = reduced;
        }
        return result;
    }

    private Map convert(NaturalEventBean natural)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Object[] columns = natural.getNatural();
        for (int i = 0; i < columns.length; i++)
        {
            map.put(columnNames[i], columns[i]);
        }
        return map;
    }
}
