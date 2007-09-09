package net.esper.event;

import java.lang.reflect.Field;

/**
 * Property getter for fields using Java's vanilla reflection.
 */
public final class ReflectionPropFieldGetter implements EventPropertyGetter
{
    private final Field field;

    /**
     * Constructor.
     * @param field is the regular reflection field to use to obtain values for a property
     */
    public ReflectionPropFieldGetter(Field field)
    {
        this.field = field;
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

        try
        {
            return field.get(underlying);
        }
        catch (IllegalArgumentException e)
        {
            throw new PropertyAccessException("Mismatched getter instance to event bean type");
        }
        catch (IllegalAccessException e)
        {
            throw new PropertyAccessException(e);
        }
    }

    public String toString()
    {
        return "ReflectionPropFieldGetter " +
                "field=" + field.toGenericString();
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }    
}
