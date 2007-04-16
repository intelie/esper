using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;
using net.esper.util;
using net.esper.view;
using net.esper.view.window;

using org.apache.commons.logging;

namespace net.esper.view.ext
{
    /// <summary> Window sorting by values in the specified field extending a specified number of elements
    /// from the lowest value up or the highest value down.
    /// The view accepts 3 parameters. The first parameter is the field name to get the values to sort for,
    /// the second parameter defines whether to sort ascending or descending, the third parameter
    /// is the number of elements to keep in the sorted list.
    /// 
    /// The type of the field to be sorted in the event must implement the Comparable interface.
    /// 
    /// The natural order in which events arrived is used as the second sorting criteria. Thus should events arrive
    /// with equal sort values the oldest event leaves the sort window first.
    /// 
    /// Old values removed from a prior view are removed from the sort view.
    /// </summary>

    public sealed class SortWindowView : ViewSupport, DataWindowView
    {
        private String[] sortFieldNames;
        private EventPropertyGetter[] sortFieldGetters;
        private Boolean[] isDescendingValues;
        private int sortWindowSize = 0;

        private ETreeDictionary<MultiKey<Object>, LinkedList<EventBean>> sortedEvents;
        private int eventCount;

        /// <summary> Gets or sets the field names supplying the values to sort by.</summary>
        /// <returns> field names to sort by
        /// </returns>

        public String[] SortFieldNames
        {
            get { return sortFieldNames; }
            set { this.sortFieldNames = value; }
        }

        /// <summary> Gets or sets the flags indicating whether to sort in descending order on each property.</summary>
        /// <returns> the isDescending value for each sort property
        /// </returns>

        public Boolean[] IsDescendingValues
        {
            get { return isDescendingValues; }
            set { this.isDescendingValues = value; }
        }

        /// <summary> Gets or sets the number of elements kept by the sort window.</summary>
        /// <returns> size of window
        /// </returns>

        public int SortWindowSize
        {
            get { return sortWindowSize; }
            set { this.sortWindowSize = value; }
        }

        /// <summary>
        /// Sets the names and is descending values.
        /// </summary>
        /// <value>The names and is descending values.</value>
        private Object[] NamesAndIsDescendingValues
        {
            set
            {
                if (value.Length % 2 != 0)
                {
                    throw new ArgumentException("Each property to sort by must have an isDescending boolean qualifier");
                }

                int length = value.Length / 2;
                sortFieldNames = new String[length];
                isDescendingValues = new Boolean[length];

                for (int i = 0; i < length; i++)
                {
                    sortFieldNames[i] = ((String)value[2 * i]);
                    isDescendingValues[i] = (Boolean)value[2 * i + 1];
                }
            }

        }


        /// <summary>
        /// Initializes a new instance of the <see cref="SortWindowView"/> class.
        /// </summary>
        public SortWindowView()
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="propertiesAndDirections">an array of the form [String, Boolean, ...],
        /// where each String represents a property name, and each Boolean indicates 
        /// whether to sort in descending order on that property
        /// </param>
        /// <param name="size">is the specified number of elements to keep in the sort
        /// </param>
        public SortWindowView(Object[] propertiesAndDirections, int size)
        {
            if (propertiesAndDirections == null || propertiesAndDirections.Length < 2)
            {
                throw new ArgumentException("The sort view must sort on at least one property");
            }

            if ((size < 1) || (size > Int32.MaxValue))
            {
                throw new ArgumentException("Illegal argument for sortWindowSize of length window");
            }

            NamesAndIsDescendingValues = propertiesAndDirections;
            this.sortWindowSize = size;

            IComparer<MultiKey<Object>> comparator = new MultiKeyComparator<Object>(isDescendingValues);
            sortedEvents = new ETreeDictionary<MultiKey<Object>, LinkedList<EventBean>>(comparator);
        }

        /// <summary> Ctor.</summary>
        /// <param name="propertyName">the property to sort on
        /// </param>
        /// <param name="isDescending">true if the property should be sorted in descending order
        /// </param>
        /// <param name="size">the number of elements to keep in the sort
        /// </param>

        public SortWindowView(String propertyName, bool isDescending, int size)
            : this(new Object[] { propertyName, isDescending }, size)
        {
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
                if (parent != null)
                {
                    int count = 0;
                    sortFieldGetters = new EventPropertyGetter[sortFieldNames.Length];
                    foreach (String name in sortFieldNames)
                    {
                        sortFieldGetters[count++] = parent.EventType.GetGetter(name);
                    }
                }
            }
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
            // Attaches to parent views where the sort fields exist and implement Comparable
            String result = null;
            foreach (String name in sortFieldNames)
            {
                result = PropertyCheckHelper.Exists(parentView.EventType, name);

                if (result != null)
                {
                    break;
                }
            }
            return result;
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
            set { }
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
                DumpUpdateParams("SortWindowView", newData, oldData);
            }

            List<EventBean> removedEvents = new List<EventBean>() ;

            // Remove old data
            if (oldData != null)
            {
                for (int i = 0; i < oldData.Length; i++)
                {
                    MultiKey<Object> sortValues = GetSortValues(oldData[i]);
                    bool result = Remove(sortValues, oldData[i]);
                    if (result)
                    {
                        eventCount--;
                        removedEvents.Add(oldData[i]);
                    }
                }
            }

            // Add new data
            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                    MultiKey<Object> sortValues = GetSortValues(newData[i]);
                    Add(sortValues, newData[i]);
                    eventCount++;
                }
            }

            // Remove data that sorts to the bottom of the window
            if (eventCount > sortWindowSize)
            {
                int removeCount = eventCount - sortWindowSize;
                for (int i = 0; i < removeCount; i++)
                {
                    // Remove the last element of the last key - sort order is key and then natural order of arrival
                    MultiKey<Object> lastKey = sortedEvents.LastKey;
                    LinkedList<EventBean> events = sortedEvents[lastKey];
                    LinkedListNode<EventBean> lastNode = events.Last;
                    EventBean _event = lastNode.Value;
                    events.Remove(lastNode);
                    eventCount--;

                    // Clear out entry if not used
                    if (events.Count == 0)
                    {
                        sortedEvents.Remove(lastKey);
                    }

                    removedEvents.Add(_event);

                    if (log.IsDebugEnabled)
                    {
                        log.Debug(".update Pushing out event event=" + _event);
                    }
                }
            }

            // If there are child views, fire update method
            if (this.HasViews)
            {
                EventBean[] expiredArr = null;
                if (removedEvents.Count > 0)
                {
                    expiredArr = removedEvents.ToArray();
                }

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
            return new SortWindowIterator(sortedEvents);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return this.GetType().FullName + " sortFieldName=" + sortFieldNames + " isDescending=" + isDescendingValues + " sortWindowSize=" + sortWindowSize;
        }

        private void Add(MultiKey<Object> key, EventBean bean)
        {
            LinkedList<EventBean> listOfBeans;
            if (sortedEvents.TryGetValue(key, out listOfBeans))
            {
                listOfBeans.AddFirst(bean); // Add to the front of the list as the second sort critertial is ascending arrival order
                return;
            }

            listOfBeans = new LinkedList<EventBean>();
            listOfBeans.AddFirst(bean);
            sortedEvents[key] = listOfBeans;
        }

        private bool Remove(MultiKey<Object> key, EventBean bean)
        {
            LinkedList<EventBean> listOfBeans;
            if (!sortedEvents.TryGetValue(key, out listOfBeans))
            {
                return false;
            }

            bool result = listOfBeans.Remove(bean);
            if (listOfBeans.Count == 0)
            {
                sortedEvents.Remove(key);
            }
            return result;
        }

        private MultiKey<Object> GetSortValues(EventBean ev)
        {
            Object[] result = new Object[sortFieldGetters.Length];
            int count = 0;
            foreach (EventPropertyGetter getter in sortFieldGetters)
            {
                result[count++] = getter.GetValue(ev);
            }

            return new MultiKey<Object>(result);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
