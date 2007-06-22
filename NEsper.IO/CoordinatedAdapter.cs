using net.esper.client;
using net.esper.schedule;

namespace net.esper.adapter
{
	/// <summary> 
	/// An Adapter that can be coordinated by an AdapterCoordinator.
	/// </summary>

	public interface CoordinatedAdapter : InputAdapter
	{
		/// <summary>
		/// Get the next event in line to be sent into the runtime , or null if there is no available event.
		/// <returns>an instance of SendableEvent that wraps the next event to send, or null if none</returns>
		/// </summary>
		SendableEvent Read();
		
	    /// <summary>
	    /// Set the usingEngineThread value
	    /// </summary>
	    bool UsingEngineThread { set; }
		
		/// <summary>
		/// Disallow subsequent state changes and throw an IllegalStateTransitionException
		/// if they are attempted.
		/// </summary>
		void DisallowStateTransitions();
		
		/// <summary>
		/// Set the scheduleSlot for thisAdapter.
		/// </summary>
        ScheduleSlot ScheduleSlot { set; }
		
		/// <summary>
		/// Set the epService
		/// </summary>
        EPServiceProvider EPService { set; }
	}
}