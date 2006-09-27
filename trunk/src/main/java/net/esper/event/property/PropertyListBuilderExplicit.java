package net.esper.event.property;

import net.esper.event.EventPropertyDescriptor;
import net.esper.event.EventPropertyType;
import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.client.ConfigurationException;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Arrays;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyListBuilderExplicit implements PropertyListBuilder
{
    private ConfigurationEventTypeLegacy legacyConfig;

    public PropertyListBuilderExplicit(ConfigurationEventTypeLegacy legacyConfig)
    {
        if (legacyConfig == null)
        {
            throw new IllegalArgumentException("Required configuration not passed");
        }
        this.legacyConfig = legacyConfig;
    }

    public List<EventPropertyDescriptor> assessProperties(Class clazz)
    {
        List<EventPropertyDescriptor> result = new LinkedList<EventPropertyDescriptor>();
        getExplicitProperties(result, clazz, legacyConfig);
        return result;
    }

    protected static void getExplicitProperties(List<EventPropertyDescriptor> result,
                                                                         Class clazz,
                                                                         ConfigurationEventTypeLegacy legacyConfig)
    {
        for (ConfigurationEventTypeLegacy.LegacyFieldPropDesc desc : legacyConfig.getFieldProperties())
        {
            result.add(makeDesc(clazz, desc));
        }
        for (ConfigurationEventTypeLegacy.LegacyMethodPropDesc desc : legacyConfig.getMethodProperties())
        {
            result.add(makeDesc(clazz, desc));
        }
    }

    private static EventPropertyDescriptor makeDesc(Class clazz, ConfigurationEventTypeLegacy.LegacyMethodPropDesc methodDesc)
    {
        Method[] methods = clazz.getMethods();
        Method method = null;
        for (int i = 0; i < methods.length; i++)
        {
            if (!methods[i].getName().equals(methodDesc.getAccessorMethodName()))
            {
                continue;
            }
            if (methods[i].getReturnType() == void.class)
            {
                continue;
            }
            if (methods[i].getParameterTypes().length >= 2)
            {
                continue;
            }
            if (methods[i].getParameterTypes().length == 0)
            {
                method = methods[i];
                break;
            }

            Class parameterType = methods[i].getParameterTypes()[0];
            if ((parameterType != int.class) && ((parameterType != Integer.class)) &&
                (parameterType != String.class))
            {
                continue;
            }

            method = methods[i];
            break;
        }

        if (method == null)
        {
            throw new ConfigurationException("Configured method named '" +
                    methodDesc.getAccessorMethodName() + "' not found for class " + clazz.getName());
        }

        return makeMethodDesc(method, methodDesc.getName());
    }

    private static EventPropertyDescriptor makeDesc(Class clazz, ConfigurationEventTypeLegacy.LegacyFieldPropDesc fieldDesc)
    {
        Field field = null;
        try
        {
            field = clazz.getField(fieldDesc.getAccessorFieldName());
        }
        catch (NoSuchFieldException ex)
        {
            throw new ConfigurationException("Configured field named '" +
                    fieldDesc.getAccessorFieldName() + "' not found for class " + clazz.getName());
        }
        return makeFieldDesc(field, fieldDesc.getName());
    }

    protected static EventPropertyDescriptor makeFieldDesc(Field field, String name)
    {
        return new EventPropertyDescriptor(name, name, field, EventPropertyType.SIMPLE);
    }

    protected static EventPropertyDescriptor makeMethodDesc(Method method, String name)
    {
        Class type = method.getReturnType();
        EventPropertyType propertyType = null;

        if (method.getParameterTypes().length == 1)
        {
            Class parameterType = method.getParameterTypes()[0];
            if (parameterType == String.class)
            {
                propertyType = EventPropertyType.MAPPED;
            }
            else
            {
                propertyType = EventPropertyType.INDEXED;
            }
        }
        else
        {
            propertyType = EventPropertyType.SIMPLE;
        }

        return new EventPropertyDescriptor(name, name, method, propertyType);
    }
}
