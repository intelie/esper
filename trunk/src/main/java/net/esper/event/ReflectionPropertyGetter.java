package net.esper.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Property getter using Java's vanilla reflection.
 */
public final class ReflectionPropertyGetter implements EventPropertyGetter
{
    private final Method method;

    /**
     * Constructor.
     * @param method is the regular reflection method to use to obtain values for a field.
     */
    public ReflectionPropertyGetter(Method method)
    {
        this.method = method;
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        try
        {
            return method.invoke(underlying, (Object[]) null);
        }
        catch (IllegalArgumentException e)
        {
            throw new PropertyAccessException("Mismatched getter instance to event bean type");
        }
        catch (IllegalAccessException e)
        {
            throw new PropertyAccessException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new PropertyAccessException(e);
        }
    }

    public String toString()
    {
        return "ReflectionPropertyGetter " +
                "method=" + method.toGenericString();
    }
}