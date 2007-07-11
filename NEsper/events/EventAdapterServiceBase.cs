///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Xml;

using net.esper.client;
using net.esper.compat;
using net.esper.events.xml;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.events
{
	/// <summary>
	/// Implementation for resolving event name to event type.
	/// <p>
	/// The implementation assigned a unique identifier to each event type.
	/// For Class-based event types, only one EventType instance and one event type id exists for the same class.
    /// </p>
	/// <p>
	/// Alias names must be unique, that is an alias name must resolve to a single event type.
    /// </p>
	/// <p>
	/// Each event type can have multiple aliases defined for it. For example, expressions such as
	/// "select * from A" and "select * from B"
	/// in which A and B are aliases for the same class X the select clauses each fireStatementStopped for events of type X.
	/// In summary, aliases A and B point to the same underlying event type and therefore event type id.
    /// </p>
	/// </summary>
    public abstract class EventAdapterServiceBase : EventAdapterService
    {
        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private readonly EDictionary<String, EventType> idToTypeMap;
        private readonly EDictionary<String, String> idToAliasMap;
        private readonly EDictionary<EventType, String> typeToIdMap;
        private readonly EDictionary<String, EventType> aliasToTypeMap;

        private readonly BeanEventAdapter beanEventAdapter;
        private readonly EDictionary<String, EventType> xmldomRootElementNames;

        /// <summary>Ctor.</summary>
        public EventAdapterServiceBase()
        {
            typeToIdMap = new HashDictionary<EventType, String>();
            aliasToTypeMap = new HashDictionary<String, EventType>();
            idToTypeMap = new HashDictionary<String, EventType>();
            idToAliasMap = new HashDictionary<String, String>();
            xmldomRootElementNames = new HashDictionary<String, EventType>();
            beanEventAdapter = new BeanEventAdapter();
        }

        /// <summary>
        /// Gets or sets the default property resolution style.
        /// </summary>

        public PropertyResolutionStyle DefaultPropertyResolutionStyle
        {
            get { return beanEventAdapter.DefaultPropertyResolutionStyle; }
            set { beanEventAdapter.DefaultPropertyResolutionStyle = value; }
        }

        /// <summary>
        /// Gets or sets the legacy class type information.
        /// </summary>
        /// <value>The type legacy configs.</value>
        public EDictionary<String, ConfigurationEventTypeLegacy> TypeLegacyConfigs
        {
            get { return beanEventAdapter.TypeToLegacyConfigs; }
            set { beanEventAdapter.TypeToLegacyConfigs = value;}
        }

        /// <summary>
        /// Returns the event type id given an event type alias. The alias is expected to exist.
        /// <para>Use getExistsTypeByAlias to check if an alias exists.</para>
        /// </summary>
        /// <param name="eventTypeAlias">is the event name</param>
        /// <returns>event type id</returns>
        /// <throws>IllegalStateException to indicate that the alias does not exist.</throws>
        public String GetIdByAlias(String eventTypeAlias)
        {
            if (eventTypeAlias == null)
            {
                throw new ArgumentException("Null event type alias parameter");
            }
            EventType type = aliasToTypeMap.Fetch(eventTypeAlias);
            if (type == null)
            {
                throw new IllegalStateException("Event type alias " + eventTypeAlias + " not in collection");
            }
            String id = typeToIdMap.Fetch(type);
            if (id == null)
            {
                throw new IllegalStateException("Event type id " + id + " not in collection");
            }
            return id;
        }

        /// <summary>
        /// Returns the event type given an event type id. The id is expected to exist.
        /// </summary>
        /// <param name="eventTypeID">is the tyoe id</param>
        /// <returns>event type</returns>
        /// <throws>IllegalStateException to indicate that the id does not exist.</throws>
        public EventType GetTypeById(String eventTypeID)
        {
            if (eventTypeID == null)
            {
                throw new ArgumentException("Null event type id parameter");
            }
            EventType eventType = idToTypeMap.Fetch(eventTypeID);
            if (eventType == null)
            {
                throw new IllegalStateException("Event type id " + eventTypeID + " not in collection");
            }
            return eventType;
        }

        /// <summary>
        /// Return the event type for a given event name, or null if none is registered for that name.
        /// </summary>
        /// <param name="eventTypeAlias">is the event type alias name to return type for</param>
        /// <returns>
        /// event type for named event, or null if unknown/unnamed type
        /// </returns>
        public EventType GetEventTypeByAlias(String eventTypeAlias)
        {
            if (eventTypeAlias == null)
            {
                throw new IllegalStateException("Null event type alias parameter");
            }
            return aliasToTypeMap.Fetch(eventTypeAlias);
        }

        /// <summary>
        /// Returns the event type id given the event type, with the type expected to exist.
        /// </summary>
        /// <param name="eventType">to return the id for</param>
        /// <returns>event type id</returns>
        /// <throws>IllegalStateException to indicate that the type does not exist.</throws>
        public String GetIdByType(EventType eventType)
        {
            if (eventType == null)
            {
                throw new ArgumentException("Null event type parameter");
            }
            String id = typeToIdMap.Fetch(eventType);
            if (id == null)
            {
                throw new IllegalStateException("Event type " + eventType.ToString() + " not in collection");
            }
            return id;
        }

        /// <summary>
        /// Returns the first event type alias for a given event type id.
        /// </summary>
        /// <param name="eventTypeID">is the type id</param>
        /// <returns>
        /// the very first alias registered for an event type
        /// </returns>
        public String GetAliasById(String eventTypeID)
        {
            if (eventTypeID == null)
            {
                throw new ArgumentException("Null event type id parameter");
            }
            String alias = idToAliasMap.Fetch(eventTypeID);
            if (alias == null)
            {
                throw new IllegalStateException("Event type id " + eventTypeID + " not in collection");
            }
            return alias;
        }

        /// <summary>
        /// Add an alias and class as an event type.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias</param>
        /// <param name="type">is the type to add</param>
        /// <returns>event type</returns>
        /// <throws>EventAdapterException to indicate an error constructing the type</throws>
        public EventType AddBeanTypeByAliasAndType(String eventTypeAlias, Type type)
        {
            lock (this)
            {
                if (log.IsDebugEnabled)
                {
                    log.Debug(".addBeanType Adding " + eventTypeAlias + " for type " + type.FullName);
                }

                EventType existingType = aliasToTypeMap.Fetch(eventTypeAlias);
                if (existingType != null)
                {
                    if (existingType.UnderlyingType.Equals(type))
                    {
                        return existingType;
                    }

                    throw new EventAdapterException("Event type named '" + eventTypeAlias +
                            "' has already been declared with differing column name or type information");
                }

                EventTypeSPI eventType = beanEventAdapter.CreateOrGetBeanType(type);
                String eventTypeId = eventType.EventTypeId;
                aliasToTypeMap.Put(eventTypeAlias, eventType);
                idToTypeMap.Put(eventTypeId, eventType);
                idToAliasMap.Put(eventTypeId, eventTypeAlias);
                typeToIdMap.Put(eventType, eventTypeId);

                return eventType;
            }
        }

        /// <summary>
        /// Create an event bean given an event of object id.
        /// </summary>
        /// <param name="_event">is the event class</param>
        /// <param name="eventId">is the event id</param>
        /// <returns>event</returns>
        public EventBean AdapterForBean(Object _event, Object eventId)
        {
            return beanEventAdapter.AdapterForBean(_event, eventId);
        }

        /// <summary>
        /// Add an event type for the given Java class name.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias</param>
        /// <param name="fullyQualClassName">is the Java class name</param>
        /// <returns>event type</returns>
        /// <throws>
        /// EventAdapterException if the Class name cannot resolve or other error occured
        /// </throws>
        public EventType AddBeanTypeByAliasAndClassName(String eventTypeAlias, String fullyQualClassName)
        {
            lock (this)
            {
                if (log.IsDebugEnabled)
                {
                    log.Debug(".addBeanType Adding " + eventTypeAlias + " for type " + fullyQualClassName);
                }

                EventType existingType = aliasToTypeMap.Fetch(eventTypeAlias);
                if (existingType != null)
                {
                    if (existingType.UnderlyingType.Name.Equals(fullyQualClassName))
                    {
                        if (log.IsDebugEnabled)
                        {
                            log.Debug(".addBeanType Returning existing type for " + eventTypeAlias);
                        }
                        return existingType;
                    }

                    throw new EventAdapterException("Event type named '" + eventTypeAlias +
                            "' has already been declared with differing column name or type information");
                }

                Type type;
                try
                {
                    type = TypeHelper.ResolveType(fullyQualClassName);
                }
                catch (TypeLoadException ex)
                {
                    throw new EventAdapterException("Failed to load class " + fullyQualClassName, ex);
                }

                EventType eventType = beanEventAdapter.CreateOrGetBeanType(type);
                String eventTypeID = ((EventTypeSPI)eventType).EventTypeId;
                aliasToTypeMap.Put(eventTypeAlias, eventType);
                idToTypeMap.Put(eventTypeID, eventType);
                idToAliasMap.Put(eventTypeID, eventTypeAlias);
                typeToIdMap.Put(eventType, eventTypeID);

                return eventType;
            }
        }

        /// <summary>
        /// Add an event type with the given alias and a given set of properties.
        /// If the alias already exists with the same event property information, returns the
        /// existing EventType instance.
        /// If the alias already exists with different event property information, throws an exception.
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.events.MapEventType"/>.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="propertyTypes">is the names and types of event properties</param>
        /// <returns>event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match property type info </throws>
        public EventType AddMapType(String eventTypeAlias, EDictionary<String, Type> propertyTypes)
        {
            lock (this)
            {
                MapEventType newEventType = new MapEventType(eventTypeAlias, propertyTypes, this);

                EventType existingType = aliasToTypeMap.Fetch(eventTypeAlias);
                if (existingType != null)
                {
                    // The existing type must be the same as the type createdStatement
                    if (!newEventType.Equals(existingType))
                    {
                        throw new EventAdapterException("Event type named '" + eventTypeAlias +
                                "' has already been declared with differing column name or type information");
                    }

                    // Since it's the same, return the existing type
                    return existingType;
                }

                String eventTypeID = "MAP_" + eventTypeAlias;
                aliasToTypeMap.Put(eventTypeAlias, newEventType);
                idToTypeMap.Put(eventTypeID, newEventType);
                idToAliasMap.Put(eventTypeID, eventTypeAlias);
                typeToIdMap.Put(newEventType, eventTypeID);

                return newEventType;
            }
        }

        /// <summary>
        /// Creates a new anonymous EventType instance for an event type that contains a map of name value pairs.
        /// The method accepts a Map that contains the property names as keys and Class objects as the values.
        /// The Class instances represent the property types.
        /// <p>
        /// New instances are created by this method on every invocation. Clients to this method need to
        /// cache the returned EventType instance to reuse EventType's for same-typed events.
        /// </p>
        /// </summary>
        /// <param name="propertyTypes">is a map of String to Class objects</param>
        /// <returns>
        /// EventType implementation for map field names and value types
        /// </returns>
        public EventType CreateAnonymousMapType(EDictionary<String, Type> propertyTypes)
        {
            return new MapEventType("", propertyTypes, this);
        }

        /// <summary>
        /// Create a new anonymous event type with the given underlying event type,
        /// as well as the additional given properties.
        /// </summary>
        /// <param name="underlyingEventType">is the event type for the event type that this wrapper wraps</param>
        /// <param name="propertyTypes">is the names and types of any additional properties</param>
        /// <returns>eventType is the type createdStatement</returns>
        /// <throws>
        /// EventAdapterException if alias already exists and doesn't match this type's info
        /// </throws>
        public EventType CreateAnonymousWrapperType(EventType underlyingEventType, EDictionary<String, Type> propertyTypes)
        {
            return new WrapperEventType("", underlyingEventType, propertyTypes, this);
        }

        /// <summary>
        /// Adapters for map.
        /// </summary>
        /// <param name="_event">The _event.</param>
        /// <param name="eventTypeAlias">The event type alias.</param>
        /// <returns></returns>
        public EventBean AdapterForMap(EDictionary<String, Object> _event, String eventTypeAlias)
        {
            EventType existingType = aliasToTypeMap.Fetch(eventTypeAlias);
            if (existingType == null)
            {
                throw new EventAdapterException("Event type alias '" + eventTypeAlias + "' has not been defined");
            }

            return CreateMapFromValues(_event, existingType);
        }

        /// <summary>
        /// Create an event wrapper bean from a set of event properties (name and value objectes) stored in a Map.
        /// </summary>
        /// <param name="properties">is key-value pairs for the event properties</param>
        /// <param name="eventType">is the type metadata for any maps of that type</param>
        /// <returns>EventBean instance</returns>
        public EventBean CreateMapFromValues(EDictionary<String, Object> properties, EventType eventType)
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
            EDictionary<String, Type> types = new HashDictionary<String, Type>();

            // Copy properties of original event, add property value
            foreach (String property in originalType.PropertyNames)
            {
                types.Put(property, originalType.GetPropertyType(property));
            }

            // Copy new properties
            for (int i = 0; i < fieldNames.Length; i++)
            {
                types.Put(fieldNames[i], fieldTypes[i]);
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
                namedNode = ((XmlDocument)node).DocumentElement;
            }
            rootElementName = namedNode.LocalName;
            if (rootElementName == null)
            {
                rootElementName = namedNode.Name;
            }

            EventType eventType = xmldomRootElementNames.Fetch(rootElementName);
            if (eventType == null)
            {
                throw new EventAdapterException("DOM event root element name '" + rootElementName +
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
        /// <param name="eventTypeAlias">is the alias name of the event type</param>
        /// <param name="configurationEventTypeXMLDOM">configures the event type schema and namespace and XPath
        /// property information.</param>
        /// <returns>event type</returns>
        public EventType AddXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
        {
            lock (this)
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

                EventType existingType = aliasToTypeMap.Fetch(eventTypeAlias);
                if (existingType != null)
                {
                    // The existing type must be the same as the type createdStatement
                    if (!type.Equals(existingType))
                    {
                        throw new EventAdapterException("Event type named '" + eventTypeAlias +
                                "' has already been declared with differing column name or type information");
                    }

                    // Since it's the same, return the existing type
                    return existingType;
                }

                aliasToTypeMap.Put(eventTypeAlias, type);
                xmldomRootElementNames.Put(configurationEventTypeXMLDOM.RootElementName, type);

                String eventTypeID = "XMLDOM_" + eventTypeAlias;
                idToTypeMap.Put(eventTypeID, type);
                idToAliasMap.Put(eventTypeID, eventTypeAlias);
                typeToIdMap.Put(type, eventTypeID);

                return type;
            }
        }

        /// <summary>
        /// Add an event type with the given alias and the given underlying event type,
        /// as well as the additional given properties.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="underlyingEventType">is the event type for the event type that this wrapper wraps</param>
        /// <param name="propertyTypes">is the names and types of any additional properties</param>
        /// <returns>eventType is the type added</returns>
        /// <throws>
        /// EventAdapterException if alias already exists and doesn't match this type's info
        /// </throws>
        public EventType AddWrapperType(String eventTypeAlias, EventType underlyingEventType, EDictionary<String, Type> propertyTypes)
        {
            lock (this)
            {
                WrapperEventType newEventType = new WrapperEventType(eventTypeAlias, underlyingEventType, propertyTypes, this);

                EventType existingType = aliasToTypeMap.Fetch(eventTypeAlias);
                if (existingType != null)
                {
                    // The existing type must be the same as the type createdStatement
                    if (!newEventType.Equals(existingType))
                    {
                        throw new EventAdapterException("Event type named '" + eventTypeAlias +
                                "' has already been declared with differing column name or type information");
                    }

                    // Since it's the same, return the existing type
                    return existingType;
                }

                aliasToTypeMap.Put(eventTypeAlias, newEventType);
                String eventTypeID = "WRAPPER_" + eventTypeAlias;
                idToTypeMap.Put(eventTypeID, newEventType);
                idToAliasMap.Put(eventTypeID, eventTypeAlias);
                typeToIdMap.Put(newEventType, eventTypeID);

                return newEventType;
            }
        }

        /// <summary>
        /// Create a wrapper around an event and some additional properties
        /// </summary>
        /// <param name="_event">is the wrapped event</param>
        /// <param name="properties">are the additional properties</param>
        /// <param name="eventType">os the type metadata for any wrappers of this type</param>
        /// <returns>wrapper event bean</returns>
        public EventBean CreateWrapper(EventBean _event, EDictionary<String, Object> properties, EventType eventType)
        {
            return new WrapperEventBean(_event, properties, eventType);
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
        /// <param name="types">is the various event types returned.</param>
        /// <returns>map of property name and type</returns>
        private static EDictionary<String, Type> GetUnderlyingTypes(EDictionary<String, EventType> types)
        {
            EDictionary<String, Type> classes = new HashDictionary<String, Type>();

            foreach (KeyValuePair<String, EventType> type in types)
            {
                classes.Put(type.Key, type.Value.UnderlyingType);
            }

            return classes;
        }

        /// <summary>
        /// Add an event type with the given alias and Java fully-qualified class name.
        /// If the alias already exists with the same class name, returns the existing EventType instance.
        /// If the alias already exists with different class name, throws an exception.
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.events.BeanEventType"/>.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="fullyQualClassName">is the fully qualified class name</param>
        /// <returns>event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match class names </throws>
		abstract public EventType AddBeanType(string eventTypeAlias, string fullyQualClassName);

        /// <summary>
        /// Add an event type with the given alias and Java class.
        /// If the alias already exists with the same Class, returns the existing EventType instance.
        /// If the alias already exists with different Class name, throws an exception.
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.events.BeanEventType"/>.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="type">is the type</param>
        /// <returns>event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match class names </throws>
		abstract public EventType AddBeanType(string eventTypeAlias, Type type);

        /// <summary>
        /// Wrap the native event returning an <seealso cref="EventBean"/>.
        /// </summary>
        /// <param name="ev">event to be wrapped</param>
        /// <returns>
        /// event bean wrapping native underlying event
        /// </returns>
		abstract public EventBean AdapterForBean(object ev);
    }
} // End of namespace
