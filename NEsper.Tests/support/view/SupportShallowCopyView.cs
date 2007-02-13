using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.view;

namespace net.esper.support.view
{

    public class SupportShallowCopyView : ViewSupport
    {
        virtual public bool NullWriteOnlyValue
        {
            get
            {
                return someWriteOnlyValue == null;
            }

        }
        virtual public String SomeReadWriteValue
        {
            get
            {
                return someReadWriteValue;
            }

            set
            {
                this.someReadWriteValue = value;
            }

        }
        virtual public String SomeReadOnlyValue
        {
            get
            {
                return someReadOnlyValue;
            }

        }
        virtual public String SomeWriteOnlyValue
        {
            set
            {
                this.someWriteOnlyValue = value;
            }

        }

        override public EventType EventType
        {
            get
            {
                return null;
            }
            set { }
        }

        public override Viewable Parent
        {
            set
            {
                throw new NotSupportedException();
            }
        }

        private String someReadWriteValue;
        private String someReadOnlyValue;
        private String someWriteOnlyValue;

        public SupportShallowCopyView(String someValue)
        {
            this.someReadWriteValue = someValue;
            this.someReadOnlyValue = someValue;
            this.someWriteOnlyValue = someValue;
        }

        public SupportShallowCopyView()
        {
        }

        public override String AttachesTo(Viewable parentViewable)
        {
            return null;
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return null;
        }
    }
}
