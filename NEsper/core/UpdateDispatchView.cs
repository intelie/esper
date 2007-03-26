using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.dispatch;
using net.esper.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.core
{
    /// <summary>
    /// Convenience view for dispatching view updates received from a parent view to update listeners
    /// via the dispatch service.
    /// </summary>

    public class UpdateDispatchView : ViewSupport, Dispatchable
    {
        private readonly ISet<UpdateListener> updateListeners;
        private readonly DispatchService dispatchService;
        private bool isDispatchWaiting;

        private List<EventBean[]> lastNewEvents = new List<EventBean[]>();
        private List<EventBean[]> lastOldEvents = new List<EventBean[]>();

        /// <summary> Ctor.</summary>
        /// <param name="updateListeners">listeners to update
        /// </param>
        /// <param name="dispatchService">for performing the dispatch
        /// </param>

        public UpdateDispatchView(ISet<UpdateListener> updateListeners, DispatchService dispatchService)
        {
            this.updateListeners = updateListeners;
            this.dispatchService = dispatchService;
        }

        /// <summary>
        /// Return null if the view will accept being attached to a particular object.
        /// </summary>
        /// <param name="parentViewable">is the potential parent for this view</param>
        /// <returns>
        /// null if this view can successfully attach to the parent, an error message if it cannot.
        /// </returns>
        public override String AttachesTo(Viewable parentViewable)
        {
            return null;
        }

        /// <summary>
        /// Notify that data has been added or removed from the Viewable parent.
        /// The last object in the newData array of objects would be the newest object added to the parent view.
        /// The first object of the oldData array of objects would be the oldest object removed from the parent view.
        /// If the call to update contains new (inserted) data, then the first argument will be a non-empty list and the
        /// second will be empty. Similarly, if the call is a notification of deleted data, then the first argument will be
        /// empty and the second will be non-empty. Either the newData or oldData will be non-null.
        /// This method won't be called with both arguments being null, but either one could be null.
        /// The same is true for zero-length arrays. Either newData or oldData will be non-empty.
        /// If both are non-empty, then the update is a modification notification.
        /// When update() is called on a view by the parent object, the data in newData will be in the collection of the
        /// parent, and its data structures will be arranged to reflect that.
        /// The data in oldData will not be in the parent's data structures, and any access to the parent will indicate that
        /// that data is no longer there.
        /// </summary>
        /// <param name="newData">is the new data that has been added to the parent view</param>
        /// <param name="oldData">is the old data that has been removed from the parent view</param>
        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (log.IsDebugEnabled)
            {
                ViewSupport.dumpUpdateParams(".update for view " + this, newData, oldData);
            }
            if (newData != null)
            {
                lastNewEvents.Add(newData);
            }
            if (oldData != null)
            {
                lastOldEvents.Add(oldData);
            }
            if (!isDispatchWaiting)
            {
                dispatchService.AddExternal(this);
                isDispatchWaiting = true;
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
            get { return null; }
            set { }
        }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
        {
            return null;
        }

        /// <summary>
        /// Execute dispatch.
        /// </summary>
        public virtual void Execute()
        {
            isDispatchWaiting = false;
            EventBean[] newEvents = EventBeanUtility.Flatten(lastNewEvents);
            EventBean[] oldEvents = EventBeanUtility.Flatten(lastOldEvents);

            if (log.IsDebugEnabled)
            {
                ViewSupport.dumpUpdateParams(".Execute", newEvents, oldEvents);
            }

            foreach (UpdateListener listener in updateListeners)
            {
                listener(newEvents, oldEvents);
            }

            lastNewEvents.Clear();
            lastOldEvents.Clear();
        }

        private static Log log = LogFactory.GetLog(typeof(UpdateDispatchView));
    }
}
