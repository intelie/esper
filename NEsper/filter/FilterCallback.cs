using System;

using net.esper.events;

namespace net.esper.filter
{
	/// <summary>
    /// Interface for a callback method to be called when an event matches a filter specification.
    /// </summary>

    public interface FilterCallback
    {
        /// <summary> Indicate that an event was evaluated by the <seealso cref="FilterService"/>
        /// which matches the filter specification <seealso cref="FilterSpec"/> associated with this callback.
        /// </summary>
        /// <param name="_event">the event received that matches the filter specification
        /// </param>

        void MatchFound(EventBean _event);
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

        /// <summary>
        /// Initializes a new instance of the <see cref="FilterCallbackImpl"/> class.
        /// </summary>
        /// <param name="_delegate">The _delegate.</param>
        public FilterCallbackImpl(FilterCallbackDelegate _delegate)
        {
            this._delegate = _delegate;
        }

        /// <summary>
        /// Indicate that an event was evaluated by the <seealso cref="FilterService"/>
        /// which matches the filter specification <seealso cref="FilterSpec"/> associated with this callback.
        /// </summary>
        /// <param name="_event">the event received that matches the filter specification</param>
        public void MatchFound(EventBean _event)
        {
        	_delegate( _event ) ;
        }
    }
}
