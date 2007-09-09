package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.PropertyAccessException;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Getter for a dynamic property (syntax field.inner?), using vanilla reflection.
 */
public class DynamicSimplePropertyGetter extends DynamicPropertyGetterBase implements EventPropertyGetter
{
    private final String getterMethodName;
    private final String isMethodName;

    /**
     * Ctor.
     * @param fieldName the property name
     */
    public DynamicSimplePropertyGetter(String fieldName)
    {
        getterMethodName = getGetterMethodName(fieldName);
        isMethodName = getIsMethodName(fieldName);
    }

    protected Object call(DynamicPropertyDescriptor descriptor, Object underlying)
    {
        try
        {
            return descriptor.getMethod().invoke(underlying, null);
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

    protected Method determineMethod(Class clazz)
    {
        try
        {
            return clazz.getMethod(getterMethodName);
        }
        catch (NoSuchMethodException ex1)
        {
            try
            {
                return clazz.getMethod(isMethodName);
            }
            catch (NoSuchMethodException ex2)
            {
                return null;
            }
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

    private static String getIsMethodName(String propertyName)
    {
        StringWriter writer = new StringWriter();
        writer.write("is");
        writer.write(Character.toUpperCase(propertyName.charAt(0)));
        writer.write(propertyName.substring(1));
        return writer.toString();
    }
}