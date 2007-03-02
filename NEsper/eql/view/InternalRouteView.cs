using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.view;
using net.esper.events;

namespace net.esper.eql.view
{
	/// <summary> View for internally routing events which is commenly the last step in execution of a statement
	/// in which an insert-into clause has been specified.
	/// </summary>

    public class InternalRouteView : ViewSupport
    {
        // Do we route the insert stream (new) events, or the remove stream (old) events
        private readonly bool isStream;
        private readonly InternalEventRouter internalEventRouter;

        /// <summary> Ctor.</summary>
        /// <param name="isStream">true for insert stream, false for remove stream
        /// </param>
        /// <param name="internalEventRouter">routes the events internally
        /// </param>
        public InternalRouteView(bool isStream, InternalEventRouter internalEventRouter)
        {
            this.isStream = isStream;
            this.internalEventRouter = internalEventRouter;
        }

        public override String AttachesTo(Viewable parentViewable)
        {
            return null;
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if ((newData != null) && (isStream))
            {
                Route(newData);
            }

            if ((oldData != null) && (!isStream))
            {
                Route(oldData);
            }

            this.UpdateChildren(newData, oldData);
        }

        private void Route(EventBean[] events)
        {
            for (int i = 0; i < events.Length; i++)
            {
                internalEventRouter.Route(events[i]);
            }
        }

        public override EventType EventType
        {
            get { return parent.EventType; }
            set { }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return parent.GetEnumerator();
        }
    }
}
