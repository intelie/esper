package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.PropertyAccessException;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Getter for a dynamic mapped property (syntax field.mapped('key')?), using vanilla reflection.
 */
public class DynamicMappedPropertyGetter extends DynamicPropertyGetterBase implements EventPropertyGetter
{
    private final String getterMethodName;
    private final Object[] params;

    /**
     * Ctor.
     * @param fieldName property name
     * @param key mapped access key
     */
    public DynamicMappedPropertyGetter(String fieldName, String key)
    {
        getterMethodName = getGetterMethodName(fieldName);
        this.params = new Object[] {key};
    }

    public Method determineMethod(Class clazz) throws PropertyAccessException
    {
        try
        {
            return clazz.getMethod(getterMethodName, String.class);
        }
        catch (NoSuchMethodException ex1)
        {
            Method method;
            try
            {
                method = clazz.getMethod(getterMethodName);
            }
            catch (NoSuchMethodException e)
            {
                return null;
            }

            if (method.getReturnType() != Map.class)
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
                Object result = descriptor.getMethod().invoke(underlying, null);
                if ((result instanceof Map) && (result != null))
                {
                    Map map = (Map) result;
                    return map.get(params[0]);
                }
                return null;
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
