package net.esper.event.property;

import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.client.ConfigurationException;
import net.esper.event.EventPropertyDescriptor;
import net.esper.event.EventPropertyType;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class PropertyListBuilderJavaBean implements PropertyListBuilder
{
    private ConfigurationEventTypeLegacy optionalLegacyConfig;

    public PropertyListBuilderJavaBean(ConfigurationEventTypeLegacy optionalLegacyConfig)
    {
        this.optionalLegacyConfig = optionalLegacyConfig;
    }

    public List<EventPropertyDescriptor> assessProperties(Class clazz)
    {
        List<EventPropertyDescriptor> result = PropertyHelper.getProperties(clazz);
        if (optionalLegacyConfig != null)
        {
            PropertyListBuilderExplicit.getExplicitProperties(result, clazz, optionalLegacyConfig);
        }
        return result;
    }
}
