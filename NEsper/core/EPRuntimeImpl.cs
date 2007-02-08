using System;
using System.Collections.Generic;
using System.Xml;
using ReaderWriterLock = System.Threading.ReaderWriterLock;

using net.esper.client;
using net.esper.client.time;
using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.timer;

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
        , TimerCallback
    {
        virtual public long NumEventsReceived
        {
            get { return services.FilterService.NumEventsEvaluated; }
        }

        virtual public long NumEventsEmitted
        {
            get { return services.EmitService.NumEventsEmitted; }
        }

        private EPServicesContext services;
        private ReaderWriterLock timerRWLock;
        private ThreadWorkQueue threadWorkQueue;

        /// <summary> Constructor.</summary>
        /// <param name="services">- references to services
        /// </param>
        public EPRuntimeImpl(EPServicesContext services)
        {
            this.services = services;
            timerRWLock = new ReaderWriterLock();
            threadWorkQueue = new ThreadWorkQueue();
        }

        public virtual void TimerCallback()
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".timerCallback Evaluating scheduled callbacks");
            }

            long msec = DateTimeHelper.TimeInMillis( DateTime.Now ) ;
            CurrentTimeEvent currentTimeEvent = new CurrentTimeEvent(msec);
            SendEvent(currentTimeEvent);
        }

        public virtual void SendEvent(Object _event)
        {
            if (_event == null)
            {
                log.Fatal(".sendEvent Null object supplied");
                return;
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".sendEvent Processing event " + _event);
            }

            // Process event
            ProcessEvent(_event);
        }

        public virtual void SendEvent(XmlNode document)
        {
            if (document == null)
            {
                log.Fatal(".sendEvent Null object supplied");
                return;
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".sendEvent Processing DOM node event " + document);
            }

            // Get it wrapped up, process event
            EventBean eventBean = services.EventAdapterService.AdapterForDOM(document);
            ProcessEvent(eventBean);
        }

        public virtual void SendEvent(IDataDictionary map, String eventTypeAlias)
        {
            if (map == null)
            {
                throw new ArgumentException("Invalid null event object");
            }

            if (log.IsDebugEnabled)
            {
            	log.Debug(".sendMap Processing event " + map.ToString()) ;
            }

            // Process event
            EventBean eventBean = services.EventAdapterService.AdapterForMap(map, eventTypeAlias);
            ProcessEvent(eventBean);
        }

        public virtual void Route(Object _event)
        {
            threadWorkQueue.Add(_event);
        }

        public virtual void Route(EventBean _event)
        {
            threadWorkQueue.Add(_event);
        }

        public virtual void Emit(Object _object)
        {
            services.EmitService.emitEvent(_object, null);
        }

        public virtual void Emit(Object _object, String channel)
        {
            services.EmitService.emitEvent(_object, channel);
        }

        public virtual void AddEmittedListener(EmittedListener listener, String channel)
        {
            services.EmitService.AddListener(listener, channel);
        }

        public virtual void ClearEmittedListeners()
        {
            services.EmitService.clearListeners();
        }

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

            // All events are processed by the filter service
            try
            {
            	timerRWLock.AcquireReaderLock( LockConstants.ReaderTimeout ) ;

                services.FilterService.Evaluate(eventBean);

                // Dispatch internal work items and results
                dispatch();

                // Work off the event queue if any events accumulated in there via a Route()
                processThreadWorkQueue();
            }
            catch (SystemException ex)
            {
                throw new EPException(ex);
            }
            finally
            {
                timerRWLock.ReleaseReaderLock();
            }
        }

        private void ProcessTimeEvent(TimerEvent _event)
        {
            if (_event is TimerControlEvent)
            {
                TimerControlEvent timerControlEvent = (TimerControlEvent)_event;
                if (timerControlEvent.getClockType() == TimerControlEvent.ClockType.CLOCK_INTERNAL)
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
            timerRWLock.AcquireWriterLock(LockConstants.WriterTimeout);

            try
            {
                if (log.IsDebugEnabled)
                {
                    log.Debug(".processTimeEvent Setting time and evaluating schedules");
                }

                CurrentTimeEvent current = (CurrentTimeEvent)_event;
                long currentTime = current.TimeInMillis;
                services.SchedulingService.Time = currentTime;

                services.SchedulingService.Evaluate();

                // Let listeners know of results
                dispatch();

                // Work off the event queue if any events accumulated in there via a Route()
                processThreadWorkQueue();
            }
            catch (SystemException ex)
            {
                throw new EPException(ex);
            }
            finally
            {
                timerRWLock.ReleaseWriterLock();
            }
        }

        private void processThreadWorkQueue()
        {
            Object _event;
            while ((_event = threadWorkQueue.Next()) != null)
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

                services.FilterService.Evaluate(eventBean);

                dispatch();
            }
        }

        private void dispatch()
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

        private static readonly Log log = LogFactory.GetLog(typeof(EPRuntimeImpl));
    }
}
