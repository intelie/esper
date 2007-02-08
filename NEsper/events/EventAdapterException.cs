using System;
using EPException = net.esper.client.EPException;
namespace net.esper.events
{
	
	/// <summary>
	/// This exception is thrown to indicate a problem resolving an event type by name.
	/// </summary>
	
	[Serializable]
	public class EventAdapterException:EPException
	{
		/// <summary> Ctor.</summary>
		/// <param name="message">- error message
		/// </param>
		public EventAdapterException(String message):base(message)
		{
		}
		
		/// <summary> Ctor.</summary>
		/// <param name="message">- error message
		/// </param>
		/// <param name="nested">- nested exception
		/// </param>
		public EventAdapterException(String message, System.Exception nested):base(message, nested)
		{
		}
	}
}
