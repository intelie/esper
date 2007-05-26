using System;
using System.Collections.Generic;

using net.esper.core;
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

    public sealed class UniqueByPropertyView
		: ViewSupport
		, CloneableView
    {
        /// <summary>
        /// Gets or sets the name of the field supplying the unique value to keep the most recent record for.
        /// </summary>
        /// <value>The name of the unique field.</value>

        public String UniqueFieldName
        {
            get { return uniqueFieldName; }
        }

        private readonly String uniqueFieldName;
        private EventPropertyGetter uniqueFieldGetter;

        private readonly EDictionary<Object, EventBean> mostRecentEvents ;

        /// <summary> Constructor.</summary>
        /// <param name="uniqueFieldName">is the field from which to pull the unique value
        /// </param>

        public UniqueByPropertyView(String uniqueFieldName)
        {
            this.mostRecentEvents = new LinkedDictionary<Object, EventBean>();
            this.uniqueFieldName = uniqueFieldName;
        }
		
		public View CloneView(StatementContext statementContext)
		{
			return new UniqueByPropertyView(uniqueFieldName);
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
                base.Parent = value;
                if (value != null)
                {
                    uniqueFieldGetter = value.EventType.GetGetter(uniqueFieldName);
                }
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
                DumpUpdateParams("UniqueByPropertyView", newData, oldData);
            }

            List<EventBean> postOldData = null;

            if (this.HasViews)
            {
                postOldData = new List<EventBean>();
            }

            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                    // Obtain unique value
                    Object uniqueValue = uniqueFieldGetter.GetValue(newData[i]);

                    // If there are no child views, just update the own collection
                    if (!this.HasViews)
                    {
                        mostRecentEvents[uniqueValue] = newData[i];
                        continue;
                    }

                    // Post the last value as old data
                    EventBean lastValue = mostRecentEvents.Fetch(uniqueValue);
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


            // If there are child views, fireStatementStopped update method
            if (this.HasViews)
            {
                if (postOldData.IsEmpty)
                {
                    UpdateChildren(newData, null);
                }
                else
                {
					UpdateChildren( newData, postOldData.ToArray() );
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
            return mostRecentEvents.Values.GetEnumerator();
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return this.GetType().FullName + " uniqueFieldName=" + uniqueFieldName;
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
