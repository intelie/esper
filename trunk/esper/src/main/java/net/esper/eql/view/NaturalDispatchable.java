package net.esper.eql.view;

import net.esper.dispatch.Dispatchable;
import net.sf.cglib.reflect.FastMethod;

import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NaturalDispatchable implements Dispatchable
{
    private static final Log log = LogFactory.getLog(NaturalDispatchable.class);

    private final Object target;
    private final FastMethod method;
    private final ThreadLocal<Boolean> isDispatchWaiting;
    
    private Object[] parameters;
    private ArrayList<Object[]> moreParameters;

    public NaturalDispatchable(Object target, FastMethod method, ThreadLocal<Boolean> isDispatchWaiting)
    {
        this.target = target;
        this.method = method;
        this.isDispatchWaiting = isDispatchWaiting;
        moreParameters = new ArrayList<Object[]>();
    }

    public void addParameters(Object[] parametersToAdd)
    {
        if (parameters != null)
        {
            moreParameters.add(parametersToAdd);
        }
        else
        {            
            this.parameters = parametersToAdd;
        }
    }

    public void execute()
    {
        invoke(parameters);
        for (Object[] moreParameter : moreParameters)
        {
            invoke(moreParameter);
        }
        moreParameters.clear();
        parameters = null;
    }

    private void invoke(Object[] parameters)
    {
        try
        {
            method.invoke(target, parameters);
        }
        catch (InvocationTargetException e)
        {
            log.error("Error invoking method '" + method.getName() + "': " + e.getMessage(), e);
        }
        isDispatchWaiting.set(false);
    }
}
