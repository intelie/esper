using System;
using System.Collections.Generic;

using net.esper.view;
using net.esper.events;
using net.esper.schedule;
using net.esper.collection;
using net.esper.client;
using net.esper.eql;
using net.esper.eql.parse;

using org.apache.commons.logging;

namespace net.esper.view.window
{
	/// <summary> This view is a moving timeWindow extending the specified amount of milliseconds into the past.
	/// The view bases the timeWindow on the time obtained from the scheduling service.
	/// All incoming events receive a timestamp and are placed in a sorted map by timestamp.
	/// The view does not care about old data published by the parent view to this view.
	/// 
	/// Events leave or expire from the time timeWindow by means of a scheduled callback registered with the
	/// scheduling service. Thus child views receive updates containing old data only asynchronously
	/// as the system-time-based timeWindow moves on. However child views receive updates containing new data
	/// as soon as the new data arrives.
	/// </summary>

    public sealed class TimeWindowView
        : ViewSupport
        , ContextAwareView
        , DataWindowView
	{
        /// <summary> Gets or sets the size of the time window in millisecond.</summary>
		/// <returns> size of window
		/// </returns>
		public long MillisecondsBeforeExpiry
		{
			get { return millisecondsBeforeExpiry; }
			set { this.millisecondsBeforeExpiry = value; }
			
		}

        /// <summary>
        /// Gets or sets the context instances used by the view.
        /// </summary>
        /// <value>The view service context.</value>
		public ViewServiceContext ViewServiceContext
		{
			get
			{
				return viewServiceContext;
			}
			
			set
			{
				this.viewServiceContext = value;
				this.scheduleSlot = value.ScheduleBucket.AllocateSlot();
			}
			
		}

        private const string dateFormat = @"yyyy-MM-dd HH:mm:ss.SSS";
		private long millisecondsBeforeExpiry;
		
		private readonly TimeWindow timeWindow = new TimeWindow();
		private ViewServiceContext viewServiceContext;
		private ScheduleSlot scheduleSlot;

        /// <summary>
        /// Initializes a new instance of the <see cref="TimeWindowView"/> class.
        /// </summary>
        public TimeWindowView()
		{
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="secondsBeforeExpiry">is the number of seconds before events gets pushed
		/// out of the timeWindow as oldData in the update method.
		/// </param>

        public TimeWindowView(int secondsBeforeExpiry)
		{
			if (secondsBeforeExpiry < 1)
			{
				throw new ArgumentException("Time window view requires a millisecond size of at least 100 msec");
			}
			
			this.millisecondsBeforeExpiry = 1000 * secondsBeforeExpiry;
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="secondsBeforeExpiry">is the number of seconds before events gets pushed
		/// out of the timeWindow as oldData in the update method.
		/// </param>

		public TimeWindowView(double secondsBeforeExpiry)
		{
			if (secondsBeforeExpiry <= 0.1)
			{
				throw new ArgumentException("Time window view requires a millisecond size of at least 100 msec");
			}
			
			this.millisecondsBeforeExpiry = (long) System.Math.Round(1000d * secondsBeforeExpiry);
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="timePeriod">is the number of seconds before events gets pushed
		/// out of the timeWindow as oldData in the update method.
		/// </param>

        public TimeWindowView(TimePeriodParameter timePeriod):this(timePeriod.NumSeconds)
		{
		}

        /// <summary>
        /// Return null if the view will accept being attached to a particular object.
        /// </summary>
        /// <param name="parentView">is the potential parent for this view</param>
        /// <returns>
        /// null if this view can successfully attach to the parent, an error message if it cannot.
        /// </returns>
		public override String AttachesTo(Viewable parentView)
		{
			// Attaches to any parent view
			return null;
		}

        /// <summary>
        /// Provides metadata information about the type of object the event collection contains.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// metadata for the objects in the collection
        /// </returns>
		public override EventType EventType
		{
            get { return parent.EventType; }
            set { }
		}

        /// <summary>
        /// Notify that data has been added or removed from the Viewable parent.
        /// The last object in the newData array of objects would be the newest object added to the parent view.
        /// The first object of the oldData array of objects would be the oldest object removed from the parent view.
        /// <para>
        /// If the call to update contains new (inserted) data, then the first argument will be a non-empty list and the
        /// second will be empty. Similarly, if the call is a notification of deleted data, then the first argument will be
        /// empty and the second will be non-empty. Either the newData or oldData will be non-null.
        /// This method won't be called with both arguments being null, but either one could be null.
        /// The same is true for zero-length arrays. Either newData or oldData will be non-empty.
        /// If both are non-empty, then the update is a modification notification.
        /// </para>
        /// 	<para>
        /// When update() is called on a view by the parent object, the data in newData will be in the collection of the
        /// parent, and its data structures will be arranged to reflect that.
        /// The data in oldData will not be in the parent's data structures, and any access to the parent will indicate that
        /// that data is no longer there.
        /// </para>
        /// </summary>
        /// <param name="newData">is the new data that has been added to the parent view</param>
        /// <param name="oldData">is the old data that has been removed from the parent view</param>
		public override void Update(EventBean[] newData, EventBean[] oldData)
		{
			if (viewServiceContext == null)
			{
				String message = "View context has not been supplied, cannot schedule callback";
				log.Fatal(".update " + message);
				throw new EPException(message);
			}
			
			long timestamp = viewServiceContext.SchedulingService.Time;
			
			// we don't care about removed data from a prior view
			if ((newData == null) || (newData.Length == 0))
			{
				return ;
			}
			
			// If we have an empty window about to be filled for the first time, schedule a callback
			// for now plus millisecondsBeforeExpiry
			if (timeWindow.IsEmpty)
			{
				scheduleCallback(millisecondsBeforeExpiry);
			}
			
			// add data points to the timeWindow
			for (int i = 0; i < newData.Length; i++)
			{
				timeWindow.Add(timestamp, newData[i]);
			}
			
			// update child views
			if (this.HasViews)
			{
				UpdateChildren(newData, null);
			}
		}
		
		/// <summary> This method removes (expires) objects from the window and schedules a new callback for the
		/// time when the next oldest message would expire from the window.
		/// </summary>
		public void expire()
		{
			long expireBeforeTimestamp = viewServiceContext.SchedulingService.Time - millisecondsBeforeExpiry + 1;
			
			if (log.IsDebugEnabled)
			{
                log.Debug(".expire Expiring messages before " + "msec=" + expireBeforeTimestamp + "  date=" + expireBeforeTimestamp.ToString(dateFormat));
			}
			
			// Remove from the timeWindow any events that have an older or timestamp then the given timestamp
			// The window extends from X to (X - millisecondsBeforeExpiry + 1)
            List<EventBean> expired = timeWindow.ExpireEvents(expireBeforeTimestamp);
			
			// If there are child views, fire update method
			if (this.HasViews)
			{
				if ((expired != null) && (expired.Count > 0))
				{
					UpdateChildren(null, expired.ToArray());
				}
			}
			
			if (log.IsDebugEnabled)
			{
				log.Debug(".expire Expired messages....size=" + expired.Count);
				foreach (Object obj in expired)
				{
					log.Debug(".expire object=" + obj);
				}
			}
			
			// If we still have events in the window, schedule new callback
			if (timeWindow.IsEmpty)
			{
				return ;
			}

            long oldestTimestamp = timeWindow.OldestTimestamp.Value;
			long currentTimestamp = viewServiceContext.SchedulingService.Time;
			long scheduleMillisec = millisecondsBeforeExpiry - (currentTimestamp - oldestTimestamp);
			scheduleCallback(scheduleMillisec);
			
			if (log.IsDebugEnabled)
			{
				log.Debug(".expire Scheduled new callback for now plus msec=" + scheduleMillisec);
			}
		}
		
		private void scheduleCallback(long msecAfterCurrentTime)
		{
            ScheduleCallback callback = new ScheduleCallbackImpl( delegate() { this.expire(); } ) ;
			viewServiceContext.SchedulingService.Add(msecAfterCurrentTime, callback, scheduleSlot);
		}

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
        {
            return timeWindow.GetEnumerator();
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return this.GetType().FullName + " millisecondsBeforeExpiry=" + millisecondsBeforeExpiry;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TimeWindowView));
	}
}
