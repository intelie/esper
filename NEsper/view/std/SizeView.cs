using System;
using System.Collections.Generic;
using System.Collections.Specialized;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.std
{
    /// <summary>
    /// This view is a very simple view presenting the number of elements in a stream or view.
    /// The view computes a single long-typed count of the number of events passed through it similar
    /// to the base statistics COUNT column.
    /// </summary>

    public sealed class SizeView : ViewSupport, ContextAwareView
    {
        public ViewServiceContext ViewServiceContext
        {
            get { return viewServiceContext; }
            set
            {
                this.viewServiceContext = value;

                EDictionary<String, Type> schemaMap = new EHashDictionary<String, Type>();
                schemaMap[ViewFieldEnum.SIZE_VIEW__SIZE.Name] =  typeof(long);
                eventType = value.EventAdapterService.CreateAnonymousMapType(schemaMap);
            }
        }

        private ViewServiceContext viewServiceContext;
        private EventType eventType;
        private long size = 0;

        /// <summary>
        /// Constructor.
        /// </summary>

        public SizeView()
        {
        }

        public override String AttachesTo(Viewable parentView)
        {
            // Attaches to just about anything
            return null;
        }

        public override EventType EventType
        {
            get { return eventType; }
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            long priorSize = size;

            // add data points to the window
            if (newData != null)
            {
                size += newData.Length;
            }

            if (oldData != null)
            {
                size -= oldData.Length;
            }

            // If there are child views, fire update method
            if ((this.HasViews()) && (priorSize != size))
            {
                EDataDictionary postNewData = new EDataDictionary();
                postNewData[ViewFieldEnum.SIZE_VIEW__SIZE.Name] = size;

                EDataDictionary postOldData = new EDataDictionary();
                postOldData[ViewFieldEnum.SIZE_VIEW__SIZE.Name] = priorSize;
                updateChildren(new EventBean[] { viewServiceContext.EventAdapterService.CreateMapFromValues(postNewData, eventType) }, new EventBean[] { viewServiceContext.EventAdapterService.CreateMapFromValues(postOldData, eventType) });
            }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
            EDataDictionary current = new EDataDictionary();
            current[ViewFieldEnum.SIZE_VIEW__SIZE.Name] = size;
            return new SingleEventIterator(viewServiceContext.EventAdapterService.CreateMapFromValues(current, eventType));
        }

        public override String ToString()
        {
            return this.GetType().FullName;
        }
    }
}
