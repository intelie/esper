using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.std
{
    /// <summary> This view is a very simple view presenting the last event posted by the parent view to any subviews.
    /// Only the very last event object is kept by this view. The update method invoked by the parent view supplies
    /// new data in an object array, of which the view keeps the very last instance as the 'last' or newest event.
    /// The view always has the same schema as the parent view and attaches to anything, and accepts no parameters.
    /// 
    /// Useful is the last view for example for "stocks.time_window(100).last()".
    /// 
    /// Notice that "stocks.last().Count" and
    /// "stocks.win:length(10).std:lastevent().std:size()" must always return 0 or 1.
    /// 
    /// Thus if 5 pieces of new data arrive, the child view receives 5 elements of new data
    /// and also 4 pieces of old data which is the first 4 elements of new data.
    /// I.e. New data elements immediatly gets to be old data elements.
    /// 
    /// Old data received from parent is not handled, it is ignored.
    /// We thus post old data as follows:
    /// last event is not null +
    /// new data from index zero to N-1, where N is the index of the last element in new data
    /// </summary>

    public class LastElementView : ViewSupport
    {
        /// <summary> The last new element posted from a parent view.</summary>
        internal EventBean lastEvent;

        /// <summary> Constructor.</summary>
        public LastElementView()
        {
        }

        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to just about anything
            return null;
        }

        public override EventType EventType
        {
            get
            {
                // The schema is the parent view's schema
                return parent.EventType;
            }
            set
            {
            }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            List<EventBean> oldDataToPost = new List<EventBean>();

            if ((newData != null) && (newData.Length != 0))
            {
                if (lastEvent != null)
                {
                    oldDataToPost.Add(lastEvent);
                }
                if (newData.Length > 1)
                {
                    for (int i = 0; i < newData.Length - 1; i++)
                    {
                        oldDataToPost.Add(newData[i]);
                    }
                }
                lastEvent = newData[newData.Length - 1];
            }

            // If there are child views, fire update method
            if (this.HasViews)
            {
                if (oldDataToPost.Count > 0)
                {
                	UpdateChildren(newData, oldDataToPost.ToArray());
                }
                else
                {
                    UpdateChildren(newData, null);
                }
            }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return new SingleEventIterator(lastEvent);
        }

        public override String ToString()
        {
            return this.GetType().FullName;
        }
    }
}
