using System;
using EventType = net.esper.events.EventType;

namespace net.esper.eql.core
{
	/// <summary>
    /// Implementation that provides stream number and property type information.
    /// </summary>
	public class StreamTypeServiceImpl : StreamTypeService
	{
        /// <summary>
        /// Returns an array of event types for each event stream in the order declared.
        /// </summary>
        /// <value></value>
        /// <returns> event types
        /// </returns>
		virtual public EventType[] EventTypes
		{
			get
			{
				return eventTypes;
			}
			
		}
        /// <summary>
        /// Returns an array of event stream names in the order declared.
        /// </summary>
        /// <value></value>
        /// <returns> stream names
        /// </returns>
		virtual public String[] StreamNames
		{
			get
			{
				return streamNames;
			}
			
		}
		private EventType[] eventTypes;
		private String[] streamNames;
		
		/// <summary> Ctor.</summary>
		/// <param name="eventTypes">array of event types, one for each stream
		/// </param>
		/// <param name="streamNames">array of stream names, one for each stream
		/// </param>
		public StreamTypeServiceImpl(EventType[] eventTypes, String[] streamNames)
		{
			this.eventTypes = eventTypes;
			this.streamNames = streamNames;
			
			if (eventTypes.Length != streamNames.Length)
			{
				throw new ArgumentException("Number of entries for event types and stream names differs");
			}
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
		public virtual PropertyResolutionDescriptor resolveByPropertyName(String propertyName)
		{
			if (propertyName == null)
			{
				throw new ArgumentException("Null property name");
			}
			return findByPropertyName(propertyName);
		}

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
		public virtual PropertyResolutionDescriptor resolveByStreamAndPropName(String streamName, String propertyName)
		{
			if (streamName == null)
			{
				throw new ArgumentException("Null property name");
			}
			if (propertyName == null)
			{
				throw new ArgumentException("Null property name");
			}
			return findByStreamName(propertyName, streamName);
		}

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
		public virtual PropertyResolutionDescriptor resolveByStreamAndPropName(String streamAndPropertyName)
		{
			if (streamAndPropertyName == null)
			{
				throw new ArgumentException("Null stream and property name");
			}
			
			PropertyResolutionDescriptor desc = null;
			try
			{
				// first try to resolve as a property name
				desc = findByPropertyName(streamAndPropertyName);
			}
			catch (PropertyNotFoundException ex)
			{
				// Attempt to resolve by extracting a stream name
				int index = streamAndPropertyName.IndexOf('.');
				if (index == - 1)
				{
					throw ex;
				}
				String streamName = streamAndPropertyName.Substring(0, (index) - (0));
				String propertyName = streamAndPropertyName.Substring(index + 1, (streamAndPropertyName.Length) - (index + 1));
				try
				{
					// try to resolve a stream and property name
					desc = findByStreamName(propertyName, streamName);
				}
				catch (StreamNotFoundException)
				{
					throw ex; // throws PropertyNotFoundException
				}
				return desc;
			}
			
			return desc;
		}
		
		private PropertyResolutionDescriptor findByPropertyName(String propertyName)
		{
			int index = 0;
			int foundIndex = 0;
			int foundCount = 0;
			EventType streamType = null;
			
			for (int i = 0; i < eventTypes.Length; i++)
			{
				if (eventTypes[i].IsProperty(propertyName))
				{
					streamType = eventTypes[i];
					foundCount++;
					foundIndex = index;
				}
				index++;
			}
			
			if (foundCount > 1)
			{
				throw new DuplicatePropertyException("Property named '" + propertyName + "' is ambigous as is valid for more then one stream");
			}
			
			if (streamType == null)
			{
				throw new PropertyNotFoundException("Property named '" + propertyName + "' is not valid in any stream");
			}
			
			return new PropertyResolutionDescriptor(streamNames[foundIndex], eventTypes[foundIndex], propertyName, foundIndex, streamType.GetPropertyType(propertyName));
		}
		
		private PropertyResolutionDescriptor findByStreamName(String propertyName, String streamName)
		{
			int index = 0;
			EventType streamType = null;
			
			for (int i = 0; i < eventTypes.Length; i++)
			{
				if ((streamNames[i] != null) && (streamNames[i].Equals(streamName)))
				{
					streamType = eventTypes[i];
					break;
				}
				index++;
			}
			
			// Stream name not found
			if (streamType == null)
			{
				throw new StreamNotFoundException("Stream named " + streamName + " is not defined");
			}
			
			Type propertyType = streamType.GetPropertyType(propertyName);
			if (propertyType == null)
			{
				throw new PropertyNotFoundException("Property named '" + propertyName + "' is not valid in stream " + streamName);
			}
			
			return new PropertyResolutionDescriptor(streamName, streamType, propertyName, index, propertyType);
		}
	}
}
