using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Reflection;

using net.esper.client;
using net.esper.compat;
using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
    /// Implementation for a property list builder that considers any public methods,
	/// public fields, and public properties as the exposed event properties, plus any
    /// explicitly configured props.
	/// </summary>

    public class PropertyListBuilderPublic : PropertyListBuilder
    {
        private ConfigurationEventTypeLegacy legacyConfig;

        /// <summary> Ctor.</summary>
        /// <param name="legacyConfig">configures legacy type
        /// </param>

        public PropertyListBuilderPublic(ConfigurationEventTypeLegacy legacyConfig)
        {
            if (legacyConfig == null)
            {
                throw new ArgumentException("Required configuration not passed");
            }
            this.legacyConfig = legacyConfig;
        }

        /// <summary>
        /// Introspect the type and deterime exposed event properties.
        /// </summary>
        /// <param name="type">type to introspect</param>
        /// <returns>list of event property descriptors</returns>
        public IList<EventPropertyDescriptor> AssessProperties(Type type)
        {
            IList<EventPropertyDescriptor> result = new List<EventPropertyDescriptor>();
            PropertyListBuilderExplicit.getExplicitProperties(result, type, legacyConfig);
            addPublicFields(result, type);
            addPublicMethods(result, type);
            addPublicProperties(result, type);
            return result;
        }

        private void addPublicMethods(IList<EventPropertyDescriptor> result, Type type)
        {
            MethodInfo[] methods = type.GetMethods();
            foreach (MethodInfo method in methods)
            {
                if (method.ReturnType == typeof(void))
                {
                    continue;
                }

                if (method.GetParameters().Length >= 2)
                {
                    continue;
                }

                if (method.GetParameters().Length == 1)
                {
                    Type parameterType = method.GetParameters()[0].ParameterType;
                    if ((parameterType != typeof(int)) && 
                        (parameterType != typeof(String)))
                    {
                        continue;
                    }
                }

                EventPropertyDescriptor desc = PropertyListBuilderExplicit.makeMethodDesc(method, method.Name);
                result.Add(desc);
            }

            PropertyHelper.RemoveClrProperties(result);
        }

        private void addPublicFields(IList<EventPropertyDescriptor> result, Type type)
        {
            FieldInfo[] fields = type.GetFields();
            foreach (FieldInfo field in fields)
            {
                EventPropertyDescriptor desc = PropertyListBuilderExplicit.makeFieldDesc(field, field.Name);
                result.Add(desc);
            }
        }

        private void addPublicProperties(IList<EventPropertyDescriptor> result, Type type)
        {
        	foreach( PropertyDescriptor property in TypeDescriptor.GetProperties( type ) )
            {
                EventPropertyDescriptor desc = PropertyListBuilderExplicit.makeNativeDesc(property, property.Name);
                result.Add(desc);
            }
        }
    }
}
