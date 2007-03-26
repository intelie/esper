using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
    /// Implementation for a property list builder that considers JavaBean-style methods
	/// as the exposed event properties, plus any explicitly configured props.
	/// </summary>

    public class PropertyListBuilderNative : PropertyListBuilder
	{
		private ConfigurationEventTypeLegacy optionalLegacyConfig;
		
		/// <summary> Ctor.</summary>
		/// <param name="optionalLegacyConfig">configures legacy type, or null information
		/// has been supplied.
		/// </param>
		
        public PropertyListBuilderNative(ConfigurationEventTypeLegacy optionalLegacyConfig)
		{
			this.optionalLegacyConfig = optionalLegacyConfig;
		}

        /// <summary>
        /// Introspect the type and deterime exposed event properties.
        /// </summary>
        /// <param name="type">type to introspect</param>
        /// <returns>list of event property descriptors</returns>
        public IList<EventPropertyDescriptor> AssessProperties(Type type)
        {
            IList<EventPropertyDescriptor> result = PropertyHelper.GetProperties(type);
            if (optionalLegacyConfig != null)
            {
                PropertyListBuilderExplicit.getExplicitProperties(result, type, optionalLegacyConfig);
            }
            return result;
        }
	}
}
