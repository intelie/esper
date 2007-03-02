using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.events;

namespace net.esper.support.view
{
    public class SupportBeanClassView : SupportBaseView
    {
        private static IList<SupportBeanClassView> instances = new List<SupportBeanClassView>();

        public SupportBeanClassView()
        {
            instances.Add(this);
        }

        public SupportBeanClassView(Type clazz)
            : base(SupportEventTypeFactory.createBeanType(clazz))
        {
            instances.Add(this);
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            base.Invoked = true;
            this.lastNewData = newData;
            this.lastOldData = oldData;

            UpdateChildren(newData, oldData);
        }

        public static IList<SupportBeanClassView> getInstances()
        {
            return instances;
        }
    }
}
