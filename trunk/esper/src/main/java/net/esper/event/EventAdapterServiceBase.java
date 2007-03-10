package net.esper.event;

import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.event.xml.SchemaXMLEventType;
import net.esper.event.xml.SimpleXMLEventType;
import net.esper.event.xml.XMLEventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation for resolving event name to event type.
 * <p>
 * The implementation assigned a unique identifier to each event type.
 * For Class-based event types, only one EventType instance and one event type id exists for the same class.
 * <p>
 * Alias names must be unique, that is an alias name must resolve to a single event type.
 * <p>
 * Each event type can have multiple aliases defined for it. For example, expressions such as
 * "select * from A" and "select * from B"
 * in which A and B are aliases for the same class X the select clauses each fireStatementStopped for events of type X.
 * In summary, aliases A and B point to the same underlying event type and therefore event type id.
 */
public abstract class EventAdapterServiceBase implements EventAdapterService
{
    private static Log log = LogFactory.getLog(EventAdapterServiceBase.class);

    private final Map<String, EventType> idToTypeMap;
    private final Map<String, String>    idToAliasMap;
    private final Map<EventType, String> typeToIdMap;
    private final Map<String, EventType> aliasToTypeMap;

    private BeanEventAdapter beanEventAdapter;
    private Map<String, EventType> xmldomRootElementNames;

    /**
     * Ctor.
     */
    public EventAdapterServiceBase()
    {
        typeToIdMap = new HashMap<EventType, String>();
        aliasToTypeMap = new HashMap<String, EventType>();
        idToTypeMap = new HashMap<String, EventType>();
        idToAliasMap = new HashMap<String, String>();
        xmldomRootElementNames = new HashMap<String, EventType>();
        beanEventAdapter = new BeanEventAdapter();
    }

    /**
     * Set the legacy Java class type information.
     * @param classToLegacyConfigs is the legacy class configs
     */
    public void setClassLegacyConfigs(Map<String, ConfigurationEventTypeLegacy> classToLegacyConfigs)
    {
        beanEventAdapter.setClassToLegacyConfigs(classToLegacyConfigs);
    }

    public String getIdByAlias(String eventTypeAlias)
    {
        if (eventTypeAlias == null)
        {
            throw new IllegalArgumentException("Null event type alias parameter");
        }
        EventType type = aliasToTypeMap.get(eventTypeAlias);
        if (type == null)
        {
            throw new IllegalStateException("Event type alias " + eventTypeAlias + " not in collection");
        }
        String id = typeToIdMap.get(type);
        if (id == null)
        {
            throw new IllegalStateException("Event type id " + id + " not in collection");
        }
        return id;
    }

    public EventType getTypeById(String eventTypeID)
    {
        if (eventTypeID == null)
        {
            throw new IllegalArgumentException("Null event type id parameter");
        }
        EventType eventType = idToTypeMap.get(eventTypeID);
        if (eventType == null)
        {
            throw new IllegalStateException("Event type id " + eventTypeID + " not in collection");
        }
        return eventType;
    }

    public EventType getExistsTypeByAlias(String eventTypeAlias)
    {
        if (eventTypeAlias == null)
        {
            throw new IllegalStateException("Null event type alias parameter");
        }
        return aliasToTypeMap.get(eventTypeAlias);
    }

    public String getIdByType(EventType eventType)
    {
        if (eventType == null)
        {
            throw new IllegalArgumentException("Null event type parameter");
        }
        String id = typeToIdMap.get(eventType);
        if (id == null)
        {
            throw new IllegalStateException("Event type " + eventType.toString() + " not in collection");
        }
        return id;
    }

    public String getAliasById(String eventTypeID)
    {
        if (eventTypeID == null)
        {
            throw new IllegalArgumentException("Null event type id parameter");
        }
        String alias = idToAliasMap.get(eventTypeID);
        if (alias == null)
        {
            throw new IllegalStateException("Event type id " + eventTypeID + " not in collection");
        }
        return alias;
    }

    /**
     * Add an alias and class as an event type.
     * @param eventTypeAlias is the alias
     * @param clazz is the Java class to add
     * @return event type
     * @throws EventAdapterException to indicate an error constructing the type
     */
    public synchronized EventType addBeanTypeByAliasAndClazz(String eventTypeAlias, Class clazz) throws EventAdapterException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".addBeanType Adding " + eventTypeAlias + " for type " + clazz.getName());
        }

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().equals(clazz))
            {
                return existingType;
            }

            throw new EventAdapterException("Event type named '" + eventTypeAlias +
                    "' has already been declared with differing column name or type information");
        }

        EventTypeSPI eventType = beanEventAdapter.createOrGetBeanType(clazz);
        String eventTypeId = eventType.getEventTypeId();
        aliasToTypeMap.put(eventTypeAlias, eventType);
        idToTypeMap.put(eventTypeId, eventType);
        idToAliasMap.put(eventTypeId, eventTypeAlias);
        typeToIdMap.put(eventType, eventTypeId);

        return eventType;
    }

    /**
     * Create an event bean given an event of object id.
     * @param event is the event class
     * @param eventId is the event id
     * @return event
     */
    public EventBean adapterForBean(Object event, Object eventId)
    {
        return beanEventAdapter.adapterForBean(event, eventId);
    }

    /**
     * Add an event type for the given Java class name.
     * @param eventTypeAlias is the alias
     * @param fullyQualClassName is the Java class name
     * @return event type
     * @throws EventAdapterException if the Class name cannot resolve or other error occured
     */
    public synchronized EventType addBeanTypeByAliasAndClassName(String eventTypeAlias, String fullyQualClassName) throws EventAdapterException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".addBeanType Adding " + eventTypeAlias + " for type " + fullyQualClassName);
        }

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().getName().equals(fullyQualClassName))
            {
                if (log.isDebugEnabled())
                {
                    log.debug(".addBeanType Returning existing type for " + eventTypeAlias);
                }
                return existingType;
            }

            throw new EventAdapterException("Event type named '" + eventTypeAlias +
                    "' has already been declared with differing column name or type information");
        }

        Class clazz;
        try
        {
            clazz = Class.forName(fullyQualClassName);
        }
        catch (ClassNotFoundException ex)
        {
            throw new EventAdapterException("Failed to load class " + fullyQualClassName, ex);
        }

        EventType eventType = beanEventAdapter.createOrGetBeanType(clazz);
        String eventTypeID = ((EventTypeSPI) eventType).getEventTypeId();
        aliasToTypeMap.put(eventTypeAlias, eventType);
        idToTypeMap.put(eventTypeID, eventType);
        idToAliasMap.put(eventTypeID, eventTypeAlias);
        typeToIdMap.put(eventType, eventTypeID);

        return eventType;
    }

    public synchronized EventType addMapType(String eventTypeAlias, Map<String, Class> propertyTypes) throws EventAdapterException
    {
        MapEventType newEventType = new MapEventType(eventTypeAlias, propertyTypes, this);

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            // The existing type must be the same as the type createdStatement
            if (!newEventType.equals(existingType))
            {
                throw new EventAdapterException("Event type named '" + eventTypeAlias +
                        "' has already been declared with differing column name or type information");
            }

            // Since it's the same, return the existing type
            return existingType;
        }

        String eventTypeID = "MAP_" + eventTypeAlias;
        aliasToTypeMap.put(eventTypeAlias, newEventType);
        idToTypeMap.put(eventTypeID, newEventType);
        idToAliasMap.put(eventTypeID, eventTypeAlias);
        typeToIdMap.put(newEventType, eventTypeID);

        return newEventType;
    }

    public EventType createAnonymousMapType(Map<String, Class> propertyTypes) throws EventAdapterException
    {
        return new MapEventType("", propertyTypes, this);
    }

    public EventType createAnonymousWrapperType(EventType underlyingEventType, Map<String, Class> propertyTypes) throws EventAdapterException
    {
    	return new WrapperEventType("", underlyingEventType, propertyTypes, this);
    }

    public EventBean adapterForMap(Map event, String eventTypeAlias) throws EventAdapterException
    {
        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType == null)
        {
            throw new EventAdapterException("Event type alias '" + eventTypeAlias + "' has not been defined");
        }

        Map<String, Object> eventMap = (Map<String, Object>) event;

        return createMapFromValues(eventMap, existingType);
    }

    public EventBean createMapFromValues(Map<String, Object> properties, EventType eventType)
    {
        return new MapEventBean(properties, eventType);
    }

    public EventBean createMapFromUnderlying(Map<String, EventBean> events, EventType eventType)
    {
        return new MapEventBean(eventType, events);
    }

    public EventType createAddToEventType(EventType originalType, String[] fieldNames, Class[] fieldTypes)
    {
        Map<String, Class> types = new HashMap<String, Class>();

        // Copy properties of original event, add property value
        for (String property : originalType.getPropertyNames())
        {
            types.put(property, originalType.getPropertyType(property));
        }

        // Copy new properties
        for (int i = 0; i < fieldNames.length; i++)
        {
            types.put(fieldNames[i], fieldTypes[i]);
        }

        return createAnonymousMapType(types);
    }

    public EventBean adapterForDOM(Node node)
    {
        String rootElementName = null;
        Node namedNode = null;
        if (node instanceof Document)
        {
            namedNode = ((Document) node).getDocumentElement();
        }
        rootElementName = namedNode.getLocalName();
        if (rootElementName == null)
        {
            rootElementName = namedNode.getNodeName();
        }

        EventType eventType = xmldomRootElementNames.get(rootElementName);
        if (eventType == null)
        {
            throw new EventAdapterException("DOM event root element name '" + rootElementName +
                    "' has not been configured");
        }

        return new XMLEventBean(node, eventType);
    }

    public EventType createAnonymousCompositeType(Map<String, EventType> taggedEventTypes)
    {
        return new CompositeEventType(taggedEventTypes);
    }

    public EventBean adapterForCompositeEvent(EventType eventType, Map<String, EventBean> taggedEvents)
    {
        return new CompositeEventBean(taggedEvents, eventType);
    }

    /**
     * Add a configured XML DOM event type.
     * @param eventTypeAlias is the alias name of the event type
     * @param configurationEventTypeXMLDOM configures the event type schema and namespace and XPath
     * property information.
     */
    public synchronized void addXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        if (configurationEventTypeXMLDOM.getRootElementName() == null)
        {
            throw new EventAdapterException("Required root element name has not been supplied");
        }
        EventType type = null;
        if (configurationEventTypeXMLDOM.getSchemaResource() == null)
        {
            type = new SimpleXMLEventType(configurationEventTypeXMLDOM);
        }
        else
        {
            type = new SchemaXMLEventType(configurationEventTypeXMLDOM);
        }

        aliasToTypeMap.put(eventTypeAlias, type);
        xmldomRootElementNames.put(configurationEventTypeXMLDOM.getRootElementName(), type);

        String eventTypeID = "XMLDOM_" + eventTypeAlias;
        idToTypeMap.put(eventTypeID, type);
        idToAliasMap.put(eventTypeID, eventTypeAlias);
        typeToIdMap.put(type, eventTypeID);
    }

    public synchronized EventType addWrapperType(String eventTypeAlias, EventType underlyingEventType, Map<String, Class> propertyTypes) throws EventAdapterException
	{
	    WrapperEventType newEventType = new WrapperEventType(eventTypeAlias, underlyingEventType, propertyTypes, this);

	    EventType existingType = aliasToTypeMap.get(eventTypeAlias);
	    if (existingType != null)
	    {
	        // The existing type must be the same as the type createdStatement
	        if (!newEventType.equals(existingType))
	        {
	            throw new EventAdapterException("Event type named '" + eventTypeAlias +
	                    "' has already been declared with differing column name or type information");
	        }

	        // Since it's the same, return the existing type
	        return existingType;
	    }

	    aliasToTypeMap.put(eventTypeAlias, newEventType);
        String eventTypeID = "WRAPPER_" + eventTypeAlias;
        idToTypeMap.put(eventTypeID, newEventType);
        idToAliasMap.put(eventTypeID, eventTypeAlias);
        typeToIdMap.put(newEventType, eventTypeID);

	    return newEventType;
	}

	public EventBean createWrapper(EventBean event, Map<String, Object> properties, EventType eventType)
	{
		return new WrapperEventBean(event, properties, eventType);
	}

	public EventType createAnonymousMapTypeUnd(Map<String, EventType> propertyTypes)
    {
        Map<String, Class> underlyingTypes = getUnderlyingTypes(propertyTypes);
        return this.createAnonymousMapType(underlyingTypes);
    }

    /**
     * Return a map of property name and types for a given map of property name and event type,
     * by extracting the underlying type for the event types.
     * @param types is the various event types returned.
     * @return map of property name and type
     */
    private static Map<String, Class> getUnderlyingTypes(Map<String, EventType> types)
    {
        Map<String, Class> classes = new HashMap<String, Class>();

        for (Map.Entry<String, EventType> type : types.entrySet())
        {
            classes.put(type.getKey(), type.getValue().getUnderlyingType());
        }

        return classes;
    }
}
