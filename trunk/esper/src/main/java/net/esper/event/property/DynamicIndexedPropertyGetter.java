package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.PropertyAccessException;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Getter for a dynamic indexed property (syntax field.indexed[0]?), using vanilla reflection.
 */
public class DynamicIndexedPropertyGetter extends DynamicPropertyGetterBase implements EventPropertyGetter
{
    private final String getterMethodName;
    private final Object[] params;
    private final int index;

    /**
     * Ctor.
     * @param fieldName property name
     * @param index index to get the element at
     */
    public DynamicIndexedPropertyGetter(String fieldName, int index)
    {
        getterMethodName = getGetterMethodName(fieldName);
        this.params = new Object[] {index};
        this.index = index;
    }

    protected Method determineMethod(Class clazz)
    {
        Method method;

        try
        {
            return clazz.getMethod(getterMethodName, int.class);
        }
        catch (NoSuchMethodException ex1)
        {
            try
            {
                method = clazz.getMethod(getterMethodName);
            }
            catch (NoSuchMethodException e)
            {
                return null;
            }
            if (!method.getReturnType().isArray())
            {
                return null;
            }
            return method;
        }
    }

    protected Object call(DynamicPropertyDescriptor descriptor, Object underlying)
    {
        try
        {
            if (descriptor.isHasParameters())
            {
                return descriptor.getMethod().invoke(underlying, params);
            }
            else
            {
                Object array = descriptor.getMethod().invoke(underlying, null);
                if (array == null)
                {
                    return null;
                }
                return Array.get(array, index);
            }
        }
        catch (ClassCastException e)
        {
            throw new PropertyAccessException("Mismatched getter instance to event bean type");
        }
        catch (InvocationTargetException e)
        {
            throw new PropertyAccessException(e);
        }
        catch (IllegalArgumentException e)
        {
            throw new PropertyAccessException(e);
        }
    }

    private static String getGetterMethodName(String propertyName)
    {
        StringWriter writer = new StringWriter();
        writer.write("get");
        writer.write(Character.toUpperCase(propertyName.charAt(0)));
        writer.write(propertyName.substring(1));
        return writer.toString();
    }
}
