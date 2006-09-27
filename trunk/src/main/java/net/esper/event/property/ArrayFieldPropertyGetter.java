package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Getter for an array property backed by a field, identified by a given index, using vanilla reflection.
 */
public class ArrayFieldPropertyGetter implements EventPropertyGetter
{
    private final Field field;
    private final int index;

    /**
     * Constructor.
     * @param field is the field to use to retrieve a value from the object
     * @param index is tge index within the array to get the property from
     */
    public ArrayFieldPropertyGetter(Field field, int index)
    {
        this.index = index;
        this.field = field;

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
            Object value = field.get(underlying);
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
        return "ArrayFieldPropertyGetter " +
                " field=" + field.toString() +
                " index=" + index;
    }
}
