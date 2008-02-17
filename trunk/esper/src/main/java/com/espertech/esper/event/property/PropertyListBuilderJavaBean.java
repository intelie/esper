package com.espertech.esper.event.property;

import com.espertech.esper.client.ConfigurationEventTypeLegacy;
import com.espertech.esper.event.EventPropertyDescriptor;
import java.util.List;

/**
 * Implementation for a property list builder that considers JavaBean-style methods
 * as the exposed event properties, plus any explicitly configured props.
 */
public class PropertyListBuilderJavaBean implements PropertyListBuilder
{
    private ConfigurationEventTypeLegacy optionalLegacyConfig;

    /**
     * Ctor.
     * @param optionalLegacyConfig configures legacy type, or null information
     * has been supplied.
     */
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
