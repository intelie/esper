using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.collection;
using net.esper.client;
using net.esper.events;
using net.esper.schedule;
using net.esper.view;

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
		, CloneableView
		, DataWindowView
	{
        private const string dateFormat = @"yyyy-MM-dd HH:mm:ss.SSS";

	    private readonly TimeWindowViewFactory timeWindowViewFactory;
	    private readonly long millisecondsBeforeExpiry;
	    private readonly TimeWindow timeWindow = new TimeWindow();
	    private readonly ViewUpdatedCollection viewUpdatedCollection;

	    private readonly StatementContext statementContext;
	    private readonly ScheduleSlot scheduleSlot;

	    /// <summary>Constructor.</summary>
	    /// <param name="millisecondsBeforeExpiry">
	    /// is the number of milliseconds before events gets pushed
	    /// out of the timeWindow as oldData in the update method.
	    /// </param>
	    /// <param name="viewUpdatedCollection">
	    /// is a collection the view must update when receiving events
	    /// </param>
	    /// <param name="statementContext">is required view services</param>
	    /// <param name="timeWindowViewFactory">for copying the view in a group-by</param>
	    public TimeWindowView(StatementContext statementContext, TimeWindowViewFactory timeWindowViewFactory, long millisecondsBeforeExpiry, ViewUpdatedCollection viewUpdatedCollection)
	    {
	        this.statementContext = statementContext;
	        this.timeWindowViewFactory = timeWindowViewFactory;
	        this.millisecondsBeforeExpiry = millisecondsBeforeExpiry;
	        this.viewUpdatedCollection = viewUpdatedCollection;
	        this.scheduleSlot = statementContext.ScheduleBucket.AllocateSlot() ;
	    }

	    public View CloneView(StatementContext statementContext)
	    {
	        return timeWindowViewFactory.MakeView(statementContext);
	    }

        /// <summary> Gets or sets the size of the time window in millisecond.</summary>
		/// <returns> size of window
		/// </returns>
		public long MillisecondsBeforeExpiry
		{
			get { return millisecondsBeforeExpiry; }
		}

	    /// <summary>
	    /// Returns the (optional) collection handling random access to window contents for prior or previous events.
	    /// </summary>
	    /// <returns>buffer for events</returns>
	    public ViewUpdatedCollection ViewUpdatedCollection
	    {
	        get { return viewUpdatedCollection; }
	    }

	    public override EventType EventType
	    {
	        get { return parent.EventType; }
	    }

	    public override void Update(EventBean[] newData, EventBean[] oldData)
	    {
	        if (statementContext == null)
	        {
	            String message = "View context has not been supplied, cannot schedule callback";
	            log.Fatal(".Update " + message);
	            throw new EPException(message);
	        }

	        long timestamp = statementContext.SchedulingService.Time;

	        // we don't care about removed data from a prior view
	        if ((newData == null) || (newData.Length == 0))
	        {
	            return;
	        }

	        // If we have an empty window about to be filled for the first time, schedule a callback
	        // for now plus millisecondsBeforeExpiry
	        if (timeWindow.IsEmpty)
	        {
	            ScheduleCallback(millisecondsBeforeExpiry);
	        }

	        // add data points to the timeWindow
	        for (int i = 0; i < newData.Length; i++)
	        {
	            timeWindow.Add(timestamp, newData[i]);
	        }

	        if (viewUpdatedCollection != null)
	        {
	            viewUpdatedCollection.Update(newData, null);
	        }

	        // update child views
	        if (this.HasViews)
	        {
	            UpdateChildren(newData, null);
	        }
	    }

	    /// <summary>
	    /// This method removes (expires) objects from the window and schedules a new callback for the
	    /// time when the next oldest message would expire from the window.
	    /// </summary>
	    internal void Expire()
	    {
	        long expireBeforeTimestamp = statementContext.SchedulingService.Time - millisecondsBeforeExpiry + 1;

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".Expire Expiring messages before " +
	                    "msec=" + expireBeforeTimestamp +
	                    "  date=" + dateFormat.Format(expireBeforeTimestamp));
	        }

	        // Remove from the timeWindow any events that have an older or timestamp then the given timestamp
	        // The window extends from X to (X - millisecondsBeforeExpiry + 1)
	        List<EventBean> expired = timeWindow.ExpireEvents(expireBeforeTimestamp);

	        // If there are child views, fireStatementStopped update method
	        if (this.HasViews)
	        {
	            if ((expired != null) && (expired.Count != 0))
	            {
	                EventBean[] oldEvents = expired.ToArray();
	                if (viewUpdatedCollection != null)
	                {
	                    viewUpdatedCollection.Update(null, oldEvents);
	                }
	                UpdateChildren(null, oldEvents);
	            }
	        }

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".Expire Expired messages....size=" + expired.Count);
	            foreach (Object item in expired)
	            {
	                log.Debug(".expire object=" + item);
	            }
	        }

	        // If we still have events in the window, schedule new callback
	        if (timeWindow.IsEmpty)
	        {
	            return;
	        }
	        long? oldestTimestamp = timeWindow.OldestTimestamp;
	        long currentTimestamp = statementContext.SchedulingService.Time;
	        long scheduleMillisec = millisecondsBeforeExpiry - (currentTimestamp - oldestTimestamp);
	        ScheduleCallback(scheduleMillisec);

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".Expire Scheduled new callback for now plus msec=" + scheduleMillisec);
	        }
	    }

	    private void ScheduleCallback(long msecAfterCurrentTime)
	    {
	        ScheduleHandleCallback callback =
	            new ScheduleHandleCallbackImpl(
	                new ScheduleHandleDelegate(
	                    delegate(ExtensionServicesContext extensionServicesContext) { Expire(); }));

			EPStatementHandleCallback handle = new EPStatementHandleCallback(statementContext.EpStatementHandle, callback);
	        statementContext.SchedulingService.Add(msecAfterCurrentTime, handle, scheduleSlot);
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

	    /// <summary>Returns true if the window is empty, or false if not empty.</summary>
	    /// <returns>true if empty</returns>
	    public bool IsEmpty
	    {
	        get { return timeWindow.IsEmpty ; }
	    }

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
