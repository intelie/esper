using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.db
{
	public class SupportPollingStrategy : PollExecStrategy
	{
        private EDictionary<MultiKey<Object>, IList<EventBean>> results;

        public SupportPollingStrategy(EDictionary<MultiKey<Object>, IList<EventBean>> results)
        {
            this.results = results;
        }

        public void Start()
        {
        }

        public IList<EventBean> Poll(Object[] lookupValues)
        {
            return results.Fetch(new MultiKey<Object>(lookupValues));
        }

        public void Done()
        {
        }

        public void Destroy()
        {
        }
	}
}
