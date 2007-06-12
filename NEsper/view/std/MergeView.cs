using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.core;
using net.esper.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.view.std
{
    /// <summary> The merge view works together with a group view that splits the data in a stream to multiple subviews, based on
    /// a key index. Every group view requires a merge view to merge the many subviews back into a single view.
    /// Typically the last view in a chain containing a group view is a merge view.
    /// The merge view has no other responsibility then becoming the single last instance in the chain
    /// to which external listeners for updates can be attached to receive updates for the many subviews
    /// that have this merge view as common child views.
    /// The parent view of this view is generally the AddPropertyValueView that adds the grouped-by information
    /// back into the data.
    /// </summary>

    public sealed class MergeView
		: ViewSupport
		, CloneableView
    {
        /// <summary>
        /// Gets or sets the field name that contains the values to group by.
        /// </summary>
        /// <value>The group field names.</value>

        public String[] GroupFieldNames
        {
            get { return groupFieldNames; }
        }

	    private readonly ELinkedList<View> parentViews = new ELinkedList<View>();
		private readonly String[] groupFieldNames;
		private readonly EventType eventType;
		private readonly StatementContext statementContext;

	   /// <summary>Constructor.</summary>
	   /// <param name="groupFieldNames">
	   /// is the fields from which to pull the value to group by
	   /// </param>
	   /// <param name="resultEventType">
	   /// is passed by the factory as the factory adds the merged fields to an event type
	   /// </param>
	   /// <param name="statementContext">contains required view services</param>
	   public MergeView(StatementContext statementContext, String[] groupFieldNames, EventType resultEventType)
	    {
	        this.groupFieldNames = groupFieldNames;
	        this.eventType = resultEventType;
	        this.statementContext = statementContext;
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
	        return new MergeView(statementContext, groupFieldNames, eventType);
	    }

        /// <summary> Add a parent data merge view.</summary>
        /// <param name="parentView">is the parent data merge view to add
        /// </param>

        public void AddParentView(AddPropertyValueView parentView)
        {
            parentViews.Add(parentView);
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
            get { return eventType ; }
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
            if (log.IsDebugEnabled)
            {
                log.Debug(".update Updating view");
                DumpUpdateParams("MergeView", newData, oldData);
            }

            UpdateChildren(newData, oldData);
        }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
        {
            // The merge data view has multiple parent views which are AddPropertyValueView
            IList<IEnumerable<EventBean>> iterables = new List<IEnumerable<EventBean>>();

            foreach (View dataView in parentViews)
            {
                iterables.Add(dataView);
            }

            return new IterablesListIterator(iterables);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return this.GetType().FullName + " groupFieldName=" + CollectionHelper.Render(groupFieldNames);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
