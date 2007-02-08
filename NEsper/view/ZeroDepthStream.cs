using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

using LogFactory = org.apache.commons.logging.LogFactory;
using Log = org.apache.commons.logging.Log;

namespace net.esper.view
{
	/// <summary>
    /// Event stream implementation that does not keep any window by itself of the events
    /// coming into the stream.
    /// </summary>
    
    public sealed class ZeroDepthStream : EventStream
    {
        private readonly ELinkedList<View> children = new ELinkedList<View>();
        private readonly EventType eventType;

        /**
         * Ctor.
         * @param eventType - type of event
         */
        public ZeroDepthStream(EventType eventType)
        {
            this.eventType = eventType;
        }

        public void Insert(EventBean ev)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".insert Received event, updating child views, event=" + ev);
            }

            int size = children.Count;

            EventBean[] events = new EventBean[] { ev };

            if (size == 1)
            {
                children.First.Update(events, null);
            }
            else
            {
                foreach (View child in children)
                {
                    child.Update(events, null);
                }
            }
        }

        public EventType EventType
        {
            get { return eventType; }
        }

        public IEnumerator<EventBean> GetEnumerator()
        {
            return null;
        }

        public View AddView(View view)
        {
            children.Add(view);
            view.Parent = this;
            return view;
        }

        public IList<View> GetViews()
        {
            return children;
        }

        public Boolean RemoveView(View view)
        {
            Boolean isRemoved = children.Remove(view);
            view.Parent = null;
            return isRemoved;
        }

        public Boolean HasViews()
        {
            return (children.Count > 0);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(ZeroDepthStream));

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return this.GetEnumerator();
        }

        #endregion
    }
}