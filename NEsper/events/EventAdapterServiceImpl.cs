using System;
using System.Collections.Generic;
using System.Xml;

using net.esper.compat;
using net.esper.client;
using net.esper.events;
using net.esper.events.xml;
using net.esper.util;

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
        /// <param name="typeToLegacyConfigs">is a map of event type alias to legacy event type config
        /// </param>
        public EventAdapterServiceImpl(EDictionary<String, ConfigurationEventTypeLegacy> typeToLegacyConfigs)
        {
            eventTypes = new EHashDictionary<String, EventType>();
            xmldomRootElementNames = new EHashDictionary<String, EventType>();
            beanEventAdapter = new BeanEventAdapter(typeToLegacyConfigs);
        }

        /// <summary>
        /// Gets the type of the event.
        /// </summary>
        /// <param name="eventName">Name of the event.</param>
        /// <returns></returns>
        public virtual EventType GetEventType(String eventName)
        {
            // Check the configuration first for the known event name
            return eventTypes.Fetch( eventName, null ) ;
        }

        /// <summary>
        /// Add an event type with the given alias and Java class.
        /// If the alias already exists with the same Class, returns the existing EventType instance.
        /// If the alias already exists with different Class name, throws an exception.
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.event.BeanEventType" />.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="clazz">is the fully Java class</param>
        /// <returns>event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match class names </throws>
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

        /// <summary>
        /// Add an event type with the given alias and Java fully-qualified class name.
        /// If the alias already exists with the same class name, returns the existing EventType instance.
        /// If the alias already exists with different class name, throws an exception.
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.event.BeanEventType" />.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="fullyQualClassName">is the fully qualified class name</param>
        /// <returns>event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match class names </throws>
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
                clazz = TypeHelper.ResolveType(fullyQualClassName);
            }
            catch (Exception ex)
            {
                throw new EventAdapterException("Failed to load class " + fullyQualClassName, ex);
            }

            EventType eventType = beanEventAdapter.CreateOrGetBeanType(clazz);
            eventTypes[eventTypeAlias] = eventType;

            return eventType;
        }

        /// <summary>
        /// Add an event type with the given alias and a given set of properties.
        /// If the alias already exists with the same event property information, returns the
        /// existing EventType instance.
        /// If the alias already exists with different event property information, throws an exception.
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.event.MapEventType" />.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="propertyTypes">is the names and types of event properties</param>
        /// <returns>event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match property type info </throws>
        public EventType AddMapType(String eventTypeAlias, EDictionary<String, Type> propertyTypes)
        {
            MapEventType newEventType = new MapEventType(propertyTypes, this);

            EventType existingType = eventTypes.Fetch(eventTypeAlias, null);
            if (existingType != null)
            {
                // The existing type must be the same as the type created
                if (!newEventType.Equals(existingType))
                {
                    throw new EventAdapterException(
                        "Event type named '" + eventTypeAlias +
                        "' has already been declared with differing type information");
                }

                // Since it's the same, return the existing type
                return existingType;
            }

            eventTypes[eventTypeAlias] = newEventType;

            return newEventType;
        }


        /// <summary>
        /// Creates a new anonymous EventType instance for an event type that contains a map of name value pairs.
        /// The method accepts a Map that contains the property names as keys and Class objects as the values.
        /// The Class instances represent the property types.
        /// New instances are created by this method on every invocation. Clients to this method need to
        /// cache the returned EventType instance to reuse EventType's for same-typed events.
        /// </summary>
        /// <param name="propertyTypes">is a map of String to Class objects</param>
        /// <returns>
        /// EventType implementation for map field names and value types
        /// </returns>
        public EventType CreateAnonymousMapType(EDictionary<String, Type> propertyTypes)
        {
            return new MapEventType(propertyTypes, this);
        }

        /// <summary>
        /// Creates an adapter for any type of object.
        /// </summary>
        /// <param name="_event">The _event.</param>
        /// <returns></returns>
        public virtual EventBean AdapterForBean(Object _event)
        {
            return beanEventAdapter.AdapterForBean(_event);
        }

        /// <summary>
        /// Creates an adapter for a map.
        /// </summary>
        /// <param name="_event">The _event.</param>
        /// <param name="eventTypeAlias">The event type alias.</param>
        /// <returns></returns>
        
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

        /// <summary>
        /// Create an event wrapper bean from a set of event properties (name and value objectes) stored in a Map.
        /// </summary>
        /// <param name="properties">is key-value pairs for the event properties</param>
        /// <param name="eventType">is the type metadata for any maps of that type</param>
        /// <returns>EventBean instance</returns>
        public EventBean CreateMapFromValues(IDataDictionary properties, EventType eventType)
        {
            return new MapEventBean(properties, eventType);
        }

        /// <summary>
        /// Create an aggregate event wrapper bean from a set of named events stored in a Map.
        /// </summary>
        /// <param name="events">is key-value pairs where the key is the event name and the value is the event</param>
        /// <param name="eventType">is the type metadata for any maps of that type</param>
        /// <returns>EventBean instance</returns>
        public EventBean CreateMapFromUnderlying(EDictionary<String, EventBean> events, EventType eventType)
        {
            return new MapEventBean(eventType, events);
        }

        /// <summary>
        /// Create an event type based on the original type passed in adding one or more properties.
        /// </summary>
        /// <param name="originalType">event type to add property to</param>
        /// <param name="fieldNames">names of properties</param>
        /// <param name="fieldTypes">types of properties</param>
        /// <returns>new event type</returns>
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

        /// <summary>
        /// Returns an adapter for the XML DOM document that exposes it's data as event properties for use in statements.
        /// </summary>
        /// <param name="node">is the node to wrap</param>
        /// <returns>event wrapper for document</returns>
        
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

        /// <summary>
        /// Creates an unnamed composite event type with event properties that are name-value pairs
        /// with values being other event types. Pattern statement generate events of such type.
        /// </summary>
        /// <param name="taggedEventTypes">is a map of name keys and event type values</param>
        /// <returns>
        /// event type representing a composite event
        /// </returns>
        public EventType CreateAnonymousCompositeType(EDictionary<String, EventType> taggedEventTypes)
        {
            return new CompositeEventType(taggedEventTypes);
        }

        /// <summary>
        /// Creates a wrapper for a composite event type. The wrapper wraps an event that
        /// consists of name-value pairs in which the values are other already-wrapped events.
        /// </summary>
        /// <param name="eventType">is the composite event type</param>
        /// <param name="taggedEvents">is the name-event map</param>
        /// <returns>wrapper for composite event</returns>
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

        /// <summary>
        /// Creates a new anonymous EventType instance for an event type that contains a map of name value pairs.
        /// The method accepts a Map that contains the property names as keys and EventType objects as the values.
        /// The EventType instances represent the property types whose underlying class is used to set the type,
        /// via method CreateAnonymousMapType.
        /// </summary>
        /// <param name="propertyTypes">is a map of String to EventType objects</param>
        /// <returns>
        /// EventType implementation for map field names and value types which are the underlying types to the event type passed in
        /// </returns>
        
        public EventType CreateAnonymousMapTypeUnd(EDictionary<String, EventType> propertyTypes)
        {
            EDictionary<String, Type> underlyingTypes = GetUnderlyingTypes(propertyTypes);
            return this.CreateAnonymousMapType(underlyingTypes);
        }

        /// <summary>
        /// Return a map of property name and types for a given map of property name and event type,
        /// by extracting the underlying type for the event types.
        /// </summary>
        /// <param name="types">the various event types returned.</param>
        /// <returns>map of property name and type</returns>

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
