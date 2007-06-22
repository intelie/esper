using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.events;

namespace net.esper.support.view
{
    public class SupportMapView : SupportBaseView
    {
        private static IList<SupportMapView> instances = new List<SupportMapView>();

        public SupportMapView()
        {
            instances.Add(this);
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            base.IsInvoked = true;
            this.lastNewData = newData;
            this.lastOldData = oldData;

            UpdateChildren(newData, oldData);
        }

        public SupportMapView(EDictionary<String, Type> eventTypeMap)
            : base(SupportEventTypeFactory.CreateMapType(eventTypeMap))
        {
            instances.Add(this);
        }


        public static IList<SupportMapView> Instances
        {
            get { return instances; }
        }
    }
}
