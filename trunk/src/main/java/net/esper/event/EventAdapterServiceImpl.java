package net.esper.event;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.*;

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
  private Map<String, Set<EventTypeListener>> eventTypeListeners;

  /**
   * Ctor.
   *
   * @param classToLegacyConfigs is a map of event type alias to legacy event type config
   */
  public EventAdapterServiceImpl(Map<String, ConfigurationEventTypeLegacy> classToLegacyConfigs)
  {
    eventTypes = new HashMap<String, EventType>();
    eventTypeListeners = new HashMap<String, Set<EventTypeListener>>();
    xmldomRootElementNames = new HashMap<String, EventType>();
    beanEventAdapter = new BeanEventAdapter(classToLegacyConfigs);
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

    EventType eventType = beanEventAdapter.createOrGetBeanType(clazz);
    eventTypes.put(eventTypeAlias, eventType);
    notifyEventTypeListeners(eventTypeAlias);
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

    EventType eventType = beanEventAdapter.createOrGetBeanType(clazz);
    eventTypes.put(eventTypeAlias, eventType);
    notifyEventTypeListeners(eventTypeAlias);
    return eventType;
  }

  public EventType addMapType(String eventTypeAlias, Map<String, Class> propertyTypes) throws EventAdapterException
  {
    MapEventType newEventType = new MapEventType(propertyTypes, this);

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
    notifyEventTypeListeners(eventTypeAlias);
    return newEventType;
  }

  public EventType createAnonymousMapType(Map<String, Class> propertyTypes) throws EventAdapterException
  {
    return new MapEventType(propertyTypes, this);
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
   *
   * @param eventTypeAlias               is the alias name of the event type
   * @param configurationEventTypeXMLDOM configures the event type schema and namespace and XPath
   *                                     property information.
   */
  public void addXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
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
    notifyEventTypeListeners(eventTypeAlias);
    xmldomRootElementNames.put(configurationEventTypeXMLDOM.getRootElementName(), type);
  }

  public EventType createAnonymousMapTypeUnd(Map<String, EventType> propertyTypes)
  {
    Map<String, Class> underlyingTypes = getUnderlyingTypes(propertyTypes);
    return this.createAnonymousMapType(underlyingTypes);
  }

  public synchronized void registerInterest(String eventTypeAlias, EventTypeListener listener)
  {
    if (!eventTypeListeners.containsKey(eventTypeAlias))
    {
      eventTypeListeners.put(eventTypeAlias, new HashSet<EventTypeListener>());
    }
    Set<EventTypeListener> listeners = eventTypeListeners.get(eventTypeAlias);
    listeners.add(listener);
  }

  private void notifyEventTypeListeners(String eventTypeAlias)
  {
    if (!eventTypeListeners.containsKey(eventTypeAlias)) return;
    Set<EventTypeListener> listeners = eventTypeListeners.get(eventTypeAlias);
    for (Iterator<EventTypeListener> it = listeners.iterator(); it.hasNext();)
    {
      it.next().registeredEventType(eventTypeAlias, eventTypes.get(eventTypeAlias));
    }
  }

  /**
   * Return a map of property name and types for a given map of property name and event type,
   * by extracting the underlying type for the event types.
   *
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
