using System;
using System.Collections.Generic;
using System.Xml;

using net.esper.compat;
using net.esper.client;
using net.esper.events;
using net.esper.events.xml;

namespace net.esper.events
{
    /// <summary>
    /// Implementation for resolving event name to event type.
    /// </summary>

    public class EventAdapterServiceImpl : EventAdapterService
    {
        private EDictionary<String, EventType> eventTypes;
        private BeanEventAdapter beanEventAdapter;
        private EDictionary<String, EventType> xmldomRootElementNames;

        /// <summary> Ctor.</summary>
        /// <param name="classToLegacyConfigs">is a map of event type alias to legacy event type config
        /// </param>
        public EventAdapterServiceImpl(EDictionary<String, ConfigurationEventTypeLegacy> typeToLegacyConfigs)
        {
            eventTypes = new EHashDictionary<String, EventType>();
            xmldomRootElementNames = new EHashDictionary<String, EventType>();
            beanEventAdapter = new BeanEventAdapter(typeToLegacyConfigs);
        }

        public virtual EventType GetEventType(String eventName)
        {
            // Check the configuration first for the known event name
            return eventTypes.Fetch( eventName, null ) ;
        }

        public virtual EventType AddBeanType(String eventTypeAlias, Type clazz)
        {
        	EventType existingType = eventTypes.Fetch(eventTypeAlias, null);
            if (existingType != null)
            {
                if (existingType.UnderlyingType.Equals(clazz))
                {
                    return existingType;
                }

                throw new EventAdapterException("Event type named '" + eventTypeAlias + "' has already been declared with differing type information");
            }

            EventType eventType = beanEventAdapter.CreateOrGetBeanType(clazz);
            eventTypes[eventTypeAlias] = eventType;

            return eventType;
        }

        public virtual EventType AddBeanType(String eventTypeAlias, String fullyQualClassName)
        {
            EventType existingType = eventTypes.Fetch(eventTypeAlias, null);
            if (existingType != null)
            {
                if (existingType.UnderlyingType.FullName.Equals(fullyQualClassName))
                {
                    return existingType;
                }

                throw new EventAdapterException("Event type named '" + eventTypeAlias + "' has already been declared with differing type information");
            }

            Type clazz = null;
            try
            {
                clazz = Type.GetType(fullyQualClassName, true, true);
            }
            catch (Exception ex)
            {
                throw new EventAdapterException("Failed to load class " + fullyQualClassName, ex);
            }

            EventType eventType = beanEventAdapter.CreateOrGetBeanType(clazz);
            eventTypes[eventTypeAlias] = eventType;

            return eventType;
        }

        public EventType AddMapType(String eventTypeAlias, EDictionary<String, Type> propertyTypes)
        {
            MapEventType newEventType = new MapEventType(propertyTypes, this);

            EventType existingType = eventTypes.Fetch(eventTypeAlias, null);
            if (existingType != null)
            {
                // The existing type must be the same as the type created
                if (!newEventType.Equals(existingType))
                {
                    throw new EventAdapterException("Event type named '" + eventTypeAlias +
                            "' has already been declared with differing type information");
                }

                // Since it's the same, return the existing type
                return existingType;
            }

            eventTypes[eventTypeAlias] = newEventType;

            return newEventType;
        }


        public EventType CreateAnonymousMapType(EDictionary<String, Type> propertyTypes)
        {
            return new MapEventType(propertyTypes, this);
        }

        public virtual EventBean AdapterForBean(Object _event)
        {
            return beanEventAdapter.AdapterForBean(_event);
        }

        public virtual EventBean AdapterForMap(EDictionary<String,Object> _event, String eventTypeAlias)
        {
        	EventType existingType = eventTypes.Fetch( eventTypeAlias, null ) ;
        	if ( existingType == null )
            {
                throw new EventAdapterException("Event type alias '" + eventTypeAlias + "' has not been defined");
            }

            IDataDictionary eventMap = (IDataDictionary)_event;

            return CreateMapFromValues(eventMap, existingType);
        }

        public EventBean CreateMapFromValues(IDataDictionary properties, EventType eventType)
        {
            return new MapEventBean(properties, eventType);
        }

        public EventBean CreateMapFromUnderlying(EDictionary<String, EventBean> events, EventType eventType)
        {
            return new MapEventBean(eventType, events);
        }

        public EventType CreateAddToEventType(EventType originalType, String[] fieldNames, Type[] fieldTypes)
        {
            EDictionary<String, Type> types = new EHashDictionary<String, Type>();

            // Copy properties of original event, add property value
            foreach (String property in originalType.PropertyNames)
            {
                types[property] = originalType.GetPropertyType(property);
            }

            // Copy new properties
            for (int i = 0; i < fieldNames.Length; i++)
            {
                types[fieldNames[i]] = fieldTypes[i];
            }

            return CreateAnonymousMapType(types);
        }

        public EventBean AdapterForDOM(XmlNode node)
        {
            String rootElementName = null;
            XmlNode namedNode = null;
            if (node is XmlDocument)
            {
				namedNode = ( (XmlDocument) node ).DocumentElement;
            }
            rootElementName = namedNode.LocalName;
            if (rootElementName == null)
            {
                rootElementName = namedNode.Name;
            }

            EventType eventType = xmldomRootElementNames[rootElementName];
            if (eventType == null)
            {
                throw new EventAdapterException(
            		"DOM event root element name '" + rootElementName +
                    "' has not been configured");
            }

            return new XMLEventBean(node, eventType);
        }

        public EventType CreateAnonymousCompositeType(EDictionary<String, EventType> taggedEventTypes)
        {
            return new CompositeEventType(taggedEventTypes);
        }

        public EventBean AdapterForCompositeEvent(EventType eventType, EDictionary<String, EventBean> taggedEvents)
        {
            return new CompositeEventBean(taggedEvents, eventType);
        }

        /// <summary>
        /// Add a configured XML DOM event type.
        /// </summary>
        /// <param name="eventTypeAlias">the alias name of the event type</param>
        /// <param name="configurationEventTypeXMLDOM">configures the event type schema and namespace and XPath</param>
        
        public void AddXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
        {
            if (configurationEventTypeXMLDOM.RootElementName == null)
            {
                throw new EventAdapterException("Required root element name has not been supplied");
            }

            EventType type = null;
            if (configurationEventTypeXMLDOM.SchemaResource == null)
            {
                type = new SimpleXMLEventType(configurationEventTypeXMLDOM);
            }
            else
            {
                type = new SchemaXMLEventType(configurationEventTypeXMLDOM);
            }

            eventTypes[eventTypeAlias] = type;
            xmldomRootElementNames[configurationEventTypeXMLDOM.RootElementName] = type;
        }

        public EventType CreateAnonymousMapTypeUnd(EDictionary<String, EventType> propertyTypes)
        {
            EDictionary<String, Type> underlyingTypes = GetUnderlyingTypes(propertyTypes);
            return this.CreateAnonymousMapType(underlyingTypes);
        }

        /**
         * Return a map of property name and types for a given map of property name and event type,
         * by extracting the underlying type for the event types.
         * @param types is the various event types returned.
         * @return map of property name and type
         */
        private static EDictionary<String, Type> GetUnderlyingTypes(IDictionary<String, EventType> types)
        {
            EDictionary<String, Type> classes = new EHashDictionary<String, Type>();

            foreach (KeyValuePair<String, EventType> type in types)
            {
                classes[type.Key] = type.Value.UnderlyingType;
            }

            return classes;
        }
    }
}
