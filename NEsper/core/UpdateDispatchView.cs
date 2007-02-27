using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.dispatch;
using net.esper.events;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.core
{
    /// <summary>
    /// Convenience view for dispatching view updates received from a parent view to update listeners
    /// via the dispatch service.
    /// </summary>

    public class UpdateDispatchView : ViewSupport, Dispatchable
    {
        private readonly ISet<UpdateListener> updateListeners;
        private readonly DispatchService dispatchService;
        private bool isDispatchWaiting;

        private List<EventBean[]> lastNewEvents = new List<EventBean[]>();
        private List<EventBean[]> lastOldEvents = new List<EventBean[]>();

        /// <summary> Ctor.</summary>
        /// <param name="updateListeners">- listeners to update
        /// </param>
        /// <param name="dispatchService">- for performing the dispatch
        /// </param>

        public UpdateDispatchView(ISet<UpdateListener> updateListeners, DispatchService dispatchService)
        {
            this.updateListeners = updateListeners;
            this.dispatchService = dispatchService;
        }

        public override String AttachesTo(Viewable parentViewable)
        {
            return null;
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            if (log.IsDebugEnabled)
            {
                ViewSupport.dumpUpdateParams(".update for view " + this, newData, oldData);
            }
            if (newData != null)
            {
                lastNewEvents.Add(newData);
            }
            if (oldData != null)
            {
                lastOldEvents.Add(oldData);
            }
            if (!isDispatchWaiting)
            {
                dispatchService.AddExternal(this);
                isDispatchWaiting = true;
            }
        }

        public override EventType EventType
        {
            get { return null; }
            set { }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            return null;
        }
        
        public virtual void execute()
        {
            isDispatchWaiting = false;
            EventBean[] newEvents = EventBeanUtility.Flatten(lastNewEvents);
            EventBean[] oldEvents = EventBeanUtility.Flatten(lastOldEvents);

            if (log.IsDebugEnabled)
            {
                ViewSupport.dumpUpdateParams(".execute", newEvents, oldEvents);
            }

            foreach (UpdateListener listener in updateListeners)
            {
                listener(newEvents, oldEvents);
            }

            lastNewEvents.Clear();
            lastOldEvents.Clear();
        }

        private static Log log = LogFactory.GetLog(typeof(UpdateDispatchView));
    }
}