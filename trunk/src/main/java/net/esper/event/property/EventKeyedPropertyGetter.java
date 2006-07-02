package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * Getter for a key property identified by a given key value, using the CGLIB fast method.
 */
public class EventKeyedPropertyGetter implements EventPropertyGetter
{
    private final FastMethod fastMethod;
    private final Object key;

    /**
     * Constructor.
     * @param fastMethod is the method to use to retrieve a value from the object.
     * @param key is the key to supply as parameter to the mapped property getter
     */
    public EventKeyedPropertyGetter(FastMethod fastMethod, Object key)
    {
        this.key = key;
        this.fastMethod = fastMethod;
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        try
        {
            return fastMethod.invoke(underlying, new Object[] {key});
        }
        catch (ClassCastException e)
        {
            throw new PropertyAccessException("Mismatched getter instance to event bean type");
        }
        catch (InvocationTargetException e)
        {
            throw new PropertyAccessException(e);
        }
    }

    public String toString()
    {
        return "EventKeyedPropertyGetter " +
                " fastMethod=" + fastMethod.toString() +
                " key=" + key;
    }
}
