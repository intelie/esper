using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.std
{
    /// <summary> This view is a very simple view presenting the last event posted by the parent view to any subviews.
    /// Only the very last event object is kept by this view. The update method invoked by the parent view supplies
    /// new data in an object array, of which the view keeps the very last instance as the 'last' or newest event.
    /// The view always has the same schema as the parent view and attaches to anything, and accepts no parameters.
    /// 
    /// Useful is the last view for example for "stocks.time_window(100).last()".
    /// 
    /// Notice that "stocks.last().Count" and
    /// "stocks.win:length(10).std:lastevent().std:size()" must always return 0 or 1.
    /// 
    /// Thus if 5 pieces of new data arrive, the child view receives 5 elements of new data
    /// and also 4 pieces of old data which is the first 4 elements of new data.
    /// I.e. New data elements immediatly gets to be old data elements.
    /// 
    /// Old data received from parent is not handled, it is ignored.
    /// We thus post old data as follows:
    /// last event is not null +
    /// new data from index zero to N-1, where N is the index of the last element in new data
    /// </summary>

    public class LastElementView
		: ViewSupport
		, CloneableView
    {
        /// <summary> The last new element posted from a parent view.</summary>
        internal EventBean lastEvent;

	    public View CloneView(StatementContext context)
	    {
	        return new LastElementView();
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
            get
            {
                // The schema is the parent view's schema
                return parent.EventType;
            }
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
            List<EventBean> oldDataToPost = new List<EventBean>();

            if ((newData != null) && (newData.Length != 0))
            {
                if (lastEvent != null)
                {
                    oldDataToPost.Add(lastEvent);
                }
                if (newData.Length > 1)
                {
                    for (int i = 0; i < newData.Length - 1; i++)
                    {
                        oldDataToPost.Add(newData[i]);
                    }
                }
                lastEvent = newData[newData.Length - 1];
            }

            // If there are child views, fireStatementStopped update method
            if (this.HasViews)
            {
                if (oldDataToPost.Count != 0)
                {
                	UpdateChildren(newData, oldDataToPost.ToArray());
                }
                else
                {
                    UpdateChildren(newData, null);
                }
            }
        }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
        {
            yield return lastEvent;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return this.GetType().FullName;
        }
    }
}
