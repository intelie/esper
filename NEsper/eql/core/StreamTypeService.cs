using System;

using net.esper.events;

namespace net.esper.eql.core
{
	/// <summary>
    /// Service supplying stream number and property type information.
    /// </summary>
	
    public interface StreamTypeService
	{
		/// <summary> Returns an array of event stream names in the order declared.</summary>
		/// <returns> stream names
		/// </returns>
		String[] StreamNames
		{
			get;
		}
		/// <summary> Returns an array of event types for each event stream in the order declared.</summary>
		/// <returns> event types
		/// </returns>
		EventType[] EventTypes
		{
			get;			
		}
        /// <summary>
        /// Returns the offset of the stream and the type of the property for the given property name,
        /// by looking through the types offered and matching up.
        /// <para>
        /// This method considers only a property name and looks at all streams to resolve the property name.
        /// </para>
        /// </summary>
        /// <param name="propertyName">property name in event</param>
        /// <returns>
        /// descriptor with stream number, property type and property name
        /// </returns>
        /// <throws>  DuplicatePropertyException to indicate property was found twice </throws>
        /// <throws>  PropertyNotFoundException to indicate property could not be resolved </throws>
		PropertyResolutionDescriptor ResolveByPropertyName(String propertyName);

        /// <summary>
        /// Returns the offset of the stream and the type of the property for the given property name,
        /// by using the specified stream name to resolve the property.
        /// <para>
        /// This method considers and explicit stream name and property name, both parameters are required.
        /// </para>
        /// </summary>
        /// <param name="streamName">name of stream, required</param>
        /// <param name="propertyName">property name in event, , required</param>
        /// <returns>
        /// descriptor with stream number, property type and property name
        /// </returns>
        /// <throws>  PropertyNotFoundException to indicate property could not be resolved </throws>
        /// <throws>  StreamNotFoundException to indicate stream name could not be resolved </throws>
		PropertyResolutionDescriptor ResolveByStreamAndPropName(String streamName, String propertyName);

        /// <summary>
        /// Returns the offset of the stream and the type of the property for the given property name,
        /// by looking through the types offered and matching up.
        /// <para>
        /// This method considers a single property name that may or may not be prefixed by a stream name.
        /// The resolution first attempts to find the property name itself, then attempts
        /// to consider a stream name that may be part of the property name.
        /// </para>
        /// </summary>
        /// <param name="streamAndPropertyName">stream name and property name (e.g. s0.p0) or just a property name (p0)</param>
        /// <returns>
        /// descriptor with stream number, property type and property name
        /// </returns>
        /// <throws>  DuplicatePropertyException to indicate property was found twice </throws>
        /// <throws>  PropertyNotFoundException to indicate property could not be resolved </throws>
		PropertyResolutionDescriptor ResolveByStreamAndPropName(String streamAndPropertyName);
	}
}