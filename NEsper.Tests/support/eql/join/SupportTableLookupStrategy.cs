using System;

using net.esper.compat;
using net.esper.eql.join.exec;
using net.esper.events;

namespace net.esper.support.eql.join
{
    public class SupportTableLookupStrategy : TableLookupStrategy
    {
        private readonly int numResults;

        public SupportTableLookupStrategy(int numResults)
        {
            this.numResults = numResults;
        }

        public Set<EventBean> Lookup(EventBean _event)
        {
            return SupportJoinResultNodeFactory.MakeEventSet(numResults);
        }
    }
}
