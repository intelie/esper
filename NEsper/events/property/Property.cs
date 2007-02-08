using System;
using net.esper.events;

namespace net.esper.events.property
{
	/// <summary>
    /// Interface for a property of an event of type BeanEventType. Properties are designed to
	/// handle the different types of properties for such events: indexed, mapped, simple, nested,
    /// or a combination of those.
	/// </summary>
	
    public interface Property
	{
        /// <summary>
        /// Returns the property type.
        /// </summary>
        /// <param name="eventType">is the event type representing the JavaBean</param>
        /// <returns>property type class</returns>
	
        Type GetPropertyType(BeanEventType eventType);

        /// <summary>
        /// Returns value getter for the property of an event of the given event type.
        /// </summary>
        /// <param name="eventType">is the type of event to make a getter for</param>
        /// <returns>fast property value getter for property</returns>
		
        EventPropertyGetter GetGetter(BeanEventType eventType);
	}
}