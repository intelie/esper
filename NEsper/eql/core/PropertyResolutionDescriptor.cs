using System;
using EventType = net.esper.events.EventType;
namespace net.esper.eql.core
{
	
	/// <summary> Encapsulates the result of resolving a property and optional stream name against a supplied list of streams
	/// {@link net.esper.eql.core.StreamTypeService}.
	/// </summary>
	public class PropertyResolutionDescriptor
	{
		/// <summary> Returns stream name.</summary>
		/// <returns> stream name
		/// </returns>
		virtual public String StreamName
		{
			get
			{
				return streamName;
			}
			
		}
		/// <summary> Returns event type of the stream that the property was found in.</summary>
		/// <returns> stream's event type
		/// </returns>
		virtual public EventType StreamEventType
		{
			get
			{
				return streamEventType;
			}
			
		}
		/// <summary> Returns resolved property name of the property as it exists in a stream.</summary>
		/// <returns> property name as resolved in a stream
		/// </returns>
		virtual public String PropertyName
		{
			get
			{
				return propertyName;
			}
			
		}
		/// <summary> Returns the number of the stream the property was found in.</summary>
		/// <returns> stream offset number Starting at zero to N-1 where N is the number of streams
		/// </returns>
		virtual public int StreamNum
		{
			get
			{
				return streamNum;
			}
			
		}
		/// <summary> Returns the property type of the resolved property.</summary>
		/// <returns> class of property
		/// </returns>
		virtual public Type PropertyType
		{
			get
			{
				return propertyType;
			}
			
		}
		private String streamName;
		private EventType streamEventType;
		private String propertyName;
		private int streamNum;
		private Type propertyType;
		
		/// <summary> Ctor.</summary>
		/// <param name="streamName">is the stream name
		/// </param>
		/// <param name="streamEventType">is the event type of the stream where the property was found
		/// </param>
		/// <param name="propertyName">is the regular name of property
		/// </param>
		/// <param name="streamNum">is the number offset of the stream
		/// </param>
		/// <param name="propertyType">is the type of the property
		/// </param>
		public PropertyResolutionDescriptor(String streamName, EventType streamEventType, String propertyName, int streamNum, Type propertyType)
		{
			this.streamName = streamName;
			this.streamEventType = streamEventType;
			this.propertyName = propertyName;
			this.streamNum = streamNum;
			this.propertyType = propertyType;
		}
	}
}