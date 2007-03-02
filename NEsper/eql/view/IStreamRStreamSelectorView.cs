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

        public override String AttachesTo(Viewable parentViewable)
        {
            return null;
        }

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

        public override EventType EventType
        {
            get { return parent.EventType; }
            set { }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return parent.GetEnumerator();
        }
    }
}
