package net.esper.core;

import net.esper.event.EventBean;
import net.esper.event.NaturalEventBean;
import net.esper.collection.Pair;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StatementResultNaturalStrategyImpl implements StatementResultNaturalStrategy
{
    private static Log log = LogFactory.getLog(StatementResultNaturalStrategyImpl.class);
    private final Object subscriber;
    private final FastMethod updateFastMethod;
    private final FastMethod startFastMethod;
    private final FastMethod endFastMethod;
    private final FastMethod updateRStreamFastMethod;
    private final DeliveryConvertor deliveryConvertor;

    public StatementResultNaturalStrategyImpl(Object subscriber, DeliveryConvertor deliveryConvertor, Method method, Method startMethod, Method endMethod, Method rStreamMethod)
    {
        this.subscriber = subscriber;
        this.deliveryConvertor = deliveryConvertor;
        FastClass fastClass = FastClass.create(subscriber.getClass());
        this.updateFastMethod = fastClass.getMethod(method);
        
        if (startMethod != null)
        {
            startFastMethod = fastClass.getMethod(startMethod);
        }
        else
        {
            startFastMethod = null;
        }

        if (endMethod != null)
        {
            endFastMethod = fastClass.getMethod(endMethod);
        }
        else
        {
            endFastMethod = null;
        }

        if (rStreamMethod != null)
        {
            updateRStreamFastMethod = fastClass.getMethod(rStreamMethod);
        }
        else
        {
            updateRStreamFastMethod = null;
        }
    }

    public void execute(Pair<EventBean[], EventBean[]> result)
    {
        if (startFastMethod != null)
        {
            int countNew = count(result.getFirst());
            int countOld = count(result.getSecond());

            try {
                startFastMethod.invoke(subscriber, new Object[] {countNew, countOld});
            }
            catch (InvocationTargetException e) {
                log.error(e);
                // TODO
            }
        }

        EventBean[] newData = result.getFirst();
        EventBean[] oldData = result.getSecond();

        if ((newData != null) && (newData.length > 0)) {
            for (int i = 0; i < newData.length; i++) {
                EventBean event = newData[i];
                if (event instanceof NaturalEventBean) {
                    NaturalEventBean natural = (NaturalEventBean) event;
                    try {
                        Object[] params = deliveryConvertor.convertRow(natural.getNatural());
                        updateFastMethod.invoke(subscriber, params);
                    }
                    catch (InvocationTargetException e) {
                        log.error(e);
                        log.error(e.getTargetException());
                        // TODO
                    }
                }
            }
        }

        if ((updateRStreamFastMethod != null) && (oldData != null) && (oldData.length > 0)) {
            for (int i = 0; i < oldData.length; i++) {
                EventBean event = oldData[i];
                if (event instanceof NaturalEventBean) {
                    NaturalEventBean natural = (NaturalEventBean) event;
                    try {
                        Object[] params = deliveryConvertor.convertRow(natural.getNatural());
                        updateRStreamFastMethod.invoke(subscriber, params);
                    }
                    catch (InvocationTargetException e) {
                        log.error(e);
                        // TODO
                    }
                }
            }
        }

        if (endFastMethod != null) {
            try {
                endFastMethod.invoke(subscriber, null);
            }
            catch (InvocationTargetException e) {
                log.error(e);
                // TODO
            }
        }
    }

    private int count(EventBean[] events) {
        if (events == null)
        {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < events.length; i++)
        {
            EventBean event = events[i];
            if (event instanceof NaturalEventBean)
            {
                count++;
            }
        }
        return count;
    }
}
