using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.support.view
{
    public abstract class SupportBaseView : ViewSupport
    {
        public override EventType EventType
        {
            get { return eventType; }
            set { eventType = value; }
        }

        virtual public EventBean[] LastNewData
        {
            get { return lastNewData; }
            set { this.lastNewData = value; }
        }

        virtual public EventBean[] LastOldData
        {
            get { return lastOldData; }
            set { this.lastOldData = value; }
        }

        virtual public bool Invoked
        {
            set { isInvoked = value; }
        }

        protected internal EventBean[] lastNewData;
        protected internal EventBean[] lastOldData;
        protected internal EventType eventType;
        protected internal bool isInvoked;

        /// <summary>
        /// Default constructor since views are also beans.
        /// </summary>
        
        public SupportBaseView()
        {
        }

        public SupportBaseView(EventType eventType)
        {
            this.eventType = eventType;
        }

		virtual public bool getAndClearIsInvoked()
		{
			bool invoked = isInvoked;
			isInvoked = false;
			return invoked;
		}

        public override String AttachesTo(Viewable _object)
        {
            log.Info(".AttachesTo Not implemented");
            return null;
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            log.Info(".iterator Not implemented");
            return null;
        }

        public virtual void clearLastNewData()
        {
            lastNewData = null;
        }

        public virtual void clearLastOldData()
        {
            lastOldData = null;
        }

        public virtual void reset()
        {
            isInvoked = false;
            lastNewData = null;
            lastOldData = null;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(SupportBaseView));
    }
}
