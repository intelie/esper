using System;
using System.Reflection;

using net.esper.client;

namespace net.esper.events.property
{

    /// <summary>
    /// Factory for creates a builder/introspector for determining event property descriptors
    /// based on a given class.
    /// </summary>

    public class PropertyListBuilderFactory
    {
        /// <summary>
        /// Creates an implementation for a builder considering the accessor style and
        /// code generation flags for a given class.
        /// </summary>
        /// <param name="optionalLegacyClassConfigs">configures how event property listy is build</param>
        /// <returns>builder/introspector implementation</returns>

        public static PropertyListBuilder CreateBuilder(ConfigurationEventTypeLegacy optionalLegacyClassConfigs)
        {
            if (optionalLegacyClassConfigs == null)
            {
                return new PropertyListBuilderNative(null);
            }
            if (optionalLegacyClassConfigs.AccessorStyle == ConfigurationEventTypeLegacy.AccessorStyleEnum.NATIVE)
            {
                return new PropertyListBuilderNative(optionalLegacyClassConfigs);
            }
            if (optionalLegacyClassConfigs.AccessorStyle == ConfigurationEventTypeLegacy.AccessorStyleEnum.EXPLICIT)
            {
                return new PropertyListBuilderExplicit(optionalLegacyClassConfigs);
            }
            if (optionalLegacyClassConfigs.AccessorStyle == ConfigurationEventTypeLegacy.AccessorStyleEnum.PUBLIC)
            {
                return new PropertyListBuilderPublic(optionalLegacyClassConfigs);
            }
            throw new ArgumentException("Cannot match accessor style to property list builder");
        }
    }
}