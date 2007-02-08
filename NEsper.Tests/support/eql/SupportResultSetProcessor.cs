using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.support.eql
{
    public class SupportResultSetProcessor : ResultSetProcessor
    {
        virtual public EventType ResultEventType
        {
            get
            {
                return SupportEventTypeFactory.createBeanType(typeof(SupportBean));
            }
        }

        public Pair<EventBean[], EventBean[]> processViewResult(EventBean[] newData, EventBean[] oldData)
        {
            return new Pair<EventBean[], EventBean[]>(newData, oldData);
        }

        public Pair<EventBean[], EventBean[]> processJoinResult(ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents)
        {
            return new Pair<EventBean[], EventBean[]>(
                CollectionHelper.First( newEvents ).Array,
                CollectionHelper.First( oldEvents ).Array);
        }
    }
}
