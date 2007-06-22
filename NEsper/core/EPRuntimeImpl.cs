using System;
using System.Collections.Generic;
using System.Xml;
using System.Threading;

using net.esper.client;
using net.esper.client.time;
using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.filter;
using net.esper.schedule;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.core
{
    /// <summary>
    /// Implements runtime interface. Also accepts timer callbacks for synchronizing time
    /// events with regular events sent in.
    /// </summary>

    public class EPRuntimeImpl 
        : EPRuntime
        , InternalEventRouter
    {
        /// <summary>
        /// Number of events received over the lifetime of the event stream processing runtime.
        /// </summary>
        /// <value></value>
        /// <returns> number of events received
        /// </returns>
        virtual public long NumEventsReceived
        {
            get { return services.FilterService.NumEventsEvaluated; }
        }

        /// <summary>
        /// Number of events emitted over the lifetime of the event stream processing runtime.
        /// </summary>
        /// <value></value>
        /// <returns> number of events emitted
        /// </returns>
        virtual public long NumEventsEmitted
        {
            get { return services.EmitService.NumEventsEmitted; }
        }

		[ThreadStatic]
		private ArrayBackedCollection<FilterHandle> matchesArrayThreadLocal;
		private ArrayBackedCollection<FilterHandle> MatchesArray
		{
			get
			{
				if ( matchesArrayThreadLocal == null )
				{
					matchesArrayThreadLocal = new ArrayBackedCollection<FilterHandle>(100);
				}

			    return matchesArrayThreadLocal;
			}
		}

		[ThreadStatic]
		private EDictionary<EPStatementHandle, Object> matchesPerStmtThreadLocal;
		private EDictionary<EPStatementHandle, Object> MatchesPerStmt
		{
			get
			{
				if ( matchesPerStmtThreadLocal == null )
				{
					matchesPerStmtThreadLocal = new HashDictionary<EPStatementHandle, Object>(10000);
				}

			    return matchesPerStmtThreadLocal;
			}
        }

		[ThreadStatic]
		private ArrayBackedCollection<ScheduleHandle> scheduleArrayThreadLocal;
		private ArrayBackedCollection<ScheduleHandle> ScheduleArray
		{
			get
			{
				if ( scheduleArrayThreadLocal == null )
				{
					scheduleArrayThreadLocal = new ArrayBackedCollection<ScheduleHandle>(100);
				}

			    return scheduleArrayThreadLocal;
			}
        }

		[ThreadStatic]
		private EDictionary<EPStatementHandle, Object> schedulePerStmtThreadLocal;
		private EDictionary<EPStatementHandle, Object> SchedulePerStmt
		{
			get
			{
				if ( schedulePerStmtThreadLocal == null )
				{
					schedulePerStmtThreadLocal = new HashDictionary<EPStatementHandle, Object>(10000);
				}

			    return schedulePerStmtThreadLocal;
			}
        }
		
        private EPServicesContext services;
        private ThreadWorkQueue threadWorkQueue;

        /// <summary> Constructor.</summary>
        /// <param name="services">references to services
        /// </param>
        public EPRuntimeImpl(EPServicesContext services)
        {
            this.services = services;
            this.threadWorkQueue = new ThreadWorkQueue();
        }

        /// <summary>
        /// Invoked by the internal clocking service at regular intervals.
        /// </summary>
        public virtual void TimerCallback()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".TimerCallback Evaluating scheduled callbacks");
            }

            long msec = DateTimeHelper.TimeInMillis( DateTime.Now ) ;
            CurrentTimeEvent currentTimeEvent = new CurrentTimeEvent(msec);
            SendEvent(currentTimeEvent);
        }

        /// <summary>
        /// Sends the event.
        /// </summary>
        /// <param name="_event">The _event.</param>
        public virtual void SendEvent(Object _event)
        {
            if (_event == null)
            {
                log.Fatal(".SendEvent Null object supplied");
                return;
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".SendEvent Processing event " + _event);
            }

            // Process event
            ProcessEvent(_event);
        }

        /// <summary>
        /// Sends the event.
        /// </summary>
        /// <param name="document">The document.</param>
        public virtual void SendEvent(XmlNode document)
        {
            if (document == null)
            {
                log.Fatal(".SendEvent Null object supplied");
                return;
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".SendEvent Processing DOM node event " + document);
            }

            // Get it wrapped up, process event
            EventBean eventBean = services.EventAdapterService.AdapterForDOM(document);
            ProcessEvent(eventBean);
        }

        /// <summary>
        /// Send a map containing event property values to the event stream processing runtime.
        /// Use the route method for sending events into the runtime from within UpdateListener code.
        /// </summary>
        /// <param name="map">map that contains event property values. Keys are expected to be of type String while values
        /// can be of any type. Keys and values should match those declared via Configuration for the given eventTypeAlias.</param>
        /// <param name="eventTypeAlias">the alias for the (property name, property type) information for this map</param>
        /// <throws>  EPException - when the processing of the event leads to an error </throws>
        public virtual void SendEvent(IDataDictionary map, String eventTypeAlias)
        {
            if (map == null)
            {
                throw new ArgumentException("Invalid null event object");
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(string.Format(".SendEvent Processing event {0}", map));
            }

            // Process event
            EventBean eventBean = services.EventAdapterService.AdapterForMap(map, eventTypeAlias);
            ProcessEvent(eventBean);
        }

        /// <summary>
        /// Creates a delegate that can be used to send mapped events to the runtime.  This method
        /// eliminates the costs associated with the lookup of an event type or any other form of
        /// initialization that would normally be incurred.
        /// </summary>
        /// <param name="eventTypeAlias"></param>
        /// <returns></returns>

        public virtual EPSender GetSender(String eventTypeAlias)
        {
            EventType eventType = services.EventAdapterService.GetEventTypeByAlias(eventTypeAlias);
            EPSender eventSender = new EPSender(
                delegate(IDataDictionary mappedEvent)
                    {
                        EventBean eventbean = services.EventAdapterService.CreateMapFromValues(mappedEvent, eventType);
                        ProcessEvent(eventbean);
                    });

            return eventSender;
        }

        /// <summary>
        /// Route the event object back to the event stream processing runtime for internal dispatching.
        /// The route event is processed just like it was sent to the runtime, that is any
        /// active expressions seeking that event receive it. The routed event has priority over other
        /// events sent to the runtime. In a single-threaded application the routed event is
        /// processed before the next event is sent to the runtime through the
        /// EPRuntime.sendEvent method.
        /// </summary>
        /// <param name="_event"></param>
        public virtual void Route(Object _event)
        {
            ThreadWorkQueue.Add(_event);
        }

        /// <summary>
        /// Route the event such that the event is processed as required.
        /// </summary>
        /// <param name="_event"></param>
        public virtual void Route(EventBean _event)
        {
            ThreadWorkQueue.Add(_event);
        }

        /// <summary>
        /// Emit an event object to any registered EmittedListener instances listening to the default channel.
        /// </summary>
        /// <param name="_object"></param>
        public virtual void Emit(Object _object)
        {
            services.EmitService.EmitEvent(_object, null);
        }

        /// <summary>
        /// Emit an event object to any registered EmittedListener instances on the specified channel.
        /// Event listeners listening to all channels as well as those listening to the specific channel
        /// are called. Supplying a null value in the channel has the same result as the Emit(Object object) method.
        /// </summary>
        /// <param name="_object"></param>
        /// <param name="channel">channel to emit the object to, or null if emitting to the default channel</param>
        public virtual void Emit(Object _object, String channel)
        {
            services.EmitService.EmitEvent(_object, channel);
        }

        /// <summary>
        /// Register an object that listens for events emitted from the event stream processing runtime on the
        /// specified channel. A null value can be supplied for the channel in which case the
        /// emit listener will be invoked for events emitted an any channel.
        /// </summary>
        /// <param name="listener">called when an event is emitted by the runtime.</param>
        /// <param name="channel">is the channel to add the listener to, a null value can be used to listen to events emitted
        /// on all channels</param>
        public virtual void AddEmittedListener(EmittedListener listener, String channel)
        {
            services.EmitService.AddListener(listener, channel);
        }

        /// <summary>
        /// Deregister all emitted event listeners.
        /// </summary>
        public virtual void ClearEmittedListeners()
        {
            services.EmitService.ClearListeners();
        }

        /// <summary>
        /// Processes the event.
        /// </summary>
        /// <param name="_event">The _event.</param>
        private void ProcessEvent(Object _event)
        {
            if (_event is TimerEvent)
            {
                ProcessTimeEvent((TimerEvent)_event);
                return;
            }

            EventBean eventBean;

            if (_event is EventBean)
            {
                eventBean = (EventBean)_event;
            }
            else
            {
                eventBean = services.EventAdapterService.AdapterForBean(_event);
            }

            ManagedReadWriteLock lockObj = services.EventProcessingRWLock;
            
            // Acquire main processing lock which locks out statement management
            lockObj.AcquireReadLock();
            try
	        {
	            ProcessMatches(eventBean);
            }
            catch (SystemException ex)
            {
                throw new EPException(ex);
            }
            finally
            {
                lockObj.ReleaseReadLock();
            }
			
		    // Dispatch results to listeners
	        Dispatch();

	        // Work off the event queue if any events accumulated in there via a route()
	        ProcessThreadWorkQueue();
        }

        /// <summary>
        /// Processes the time event.
        /// </summary>
        /// <param name="_event">The _event.</param>
        private void ProcessTimeEvent(TimerEvent _event)
        {
            if (_event is TimerControlEvent)
            {
                TimerControlEvent timerControlEvent = (TimerControlEvent)_event;
                if (timerControlEvent.ClockType == TimerControlEvent.ClockTypeEnum.CLOCK_INTERNAL)
                {
                    // Start internal clock which supplies CurrentTimeEvent events every 100ms
                    // This may be done without delay thus the write lock indeed must be reentrant.
                    services.TimerService.StartInternalClock();
                }
                else
                {
                    // Stop internal clock, for unit testing and for external clocking
                    services.TimerService.StopInternalClock(true);
                }

                return;
            }

            // Evaluation of all time events is protected from regular event stream processing
			if (log.IsDebugEnabled)
			{
				log.Debug(".ProcessTimeEvent Setting time and evaluating schedules");
			}

			CurrentTimeEvent current = (CurrentTimeEvent)_event;
			long currentTime = current.TimeInMillis;
			services.SchedulingService.Time = currentTime;

			ProcessSchedule();

			// Let listeners know of results
			Dispatch();

			// Work off the event queue if any events accumulated in there via a Route()
			ProcessThreadWorkQueue();
        }

	    private void ProcessSchedule()
	    {
	        ArrayBackedCollection<ScheduleHandle> handles = ScheduleArray;

	        // Evaluation of schedules is protected by an optional scheduling service lock and then the engine lock
	        // We want to stay in this order for allowing the engine lock as a second-order lock to the
	        // services own lock, if it has one.
	        services.SchedulingService.EvaluateLock();
	        services.EventProcessingRWLock.AcquireReadLock();
	        try
	        {
	            services.SchedulingService.Evaluate(handles);
	        }
	        finally
	        {
	            services.EventProcessingRWLock.ReleaseReadLock();
	            services.SchedulingService.EvaluateUnLock();
	        }

	        services.EventProcessingRWLock.AcquireReadLock();
	        try
	        {
	            ProcessScheduleHandles(handles);
	        }
	        finally
	        {
				services.EventProcessingRWLock.ReleaseReadLock();
	        }
	    }

	    private void ProcessScheduleHandles(ArrayBackedCollection<ScheduleHandle> handles)
	    {
	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.Trace("Found schedules for", handles.Count);
	        }

	        if (handles.Count == 0)
	        {
	            return;
	        }

	        // handle 1 result separatly for performance reasons
	        if (handles.Count == 1)
	        {
	            Object[] handleArray = handles.Array;
	            EPStatementHandleCallback handle = (EPStatementHandleCallback) handleArray[0];
	            ManagedLock statementLock = handle.EpStatementHandle.StatementLock;
	            statementLock.AcquireLock(services.StatementLockFactory);
	            try
	            {
	                handle.ScheduleCallback.ScheduledTrigger(services.ExtensionServicesContext);
	                handle.EpStatementHandle.InternalDispatch();
	            }
	            finally
	            {
	                handle.EpStatementHandle.StatementLock.ReleaseLock(services.StatementLockFactory);
	            }
	            handles.Clear();
	            return;
	        }

	        Object[] matchArray = handles.Array;
	        int entryCount = handles.Count;

	        // sort multiple matches for the event into statements
	        EDictionary<EPStatementHandle, Object> stmtCallbacks = SchedulePerStmt;
	        stmtCallbacks.Clear();
	        for (int i = 0; i < entryCount; i++)    // need to use the size of the collection
	        {
	            EPStatementHandleCallback handleCallback = (EPStatementHandleCallback) matchArray[i];
	            EPStatementHandle handle = handleCallback.EpStatementHandle;
	            ScheduleHandleCallback callback = handleCallback.ScheduleCallback;

	            Object entry = stmtCallbacks.Fetch(handle);

	            // This statement has not been encountered before
	            if (entry == null)
	            {
	                stmtCallbacks[handle] = callback;
	                continue;
	            }

	            // This statement has been encountered once before
	            if (entry is ScheduleHandleCallback)
	            {
	                ScheduleHandleCallback existingCallback = (ScheduleHandleCallback) entry;
	                LinkedList<ScheduleHandleCallback> entries = new LinkedList<ScheduleHandleCallback>();
	                entries.AddLast(existingCallback);
	                entries.AddLast(callback);
	                stmtCallbacks[handle] = entries;
	                continue;
	            }

	            // This statement has been encountered more then once before
	            LinkedList<ScheduleHandleCallback> _entries = (LinkedList<ScheduleHandleCallback>) entry;
	            _entries.AddLast(callback);
	        }
	        handles.Clear();

	        foreach (EPStatementHandle handle in stmtCallbacks.Keys)
	        {
	            Object callbackObject = stmtCallbacks.Fetch(handle);

	            handle.StatementLock.AcquireLock(services.StatementLockFactory);
	            try
	            {
                    LinkedList<ScheduleHandleCallback> callbackList = callbackObject as LinkedList<ScheduleHandleCallback>;
                    if (callbackList != null)
	                {
	                    foreach (ScheduleHandleCallback callback in callbackList)
	                    {
	                        callback.ScheduledTrigger(services.ExtensionServicesContext);
	                    }
	                }
	                else
	                {
	                    ScheduleHandleCallback callback = (ScheduleHandleCallback) callbackObject;
	                    callback.ScheduledTrigger(services.ExtensionServicesContext);
	                }

	                // internal join processing, if applicable
	                handle.InternalDispatch();
	            }
	            finally
	            {
	                handle.StatementLock.ReleaseLock(services.StatementLockFactory);
	            }
	        }
	    }
	
        private void ProcessThreadWorkQueue()
        {
            Object _event;
            while ((_event = ThreadWorkQueue.Next()) != null)
            {
                EventBean eventBean;
                if (_event is EventBean)
                {
                    eventBean = (EventBean)_event;
                }
                else
                {
                    eventBean = services.EventAdapterService.AdapterForBean(_event);
                }

                services.EventProcessingRWLock.AcquireReadLock();
	            try
	            {
	                ProcessMatches(eventBean);
	            }
	            finally
	            {
	                services.EventProcessingRWLock.ReleaseReadLock();
	            }

                Dispatch();
            }
        }
		
	    private void ProcessMatches(EventBean _event)
	    {
	        // get matching filters
	        ArrayBackedCollection<FilterHandle> matches = MatchesArray;
	        services.FilterService.Evaluate(_event, matches.Array);

	        if (ThreadLogUtil.ENABLED_TRACE)
	        {
	            ThreadLogUtil.Trace("Found matches for underlying ", matches.Count, _event.Underlying);
	        }

	        if (matches.Count == 0)
	        {
	            return;
	        }

	        EDictionary<EPStatementHandle, Object> stmtCallbacks = MatchesPerStmt;
	        Object[] matchArray = matches.Array;
	        int entryCount = matches.Count;

	        for (int i = 0; i < entryCount; i++)
	        {
	            EPStatementHandleCallback handleCallback = (EPStatementHandleCallback) matchArray[i];
	            EPStatementHandle handle = handleCallback.EpStatementHandle;

	            // Self-joins require that the internal dispatch happens after all streams are evaluated
	            if (handle.IsCanSelfJoin)
	            {
	                List<FilterHandleCallback> callbacks = (List<FilterHandleCallback>) stmtCallbacks.Fetch(handle);
	                if (callbacks == null)
	                {
	                    callbacks = new List<FilterHandleCallback>();
	                    stmtCallbacks[handle] = callbacks;
	                }
	                callbacks.Add(handleCallback.FilterCallback);
	                continue;
	            }

	            handle.StatementLock.AcquireLock(services.StatementLockFactory);
	            try
	            {
	                handleCallback.FilterCallback.MatchFound(_event);
	                
	                // internal join processing, if applicable
	                handle.InternalDispatch();
	            }
	            finally
	            {
	                handleCallback.EpStatementHandle.StatementLock.ReleaseLock(services.StatementLockFactory);
	            }
	        }
	        matches.Clear();
	        if (stmtCallbacks.Count == 0)
	        {
	            return;
	        }

	        foreach (EPStatementHandle handle in stmtCallbacks.Keys)
	        {
	            handle.StatementLock.AcquireLock(services.StatementLockFactory);
	            try
	            {
	                List<FilterHandleCallback> callbackList = (List<FilterHandleCallback>) stmtCallbacks.Fetch(handle);
	                foreach (FilterHandleCallback callback in callbackList)
	                {
	                    callback.MatchFound(_event);
	                }

	                // internal join processing, if applicable
	                handle.InternalDispatch();
	            }
	            finally
	            {
	                handle.StatementLock.ReleaseLock(services.StatementLockFactory);
	            }
	        }
	        stmtCallbacks.Clear();
	    }

        private void Dispatch()
        {
            try
            {
                services.DispatchService.Dispatch();
            }
            catch (SystemException ex)
            {
                throw new EPException(ex);
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
