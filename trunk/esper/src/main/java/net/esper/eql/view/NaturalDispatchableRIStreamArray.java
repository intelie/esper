package net.esper.eql.view;

import net.esper.dispatch.Dispatchable;
import net.esper.eql.core.NaturalEventBean;
import net.esper.event.EventBean;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

public class NaturalDispatchableRIStreamArray implements Dispatchable
{
    private static final Log log = LogFactory.getLog(NaturalDispatchableRIStreamArray.class);

    private final Class componentType;
    private final Object target;
    private final FastMethod method;
    private final ThreadLocal<Boolean> isDispatchWaiting;

    private EventBean[] newEvents;
    private EventBean[] oldEvents;

    public NaturalDispatchableRIStreamArray(Object target, FastMethod method, ThreadLocal<Boolean> isDispatchWaiting)
    {
        this.target = target;
        this.method = method;
        this.isDispatchWaiting = isDispatchWaiting;
        componentType = method.getParameterTypes()[0].getComponentType();
    }

    public void addParameters(EventBean[] newEvents, EventBean[] oldEvents)
    {
        // TODO: add up buffers, use group-by to test
        this.newEvents = newEvents;
        this.oldEvents = oldEvents;
    }

    public void execute()
    {
        invoke();
        newEvents = null;
        oldEvents = null;
    }

    private Object toArray(EventBean[] events)
    {
        if (events == null)
        {
            return null;
        }
        Object array = Array.newInstance(componentType, events.length);

        int count = 0;
        for (EventBean event : events)
        {
            NaturalEventBean natural = (NaturalEventBean) event;
            Array.set(array, count, natural.getNatural()[0]);
            count++;
        }

        return array;
    }

    private void invoke()
    {
        Object iStreamArray = toArray(newEvents);
        Object rStreamArray = toArray(oldEvents);

        try
        {
            method.invoke(target, new Object[] {iStreamArray, rStreamArray});
        }
        catch (InvocationTargetException e)
        {
            log.error("Error invoking method '" + method.getName() + "': " + e.getMessage(), e);
        }
        isDispatchWaiting.set(false);
    }
}
