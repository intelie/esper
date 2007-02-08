using System;

using net.esper.events;

namespace net.esper.filter
{
	/// <summary>
    /// Interface for a callback method to be called when an event matches a filter specification.
    /// </summary>

    public interface FilterCallback
    {
        /// <summary> Indicate that an event was evaluated by the {@link FilterService}
        /// which matches the filter specification {@link FilterSpec} associated with this callback.
        /// </summary>
        /// <param name="event">the event received that matches the filter specification
        /// </param>

        void matchFound(EventBean _event);
    }
    
    /// <summary>
    /// A delegate wrapper for the filter callback
    /// </summary>
    /// <param name="_event">the event received that matches the filter specification</param>
    /// <returns></returns>

    public delegate void FilterCallbackDelegate( EventBean _event );

    /// <summary>
    /// An interface that wraps the the filter callback with a delegate
    /// </summary>

    public sealed class FilterCallbackImpl : FilterCallback
    {
        private FilterCallbackDelegate _delegate;

        public FilterCallbackImpl(FilterCallbackDelegate _delegate)
        {
            this._delegate = _delegate;
        }

        public void matchFound(EventBean _event)
        {
        	_delegate( _event ) ;
        }
    }
}
