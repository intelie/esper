using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.core;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.window
{
    /// <summary>
    /// This view is a moving window extending the specified number of elements into the past.
    /// </summary>

    public sealed class LengthWindowView
		: ViewSupport
		, DataWindowView
		, CloneableView
    {
		private readonly LengthWindowViewFactory lengthWindowViewFactory;
	    private readonly int size;
	    private readonly ViewUpdatedCollection viewUpdatedCollection;
	    private readonly ELinkedList<EventBean> events = new ELinkedList<EventBean>();

        /// <summary>
        /// Gets or sets the size of the length window.
        /// </summary>
        /// <value>The size.</value>
        /// <returns> size of length window
        /// </returns>
        public int Size
        {
            get { return size; }
        }

	    /// <summary>
	    /// Constructor creates a moving window extending the specified number of elements into the past.
	    /// </summary>
	    /// <param name="size">is the specified number of elements into the past</param>
	    /// <param name="viewUpdatedCollection">
	    /// is a collection that the view must update when receiving events
	    /// </param>
	    /// <param name="lengthWindowViewFactory">for copying this view in a group-by</param>
	    public LengthWindowView(LengthWindowViewFactory lengthWindowViewFactory, int size, ViewUpdatedCollection viewUpdatedCollection)
	    {
	        if (size < 1)
	        {
	            throw new ArgumentException("Illegal argument for size of length window");
	        }

	        this.lengthWindowViewFactory = lengthWindowViewFactory;
	        this.size = size;
	        this.viewUpdatedCollection = viewUpdatedCollection;
	    }

        /// <summary>
        /// Duplicates the view.
        /// <p>
        /// Expected to return a same view in initialized state for grouping.
        /// </p>
        /// </summary>
        /// <param name="statementContext">is services for the view</param>
        /// <returns>duplicated view</returns>
	    public View CloneView(StatementContext statementContext)
	    {
	        return lengthWindowViewFactory.MakeView(statementContext);
	    }

	    /// <summary>Returns true if the window is empty, or false if not empty.</summary>
	    /// <returns>true if empty</returns>
	    public bool IsEmpty
	    {
	        get { return events.IsEmpty; }
	    }

		/// <summary>
		/// Returns the (optional) collection handling random access to window contents for prior or previous events.
		/// </summary>
		/// <returns>buffer for events</returns>
	    public ViewUpdatedCollection ViewUpdatedCollection
	    {
	        get { return viewUpdatedCollection; }
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
                // The event type is the parent view's event type
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
            // add data points to the window
            // we don't care about removed data from a prior view
            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                    events.Add(newData[i]);
                }
            }

            // Check for any events that get pushed out of the window
            int expiredCount = events.Count - size;
            EventBean[] expiredArr = null;
            if (expiredCount > 0)
            {
                expiredArr = new EventBean[expiredCount];
                for (int i = 0; i < expiredCount; i++)
                {
                    expiredArr[i] = events.RemoveFirst();
                }
            }

	        // If there are child views, fireStatementStopped update method
	        if (viewUpdatedCollection != null)
	        {
	            viewUpdatedCollection.Update(newData, oldData);
	        }
			if (this.HasViews)
            {
                UpdateChildren(newData, expiredArr);
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
            return events.GetEnumerator();
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return this.GetType().FullName + " size=" + size;
        }
    }
}
