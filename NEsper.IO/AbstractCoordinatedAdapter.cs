using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.client;
using net.esper.compat;
using net.esper.core;
using net.esper.schedule;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.adapter
{
	/// <summary>
	/// A skeleton implementation for coordinated adapter reading, for adapters that
	/// can do timestamp-coordinated input.
	/// </summary>
	public abstract class AbstractCoordinatedAdapter : CoordinatedAdapter
	{
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>
	    /// Statement management.
	    /// </summary>
	    protected readonly AdapterStateManager stateManager = new AdapterStateManager();

	    /// <summary>
	    /// Sorted events to be sent.
	    /// </summary>
	    protected readonly TreeSet<SendableEvent> eventsToSend = new TreeSet<SendableEvent>(new SendableEventComparator());

	    /// <summary>
	    /// Slot for scheduling.
	    /// </summary>
	    protected ScheduleSlot scheduleSlot;

		private EPRuntime runtime;
		private SchedulingService schedulingService;
		private bool usingEngineThread;
		private long currentTime = 0;
		private long startTime;

		/// <summary>
		/// Ctor.
		/// <param name="epService">the EPServiceProvider for the engine runtime and services</param>
		/// <param name="usingEngineThread">true if the Adapter should set time by the scheduling service in the engine,</param>
		///                           false if it should set time externally through the calling thread
		/// </summary>
		public AbstractCoordinatedAdapter(EPServiceProvider epService, bool usingEngineThread)
		{
			this.usingEngineThread = usingEngineThread;

			if(epService == null)
			{
				return;
			}
			if(!(epService is EPServiceProviderSPI))
			{
				throw new ArgumentException("Invalid epService provided");
			}
			this.runtime = ((EPServiceProviderSPI)epService).EPRuntime;
			this.schedulingService = ((EPServiceProviderSPI)epService).SchedulingService;
		}

        /// <summary>
        /// Get the state of this Adapter.
        /// </summary>
        /// <value></value>
		public virtual AdapterState State
		{
			get { return stateManager.State; }
		}

        /// <summary>
        /// Start the sending of events into the runtime egine.
        /// </summary>
        /// <throws>EPException in case of errors processing the events</throws>
		public virtual void Start()
		{
			log.Debug(".Start");
			if(runtime == null)
			{
				throw new EPException("Attempting to start an Adapter that hasn't had the epService provided");
			}
			startTime = CurrentTime;
			log.Debug(".start startTime==" + startTime);
			stateManager.Start();
			ContinueSendingEvents();
		}

        /// <summary>
        /// Pause the sending of events after a Adapter has been started.
        /// </summary>
        /// <throws>EPException if this Adapter has already been stopped</throws>
		public virtual void Pause()
		{
			stateManager.Pause();
		}

        /// <summary>
        /// Resume sending events after the Adapter has been paused.
        /// </summary>
        /// <throws>EPException in case of errors processing the events</throws>
		public virtual void Resume()
		{
			stateManager.Resume();
			ContinueSendingEvents();
		}

        /// <summary>
        /// Destroy the Adapter, stopping the sending of all events and releasing all
        /// the resources, and disallowing any further state changes on the Adapter.
        /// </summary>
        /// <throws>EPException to indicate errors during destroy</throws>
		public virtual void Destroy()
		{
			stateManager.Destroy();
			Close();
		}

        /// <summary>
        /// Stop sending events and return the Adapter to the OPENED state, ready to be
        /// started once again.
        /// </summary>
        /// <throws>EPException in case of errors releasing resources</throws>
		public virtual void Stop()
		{
			log.Debug(".stop");
			stateManager.Stop();
			eventsToSend.Clear();
			currentTime = 0;
			Reset();
		}

        /// <summary>
        /// Disallow subsequent state changes and throw an IllegalStateTransitionException
        /// if they are attempted.
        /// </summary>
		public virtual void DisallowStateTransitions()
		{
			stateManager.DisallowStateTransitions();
		}

        /// <summary>
        /// Gets or sets the using engine thread.
        /// </summary>
        /// <value>The using engine thread.</value>
		public virtual bool UsingEngineThread
		{
			get { return this.usingEngineThread ; }
			set { this.usingEngineThread = value ; }
		}

        /// <summary>
        /// Gets or sets the schedule slot.
        /// </summary>
        /// <value>The schedule slot.</value>

        public virtual ScheduleSlot ScheduleSlot
		{
			get { return this.scheduleSlot ; }
			set { this.scheduleSlot = value ; }
		}

        /// <summary>
        /// Sets the service.
        /// </summary>

        public virtual EPServiceProvider EPService
		{
			set
			{
				if(value == null)
				{
					throw new ArgumentException("epService cannot be null");
				}
				if(!(value is EPServiceProviderSPI))
				{
					throw new ArgumentException("Invalid type of EPServiceProvider");
				}
				EPServiceProviderSPI spi = (EPServiceProviderSPI)value;
				runtime = spi.EPRuntime;
				schedulingService = spi.SchedulingService;
			}
		}

		/// <summary>
		/// Perform any actions specific to this Adapter that should
		/// be completed before the Adapter is stopped.
		/// </summary>
		protected abstract void Close();

		/// <summary>
		/// Remove the first member of eventsToSend and insert
		/// another event chosen in some fashion specific to this
		/// Adapter.
		/// </summary>
		protected abstract void ReplaceFirstEventToSend();

		/// <summary>
		/// Reset all the changeable state of this Adapter, as if it were just created.
		/// </summary>
		protected abstract void Reset();

		private void ContinueSendingEvents()
		{
			if(stateManager.State == AdapterState.STARTED)
			{
				currentTime = CurrentTime;
				log.Debug(".continueSendingEvents currentTime==" + currentTime);
				FillEventsToSend();
				SendSoonestEvents();
				WaitToSendEvents();
			}
		}

		private void WaitToSendEvents()
		{
			if(usingEngineThread)
			{
				ScheduleNextCallback();
			}
			else
			{
				long sleepTime = 0;
				if(eventsToSend.IsEmpty)
				{
					sleepTime = 100;
				}
				else
				{
					sleepTime = eventsToSend.First.SendTime - (currentTime - startTime);
				}

				Thread.Sleep((int) sleepTime);
				
				ContinueSendingEvents();
			}
		}

		private long CurrentTime
		{
			get
			{
			    return usingEngineThread
                    ? schedulingService.Time
			        : DateTimeHelper.CurrentTimeMillis;
			}
		}

		private void FillEventsToSend()
		{
			if(eventsToSend.IsEmpty)
			{
				SendableEvent _event = Read();
				if(_event != null)
				{
					eventsToSend.Add(_event);
				}
			}
		}

		private void SendSoonestEvents()
		{
            while (!eventsToSend.IsEmpty && eventsToSend.First.SendTime <= (currentTime - startTime))
			{
				log.Debug(".sendSoonestEvents currentTime==" + currentTime);
				log.Debug(".sendSoonestEvents sending event " + eventsToSend.First + ", its sendTime==" + eventsToSend.First.SendTime);
				eventsToSend.First.Send(runtime);
				ReplaceFirstEventToSend();
			}
		}

		private void ScheduleNextCallback()
		{
		    ScheduleHandleCallback nextScheduleCallback =
		        new ScheduleHandleCallbackImpl(
		            new ScheduleHandleDelegate(
		                delegate(ExtensionServicesContext extensionServicesContext)
		                { 
		                	ContinueSendingEvents();
		                }));

            EPStatementHandleCallback scheduleCSVHandle = new EPStatementHandleCallback(
	            new EPStatementHandle("AbstractCoordinatedAdapter", new ManagedLockImpl("CSV"), "AbstractCoordinatedAdapter"),
	            nextScheduleCallback);
	        ScheduleSlot nextScheduleSlot;

			if(eventsToSend.IsEmpty)
			{
				log.Debug(".scheduleNextCallback no events to send, scheduling callback in 100 ms");
				nextScheduleSlot = new ScheduleSlot(0,0);
				schedulingService.Add(100, scheduleCSVHandle, nextScheduleSlot);
			}
			else
			{
				long afterMsec = eventsToSend.First.SendTime - currentTime;
				nextScheduleSlot = eventsToSend.First.ScheduleSlot;
				log.Debug(".scheduleNextCallback schedulingCallback in " + afterMsec + " milliseconds");
				schedulingService.Add(afterMsec, scheduleCSVHandle, nextScheduleSlot);
			}
		}
		
		abstract public SendableEvent Read();
   }
}
