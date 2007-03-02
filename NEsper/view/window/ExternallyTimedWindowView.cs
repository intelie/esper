using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.eql.parse;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.window
{
    /// <summary> View for a moving window extending the specified amount of time into the past, driven entirely by external timing
    /// supplied within long-type timestamp values in a field of the event beans that the view receives.
    /// 
    /// The view is completely driven by timestamp values that are supplied by the events it receives,
    /// and does not use the system time.
    /// It requires a field name as parameter for a field that returns ascending long-type timestamp values.
    /// It also requires a long-type parameter setting the time length in milliseconds of the time window.
    /// Events are expected to provide long-type timestamp values in natural order. The view does
    /// itself not use the current system time for keeping track of the time window, but just the
    /// timestamp values supplied by the events sent in.
    /// 
    /// The arrival of new events with a newer timestamp then past events causes the window to be re-evaluated and the oldest
    /// events pushed out of the window. Ie. Assume event X1 with timestamp T1 is in the window.
    /// When event Xn with timestamp Tn arrives, and the window time length in milliseconds is t, then if
    /// ((Tn - T1) > t == true) then event X1 is pushed as oldData out of the window. It is assumed that
    /// events are sent in in their natural order and the timestamp values are ascending.
    /// </summary>

    public sealed class ExternallyTimedWindowView : ViewSupport, DataWindowView
    {
        /// <summary> Returns the field name to get timestamp values from.</summary>
        /// <returns> field name for timestamp values
        /// </returns>
        /// <summary> Sets the field name to get timestamp values from.</summary>
        /// <param name="timestampFieldName">is the field name for timestamp values
        /// </param>

        public String TimestampFieldName
        {
            get { return timestampFieldName; }
            set { this.timestampFieldName = value; }
        }

        /// <summary> Retuns the window size in milliseconds.</summary>
        /// <returns> number of milliseconds before events expire from the window
        /// </returns>
        /// <summary> Sets the window size in milliseconds.</summary>
        /// <param name="millisecondsBeforeExpiry">number of milliseconds before events expire from the window
        /// </param>

        public long MillisecondsBeforeExpiry
        {
            get { return millisecondsBeforeExpiry; }
            set { this.millisecondsBeforeExpiry = value; }
        }

        private String timestampFieldName;
        private long millisecondsBeforeExpiry;
        private EventPropertyGetter timestampFieldGetter;

        private readonly TimeWindow timeWindow = new TimeWindow();

        /// <summary>
        /// Default constructor - required by all views to adhere to the Java bean specification.
        /// </summary>

        public ExternallyTimedWindowView()
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="timestampFieldName">is the field name containing a long timestamp value
        /// </param>
        /// <param name="secondsBeforeExpiry">is the number of seconds
        /// </param>

        public ExternallyTimedWindowView(String timestampFieldName, long secondsBeforeExpiry)
            : this(timestampFieldName, (double)secondsBeforeExpiry)
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="timestampFieldName">is the field name containing a long timestamp value
        /// that should be in ascending order for the natural order of events and is intended to reflect
        /// System.currentTimeInMillis but does not necessarily have to.
        /// </param>
        /// <param name="secondsBeforeExpiry">is the number of seconds before events gets pushed
        /// out of the window as oldData in the update method. The view compares
        /// each events timestamp against the newest event timestamp and those with a delta
        /// greater then secondsBeforeExpiry are pushed out of the window.
        /// </param>

        public ExternallyTimedWindowView(String timestampFieldName, double secondsBeforeExpiry)
        {
            this.timestampFieldName = timestampFieldName;
            this.millisecondsBeforeExpiry = (long)System.Math.Round(secondsBeforeExpiry * 1000d);

            if (millisecondsBeforeExpiry <= 0)
            {
                throw new ArgumentException("Externally timed window does not allow a zero or negative parameter for the millisecond window length");
            }
        }

        /// <summary> Constructor - implemented via (String, long) constructor.</summary>
        /// <param name="timestampFieldName">is the field name containing a long timestamp value
        /// </param>
        /// <param name="timePeriodParameter">is the number of milliseconds before events gets pushed
        /// </param>

        public ExternallyTimedWindowView(String timestampFieldName, TimePeriodParameter timePeriodParameter)
            : this(timestampFieldName, timePeriodParameter.NumSeconds)
        {
        }

        public override Viewable Parent
        {
            set
            {
                Viewable parent = value;
                base.Parent = value;
                if (parent != null)
                {
                    timestampFieldGetter = parent.EventType.GetGetter(timestampFieldName);
                }
            }
        }

        public override String AttachesTo(Viewable parentView)
        {
            return PropertyCheckHelper.checkLong(parentView.EventType, timestampFieldName);
        }

        public override EventType EventType
        {
            get
            {
                // The schema is the parent view's schema
                return parent.EventType;
            }
            set { }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            long timestamp = -1;

            // add data points to the window
            // we don't care about removed data from a prior view
            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                    timestamp = getLongValue(newData[i]);
                    timeWindow.Add(timestamp, newData[i]);
                }
            }

            // Remove from the window any events that have an older timestamp then the last event's timestamp
            List<EventBean> expired = null;
            if (timestamp != -1)
            {
                expired = timeWindow.ExpireEvents(timestamp - millisecondsBeforeExpiry);
            }

            // If there are child views, fire update method
            if (this.HasViews)
            {
                if ((expired != null) && (expired.Count > 0))
                {
                	UpdateChildren(newData, expired.ToArray()) ;
                }
                else
                {
                    UpdateChildren(newData, null);
                }
            }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return timeWindow.GetEnumerator();
        }

        public override String ToString()
        {
            return
            	this.GetType().FullName + 
            	" timestampFieldName=" + timestampFieldName +
            	" millisecondsBeforeExpiry=" + millisecondsBeforeExpiry;
        }

        private long getLongValue(EventBean obj)
        {
        	return Convert.ToInt64( timestampFieldGetter.GetValue( obj ) ) ;
        }
    }
}
