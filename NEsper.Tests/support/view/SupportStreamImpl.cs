using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.support.view
{
    /// <summary>
    /// Unit test class for view testing that implements the EventStream interface to which views can be attached as child views.
    /// The schema class is passed in during construction. The stream behaves much like a length window in that it
    /// keeps a reference to the last X inserted events in the past. The update method reflects new events added
    /// and events pushed out of the window. This is useful for view testing of views that use the oldData values
    /// supplied in the update method.
    /// </summary>

    public class SupportStreamImpl : EventStream
    {
        virtual public EventType EventType
        {
            get
            {
                return eventType;
            }
        }

        private readonly EventType eventType;
        private readonly int depth;

        IList<EventBean> events;
        IList<View> childViews;

        public SupportStreamImpl(Type clazz, int depth)
        {
            this.eventType = SupportEventTypeFactory.createBeanType(clazz);
            this.depth = depth;

            this.events = new List<EventBean>();
            this.childViews = new List<View>();
        }

        /// <summary> Insert a single event to the stream</summary>
        /// <param name="event">
        /// </param>
        public virtual void Insert(EventBean _event)
        {
            events.Add(_event);

            EventBean[] oldEvents = null;
            if (events.Count > depth)
            {
                EventBean _oldEvent = events[0];
                events.RemoveAt(0);

                oldEvents = new EventBean[] { _oldEvent } ;
            }

            foreach (View child in childViews)
            {
                child.Update(new EventBean[] { _event }, oldEvents);
            }
        }

        /// <summary> Insert a bunch of events to the stream</summary>
        /// <param name="eventArray">
        /// </param>
        public virtual void Insert(EventBean[] eventArray)
        {
            foreach (EventBean _event in eventArray)
            {
                events.Add(_event);
            }

            EventBean[] oldEvents = null;
            int expiredCount = events.Count - depth;
            if (expiredCount > 0)
            {
                oldEvents = new EventBean[expiredCount];
                for (int i = 0; i < expiredCount; i++)
                {
                    oldEvents[i] = events[0];
                    events.RemoveAt(0);
                }
            }

            foreach (View child in childViews)
            {
                child.Update(eventArray, oldEvents);
            }
        }

        public virtual Object this[long index]
        {
            get
            {
                if ((index > Int32.MaxValue) || (index < Int32.MinValue))
                {
                    throw new ArgumentOutOfRangeException("Index not within int range supported by this implementation");
                }
             
                return events[(int) index];
            }
        }

        public virtual IEnumerator<EventBean> GetEnumerator()
        {
            log.Info(".iterator Not yet implemented");
            return null;
        }


        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            log.Info(".iterator Not yet implemented");
            return null;
        }

        #endregion

        public virtual View AddView(View view)
        {
            childViews.Add(view);
            view.Parent = this;
            return view;
        }

        public IList<View> GetViews()
        {
            return childViews;
        }

        public virtual bool RemoveView(View view)
        {
            bool isRemoved = childViews.Remove(view);
            view.Parent = null;
            return isRemoved;
        }

        public virtual bool HasViews
        {
            get { return (childViews.Count > 0); }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(SupportStreamImpl));
    }
}
