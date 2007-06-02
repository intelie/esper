using System;
using System.Collections.Generic;

using net.esper.eql.spec;
using net.esper.events;
using net.esper.view;

namespace net.esper.eql.view
{
	/// <summary> View for applying a final "rstream" or "istream" selection on the result event rows before
	/// publishing to listeners.
	/// </summary>

    public class IStreamRStreamSelectorView : ViewSupport
    {
        private readonly SelectClauseStreamSelectorEnum selectStreamDirEnum;

        /// <summary> Ctor.</summary>
        /// <param name="selectStreamDirEnum">defines what stream is selected, or both streams
        /// </param>

        public IStreamRStreamSelectorView(SelectClauseStreamSelectorEnum selectStreamDirEnum)
        {
            this.selectStreamDirEnum = selectStreamDirEnum;
        }

        /// <summary>
        /// Notify that data has been added or removed from the Viewable parent.
        /// The last object in the newData array of objects would be the newest object added to the parent view.
        /// The first object of the oldData array of objects would be the oldest object removed from the parent view.
        /// If the call to update contains new (inserted) data, then the first argument will be a non-empty list and the
        /// second will be empty. Similarly, if the call is a notification of deleted data, then the first argument will be
        /// empty and the second will be non-empty. Either the newData or oldData will be non-null.
        /// This method won't be called with both arguments being null, but either one could be null.
        /// The same is true for zero-length arrays. Either newData or oldData will be non-empty.
        /// If both are non-empty, then the update is a modification notification.
        /// When update() is called on a view by the parent object, the data in newData will be in the collection of the
        /// parent, and its data structures will be arranged to reflect that.
        /// The data in oldData will not be in the parent's data structures, and any access to the parent will indicate that
        /// that data is no longer there.
        /// </summary>
        /// <param name="newData">is the new data that has been added to the parent view</param>
        /// <param name="oldData">is the old data that has been removed from the parent view</param>
        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ONLY)
            {
                if (oldData != null)
                {
                    // Hand only the old data as new data to child views
                    this.UpdateChildren(oldData, null);
                }
            }
            else if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.ISTREAM_ONLY)
            {
                if (newData != null)
                {
                    // Hand only the new data as new data to child views
                    this.UpdateChildren(newData, null);
                }
            }
            else if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
            {
                this.UpdateChildren(newData, oldData);
            }
            else
            {
                throw new SystemException("Unknown stream selector " + selectStreamDirEnum);
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
            get { return parent.EventType; }
            set { }
        }

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
        {
            return parent.GetEnumerator();
        }
    }
}
