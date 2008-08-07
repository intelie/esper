package com.espertech.esper.event.property;

import com.espertech.esper.client.ConfigurationEventTypeLegacy;
import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.event.EventPropertyDescriptor;
import com.espertech.esper.event.EventPropertyType;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * Implementation for a property list builder that considers any public method
 * and public field as the exposed event properties, plus any explicitly configured props.
 */
public class PropertyListBuilderPublic implements PropertyListBuilder
{
    private ConfigurationEventTypeLegacy legacyConfig;

    /**
     * Ctor.
     * @param legacyConfig configures legacy type
     */
    public PropertyListBuilderPublic(ConfigurationEventTypeLegacy legacyConfig)
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
        PropertyListBuilderExplicit.getExplicitProperties(result, clazz, legacyConfig);
        addPublicFields(result, clazz);
        addPublicMethods(result, clazz);
        return result;
    }

    private static void addPublicMethods(List<EventPropertyDescriptor> result, Class clazz)
    {
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++)
        {
            if (methods[i].getReturnType() == void.class)
            {
                continue;
            }
            if (methods[i].getParameterTypes().length >= 2)
            {
                continue;
            }
            if (methods[i].getParameterTypes().length == 1)
            {
                Class parameterType = methods[i].getParameterTypes()[0];
                if ((parameterType != int.class) && ((parameterType != Integer.class)) &&
                    (parameterType != String.class))
                {
                    continue;
                }
            }

            EventPropertyDescriptor desc = PropertyListBuilderExplicit.makeMethodDesc(methods[i], methods[i].getName());
            result.add(desc);
        }

        PropertyHelper.removeJavaProperties(result);
    }

    private static void addPublicFields(List<EventPropertyDescriptor> result, Class clazz)
    {
        Field[] fields = clazz.getFields();
        for (int i = 0; i < fields.length; i++)
        {
            EventPropertyDescriptor desc = PropertyListBuilderExplicit.makeFieldDesc(fields[i], fields[i].getName());
            result.add(desc);
        }
    }
}
