using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.dispatch;
using net.esper.events;

namespace net.esper.core
{
	/// <summary>
	/// Dispatchable for dispatching events to update listeners.
	/// </summary>

	public class PatternListenerDispatch : Dispatchable
	{
		private readonly ISet<UpdateListener> listeners;
		private EventBean singleEvent;
		private List<EventBean> eventList;

		/// <summary> Constructor.</summary>
		/// <param name="listeners">is the listeners to dispatch to.
		/// </param>

		public PatternListenerDispatch( ISet<UpdateListener> listeners )
		{
			this.listeners = listeners;
		}

        /// <summary>
        /// Add an event to be dispatched.
        /// </summary>
        /// <param name="_event">event to add</param>

		public virtual void Add( EventBean _event )
		{
			if ( singleEvent == null )
			{
				singleEvent = _event;
			}
			else
			{
				if ( eventList == null )
				{
					eventList = new List<EventBean>( 5 );
					eventList.Add( singleEvent );
				}

				eventList.Add( _event );
			}
		}

        /// <summary>
        /// Execute any listeners.
        /// </summary>
		public virtual void Execute()
		{
			EventBean[] eventArray = null;

			if ( eventList != null )
			{
                eventArray = eventList.ToArray();
				eventList = null;
				singleEvent = null;
			}
			else
			{
				eventArray = new EventBean[] { singleEvent };
				singleEvent = null;
			}

			foreach ( UpdateListener listener in listeners )
			{
				listener( eventArray, null );
			}
		}

		/// <summary> Returns true if at least one event has been added.</summary>
		/// <returns> true if it has data, false if not
		/// </returns>

		public virtual bool HasData
		{
            get
            {
                if (singleEvent != null)
                {
                    return true;
                }
                return false;
            }
		}
	}
}
