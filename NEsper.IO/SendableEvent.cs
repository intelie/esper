using net.esper.client;
using net.esper.schedule;

namespace net.esper.adapter
{
	/// <summary>
	/// A wrapper that packages an event up so that it can be 
	/// sent into the caller-specified runtime. It also provides 
	/// the scheduling information for this event (send time and 
	/// schedule slot), so the user can send this event on schedule.
	/// </summary>
	public interface SendableEvent
	{
		/// <summary>
		/// Send the event into the runtime.
		/// </summary>
		/// <param name="runtime">the runtime to send the event into</param>
		void Send(EPRuntime runtime);
		
		/// <summary>
		/// Get the send time of this event, relative to all the other events sent or read by the same entity
		/// </summary>
		/// <returns>timestamp</returns>
        long SendTime { get; }
		
		/// <summary>
		/// Get the schedule slot for the entity that created this event
		/// </summary>
		/// <returns>schedule slot</returns>
        ScheduleSlot ScheduleSlot { get; }
	}
}