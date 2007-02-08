using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;
using net.esper.compat;
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

    public sealed class GroupByView : ViewSupport, ContextAwareView
    {
        public ViewServiceContext ViewServiceContext
        {
            get { return viewServiceContext; }
            set { this.viewServiceContext = value; }
        }

        private String[] groupFieldNames;
        private EventPropertyGetter[] groupFieldGetters;
        private ViewServiceContext viewServiceContext;

        private readonly EDictionary<MultiKey<Object>, IList<View>> subViewsPerKey = new EHashDictionary<MultiKey<Object>, IList<View>>();

        private EDictionary<IList<View>, Pair<IList<EventBean>, IList<EventBean>>> groupedEvents = new EHashDictionary<IList<View>, Pair<IList<EventBean>, IList<EventBean>>>();

        /// <summary>
        /// Default constructor - required by all views to adhere to the Java bean specification.
        /// </summary>

        public GroupByView()
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="groupFieldName">is the field from which to pull the value to group by
        /// </param>
        public GroupByView(String groupFieldName)
        {
            this.groupFieldNames = new String[] { groupFieldName };
        }

        /// <summary> Constructor.</summary>
        /// <param name="groupFieldNames">is the fields from which to pull the values to group by
        /// </param>

        public GroupByView(String[] groupFieldNames)
        {
            this.groupFieldNames = groupFieldNames;
        }

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

        /// <summary> Returns the field name that provides the key valie by which to group by.</summary>
        /// <returns> field name providing group-by key.
        /// </returns>

        public String[] getGroupFieldNames()
        {
            return groupFieldNames;
        }

        /// <summary> Sets the field name that provides the key valie by which to group by.</summary>
        /// <param name="groupFieldNames">the the field names providing the group-by key values.
        /// </param>
        public void setGroupFieldNames(String[] groupFieldNames)
        {
            this.groupFieldNames = groupFieldNames;
        }

        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to just about anything as long as all the fields exists
            for (int i = 0; i < groupFieldNames.Length; i++)
            {
                String message = PropertyCheckHelper.exists(parentView.EventType, groupFieldNames[i]);
                if (message != null)
                {
                    return message;
                }
            }
            return null;
        }

        public override EventType EventType
        {
            get
            {
                // The schema is the parent view's schema
                return parent.EventType;
            }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".update Updating view");
                dumpUpdateParams("GroupByView", newData, oldData);
            }

            if (newData != null)
            {
                foreach (EventBean newValue in newData)
                {
                    handleEvent(newValue, true);
                }
            }

            if (oldData != null)
            {
                foreach (EventBean oldValue in oldData)
                {
                    handleEvent(oldValue, false);
                }
            }

            // Update child views
            foreach (KeyValuePair<IList<View>, Pair<IList<EventBean>, IList<EventBean>>> entry in groupedEvents)
            {
                EventBean[] newEvents = EventBeanUtility.ToArray(entry.Value.First);
                EventBean[] oldEvents = EventBeanUtility.ToArray(entry.Value.Second);
                ViewSupport.updateChildren(entry.Key, newEvents, oldEvents);
            }

            groupedEvents.Clear();
        }

        private void handleEvent(EventBean _event, bool isNew)
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
                subViews = makeSubViews(this, groupByValuesKey.Array, viewServiceContext);
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

        public override IEnumerator<EventBean> GetEnumerator()
        {
            throw new NotSupportedException("Cannot iterate over group view, this operation is not supported");
        }

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
        /// <param name="viewServiceContext">is the view services that sub-views may need
        /// </param>
        /// <returns> a list of views that are copies of the original list, with copied children, with
        /// data merge views added to the copied child leaf views.
        /// </returns>
        
        public static IList<View> makeSubViews(GroupByView groupView, Object[] groupByValues, ViewServiceContext viewServiceContext)
        {
            if (!groupView.HasViews())
            {
                String message = "Unexpected empty list of child nodes for group view";
                log.Fatal(".copySubViews " + message);
                throw new EPException(message);
            }

            ELinkedList<View> subViewList = new ELinkedList<View>();

            // For each child node
            foreach (View originalChildView in groupView.GetViews())
            {
                if (originalChildView is MergeView)
                {
                    String message = "Unexpected merge view as child of group-by view";
                    log.Fatal(".copySubViews " + message);
                    throw new EPException(message);
                }

                // Shallow copy child node
                View copyChildView = ViewSupport.shallowCopyView(originalChildView);
                copyChildView.Parent = groupView;
                subViewList.Add(copyChildView);

                // Make the sub views for child copying from the original to the child
                copySubViews(groupView.getGroupFieldNames(), groupByValues, originalChildView, copyChildView, viewServiceContext);
            }

            return subViewList;
        }

        private static void copySubViews(String[] groupFieldNames, Object[] groupByValues, View originalView, View copyView, ViewServiceContext viewServiceContext)
        {
            foreach (View subView in originalView.GetViews())
            {
                // Determine if view is our merge view
                if (subView is MergeView)
                {
                    MergeView mergeView = (MergeView)subView;
                    if (ArrayHelper.AreEqual(mergeView.GroupFieldNames, groupFieldNames))
                    {
                        // We found our merge view - install a new data merge view on top of it
                        AddPropertyValueView mergeDataView = new AddPropertyValueView(groupFieldNames, groupByValues);
                        mergeDataView.ViewServiceContext = viewServiceContext;

                        // Add to the copied parent subview the view merge data view
                        copyView.AddView(mergeDataView);

                        // Add to the new merge data view the actual single merge view instance that clients may attached to
                        mergeDataView.AddView(mergeView);

                        // Add a parent view to the single merge view instance
                        mergeView.addParentView(mergeDataView);

                        continue;
                    }
                }

                View copiedChild = ViewSupport.shallowCopyView(subView);
                copyView.AddView(copiedChild);

                // Make the sub views for child
                copySubViews(groupFieldNames, groupByValues, subView, copiedChild, viewServiceContext);
            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(GroupByView));
    }
}
