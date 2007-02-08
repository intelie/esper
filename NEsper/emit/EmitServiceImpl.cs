using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.client;
using net.esper.compat;

namespace net.esper.emit
{
	/// <summary>
    /// Implementation of the event emit service.
    /// </summary>

    public class EmitServiceImpl : EmitService
	{
		public long NumEventsEmitted
		{
            get { return Interlocked.Read( ref numEventsEmitted ); }
		}

		private readonly EDictionary<String, IList<EmittedListener>> channelEmitListeners ;
		private readonly ReaderWriterLock channelEmitListenersRWLock ;
		private long numEventsEmitted ;

        public const string DEFAULT_CHANNEL = "" ;
		
		/// <summary>
        /// Constructor.
        /// </summary>
		
        protected internal EmitServiceImpl()
		{
        	this.channelEmitListeners = new EHashDictionary<String, IList<EmittedListener>>();
        	this.channelEmitListenersRWLock = new ReaderWriterLock() ;
		}
		
		public void AddListener(EmittedListener listener, String channel)
		{
            if (channel == null)
            {
                channel = DEFAULT_CHANNEL;
            }

			channelEmitListenersRWLock.AcquireWriterLock( LockConstants.WriterTimeout );
			
			// Check if the listener already exists, to make sure the same listener
			// doesn't subscribe twice to the same or the default channel
			
            foreach (KeyValuePair<String, IList<EmittedListener>> entry in channelEmitListeners)
			{
				if (entry.Value.Contains(listener))
				{
					// If already subscribed to the default channel, do not add
					// If already subscribed to the same channel, do not add
                    if ((entry.Key == DEFAULT_CHANNEL) || ((channel != DEFAULT_CHANNEL) && (channel == entry.Key)))
					{
						channelEmitListenersRWLock.ReleaseWriterLock();
						return ;
					}
					
					// If subscribing to default channel, remove from existing channel
                    if (channel == DEFAULT_CHANNEL)
					{
						entry.Value.Remove(listener);
					}
				}
			}
			
			// Add listener, its a new listener or new channel for an existing listener
			IList<EmittedListener> listeners = channelEmitListeners.Fetch( channel, null ) ;
			if (listeners == null)
			{
				listeners = new ELinkedList<EmittedListener>();
				channelEmitListeners[channel] = listeners;
			}
			
			listeners.Add(listener);
			
			channelEmitListenersRWLock.ReleaseWriterLock();
		}
		
		public void  clearListeners()
		{
			channelEmitListenersRWLock.AcquireWriterLock( LockConstants.WriterTimeout );
			channelEmitListeners.Clear();
			channelEmitListenersRWLock.ReleaseWriterLock();
		}
		
		public void emitEvent(Object _object, String channel)
		{
            if (channel == null)
            {
                channel = DEFAULT_CHANNEL;
            }

			channelEmitListenersRWLock.AcquireReaderLock( LockConstants.ReaderTimeout );
			
			// Emit to specific channel first
            if (channel != DEFAULT_CHANNEL)
			{
                IList<EmittedListener> listeners = channelEmitListeners.Fetch(channel);
				if (listeners != null)
				{
					foreach(EmittedListener listener in listeners)
					{
						listener.emitted(_object);
					}
				}
			}
			
			// Emit to default channel if there are any listeners
            IList<EmittedListener> _listeners = channelEmitListeners.Fetch(DEFAULT_CHANNEL);
			if (_listeners != null)
			{
				foreach(EmittedListener listener in _listeners)
				{
					listener.emitted(_object);
				}
			}
			
			channelEmitListenersRWLock.ReleaseReaderLock();

            Interlocked.Increment(ref numEventsEmitted);
		}
	}
}
