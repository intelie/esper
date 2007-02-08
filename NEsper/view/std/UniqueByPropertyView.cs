using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.view.std
{
    /// <summary> This view includes only the most recent among events having the same value for the specified field.
    /// The view accepts the field name as parameter from which the unique values are obtained.
    /// For example, a trade's symbol could be used as a unique value.
    /// In this example, the first trade for symbol IBM would be posted as new data to child views.
    /// When the second trade for symbol IBM arrives the second trade is posted as new data to child views,
    /// and the first trade is posted as old data.
    /// Should more than one trades for symbol IBM arrive at the same time (like when batched)
    /// then the child view will get all new events in newData and all new events in oldData minus the most recent event.
    /// When the current new event arrives as old data, the the current unique event gets thrown away and
    /// posted as old data to child views.
    /// Iteration through the views data shows only the most recent events received for the unique value in the order
    /// that events arrived in.
    /// The type of the field returning the unique value can be any type but should override equals and hashCode()
    /// as the type plays the role of a key in a map storing unique values.
    /// </summary>

    public sealed class UniqueByPropertyView : ViewSupport
    {
        /// <summary> Returns the name of the field supplying the unique value to keep the most recent record for.</summary>
        /// <returns> field name for unique value
        /// </returns>
        /// <summary> Sets the name of the field supplying the unique value to keep the most recent record for.</summary>
        /// <param name="uniqueFieldName">field name for unique value
        /// </param>

        public String UniqueFieldName
        {
            get { return uniqueFieldName; }
            set { this.uniqueFieldName = value; }
        }

        private String uniqueFieldName;
        private EventPropertyGetter uniqueFieldGetter;

        private readonly IDictionary<Object, EventBean> mostRecentEvents = new LinkedDictionary<Object, EventBean>();

        /// <summary>
        /// Default constructor - required by all views to adhere to the Java bean specification.
        /// </summary>

        public UniqueByPropertyView()
        {
        }

        /// <summary> Constructor.</summary>
        /// <param name="uniqueFieldName">is the field from which to pull the unique value
        /// </param>

        public UniqueByPropertyView(String uniqueFieldName)
        {
            this.uniqueFieldName = uniqueFieldName;
        }

		public override Viewable Parent
		{
            set
            {
                base.Parent = value;
                if (value != null)
                {
                    uniqueFieldGetter = value.EventType.GetGetter(uniqueFieldName);
                }
            }
        }

        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to just about anything as long as the field exists
            return PropertyCheckHelper.exists(parentView.EventType, uniqueFieldName);
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
                dumpUpdateParams("UniqueByPropertyView", newData, oldData);
            }

            ELinkedList<EventBean> postOldData = null;

            if (this.HasViews())
            {
                postOldData = new ELinkedList<EventBean>();
            }

            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                    // Obtain unique value
                    Object uniqueValue = uniqueFieldGetter.GetValue(newData[i]);

                    // If there are no child views, just update the own collection
                    if (!this.HasViews())
                    {
                        mostRecentEvents[uniqueValue] = newData[i];
                        continue;
                    }

                    // Post the last value as old data
                    EventBean lastValue = mostRecentEvents[uniqueValue];
                    if (lastValue != null)
                    {
                        postOldData.Add(lastValue);
                    }

                    // Override with recent event
                    mostRecentEvents[uniqueValue] = newData[i];
                }
            }

            if (oldData != null)
            {
                for (int i = 0; i < oldData.Length; i++)
                {
                    // Obtain unique value
                    Object uniqueValue = uniqueFieldGetter.GetValue(oldData[i]);

                    // If the old event is the current unique event, remove and post as old data
                    EventBean lastValue = mostRecentEvents[uniqueValue];
                    if (lastValue != oldData[i])
                    {
                        continue;
                    }

                    postOldData.Add(lastValue);
                    mostRecentEvents.Remove(uniqueValue);
                }
            }


            // If there are child views, fire update method
            if (this.HasViews())
            {
                if (postOldData.Count == 0)
                {
                    updateChildren(newData, null);
                }
                else
                {
					updateChildren( newData, postOldData.ToArray() );
                }
            }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return mostRecentEvents.Values.GetEnumerator();
        }

        public override String ToString()
        {
            return this.GetType().FullName + " uniqueFieldName=" + uniqueFieldName;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(UniqueByPropertyView));
    }
}
