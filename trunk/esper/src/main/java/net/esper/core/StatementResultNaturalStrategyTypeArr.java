package net.esper.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.FastClass;
import net.esper.event.EventBean;
import net.esper.event.NaturalEventBean;
import net.esper.collection.Pair;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Array;

public class StatementResultNaturalStrategyTypeArr implements StatementResultNaturalStrategy
{
    private static Log log = LogFactory.getLog(StatementResultNaturalStrategyImpl.class);
    private final Object subscriber;
    private final FastMethod fastMethod;
    private final Class componentType;

    public StatementResultNaturalStrategyTypeArr(Object subscriber, Method method)
    {
        this.subscriber = subscriber;
        FastClass fastClass = FastClass.create(subscriber.getClass());
        this.fastMethod = fastClass.getMethod(method);
        componentType = method.getParameterTypes()[0].getComponentType();
    }

    public void execute(Pair<EventBean[], EventBean[]> result)
    {
        Object newData = convert(result.getFirst());
        Object oldData = convert(result.getSecond());

        try {
            fastMethod.invoke(subscriber, new Object[] {newData, oldData});
        }
        catch (InvocationTargetException e) {
            log.error(e);
            // TODO
        }
    }

    private Object convert(EventBean[] events)
    {
        if ((events == null) || (events.length == 0))
        {
            return null;
        }

        Object array = Array.newInstance(componentType, events.length);
        int length = 0;
        for (int i = 0; i < events.length; i++)
        {
            if (events[i] instanceof NaturalEventBean)
            {
                NaturalEventBean natural = (NaturalEventBean) events[i];
                Array.set(array, length, natural.getNatural()[0]);
                length++;
            }
        }

        if (length == 0)
        {
            return null;
        }
        if (length != events.length)
        {
            Object reduced = Array.newInstance(componentType, events.length);
            System.arraycopy(array, 0, reduced, 0, length);
            array = reduced;
        }
        return array;
    }
}
