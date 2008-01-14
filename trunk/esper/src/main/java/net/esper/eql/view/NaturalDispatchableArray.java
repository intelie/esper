package net.esper.eql.view;

import net.esper.dispatch.Dispatchable;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Array;

public class NaturalDispatchableArray implements Dispatchable
{
    private static final Log log = LogFactory.getLog(NaturalDispatchableArray.class);

    private final Class componentType;
    private final Object target;
    private final FastMethod method;
    private final ThreadLocal<Boolean> isDispatchWaiting;

    private ArrayList<Object> parameters;

    public NaturalDispatchableArray(Object target, FastMethod method, ThreadLocal<Boolean> isDispatchWaiting)
    {
        this.target = target;
        this.method = method;
        this.isDispatchWaiting = isDispatchWaiting;
        parameters = new ArrayList<Object>();
        componentType = method.getParameterTypes()[0].getComponentType();
    }

    public void addParameters(Object[] parametersToAdd)
    {
        this.parameters.add(parametersToAdd[0]);
    }

    public void execute()
    {
        invoke(parameters);
        parameters = new ArrayList<Object>();
    }

    private void invoke(ArrayList<Object> parameters)
    {
        Object array = Array.newInstance(componentType, parameters.size());

        int count = 0;
        for (Object param : parameters)
        {
            Array.set(array, count, param);
            count++;
        }

        try
        {
            method.invoke(target, new Object[] {array});
        }
        catch (InvocationTargetException e)
        {
            log.error("Error invoking method '" + method.getName() + "': " + e.getMessage(), e);
        }
        isDispatchWaiting.set(false);
    }
}
