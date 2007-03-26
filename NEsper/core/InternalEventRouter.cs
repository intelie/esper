using System;
using EventBean = net.esper.events.EventBean;

namespace net.esper.core
{
	/// <summary>
    /// Interface for a service that routes events within the engine for further processing.
    /// </summary>
	
    public interface InternalEventRouter
	{
        /// <summary>
        /// Route the event such that the event is processed as required.
        /// </summary>
        /// <param name="_event">The _event.</param>
		void Route(EventBean _event);
	}
}