package net.esper.eql.core;

import net.esper.client.EPException;
import net.esper.eql.db.PollExecStrategy;
import net.esper.event.EventAdapterService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Viewable providing historical data from a database.
 */
public class MethodPollingExecStrategy implements PollExecStrategy
{
    private static final Log log = LogFactory.getLog(MethodPollingExecStrategy.class);
    private final EventAdapterService eventAdapterService;
    private final FastMethod method;
    private boolean isArray;
    private boolean useMapType;
    private EventType eventType;

    /**
     * Ctor.
     * @param eventAdapterService for generating event beans
     * @param method the method to invoke
     * @param useMapType is true to indicate that Map-events are generated
     * @param eventType is the event type to use
     */
    public MethodPollingExecStrategy(EventAdapterService eventAdapterService, FastMethod method, boolean useMapType, EventType eventType)
    {
        this.eventAdapterService = eventAdapterService;
        this.method = method;
        this.isArray = method.getReturnType().isArray();
        this.useMapType = useMapType;
        this.eventType = eventType;
    }

    public void start()
    {
    }

    public void done()
    {
    }

    public void destroy()
    {
    }

    public List<EventBean> poll(Object[] lookupValues)
    {
        List<EventBean> rowResult = null;
        try
        {
            Object invocationResult = method.invoke(null, lookupValues);
            if (invocationResult != null)
            {
                if (isArray)
                {
                    int length = Array.getLength(invocationResult);
                    if (length > 0)
                    {
                        rowResult = new ArrayList<EventBean>();
                        for (int i = 0; i < length; i++)
                        {
                            Object value = Array.get(invocationResult, i);
                            if (value == null)
                            {
                                log.warn("Expected non-null return result from method '" + method.getName() + "', but received null value");
                                continue;
                            }

                            EventBean event;
                            if (useMapType)
                            {
                                if (!(value instanceof Map))
                                {
                                    log.warn("Expected Map-type return result from method '" + method.getName() + "', but received type '" + value.getClass() + "'");
                                    continue;
                                }
                                Map mapValues = (Map) value;
                                event = eventAdapterService.createMapFromValues(mapValues, eventType);
                            }
                            else
                            {
                                event = eventAdapterService.adapterForBean(value);
                            }

                            rowResult.add(event);
                        }                        
                    }
                }
                else
                {
                    rowResult = new LinkedList<EventBean>();

                    EventBean event;
                    if (useMapType)
                    {
                        if (!(invocationResult instanceof Map))
                        {
                            log.warn("Expected Map-type return result from method '" + method.getName() + "', but received type '" + invocationResult.getClass() + "'");
                        }
                        else
                        {
                            Map mapValues = (Map) invocationResult;
                            event = eventAdapterService.createMapFromValues(mapValues, eventType);
                            rowResult.add(event);
                        }
                    }
                    else
                    {
                        event = eventAdapterService.adapterForBean(invocationResult);
                        rowResult.add(event);
                    }
                }
            }
        }
        catch (InvocationTargetException ex)
        {
            throw new EPException("Error invoking method '" + method.getName() + " on '" + method.getJavaMethod().getDeclaringClass().getName() + "'", ex);
        }

        return rowResult;
    }
}
