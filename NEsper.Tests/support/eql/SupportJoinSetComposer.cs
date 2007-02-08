using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.join;
using net.esper.events;

namespace net.esper.support.eql
{
    public class SupportJoinSetComposer : JoinSetComposer
    {
        private UniformPair<ISet<MultiKey<EventBean>>> result;

        public SupportJoinSetComposer(UniformPair<ISet<MultiKey<EventBean>>> result)
        {
            this.result = result;
        }

        public UniformPair<ISet<MultiKey<EventBean>>> join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
        {
            return result;
        }
    }
}
