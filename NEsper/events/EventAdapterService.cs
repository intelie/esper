using System;
using System.Xml;

using net.esper.client;
using net.esper.compat;

namespace net.esper.events
{
    /// <summary>
    /// Interface for a service to resolve event names to event type.
    /// </summary>

    public interface EventAdapterService
    {
		/// <summary>
		/// Returns the event type id given an event type alias. The alias is expected to exist.
		/// &lt;p&gt; Use getExistsTypeByAlias to check if an alias exists.
		/// </summary>
		/// <param name="eventTypeAlias">is the event name</param>
		/// <returns>event type id</returns>
		/// <throws>IllegalStateException to indicate that the alias does not exist.</throws>
		String GetIdByAlias(String eventTypeAlias);

		/// <summary>
		/// Returns the event type given an event type id. The id is expected to exist.
		/// </summary>
		/// <param name="eventTypeID">is the tyoe id</param>
		/// <returns>event type</returns>
		/// <throws>IllegalStateException to indicate that the id does not exist.</throws>
		EventType GetTypeById(String eventTypeID);

		/// <summary>
		/// Returns the event type id given the event type, with the type expected to exist.
		/// </summary>
		/// <param name="eventType">to return the id for</param>
		/// <returns>event type id</returns>
		/// <throws>IllegalStateException to indicate that the type does not exist.</throws>
		String GetIdByType(EventType eventType);

		/// <summary>Returns the first event type alias for a given event type id.</summary>
		/// <param name="eventTypeID">is the type id</param>
		/// <returns>the very first alias registered for an event type</returns>
		String GetAliasById(String eventTypeID);

		/// <summary> Return the event type for a given event name, or null if none is registered for that name.</summary>
        /// <param name="eventTypeAlias">is the event type alias name to return type for
        /// </param>
        /// <returns> event type for named event, or null if unknown/unnamed type
        /// </returns>
        EventType GetEventTypeByAlias(String eventTypeAlias);

        /// <summary> Add an event type with the given alias and a given set of properties.
        ///
        /// If the alias already exists with the same event property information, returns the
        /// existing EventType instance.
        ///
        /// If the alias already exists with different event property information, throws an exception.
        ///
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.events.MapEventType" />.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type</param>
        /// <param name="propertyTypes">is the names and types of event properties</param>
        /// <returns> event type is the type added</returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match property type info </throws>
        EventType AddMapType(String eventTypeAlias, EDictionary<String, Type> propertyTypes);

		/// <summary>
		/// Add an event type with the given alias and the given underlying event type,
		/// as well as the additional given properties.
		/// </summary>
		/// <param name="eventTypeAlias">is the alias name for the event type</param>
		/// <param name="underlyingEventType">
		/// is the event type for the event type that this wrapper wraps
		/// </param>
		/// <param name="propertyTypes">
		/// is the names and types of any additional properties
		/// </param>
		/// <returns>eventType is the type added</returns>
		/// <throws>
		/// EventAdapterException if alias already exists and doesn't match this type's info
		/// </throws>
		EventType AddWrapperType(String eventTypeAlias, EventType underlyingEventType, EDictionary<String, Type> propertyTypes);

        /// <summary> Creates a new anonymous EventType instance for an event type that contains a map of name value pairs.
        /// The method accepts a Map that contains the property names as keys and Class objects as the values.
        /// The Class instances represent the property types.
        /// <p>
        /// New instances are created by this method on every invocation. Clients to this method need to
        /// cache the returned EventType instance to reuse EventType's for same-typed events.
        /// </p>
        /// </summary>
        /// <param name="propertyTypes">is a map of String to Class objects</param>
        /// <returns> EventType implementation for map field names and value types
        /// </returns>
        EventType CreateAnonymousMapType(EDictionary<String, Type> propertyTypes);

        /// <summary> Creates a new anonymous EventType instance for an event type that contains a map of name value pairs.
        /// The method accepts a Map that contains the property names as keys and EventType objects as the values.
        /// The EventType instances represent the property types whose underlying class is used to set the type,
        /// via method CreateAnonymousMapType.
        /// </summary>
        /// <param name="propertyTypes">is a map of String to EventType objects
        /// </param>
        /// <returns> EventType implementation for map field names and value types which are the underlying types to the event type passed in
        /// </returns>
        EventType CreateAnonymousMapTypeUnd(EDictionary<String, EventType> propertyTypes);

        /// <summary> Create an event wrapper bean from a set of event properties (name and value objectes) stored in a Map.</summary>
        /// <param name="properties">is key-value pairs for the event properties
        /// </param>
        /// <param name="eventType">is the type metadata for any maps of that type
        /// </param>
        /// <returns> EventBean instance
        /// </returns>
        EventBean CreateMapFromValues(EDictionary<string,object> properties, EventType eventType);

		/// <summary>Create a wrapper around an event and some additional properties</summary>
		/// <param name="_event">is the wrapped event</param>
		/// <param name="properties">are the additional properties</param>
		/// <param name="eventType">os the type metadata for any wrappers of this type</param>
		/// <returns>wrapper event bean</returns>
		EventBean CreateWrapper(EventBean _event, EDictionary<String, Object> properties, EventType eventType);

        /// <summary> Create an aggregate event wrapper bean from a set of named events stored in a Map.</summary>
        /// <param name="events">is key-value pairs where the key is the event name and the value is the event
        /// </param>
        /// <param name="eventType">is the type metadata for any maps of that type
        /// </param>
        /// <returns> EventBean instance
        /// </returns>
        EventBean CreateMapFromUnderlying(EDictionary<String, EventBean> events, EventType eventType);

        /// <summary> Add an event type with the given alias and Java fully-qualified class name.
        ///
        /// If the alias already exists with the same class name, returns the existing EventType instance.
        ///
        /// If the alias already exists with different class name, throws an exception.
        ///
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.events.BeanEventType" />.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type
        /// </param>
        /// <param name="fullyQualClassName">is the fully qualified class name
        /// </param>
        /// <returns> event type is the type added
        /// </returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match class names </throws>
        EventType AddBeanType(String eventTypeAlias, String fullyQualClassName);

        /// <summary> Add an event type with the given alias and Java class.
        ///
        /// If the alias already exists with the same Class, returns the existing EventType instance.
        ///
        /// If the alias already exists with different Class name, throws an exception.
        ///
        /// If the alias does not already exists, adds the alias and constructs a new <seealso cref="net.esper.events.BeanEventType" />.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias name for the event type
        /// </param>
        /// <param name="type">is the type
        /// </param>
        /// <returns> event type is the type added
        /// </returns>
        /// <throws>  EventAdapterException if alias already exists and doesn't match class names </throws>
        EventType AddBeanType(String eventTypeAlias, Type type);

        /// <summary> Wrap the native event returning an <seealso cref="EventBean" />.</summary>
        /// <param name="ev">event to be wrapped
        /// </param>
        /// <returns> event bean wrapping native underlying event
        /// </returns>
        EventBean AdapterForBean(Object ev);

        /// <summary>
        /// Wrap the Map-type event returning an <seealso cref="EventBean" /> using the event type alias name
        /// to identify the EventType that the event should carry.
        /// </summary>
        /// <param name="ev">to be wrapped</param>
        /// <param name="eventTypeAlias">alias for the event type of the event</param>
        /// <returns>
        /// event bean wrapping native underlying event
        /// </returns>
        /// <throws>  EventAdapterException if the alias has not been declared, or the event cannot be wrapped using that </throws>
        EventBean AdapterForMap(EDictionary<String,Object> ev, String eventTypeAlias);

        /// <summary> Create an event type based on the original type passed in adding one or more properties.</summary>
        /// <param name="originalType">event type to add property to
        /// </param>
        /// <param name="fieldNames">names of properties
        /// </param>
        /// <param name="fieldTypes">types of properties
        /// </param>
        /// <returns> new event type
        /// </returns>
        EventType CreateAddToEventType(EventType originalType, String[] fieldNames, Type[] fieldTypes);

        /// <summary> Returns an adapter for the XML DOM document that exposes it's data as event properties for use in statements.</summary>
        /// <param name="node">is the node to wrap
        /// </param>
        /// <returns> event wrapper for document
        /// </returns>
        EventBean AdapterForDOM(XmlNode node);

        /// <summary> Creates an unnamed composite event type with event properties that are name-value pairs
        /// with values being other event types. Pattern statement generate events of such type.
        /// </summary>
        /// <param name="taggedEventTypes">is a map of name keys and event type values
        /// </param>
        /// <returns> event type representing a composite event
        /// </returns>
        EventType CreateAnonymousCompositeType(EDictionary<String, EventType> taggedEventTypes);

        /// <summary> Creates a wrapper for a composite event type. The wrapper wraps an event that
        /// consists of name-value pairs in which the values are other already-wrapped events.
        /// </summary>
        /// <param name="eventType">is the composite event type
        /// </param>
        /// <param name="taggedEvents">is the name-event map
        /// </param>
        /// <returns> wrapper for composite event
        /// </returns>
        EventBean AdapterForCompositeEvent(EventType eventType, EDictionary<String, EventBean> taggedEvents);

		/// <summary>
		/// Create a new anonymous event type with the given underlying event type,
		/// as well as the additional given properties.
		/// </summary>
		/// <param name="underlyingEventType">
		/// is the event type for the event type that this wrapper wraps
		/// </param>
		/// <param name="propertyTypes">
		/// is the names and types of any additional properties
		/// </param>
		/// <returns>eventType is the type createdStatement</returns>
		/// <throws>
		/// EventAdapterException if alias already exists and doesn't match this type's info
		/// </throws>
	    EventType CreateAnonymousWrapperType(EventType underlyingEventType, EDictionary<String, Type> propertyTypes);

		/// <summary>Adds an XML DOM event type.</summary>
		/// <param name="eventTypeAlias">is the alias to add the type for</param>
		/// <param name="configurationEventTypeXMLDOM">is the XML DOM config info</param>
		/// <returns>event type</returns>
	    EventType AddXMLDOMType(String eventTypeAlias, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM);
    }
}
