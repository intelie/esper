package net.esper.eql.core;

import net.esper.client.EPException;
import net.esper.eql.db.PollExecStrategy;
import net.esper.event.EventAdapterService;
import net.esper.event.EventBean;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Viewable providing historical data from a database.
 */
public class MethodPollingExecStrategy implements PollExecStrategy
{
    private static final Log log = LogFactory.getLog(MethodPollingExecStrategy.class);
    private final EventAdapterService eventAdapterService;
    private final FastMethod method;
    private boolean isArray;

    /**
     * Ctor.
     * @param eventAdapterService for generating event beans
     * @param method the method to invoke
     */
    public MethodPollingExecStrategy(EventAdapterService eventAdapterService, FastMethod method)
    {
        this.eventAdapterService = eventAdapterService;
        this.method = method;
        this.isArray = method.getReturnType().isArray();
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
                            EventBean event = eventAdapterService.adapterForBean(Array.get(invocationResult, i));
                            rowResult.add(event);
                        }                        
                    }
                }
                else
                {
                    rowResult = new LinkedList<EventBean>();
                    EventBean event = eventAdapterService.adapterForBean(invocationResult);
                    rowResult.add(event);
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
