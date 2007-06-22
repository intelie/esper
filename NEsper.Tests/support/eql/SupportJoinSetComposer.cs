using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.join;
using net.esper.events;

namespace net.esper.support.eql
{
    public class SupportJoinSetComposer : JoinSetComposer
    {
        private UniformPair<Set<MultiKey<EventBean>>> result;

        public SupportJoinSetComposer(UniformPair<Set<MultiKey<EventBean>>> result)
        {
            this.result = result;
        }

        public UniformPair<Set<MultiKey<EventBean>>> Join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
        {
            return result;
        }
    }
}
