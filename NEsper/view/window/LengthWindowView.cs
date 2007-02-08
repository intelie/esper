using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.window
{
    /// <summary>
    /// This view is a moving window extending the specified number of elements into the past.
    /// </summary>

    public sealed class LengthWindowView : ViewSupport, DataWindowView
    {
        private int size = 0;
        private readonly ELinkedList<EventBean> events = new ELinkedList<EventBean>();

        /// <summary> Returns the size of the length window.</summary>
        /// <returns> size of length window
        /// </returns>
        /// <summary> Sets the size of the length window.</summary>
        /// <param name="size">size of length window
        /// </param>
        public int Size
        {
            get { return size; }
            set { this.size = value; }
        }

        /// <summary>
        /// Default constructor - required by all views to adhere to the Java bean specification.
        /// </summary>

        public LengthWindowView()
        {
        }

        /// <summary> Constructor creates a moving window extending the specified number of elements into the past.</summary>
        /// <param name="size">is the specified number of elements into the past
        /// </param>

        public LengthWindowView(int size)
        {
            if (size < 1)
            {
                throw new ArgumentException("Illegal argument for size of length window");
            }

            this.size = size;
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
                // The event type is the parent view's event type
                return parent.EventType;
            }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            // add data points to the window
            // we don't care about removed data from a prior view
            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                    events.Add(newData[i]);
                }
            }

            // Check for any events that get pushed out of the window
            int expiredCount = events.Count - size;
            EventBean[] expiredArr = null;
            if (expiredCount > 0)
            {
                expiredArr = new EventBean[expiredCount];
                for (int i = 0; i < expiredCount; i++)
                {
                    expiredArr[i] = events.RemoveFirst();
                }
            }

            // If there are child views, fire update method
            if (this.HasViews())
            {
                updateChildren(newData, expiredArr);
            }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return events.GetEnumerator();
        }

        public override String ToString()
        {
            return this.GetType().FullName + " size=" + size;
        }
    }
}