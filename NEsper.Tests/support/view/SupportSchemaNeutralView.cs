using System;

using net.esper.events;
using net.esper.view;

namespace net.esper.support.view
{
    public class SupportSchemaNeutralView : SupportBaseView
    {
        override public Viewable Parent
        {
            set
            {
                base.Parent = value;
                if (value != null)
                {
                    SetEventType(value.EventType);
                }
                else
                {
                    SetEventType(null);
                }
            }

        }
        public SupportSchemaNeutralView()
        {
        }

        public SupportSchemaNeutralView(String viewName)
        {
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            this.lastNewData = newData;
            this.lastOldData = oldData;

            updateChildren(newData, oldData);
        }
    }
}
