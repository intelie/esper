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
        }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
        {
            return parent.GetEnumerator();
        }
    }
}
