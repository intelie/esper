using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
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

    public sealed class MergeView : ViewSupport, ParentAwareView, ContextAwareView
    {
        /// <summary>
        /// Gets or sets the field name that contains the values to group by.
        /// </summary>
        /// <value>The group field names.</value>

        public String[] GroupFieldNames
        {
            get { return groupFieldNames; }
            set { this.groupFieldNames = value; }
        }

        /// <summary>
        /// Gets or sets the context instances used by the view.
        /// </summary>
        /// <value>The view service context.</value>
        public ViewServiceContext ViewServiceContext
        {
            get { return viewServiceContext; }
            set { this.viewServiceContext = value; }
        }

        /// <summary>
        /// Returns types of fields used in the group-by.
        /// </summary>
        /// <value>The group field types.</value>

        public Type[] GroupFieldTypes
        {
            get { return groupFieldTypes; }
        }

        /// <summary>
        /// Sets types of fields used in the group-by.
        /// </summary>
        /// <value>The type of the group field.</value>

        public Type[] GroupFieldType
        {
            set { this.groupFieldTypes = value; }
        }

        private readonly ELinkedList<View> parentViews = new ELinkedList<View>();
        private String[] groupFieldNames;
        private Type[] groupFieldTypes;
        private EventType eventType;
        private ViewServiceContext viewServiceContext;

        /// <summary>
        /// Initializes a new instance of the <see cref="MergeView"/> class.
        /// </summary>
        public MergeView()
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="groupFieldName">is the field from which to pull the value to group by
        /// </param>

        public MergeView(String groupFieldName)
        {
            this.groupFieldNames = new String[] { groupFieldName };
        }

        /// <summary> Constructor.</summary>
        /// <param name="groupFieldNames">is the fields from which to pull the value to group by
        /// </param>

        public MergeView(String[] groupFieldNames)
        {
            this.groupFieldNames = groupFieldNames;
        }

        /// <summary> Add a parent data merge view.</summary>
        /// <param name="parentView">is the parent data merge view to add
        /// </param>

        public void AddParentView(AddPropertyValueView parentView)
        {
            parentViews.Add(parentView);
        }

        /// <summary>
        /// Return null if the view will accept being attached to a particular object.
        /// </summary>
        /// <param name="parentView">is the potential parent for this view</param>
        /// <returns>
        /// null if this view can successfully attach to the parent, an error message if it cannot.
        /// </returns>
        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to just about anything
            return null;
        }

        /// <summary>
        /// Sets a flag indicating that the view must couple to parent views.
        /// </summary>
        /// <value></value>
        public IList<View> ParentAware
        {
            set
            {
                // Find the group by view matching the merge view
                View groupByView = null;
                foreach (View parentView in value)
                {
                    if (!(parentView is GroupByView))
                    {
                        continue;
                    }
                    GroupByView candidateGroupByView = (GroupByView)parentView;
                    if (ArrayHelper.AreEqual(candidateGroupByView.GroupFieldNames, this.GroupFieldNames))
                    {
                        groupByView = candidateGroupByView;
                    }
                }

                if (groupByView == null)
                {
                    throw new SystemException("Group by view for this merge view could not be found among parent views");
                }

                groupFieldTypes = new Type[groupFieldNames.Length];
                for (int i = 0; i < groupFieldTypes.Length; i++)
                {
                    groupFieldTypes[i] = groupByView.EventType.GetPropertyType(groupFieldNames[i]);
                }
                eventType = viewServiceContext.EventAdapterService.CreateAddToEventType(this.Parent.EventType, groupFieldNames, groupFieldTypes);
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
            get
            {
                // The schema is the parent view's schema plus the added field
                return eventType;
            }
            set
            {
                this.eventType = value;
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

        private static readonly Log log = LogFactory.GetLog(typeof(MergeView));
    }
}
