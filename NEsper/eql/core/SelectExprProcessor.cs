using System;
using EventBean = net.esper.events.EventBean;
using EventType = net.esper.events.EventType;
namespace net.esper.eql.core
{
	
	/// <summary> Interface for processors of select-clause items, implementors are computing results based on matching events.</summary>
	public interface SelectExprProcessor
	{
		/// <summary> Returns the event type that represents the select-clause items.</summary>
		/// <returns> event type representing select-clause items
		/// </returns>
		EventType ResultEventType
		{
			get;
			
		}
		
		/// <summary> Computes the select-clause results and returns an event of the result event type that contains, in it's
		/// properties, the selected items.
		/// </summary>
		/// <param name="eventsPerStream">
		/// </param>
		/// <returns> event with properties containing selected items
		/// </returns>
		EventBean Process(EventBean[] eventsPerStream);
	}
}