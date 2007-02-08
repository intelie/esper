using System;

namespace net.esper.schedule
{
	/// <summary>
    /// Interface for scheduled callbacks.
    /// </summary>

    public interface ScheduleCallback
    {
        void scheduledTrigger();
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
    	
    	public ScheduleCallbackImpl( ScheduleCallbackDelegate _delegate )
    	{
    		m_delegate = _delegate;
    	}
    	
        public void scheduledTrigger()
        {
        	m_delegate() ;
        }
    }
}
