using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.client;
using net.esper.compat;
using net.esper.eql.parse;
using net.esper.events;
using net.esper.schedule;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.view.window
{
    /// <summary> A data view that aggregates events in a stream and releases them in one batch at every specified time interval.
    /// The view works similar to a time_window but in not continuous.
    /// The view releases the batched events after the interval as new data to child views. The prior batch if
    /// not empty is released as old data to child view. The view doesn't release intervals with no old or new data.
    /// It also does not getSelectListEvents old data published by a parent view.
    /// 
    /// For example, we want to calculate the average of IBM stock every hour, for the last hour.
    /// The view accepts 2 parameter combinations.
    /// (1) A time interval is supplied with a reference point - based on this point the intervals are set.
    /// (1) A time interval is supplied but no reference point - the reference point is set when the first event arrives.
    /// 
    /// If there are no events in the current and prior batch, the view will not invoke the update method of child views.
    /// In that case also, no next callback is scheduled with the scheduling service until the next event arrives.
    /// </summary>
    public sealed class TimeBatchView : ViewSupport, ContextAwareView
    {
        /// <summary> Returns the interval size in milliseconds.</summary>
        /// <returns> batch size
        /// </returns>
        /// <summary> Sets the interval size in milliseconds.</summary>
        /// <param name="msecIntervalSize">batch size
        /// </param>

        public long MsecIntervalSize
        {
            get { return msecIntervalSize; }
            set { this.msecIntervalSize = value; }
        }

        /// <summary>
        /// Gets or sets the reference point to use to anchor interval Start and end dates to.
        /// </summary>

        public long? InitialReferencePoint
        {
            get { return initialReferencePoint; }
            set { this.initialReferencePoint = value; }
        }

        public ViewServiceContext ViewServiceContext
        {
            get { return viewServiceContext; }
            set
            {
                this.viewServiceContext = value;
                this.scheduleSlot = value.ScheduleBucket.allocateSlot();
            }
        }

        private const String dateFormat = @"yyyy-MM-dd HH:mm:ss.SSS";

        // View parameters
        private long msecIntervalSize;
        private long? initialReferencePoint;

        // Current running parameters
        private long? currentReferencePoint;
        private ViewServiceContext viewServiceContext;
        private ELinkedList<EventBean> lastBatch = null;
        private ELinkedList<EventBean> currentBatch = new ELinkedList<EventBean>();
        private bool isCallbackScheduled;
        private ScheduleSlot scheduleSlot;

        /// <summary> Default constructor - required by all views to adhere to the Java bean specification.</summary>
        public TimeBatchView()
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="secIntervalSize">is the number of seconds to batch events for.
        /// </param>
        public TimeBatchView(int secIntervalSize)
        {
            if (secIntervalSize < 1)
            {
                throw new ArgumentException("Time batch view requires a millisecond interval size of at least 100 msec");
            }
            this.msecIntervalSize = 1000 * secIntervalSize;
        }

        /// <summary> Constructor.</summary>
        /// <param name="secIntervalSize">is the number of milliseconds to batch events for
        /// </param>
        /// <param name="referencePoint">is the reference point onto which to base intervals.
        /// </param>

        public TimeBatchView(int secIntervalSize, long? referencePoint)
            : this(secIntervalSize)
        {
            this.initialReferencePoint = referencePoint;
        }

        /// <summary> Constructor.</summary>
        /// <param name="secIntervalSize">is the number of seconds to batch events for.
        /// </param>

        public TimeBatchView(double secIntervalSize)
        {
            if (secIntervalSize < 0.1)
            {
                throw new ArgumentException("Time batch view requires a millisecond interval size of at least 100 msec");
            }

            this.msecIntervalSize = (long)System.Math.Round(1000 * secIntervalSize);
        }

        /// <summary> Constructor.</summary>
        /// <param name="timeTimePeriod">is the number of seconds to batch events for.
        /// </param>

        public TimeBatchView(TimePeriodParameter timeTimePeriod)
            : this(timeTimePeriod.NumSeconds)
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="secIntervalSize">is the number of seconds to batch events for
        /// </param>
        /// <param name="referencePoint">is the reference point onto which to base intervals.
        /// </param>

        public TimeBatchView(double secIntervalSize, long? referencePoint)
            : this(secIntervalSize)
        {
            this.initialReferencePoint = referencePoint;
        }

        /// <summary> Constructor.</summary>
        /// <param name="timeTimePeriod">is the number of seconds to batch events for
        /// </param>
        /// <param name="referencePoint">is the reference point onto which to base intervals.
        /// </param>

        public TimeBatchView(TimePeriodParameter timeTimePeriod, long? referencePoint)
            : this(timeTimePeriod.NumSeconds, referencePoint)
        {
        }

        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to any parent view
            return null;
        }

        public override EventType EventType
        {
            get { return parent.EventType; }
            set { }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".update Received update, " + "  newData.Length==" + ((newData == null) ? 0 : newData.Length) + "  oldData.Length==" + ((oldData == null) ? 0 : oldData.Length));
            }

            if (viewServiceContext == null)
            {
                String message = "View context has not been supplied, cannot schedule callback";
                log.Fatal(".update " + message);
                throw new EPException(message);
            }

            // we don't care about removed data from a prior view
            if ((newData == null) || (newData.Length == 0))
            {
                return;
            }

            // If we have an empty window about to be filled for the first time, schedule a callback
            if (currentBatch.Count == 0)
            {
                if (currentReferencePoint == null)
                {
                    currentReferencePoint = initialReferencePoint;
                    if (currentReferencePoint == null)
                    {
                        currentReferencePoint = viewServiceContext.SchedulingService.Time;
                    }
                }

                // Schedule the next callback if there is none currently scheduled
                if (!isCallbackScheduled)
                {
                    ScheduleCallback();
                    isCallbackScheduled = true;
                }
            }

            // add data points to the timeWindow
            for (int i = 0; i < newData.Length; i++)
            {
                currentBatch.Add(newData[i]);
            }

            // We do not update child views, since we batch the events.
        }

        /// <summary> 
        /// This method updates child views and clears the batch of events.
        /// We schedule a new callback at this time if there were events in the batch.
        /// </summary>
        
        public void SendBatch()
        {
            isCallbackScheduled = false;

            if (log.IsDebugEnabled)
            {
                log.Debug(
                    ".SendBatch Update child views, " +
                    "  time=" + viewServiceContext.SchedulingService.Time.ToString(dateFormat));
            }

            // If there are child views and the batch was filled, fire update method
            if (this.HasViews)
            {
                // Convert to object arrays
                EventBean[] newData = null;
                EventBean[] oldData = null;
                if (currentBatch.Count > 0)
                {
					newData = currentBatch.ToArray();
                }
                if ((lastBatch != null) && (lastBatch.Count > 0))
                {
					oldData = lastBatch.ToArray();
                }

                // Post new data (current batch) and old data (prior batch)
                if ((newData != null) || (oldData != null))
                {
                    updateChildren(newData, oldData);
                }
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".SendBatch Published updated data, ....newData size=" + currentBatch.Count);
                foreach (Object obj in currentBatch)
                {
                    log.Debug(".SendBatch object=" + obj);
                }
            }

            // Only if there have been any events in this or the last interval do we schedule a callback,
            // such as to not waste resources when no events arrive.
            if ((currentBatch.Count > 0) || ((lastBatch != null) && (lastBatch.Count > 0)))
            {
                ScheduleCallback();
                isCallbackScheduled = true;
            }

            lastBatch = currentBatch;
            currentBatch = new ELinkedList<EventBean>();
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return currentBatch.GetEnumerator();
        }

        public override String ToString()
        {
            return
                this.GetType().FullName +
                " msecIntervalSize=" + msecIntervalSize +
                " initialReferencePoint=" + initialReferencePoint;
        }

        private void ScheduleCallback()
        {
            long current = viewServiceContext.SchedulingService.Time;
            long afterMSec = ComputeWaitMSec(current, this.currentReferencePoint.Value, this.msecIntervalSize);

            if (log.IsDebugEnabled)
            {
                log.Debug(".scheduleCallback Scheduled new callback for " + 
                    " afterMsec=" + afterMSec +
                    " now=" + current + 
                    " currentReferencePoint=" + currentReferencePoint +
                    " initialReferencePoint=" + initialReferencePoint + 
                    " msecIntervalSize=" + msecIntervalSize);
            }

            ScheduleCallback callback = new ScheduleCallbackImpl(SendBatch);
            viewServiceContext.SchedulingService.Add(afterMSec, callback, scheduleSlot);
        }

        /// <summary> Given a current time and a reference time and an interval size, compute the amount of
        /// milliseconds till the next interval.
        /// </summary>
        /// <param name="current">is the current time
        /// </param>
        /// <param name="reference">is the reference point
        /// </param>
        /// <param name="interval">is the interval size
        /// </param>
        /// <returns> milliseconds after current time that marks the end of the current interval
        /// </returns>
        public static long ComputeWaitMSec(long current, long reference, long interval)
        {
            // Example:  current c=2300, reference r=1000, interval i=500, solution s=200
            //
            // int n = ((2300 - 1000) / 500) = 2
            // r + (n + 1) * i - c = 200
            //
            // Negative example:  current c=2300, reference r=4200, interval i=500, solution s=400
            // int n = ((2300 - 4200) / 500) = -3
            // r + (n + 1) * i - c = 4200 - 3*500 - 2300 = 400
            //
            long n = (long)((current - reference) / (interval * 1f));
            if (reference > current)
            // References in the future need to deduct one window
            {
                n = n - 1;
            }
            long solution = reference + (n + 1) * interval - current;

            if (solution == 0)
            {
                return interval;
            }
            return solution;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TimeBatchView));
    }
}
