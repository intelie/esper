package net.esper.event;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.HashMap;

import net.esper.client.ConfigurationEventTypeXMLDOM;
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
    private Map<String, EventType> rootNodeTypes;

    /**
     * Ctor.
     */
    public EventAdapterServiceImpl()
    {
        eventTypes = new HashMap<String, EventType>();
        rootNodeTypes = new HashMap<String, EventType>();
        beanEventAdapter = new BeanEventAdapter();
    }

    public EventType getEventType(String eventName)
    {
        // Check the configuration first for the known event name
        return eventTypes.get(eventName);
    }

    public EventType addBeanType(String eventTypeAlias, Class clazz) throws EventAdapterException
    {
        EventType existingType = eventTypes.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().equals(clazz))
            {
                return existingType;
            }

            throw new EventAdapterException("Event type named '" + eventTypeAlias +
                    "' has already been declared with differing type information");
        }

        EventType eventType = beanEventAdapter.createBeanType(clazz);
        eventTypes.put(eventTypeAlias, eventType);

        return eventType;
    }

    public EventType addBeanType(String eventTypeAlias, String fullyQualClassName) throws EventAdapterException
    {
        EventType existingType = eventTypes.get(eventTypeAlias);
        if (existingType != null)
        {
            if (existingType.getUnderlyingType().getName().equals(fullyQualClassName))
            {
                return existingType;
            }

            throw new EventAdapterException("Event type named '" + eventTypeAlias +
                    "' has already been declared with differing type information");
        }

        Class clazz = null;
        try
        {
            clazz = Class.forName(fullyQualClassName);
        }
        catch (ClassNotFoundException ex)
        {
            throw new EventAdapterException("Failed to load class " + fullyQualClassName, ex);
        }

        EventType eventType = beanEventAdapter.createBeanType(clazz);
        eventTypes.put(eventTypeAlias, eventType);

        return eventType;
    }

    public EventType addMapType(String eventTypeAlias, Map<String, Class> propertyTypes) throws EventAdapterException
    {
        MapEventType newEventType = new MapEventType(propertyTypes);

        EventType existingType = eventTypes.get(eventTypeAlias);
        if (existingType != null)
        {
            // The existing type must be the same as the type created
            if (!newEventType.equals(existingType))
            {
                throw new EventAdapterException("Event type named '" + eventTypeAlias +
                        "' has already been declared with differing type information");
            }

            // Since it's the same, return the existing type
            return existingType;
        }

        eventTypes.put(eventTypeAlias, newEventType);

        return newEventType;
    }

    public EventType createAnonymousMapType(Map<String, Class> propertyTypes) throws EventAdapterException
    {
        return new MapEventType(propertyTypes);
    }

    public EventBean adapterForBean(Object event)
    {
        return beanEventAdapter.adapterForBean(event);
    }

    public EventBean adapterForMap(Map<String,Object> event, String eventTypeAlias) throws EventAdapterException
    {
        EventType existingType = eventTypes.get(eventTypeAlias);
        if (existingType == null)
        {
            throw new EventAdapterException("Event type alias '" + eventTypeAlias + "' has not been defined");
        }

        return createMapFromValues(event, existingType);
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

    public EventBean adapterForDOM(Document document)
    {
        Element rootElement = document.getDocumentElement();
        String rootNodeName = rootElement.getNodeName();

        EventType eventType = rootNodeTypes.get(rootNodeName);
        if (eventType == null)
        {
            throw new EventAdapterException("DOM event root node '" + rootNodeName +
                    "' has not been configured");
        }

        EventBean event = new XMLEventBean(document, eventType);

        return event;
    }

    public void addXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        if (configurationEventTypeXMLDOM.getSchemaURI() == null)
        {
            EventType type = new SimpleXMLEventType(configurationEventTypeXMLDOM);
            eventTypes.put(eventTypeAlias, type);
            rootNodeTypes.put(configurationEventTypeXMLDOM.getRootElementName(), type);
        }
        else
        {
            EventType type = new SchemaXMLEventType(configurationEventTypeXMLDOM);
            eventTypes.put(eventTypeAlias, type);
            rootNodeTypes.put(configurationEventTypeXMLDOM.getRootElementName(), type);
        }
    }
}
