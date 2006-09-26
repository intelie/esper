package net.esper.event.property;

import net.esper.client.ConfigurationEventTypeLegacy;

public class PropertyListBuilderFactory
{
    public static PropertyListBuilder createBuilder(ConfigurationEventTypeLegacy optionalLegacyClassConfigs)
    {
        if (optionalLegacyClassConfigs == null)
        {
            return new PropertyListBuilderJavaBean(null);
        }
        if (optionalLegacyClassConfigs.getAccessorStyle() == ConfigurationEventTypeLegacy.AccessorStyle.JAVABEAN)
        {
            return new PropertyListBuilderJavaBean(optionalLegacyClassConfigs);
        }
        if (optionalLegacyClassConfigs.getAccessorStyle() == ConfigurationEventTypeLegacy.AccessorStyle.EXPLICIT)
        {
            return new PropertyListBuilderExplicit(optionalLegacyClassConfigs);
        }
        if (optionalLegacyClassConfigs.getAccessorStyle() == ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC)
        {
            return new PropertyListBuilderPublic(optionalLegacyClassConfigs);
        }
        throw new IllegalArgumentException("Cannot match accessor style to property list builder");
    }
}
