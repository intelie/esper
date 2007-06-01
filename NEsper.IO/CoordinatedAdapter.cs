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
		///@throws EPException in case of errors creating the event
		/// </summary>
		SendableEvent Read();
		
		/// <summary>
		/// Set the usingEngineThread value
		/// <param name="usingEngineThread">the value to set</param>
		/// </summary>
		void setUsingEngineThread(bool usingEngineThread);
		
		/// <summary>
		/// Disallow subsequent state changes and throw an IllegalStateTransitionException
		/// if they are attempted.
		/// </summary>
		void DisallowStateTransitions();
		
		/// <summary>
		/// Set the scheduleSlot for this Adapter.
		/// <param name="scheduleSlot">the scheduleSlot to set</param>
		/// </summary>
		void SetScheduleSlot(ScheduleSlot scheduleSlot);
		
		/// <summary>
		/// Set the epService
		/// <param name="epService">the value to set</param>
		/// </summary>
		void SetEPService(EPServiceProvider epService);
	}
}