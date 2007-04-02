using System;

namespace net.esper.schedule
{
	/// <summary>
    /// Interface for scheduled callbacks.
    /// </summary>

    public interface ScheduleCallback
    {
        /// <summary>
        /// Called when a scheduled callback occurs.
        /// </summary>

        void ScheduledTrigger();
    }

    /// <summary>
    /// Interface method wrapped as a delegate
    /// </summary>
    
	public delegate void ScheduleCallbackDelegate() ;
	
	/// <summary>
    /// An implementation of the ScheduleCallbackDelegate that proxies the
    /// interface through a delegate.  IMO, this is a lot of extra baggage
    /// that is cleanly handled thorugh the delegate interface, but will
    /// remain intact until we can review the interaction points and
    /// convert them to delegates.
    /// </summary>
    
    public sealed class ScheduleCallbackImpl : ScheduleCallback
    {
    	private ScheduleCallbackDelegate m_delegate ;

        /// <summary>
        /// Initializes a new instance of the <see cref="ScheduleCallbackImpl"/> class.
        /// </summary>
        /// <param name="_delegate">The _delegate.</param>
    	public ScheduleCallbackImpl( ScheduleCallbackDelegate _delegate )
    	{
    		m_delegate = _delegate;
    	}

        /// <summary>
        /// Called when a scheduled callback occurs.
        /// </summary>
        public void ScheduledTrigger()
        {
        	m_delegate() ;
        }
    }
}
