package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Getter for a key property identified by a given key value, using vanilla reflection.
 */
public class KeyedMethodPropertyGetter implements EventPropertyGetter
{
    private final Method method;
    private final Object key;

    /**
     * Constructor.
     * @param method is the method to use to retrieve a value from the object.
     * @param key is the key to supply as parameter to the mapped property getter
     */
    public KeyedMethodPropertyGetter(Method method, Object key)
    {
        this.key = key;
        this.method = method;
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        try
        {
            return method.invoke(underlying, new Object[] {key});
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
        return "KeyedMethodPropertyGetter " +
                " method=" + method.toString() +
                " key=" + key;
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }
}
