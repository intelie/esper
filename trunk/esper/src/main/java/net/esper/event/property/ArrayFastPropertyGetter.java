package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Array;

/**
 * Getter for an array property identified by a given index, using the CGLIB fast method.
 */
public class ArrayFastPropertyGetter implements EventPropertyGetter
{
    private final FastMethod fastMethod;
    private final int index;

    /**
     * Constructor.
     * @param fastMethod is the method to use to retrieve a value from the object
     * @param index is tge index within the array to get the property from
     */
    public ArrayFastPropertyGetter(FastMethod fastMethod, int index)
    {
        this.index = index;
        this.fastMethod = fastMethod;

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
            Object value = fastMethod.invoke(underlying, null);
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
    }

    public String toString()
    {
        return "ArrayFastPropertyGetter " +
                " fastMethod=" + fastMethod.toString() +
                " index=" + index;
    }
}
