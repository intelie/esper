using System;
using EmittedListener = net.esper.client.EmittedListener;
namespace net.esper.emit
{
	
	/// <summary> The emit service is a simple publish-subscribe mechanism for sending events out of the runtime to
	/// emitted event listeners that registered interest in the same or default channel that an event was
	/// emitted to.
	/// </summary>
	public interface EmitService
	{
		/// <summary> Number of events emitted.</summary>
		/// <returns> total of events emitted
		/// </returns>
		long NumEventsEmitted
		{
			get;
		}
		/// <summary> Emit an event to the specified channel. All listeners listening to the exact same channel and
		/// all listeners listening to the default channel are handed the event emitted.
		/// </summary>
		/// <param name="object">is the event to emit
		/// </param>
		/// <param name="channel">is the channel to emit to
		/// </param>
		void  emitEvent(Object _object, String channel);
		
		/// <summary> Add emitted event listener for the specified channel, or the default channel if the channel value is null.
		/// The listener will be invoked when an event is emitted on the subscribed channel. Listeners subscribed to the
		/// default channel are invoked for all emitted events regardless of what channel the event is emitted onto.
		/// </summary>
		/// <param name="listener">is the callback to receive when events are emitted
		/// </param>
		/// <param name="channel">is the channel to listen to, with null values allowed to indicate the default channel
		/// </param>
		void  AddListener(EmittedListener listener, String channel);
		
		/// <summary> Removes all listeners for emitted events.</summary>
		void  clearListeners();
	}
}