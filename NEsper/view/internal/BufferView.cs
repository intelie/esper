using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.internal_Renamed
{
    /// <summary> A view that acts as an adapter between views and update listeners.
    /// The view can be added to a parent view. When the parent view publishes data, the view will forward the
    /// data to the UpdateListener implementation that has been supplied. If no UpdateListener has been supplied,
    /// then the view will cache the last data published by the parent view.
    /// </summary>

    public sealed class BufferView : ViewSupport
    {
        /// <summary> Set the observer for indicating new and old data.</summary>
        /// <param name="observer">to indicate new and old events
        /// </param>

        public BufferObserver Observer
        {
            set { this.observer = value; }
        }

        private readonly int streamId;

        private BufferObserver observer;
        private EventBuffer newDataBuffer = new EventBuffer();
        private EventBuffer oldDataBuffer = new EventBuffer();

        /// <summary> Ctor.</summary>
        /// <param name="streamId">- number of the stream for which the view buffers the generated events.
        /// </param>

        public BufferView(int streamId)
        {
            this.streamId = streamId;
        }

        public override EventType EventType
        {
            get { return parent.EventType; }
            set { }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return parent.GetEnumerator();
        }

        public override String AttachesTo(Viewable _object)
        {
            // This view attaches to any parent view
            return null;
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            newDataBuffer.Add(newData);
            oldDataBuffer.Add(oldData);
            observer.newData(streamId, newDataBuffer, oldDataBuffer);
        }
    }
}
