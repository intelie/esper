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

        private ETreeDictionary<MultiKey<Object>, ELinkedList<EventBean>> sortedEvents;
        private int eventCount;

        /// <summary> Returns the field names supplying the values to sort by.</summary>
        /// <returns> field names to sort by
        /// </returns>
        /// <summary> Set the names of the properties to sort on.</summary>
        /// <param name="sortFieldNames">- the names of the properties to sort on
        /// </param>

        public String[] SortFieldNames
        {
            get { return sortFieldNames; }
            set { this.sortFieldNames = value; }
        }

        /// <summary> Returns the flags indicating whether to sort in descending order on each property.</summary>
        /// <returns> the isDescending value for each sort property
        /// </returns>
        /// <summary> Set the sort order for the sort properties.</summary>
        /// <param name="isDescendingValues">- the direction to sort in for each sort property
        /// </param>

        public Boolean[] IsDescendingValues
        {
            get { return isDescendingValues; }
            set { this.isDescendingValues = value; }
        }

        /// <summary> Returns the number of elements kept by the sort window.</summary>
        /// <returns> size of window
        /// </returns>
        /// <summary> Set the number of elements kept by the sort window.</summary>
        /// <param name="sortWindowSize">- size of window
        /// </param>

        public int SortWindowSize
        {
            get { return sortWindowSize; }
            set { this.sortWindowSize = value; }
        }

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
        /// Default constructor - required by all views to adhere to the Java bean specification.
        /// </summary>

        public SortWindowView()
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="propertiesAndDirections">- an array of the form [String, Boolean, ...],
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
            sortedEvents = new ETreeDictionary<MultiKey<Object>, ELinkedList<EventBean>>(comparator);
        }

        /// <summary> Ctor.</summary>
        /// <param name="propertyName">- the property to sort on
        /// </param>
        /// <param name="isDescending">- true if the property should be sorted in descending order
        /// </param>
        /// <param name="size">- the number of elements to keep in the sort
        /// </param>

        public SortWindowView(String propertyName, bool isDescending, int size)
            : this(new Object[] { propertyName, isDescending }, size)
        {
        }

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

        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to parent views where the sort fields exist and implement Comparable
            String result = null;
            foreach (String name in sortFieldNames)
            {
                result = PropertyCheckHelper.exists(parentView.EventType, name);

                if (result != null)
                {
                    break;
                }
            }
            return result;
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
                dumpUpdateParams("SortWindowView", newData, oldData);
            }

            List<EventBean> removedEvents = new List<EventBean>() ;

            // Remove old data
            if (oldData != null)
            {
                for (int i = 0; i < oldData.Length; i++)
                {
                    MultiKey<Object> sortValues = getSortValues(oldData[i]);
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
                    MultiKey<Object> sortValues = getSortValues(newData[i]);
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
                    ELinkedList<EventBean> events = sortedEvents[lastKey];
                    EventBean _event = events.RemoveLast();
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
            if (this.HasViews())
            {
                EventBean[] expiredArr = null;
                if (removedEvents.Count > 0)
                {
                    expiredArr = removedEvents.ToArray();
                }

                updateChildren(newData, expiredArr);
            }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return new SortWindowIterator(sortedEvents);
        }

        public override String ToString()
        {
            return this.GetType().FullName + " sortFieldName=" + sortFieldNames + " isDescending=" + isDescendingValues + " sortWindowSize=" + sortWindowSize;
        }

        private void Add(MultiKey<Object> key, EventBean bean)
        {
            ELinkedList<EventBean> listOfBeans;
            if (sortedEvents.TryGetValue(key, out listOfBeans))
            {
                listOfBeans.AddFirst(bean); // Add to the front of the list as the second sort critertial is ascending arrival order
                return;
            }

            listOfBeans = new ELinkedList<EventBean>();
            listOfBeans.Add(bean);
            sortedEvents[key] = listOfBeans;
        }

        private bool Remove(MultiKey<Object> key, EventBean bean)
        {
            ELinkedList<EventBean> listOfBeans;
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

        private MultiKey<Object> getSortValues(EventBean ev)
        {
            Object[] result = new Object[sortFieldGetters.Length];
            int count = 0;
            foreach (EventPropertyGetter getter in sortFieldGetters)
            {
                result[count++] = getter.GetValue(ev);
            }

            return new MultiKey<Object>(result);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(SortWindowView));
    }
}
