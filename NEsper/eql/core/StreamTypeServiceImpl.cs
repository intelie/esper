using System;

using net.esper.compat;
using net.esper.events;

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
			get { return eventTypes; }
		}
		
        /// <summary>
        /// Returns an array of event stream names in the order declared.
        /// </summary>
        /// <value></value>
        /// <returns> stream names
        /// </returns>
		virtual public String[] StreamNames
		{
			get { return streamNames; }
		}
		
	    private readonly EventType[] eventTypes;
	    private readonly String[] streamNames;
	    private bool isStreamZeroUnambigous;
	    private bool requireStreamNames;
		
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

	    /**
	     * Ctor.
	     * @param namesAndTypes is the ordered list of stream names and event types available (stream zero to N)
	     * @param isStreamZeroUnambigous indicates whether when a property is found in stream zero and another stream an exception should be
	     * thrown or the stream zero should be assumed
	     * @param requireStreamNames is true to indicate that stream names are required for any non-zero streams (for subqueries)
	     */
	    public StreamTypeServiceImpl(LinkedDictionary<string, EventType> namesAndTypes, bool isStreamZeroUnambigous, bool requireStreamNames)
	    {
	        this.isStreamZeroUnambigous = isStreamZeroUnambigous;
	        this.requireStreamNames = requireStreamNames;
	        eventTypes = new EventType[namesAndTypes.Count] ;
	        streamNames = new String[namesAndTypes.Count] ;
	        int count = 0;
	        foreach (String streamName in namesAndTypes.Keys)
	        {
	            streamNames[count] = streamName;
	            eventTypes[count] = namesAndTypes.Fetch(streamName);
	            count++;
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
        public virtual PropertyResolutionDescriptor ResolveByPropertyName(String propertyName)
		{
			if (propertyName == null)
			{
				throw new ArgumentException("Null property name");
			}
	        PropertyResolutionDescriptor desc = FindByPropertyName(propertyName);
	        if ((requireStreamNames) && (desc.StreamNum != 0))
	        {
	            if (desc.StreamName != null)
	            {
	                throw new PropertyNotFoundException("Property named '" + propertyName + "' must be prefixed by stream name '" + desc.getStreamName() + "'");
	            }
	            else
	            {
	                throw new PropertyNotFoundException("Property named '" + propertyName + "' must be prefixed by a stream name, use the as-clause to name the stream");
	            }
	        }
	        return desc;
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
		public virtual PropertyResolutionDescriptor ResolveByStreamAndPropName(String streamName, String propertyName)
		{
			if (streamName == null)
			{
				throw new ArgumentException("Null property name");
			}
			if (propertyName == null)
			{
				throw new ArgumentException("Null property name");
			}
			return FindByStreamName(propertyName, streamName);
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
		public virtual PropertyResolutionDescriptor ResolveByStreamAndPropName(String streamAndPropertyName)
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
					desc = FindByStreamName(propertyName, streamName);
				}
				catch (StreamNotFoundException)
				{
					throw ex; // throws PropertyNotFoundException
				}
				return desc;
			}
			
			return desc;
		}
		
		private PropertyResolutionDescriptor FindByPropertyName(String propertyName)
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
					
					// If the property could be resolved from stream 0 then we don't need to look further
	                if ((i == 0) && isStreamZeroUnambigous)
	                {
	                    return new PropertyResolutionDescriptor(streamNames[0], eventTypes[0], propertyName, 0, streamType.GetPropertyType(propertyName));
	                }
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
		
		private PropertyResolutionDescriptor FindByStreamName(String propertyName, String streamName)
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
