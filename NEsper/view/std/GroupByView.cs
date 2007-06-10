using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;
using net.esper.compat;
using net.esper.core;
using net.esper.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.view.std
{
    /// <summary> The group view splits the data in a stream to multiple subviews, based on a key index.
    /// The key is one or more fields in the stream. Any view that follows the GROUP view will be executed
    /// separately on each subview, one per unique key.
    ///
    /// The view takes a single parameter which is the field name returning the key value to group.
    ///
    /// This view can, for example, be used to calculate the average price per symbol for a list of symbols.
    ///
    /// The view treats its child views and their child views as prototypes. It dynamically instantiates copies
    /// of each child view and their child views, and the child view's child views as so on. When there are
    /// no more child views or the special merge view is encountered, it ends. The view installs a special merge
    /// view unto each leaf child view that merges the value key that was grouped by back into the stream
    /// using the group-by field name.
    /// </summary>

    public sealed class GroupByView
		: ViewSupport
		, CloneableView
    {
	    private readonly String[] groupFieldNames;
	    private readonly StatementContext statementContext;
	    private EventPropertyGetter[] groupFieldGetters;

        private readonly EDictionary<MultiKey<Object>, IList<View>> subViewsPerKey = new EHashDictionary<MultiKey<Object>, IList<View>>();

        private EDictionary<IList<View>, Pair<IList<EventBean>, IList<EventBean>>> groupedEvents = new EHashDictionary<IList<View>, Pair<IList<EventBean>, IList<EventBean>>>();

		/// <summary>Constructor.</summary>
		/// <param name="groupFieldNames">
		/// is the fields from which to pull the values to group by
		/// </param>
		/// <param name="statementContext">contains required view services</param>
	    public GroupByView(StatementContext statementContext, String[] groupFieldNames)
	    {
	        this.statementContext = statementContext;
	        this.groupFieldNames = groupFieldNames;
	    }

	    public View CloneView(StatementContext statementContext)
	    {
	        return new GroupByView(statementContext, groupFieldNames);
	    }

        /// <summary>
        /// Gets or sets the View's parent Viewable.
        /// </summary>
        /// <value></value>
        /// <returns> viewable
        /// </returns>
        public override Viewable Parent
        {
            set
            {
                Viewable parent = value;

                base.Parent = value;
                if (parent == null)
                // Since we may dis-associate view
                {
                    return;
                }

                groupFieldGetters = new EventPropertyGetter[groupFieldNames.Length];
                for (int i = 0; i < groupFieldNames.Length; i++)
                {
                    groupFieldGetters[i] = parent.EventType.GetGetter(groupFieldNames[i]);
                }
            }
        }

        /// <summary>
        /// Gets or sets the field name that provides the key valie by which to group by.
        /// </summary>

        public String[] GroupFieldNames
        {
            get { return groupFieldNames; }
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
            if (log.IsDebugEnabled)
            {
                log.Debug(".update Updating view");
                DumpUpdateParams("GroupByView", newData, oldData);
            }

            if (newData != null)
            {
                foreach (EventBean newValue in newData)
                {
                    HandleEvent(newValue, true);
                }
            }

            if (oldData != null)
            {
                foreach (EventBean oldValue in oldData)
                {
                    HandleEvent(oldValue, false);
                }
            }

            // Update child views
            foreach (KeyValuePair<IList<View>, Pair<IList<EventBean>, IList<EventBean>>> entry in groupedEvents)
            {
                EventBean[] newEvents = EventBeanUtility.ToArray(entry.Value.First);
                EventBean[] oldEvents = EventBeanUtility.ToArray(entry.Value.Second);
                ViewSupport.UpdateChildren(entry.Key, newEvents, oldEvents);
            }

            groupedEvents.Clear();
        }

        private void HandleEvent(EventBean _event, bool isNew)
        {
            // Get values for group-by, construct MultiKey
            Object[] groupByValues = new Object[groupFieldGetters.Length];
            for (int i = 0; i < groupFieldGetters.Length; i++)
            {
            	groupByValues[i] = groupFieldGetters[i].GetValue(_event);
            }
            MultiKey<Object> groupByValuesKey = new MultiKey<Object>(groupByValues);

            // Get child views that belong to this group-by value combination
            IList<View> subViews = subViewsPerKey.Fetch(groupByValuesKey);

            // If this is a new group-by value, the list of subviews is null and we need to make clone sub-views
            if ( subViews == null )
            {
                subViews = MakeSubViews(this, groupByValuesKey.Array, statementContext);
                subViewsPerKey[groupByValuesKey] = subViews;
            }

            // Construct a pair of lists to hold the events for the grouped value if not already there
            Pair<IList<EventBean>, IList<EventBean>> pair = groupedEvents.Fetch(subViews) ;
            if ( pair == null )
            {
                ELinkedList<EventBean> listNew = new ELinkedList<EventBean>();
                ELinkedList<EventBean> listOld = new ELinkedList<EventBean>();
                pair = new Pair<IList<EventBean>, IList<EventBean>>(listNew, listOld);
                groupedEvents[subViews] = pair;
            }

            // Add event to a child view event list for later child update that includes new and old events
            if (isNew)
            {
                pair.First.Add(_event);
            }
            else
            {
                pair.Second.Add(_event);
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
            throw new NotSupportedException("Cannot iterate over group view, this operation is not supported");
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return this.GetType().FullName + " groupFieldNames=" + CollectionHelper.Render(groupFieldNames);
        }

        /// <summary> Instantiate subviews for the given group view and the given key value to group-by.
        /// Makes shallow copies of each child view and its subviews up to the merge point.
        /// Sets up merge data views for merging the group-by key value back in.
        /// </summary>
        /// <param name="groupView">is the parent view for which to copy subviews for
        /// </param>
        /// <param name="groupByValues">is the key values to group-by
        /// </param>
        /// <param name="statementContext">is the view services that sub-views may need
        /// </param>
        /// <returns> a list of views that are copies of the original list, with copied children, with
        /// data merge views added to the copied child leaf views.
        /// </returns>

        public static IList<View> MakeSubViews(GroupByView groupView, Object[] groupByValues, StatementContext statementContext)
        {
            if (!groupView.HasViews)
            {
                String message = "Unexpected empty list of child nodes for group view";
                log.Fatal(".MakeSubViews " + message);
                throw new EPException(message);
            }

            ELinkedList<View> subViewList = new ELinkedList<View>();

            // For each child node
            foreach (View originalChildView in groupView.Views)
            {
                if (originalChildView is MergeView)
                {
                    String message = "Unexpected merge view as child of group-by view";
                    log.Fatal(".CopySubViews " + message);
                    throw new EPException(message);
                }


				CloneableView cloneableView = originalChildView as CloneableView;
				if ( cloneableView == null )
				{
					throw new EPException("Unexpected error copying subview " + originalChildView.getClass().getName());
				}

				// Copy child node
				View copyChildView = cloneableView.cloneView(statementContext);
                copyChildView.Parent = groupView;
                subViewList.Add(copyChildView);

                // Make the sub views for child copying from the original to the child
                CopySubViews(groupView.GroupFieldNames, groupByValues, originalChildView, copyChildView, statementContext);
            }

            return subViewList;
        }

        private static void CopySubViews(String[] groupFieldNames, Object[] groupByValues, View originalView, View copyView, StatementContext statementContext)
        {
            foreach (View subView in originalView.Views)
            {
                // Determine if view is our merge view
                if (subView is MergeView)
                {
                    MergeView mergeView = (MergeView)subView;
                    if (ArrayHelper.AreEqual(mergeView.GroupFieldNames, groupFieldNames))
                    {
                        // We found our merge view - install a new data merge view on top of it
						AddPropertyValueView mergeDataView = new AddPropertyValueView(statementContext, groupFieldNames, groupByValues, mergeView.EventType);

                        // Add to the copied parent subview the view merge data view
                        copyView.AddView(mergeDataView);

                        // Add to the new merge data view the actual single merge view instance that clients may attached to
                        mergeDataView.AddView(mergeView);

                        // Add a parent view to the single merge view instance
                        mergeView.AddParentView(mergeDataView);

                        continue;
                    }
                }

	            CloneableView cloneableView = subView as CloneableView;
				if (cloneableView == null)
	            {
	                throw new EPException("Unexpected error copying subview");
	            }

	            View copiedChild = cloneableView.cloneView(statementContext);
                copyView.AddView(copiedChild);

                // Make the sub views for child
                CopySubViews(groupFieldNames, groupByValues, subView, copiedChild, statementContext);
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
