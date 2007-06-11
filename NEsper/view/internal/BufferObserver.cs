using System;

using net.esper.collection;

namespace net.esper.view.internals
{
    /// <summary>
    /// Observer interface to a stream publishing new and old events.
    /// </summary>
    public interface BufferObserver
    {
        /// <summary>
        /// Receive new and old events from a stream.
        /// </summary>
        /// <param name="streamId">the stream number sending the events</param>
        /// <param name="newEventBuffer">buffer for new events</param>
        /// <param name="oldEventBuffer">buffer for old events</param>
        void NewData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer);
    }

    public delegate void BufferObserverDelegate(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer);

    public class BufferObserverImpl : BufferObserver
    {
        private BufferObserverDelegate m_delegate;

        /// <summary>
        /// Initializes a new instance of the <see cref="BufferObserverImpl"/> class.
        /// </summary>
        /// <param name="d">The d.</param>
        public BufferObserverImpl(BufferObserverDelegate d)
        {
            m_delegate = d;
        }

        #region BufferObserver Members

        /// <summary>
        /// Receive new and old events from a stream.
        /// </summary>
        /// <param name="streamId">the stream number sending the events</param>
        /// <param name="newEventBuffer">buffer for new events</param>
        /// <param name="oldEventBuffer">buffer for old events</param>
        public void NewData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
        {
            m_delegate(streamId, newEventBuffer, oldEventBuffer);
        }

        #endregion
    }
}
