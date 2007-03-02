using System;

using net.esper.events;
using net.esper.view;

namespace net.esper.support.view
{
    public class SupportSchemaNeutralView : SupportBaseView
    {
        private string _viewName;

        override public Viewable Parent
        {
            set
            {
                base.Parent = value;
                if (value != null)
                {
                    EventType = value.EventType;
                }
                else
                {
                    EventType = null;
                }
            }
        }

        public string ViewName
        {
            get { return ViewName; }
        }

        public SupportSchemaNeutralView()
        {
        }

        public SupportSchemaNeutralView(String viewName)
        {
            this._viewName = viewName;
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            this.lastNewData = newData;
            this.lastOldData = oldData;

            UpdateChildren(newData, oldData);
        }
    }
}
