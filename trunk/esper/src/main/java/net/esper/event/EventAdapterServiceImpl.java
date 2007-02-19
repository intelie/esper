package net.esper.event;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.Map;
import java.util.HashMap;

import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.event.xml.SimpleXMLEventType;
import net.esper.event.xml.XMLEventBean;
import net.esper.event.xml.SchemaXMLEventType;

/**
 * Implementation for resolving event name to event type.
 */
public class EventAdapterServiceImpl implements EventAdapterService
{
    private Map<String, EventType> eventTypes;
    private BeanEventAdapter beanEventAdapter;
    private Map<String, EventType> xmldomRootElementNames;

    /**
     * Ctor.
     * @param classToLegacyConfigs is a map of event type alias to legacy event type config
     */
    public EventAdapterServiceImpl(Map<String, ConfigurationEventTypeLegacy> classToLegacyConfigs)
    {
        eventTypes = new HashMap<String, EventType>();
        xmldomRootElementNames = new HashMap<String, EventType>();
        beanEventAdapter = new BeanEventAdapter(classToLegacyConfigs);
    }

    public EventType getEventType(String eventName)
    {
        // Check the configuration first for the known event name
        return eventTypes.get(eventName);
    }

    public synchronized EventType addBeanType(String eventTypeAlias, Class clazz) throws EventAdapterException
    {
        EventType existingType = eventTypes.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().equals(clazz))
            {
                return existingType;
            }

            throw new EventAdapterException("Event type named '" + eventTypeAlias +
                    "' has already been declared with differing column name or type information");
        }

        EventType eventType = beanEventAdapter.createOrGetBeanType(clazz);
        eventTypes.put(eventTypeAlias, eventType);

        return eventType;
    }

    public synchronized EventType addBeanType(String eventTypeAlias, String fullyQualClassName) throws EventAdapterException
    {
        EventType existingType = eventTypes.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().getName().equals(fullyQualClassName))
            {
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
        eventTypes.put(eventTypeAlias, eventType);

        return eventType;
    }

    public synchronized EventType addMapType(String eventTypeAlias, Map<String, Class> propertyTypes) throws EventAdapterException
    {
        MapEventType newEventType = new MapEventType(eventTypeAlias, propertyTypes, this);

        EventType existingType = eventTypes.get(eventTypeAlias);
        if (existingType != null)
        {
            // The existing type must be the same as the type created
            if (!newEventType.equals(existingType))
            {
                throw new EventAdapterException("Event type named '" + eventTypeAlias +
                        "' has already been declared with differing column name or type information");
            }

            // Since it's the same, return the existing type
            return existingType;
        }

        eventTypes.put(eventTypeAlias, newEventType);

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

    public EventBean adapterForBean(Object event)
    {
        return beanEventAdapter.adapterForBean(event);
    }

    public EventBean adapterForMap(Map event, String eventTypeAlias) throws EventAdapterException
    {
        EventType existingType = eventTypes.get(eventTypeAlias);
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

        eventTypes.put(eventTypeAlias, type);
        xmldomRootElementNames.put(configurationEventTypeXMLDOM.getRootElementName(), type);
    }

    public synchronized EventType addWrapperType(String eventTypeAlias, EventType underlyingEventType, Map<String, Class> propertyTypes) throws EventAdapterException
	{
	    WrapperEventType newEventType = new WrapperEventType(eventTypeAlias, underlyingEventType, propertyTypes, this);
	
	    EventType existingType = eventTypes.get(eventTypeAlias);
	    if (existingType != null)
	    {
	        // The existing type must be the same as the type created
	        if (!newEventType.equals(existingType))
	        {
	            throw new EventAdapterException("Event type named '" + eventTypeAlias +
	                    "' has already been declared with differing column name or type information");
	        }
	
	        // Since it's the same, return the existing type
	        return existingType;
	    }
	
	    eventTypes.put(eventTypeAlias, newEventType);
	
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
