package net.esper.client;

import java.util.Map;
import java.util.Properties;

/**
 * Provides configuration operations for configuration-time and runtime parameters.
 */
public interface ConfigurationOperations
{
    /**
     * Add an alias for an event type represented by JavaBean object events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClassName fully-qualified class name of the event type
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, String javaEventClassName)
            throws ConfigurationException;

    /**
     * Add an alias for an event type represented by Java-bean plain-old Java object events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClass is the Java event class for which to create the alias
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, Class javaEventClass)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents java.util.Map events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param typeMap maps the name of each property in the Map event to the type
     * (fully qualified classname) of its value in Map event instances.
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, Properties typeMap)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents java.util.Map events, taking a Map of
     * event property and class name as a parameter.
     * <p>
     * This method is provided for convenience and is same in function to method
     * taking a Properties object that contain fully qualified class name as values.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param typeMap maps the name of each property in the Map event to the type of its value in the Map object
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, Map<String, Class> typeMap)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents org.w3c.dom.Node events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param xmlDOMEventTypeDesc descriptor containing property and mapping information for XML-DOM events
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc)
            throws ConfigurationException;
}
