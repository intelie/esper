package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Getter for an array property identified by a given index, using vanilla reflection.
 */
public class ArrayMethodPropertyGetter implements EventPropertyGetter
{
    private final Method method;
    private final int index;

    /**
     * Constructor.
     * @param method is the method to use to retrieve a value from the object
     * @param index is tge index within the array to get the property from
     */
    public ArrayMethodPropertyGetter(Method method, int index)
    {
        this.index = index;
        this.method = method;

        if (index < 0)
        {
            throw new IllegalArgumentException("Invalid negative index value");
        }
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        try
        {
            Object value = method.invoke(underlying, null);
            if (Array.getLength(value) <= index)
            {
                return null;
            }
            return Array.get(value, index);
        }
        catch (ClassCastException e)
        {
            throw new PropertyAccessException("Mismatched getter instance to event bean type");
        }
        catch (InvocationTargetException e)
        {
            throw new PropertyAccessException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new PropertyAccessException(e);
        }
        catch (IllegalArgumentException e)
        {
            throw new PropertyAccessException(e);
        }
    }

    public String toString()
    {
        return "ArrayMethodPropertyGetter " +
                " method=" + method.toString() +
                " index=" + index;
    }
}
