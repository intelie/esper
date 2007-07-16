package net.esper.event;

import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.Configuration;
import net.esper.event.xml.SchemaXMLEventType;
import net.esper.event.xml.SimpleXMLEventType;
import net.esper.event.xml.XMLEventBean;
import net.esper.util.UuidGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class EventAdapterServiceImpl implements EventAdapterService
{
    private static Log log = LogFactory.getLog(EventAdapterServiceImpl.class);

    private final ConcurrentHashMap<Class, BeanEventType> typesPerJavaBean;
    private final Map<String, EventType> aliasToTypeMap;
    private BeanEventAdapter beanEventAdapter;
    private Map<String, EventType> xmldomRootElementNames;

    /**
     * Ctor.
     */
    public EventAdapterServiceImpl()
    {
        aliasToTypeMap = new HashMap<String, EventType>();
        xmldomRootElementNames = new HashMap<String, EventType>();

        // Share the mapping of class to type with the type creation for thread safety
        typesPerJavaBean = new ConcurrentHashMap<Class, BeanEventType>();
        beanEventAdapter = new BeanEventAdapter(typesPerJavaBean);
    }

    /**
     * Set the legacy Java class type information.
     * @param classToLegacyConfigs is the legacy class configs
     */
    public void setClassLegacyConfigs(Map<String, ConfigurationEventTypeLegacy> classToLegacyConfigs)
    {
        beanEventAdapter.setClassToLegacyConfigs(classToLegacyConfigs);
    }

    /**
     * Sets the default property resolution style.
     * @param defaultPropertyResolutionStyle is the default style
     */
    public void setDefaultPropertyResolutionStyle(Configuration.PropertyResolutionStyle defaultPropertyResolutionStyle)
    {
        beanEventAdapter.setDefaultPropertyResolutionStyle(defaultPropertyResolutionStyle);
    }

    public EventType getExistsTypeByAlias(String eventTypeAlias)
    {
        if (eventTypeAlias == null)
        {
            throw new IllegalStateException("Null event type alias parameter");
        }
        return aliasToTypeMap.get(eventTypeAlias);
    }

    /**
     * Add an alias and class as an event type.
     * @param eventTypeAlias is the alias
     * @param clazz is the Java class to add
     * @return event type
     * @throws EventAdapterException to indicate an error constructing the type
     */
    public synchronized EventType addBeanType(String eventTypeAlias, Class clazz) throws EventAdapterException
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

        EventType eventType = beanEventAdapter.createBeanType(eventTypeAlias, clazz);
        aliasToTypeMap.put(eventTypeAlias, eventType);

        return eventType;
    }

    /**
     * Create an event bean given an event of object id.
     * @param event is the event class
     * @return event
     */
    public EventBean adapterForBean(Object event)
    {
        EventType eventType = typesPerJavaBean.get(event.getClass());
        if (eventType == null)
        {
            // This will update the typesPerJavaBean mapping
            eventType = beanEventAdapter.createBeanType(event.getClass().getName(), event.getClass());
        }
        return new BeanEventBean(event, eventType);
    }

    /**
     * Add an event type for the given Java class name.
     * @param eventTypeAlias is the alias
     * @param fullyQualClassName is the Java class name
     * @return event type
     * @throws EventAdapterException if the Class name cannot resolve or other error occured
     */
    public synchronized EventType addBeanType(String eventTypeAlias, String fullyQualClassName) throws EventAdapterException
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

        EventType eventType = beanEventAdapter.createBeanType(eventTypeAlias, clazz);
        aliasToTypeMap.put(eventTypeAlias, eventType);

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

        aliasToTypeMap.put(eventTypeAlias, newEventType);

        return newEventType;
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

    public EventBean adapterForDOM(Node node)
    {
        String rootElementName;
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

    /**
     * Add a configured XML DOM event type.
     * @param eventTypeAlias is the alias name of the event type
     * @param configurationEventTypeXMLDOM configures the event type schema and namespace and XPath
     * property information.
     */
    public synchronized EventType addXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        if (configurationEventTypeXMLDOM.getRootElementName() == null)
        {
            throw new EventAdapterException("Required root element name has not been supplied");
        }
        EventType type;
        if (configurationEventTypeXMLDOM.getSchemaResource() == null)
        {
            type = new SimpleXMLEventType(configurationEventTypeXMLDOM);
        }
        else
        {
            type = new SchemaXMLEventType(configurationEventTypeXMLDOM);
        }

        EventType existingType = aliasToTypeMap.get(eventTypeAlias);
        if (existingType != null)
        {
            // The existing type must be the same as the type createdStatement
            if (!type.equals(existingType))
            {
                throw new EventAdapterException("Event type named '" + eventTypeAlias +
                        "' has already been declared with differing column name or type information");
            }

            // Since it's the same, return the existing type
            return existingType;
        }

        aliasToTypeMap.put(eventTypeAlias, type);
        xmldomRootElementNames.put(configurationEventTypeXMLDOM.getRootElementName(), type);

        return type;
    }

    public final EventBean createMapFromValues(Map<String, Object> properties, EventType eventType)
    {
        return new MapEventBean(properties, eventType);
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

	    return newEventType;
	}

    public final EventType createAnonymousMapType(Map<String, Class> propertyTypes) throws EventAdapterException
    {
        String alias = UuidGenerator.generate(propertyTypes);
        return new MapEventType(alias, propertyTypes, this);
    }

    public final EventType createAnonymousWrapperType(EventType underlyingEventType, Map<String, Class> propertyTypes) throws EventAdapterException
    {
        String alias = UuidGenerator.generate(propertyTypes);
    	return new WrapperEventType(alias, underlyingEventType, propertyTypes, this);
    }

    public final EventBean createMapFromUnderlying(Map<String, EventBean> events, EventType eventType)
    {
        return new MapEventBean(eventType, events);
    }

    public final EventType createAddToEventType(EventType originalType, String[] fieldNames, Class[] fieldTypes)
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

    public final EventType createAnonymousCompositeType(Map<String, EventType> taggedEventTypes)
    {
        String alias = UuidGenerator.generate(taggedEventTypes);
        return new CompositeEventType(alias, taggedEventTypes);
    }

	public final EventBean createWrapper(EventBean event, Map<String, Object> properties, EventType eventType)
	{
		return new WrapperEventBean(event, properties, eventType);
	}

    public final EventBean adapterForCompositeEvent(EventType eventType, Map<String, EventBean> taggedEvents)
    {
        return new CompositeEventBean(taggedEvents, eventType);
    }

	public final EventType createAnonymousMapTypeUnd(Map<String, EventType> propertyTypes)
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
