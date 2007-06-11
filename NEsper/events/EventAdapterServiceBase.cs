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

        private BeanEventAdapter beanEventAdapter;
        private EDictionary<String, EventType> xmldomRootElementNames;

        /// <summary>Ctor.</summary>
        public EventAdapterServiceBase()
        {
            typeToIdMap = new EHashDictionary<EventType, String>();
            aliasToTypeMap = new EHashDictionary<String, EventType>();
            idToTypeMap = new EHashDictionary<String, EventType>();
            idToAliasMap = new EHashDictionary<String, String>();
            xmldomRootElementNames = new EHashDictionary<String, EventType>();
            beanEventAdapter = new BeanEventAdapter();
        }

        /// <summary>Gets or sets the legacy class type information.</summary>
        public EDictionary<String, ConfigurationEventTypeLegacy> TypeLegacyConfigs
        {
            get { return beanEventAdapter.TypeToLegacyConfigs; }
            set { beanEventAdapter.TypeToLegacyConfigs = value;}
        }

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

        public EventType GetEventTypeByAlias(String eventTypeAlias)
        {
            if (eventTypeAlias == null)
            {
                throw new IllegalStateException("Null event type alias parameter");
            }
            return aliasToTypeMap.Fetch(eventTypeAlias);
        }

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

        /// <summary>Add an alias and class as an event type.</summary>
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

        /// <summary>Create an event bean given an event of object id.</summary>
        /// <param name="event">is the event class</param>
        /// <param name="eventId">is the event id</param>
        /// <returns>event</returns>
        public EventBean AdapterForBean(Object _event, Object eventId)
        {
            return beanEventAdapter.AdapterForBean(_event, eventId);
        }

        /// <summary>Add an event type for the given Java class name.</summary>
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
                    type = Type.GetType(fullyQualClassName);
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

        public EventType CreateAnonymousMapType(EDictionary<String, Type> propertyTypes)
        {
            return new MapEventType("", propertyTypes, this);
        }

        public EventType CreateAnonymousWrapperType(EventType underlyingEventType, EDictionary<String, Type> propertyTypes)
        {
            return new WrapperEventType("", underlyingEventType, propertyTypes, this);
        }

        public EventBean AdapterForMap(EDictionary<String, Object> _event, String eventTypeAlias)
        {
            EventType existingType = aliasToTypeMap.Fetch(eventTypeAlias);
            if (existingType == null)
            {
                throw new EventAdapterException("Event type alias '" + eventTypeAlias + "' has not been defined");
            }

            return CreateMapFromValues(_event, existingType);
        }

        public EventBean CreateMapFromValues(EDictionary<String, Object> properties, EventType eventType)
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
                types.Put(property, originalType.GetPropertyType(property));
            }

            // Copy new properties
            for (int i = 0; i < fieldNames.Length; i++)
            {
                types.Put(fieldNames[i], fieldTypes[i]);
            }

            return CreateAnonymousMapType(types);
        }

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

        public EventType CreateAnonymousCompositeType(EDictionary<String, EventType> taggedEventTypes)
        {
            return new CompositeEventType(taggedEventTypes);
        }

        public EventBean AdapterForCompositeEvent(EventType eventType, EDictionary<String, EventBean> taggedEvents)
        {
            return new CompositeEventBean(taggedEvents, eventType);
        }

        /// <summary>Add a configured XML DOM event type.</summary>
        /// <param name="eventTypeAlias">is the alias name of the event type</param>
        /// <param name="configurationEventTypeXMLDOM">
        /// configures the event type schema and namespace and XPath
        /// property information.
        /// </param>
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

        public EventBean CreateWrapper(EventBean _event, EDictionary<String, Object> properties, EventType eventType)
        {
            return new WrapperEventBean(_event, properties, eventType);
        }

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
            EDictionary<String, Type> classes = new EHashDictionary<String, Type>();

            foreach (KeyValuePair<String, EventType> type in types)
            {
                classes.Put(type.Key, type.Value.UnderlyingType);
            }

            return classes;
        }
    	
		abstract public EventType AddBeanType(string eventTypeAlias, string fullyQualClassName);
    	
		abstract public EventType AddBeanType(string eventTypeAlias, Type type);
    	
		abstract public EventBean AdapterForBean(object ev);
    }
} // End of namespace
