using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.pattern
{
	/// <summary>
    /// Callback interface for anything that requires to be informed of matching events which would be stored
	/// in the MatchedEventMap structure passed to the implementation.
	/// </summary>

    public interface PatternMatchCallback
    {
        /// <summary>Indicate matching events.</summary>
        /// <param name="matchEvent">contains a map of event tags and event objects</param>

        void matchFound(EDictionary<String, EventBean> matchEvent);
    }
    
    /// <summary>
    /// A delegate that mimics the behavior of the PatternMatchCallback.
    /// At some time in the future, this delegate should replace the
    /// PatternMatchCallback interface.
    /// </summary>
    
    public delegate void PatternMatcherDelegate( EDictionary<String, EventBean> matchEvent );
    
    /// <summary>
    /// An implementation of the PatternMatchCallback that proxies the
    /// interface through a delegate.  IMO, this is a lot of extra baggage
    /// that is cleanly handled thorugh the delegate interface, but will
    /// remain intact until we can review the interaction points and
    /// convert them to delegates.
    /// </summary>
    
    public sealed class PatternMatchCallbackImpl : PatternMatchCallback
    {
    	private PatternMatcherDelegate m_delegate ;

        /// <summary>
        /// Initializes a new instance of the <see cref="PatternMatchCallbackImpl"/> class.
        /// </summary>
        /// <param name="_delegate">The _delegate.</param>
    	public PatternMatchCallbackImpl( PatternMatcherDelegate _delegate )
    	{
    		m_delegate = _delegate;
    	}
    	
        /// <summary>Indicate matching events.</summary>
        /// <param name="matchEvent">contains a map of event tags and event objects</param>

        public void matchFound(EDictionary<String, EventBean> matchEvent)
        {
        	m_delegate( matchEvent ) ;
        }
    }
}
