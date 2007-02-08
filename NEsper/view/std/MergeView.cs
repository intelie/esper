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
        /// <summary> Returns the field name that contains the values to group by.</summary>
        /// <returns> field name providing group key value
        /// </returns>
        /// <summary> Sets the field name that contains the values to group by.</summary>
        /// <param name="groupFieldNames">is the field names providing group key values
        /// </param>

        public String[] GroupFieldNames
        {
            get { return groupFieldNames; }
            set { this.groupFieldNames = value; }
        }

        public ViewServiceContext ViewServiceContext
        {
            get { return viewServiceContext; }
            set { this.viewServiceContext = value; }
        }

        /// <summary> Returns types of fields used in the group-by.</summary>
        /// <returns> types for group-by fields
        /// </returns>

        public Type[] GroupFieldTypes
        {
            get { return groupFieldTypes; }
        }

        /// <summary> Sets types of fields used in the group-by.</summary>
        /// <param name="groupFieldTypes">- types for group-by fields
        /// </param>

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
        /// Default constructor - required by all views to adhere to the Java bean specification.
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

        /// <summary> Sets event type - required for successful view copy.</summary>
        /// <param name="eventType">is the event type
        /// </param>

        public void SetEventType(EventType eventType)
        {
            this.eventType = eventType;
        }

        /// <summary> Add a parent data merge view.</summary>
        /// <param name="parentView">is the parent data merge view to add
        /// </param>

        public void addParentView(AddPropertyValueView parentView)
        {
            parentViews.Add(parentView);
        }

        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to just about anything
            return null;
        }

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
                    if (ArrayHelper.AreEqual(candidateGroupByView.getGroupFieldNames(), this.GroupFieldNames))
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

        public override EventType EventType
        {
            get
            {
                // The schema is the parent view's schema plus the added field
                return eventType;
            }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".update Updating view");
                dumpUpdateParams("MergeView", newData, oldData);
            }

            updateChildren(newData, oldData);
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            // The merge data view has multiple parent views which are AddPropertyValueView
            IList<IEnumerable<EventBean>> iterables = new ELinkedList<IEnumerable<EventBean>>();

            foreach (View dataView in parentViews)
            {
                iterables.Add(dataView);
            }

            return new IterablesListIterator(iterables);
        }

        public override String ToString()
        {
            return this.GetType().FullName + " groupFieldName=" + CollectionHelper.Render(groupFieldNames);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(MergeView));
    }
}
