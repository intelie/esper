using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join
{

	[TestFixture]
    public class TestJoinExecutionStrategyImpl 
    {
        private JoinExecutionStrategyImpl join;
        private ISet<MultiKey<EventBean>> oldEvents;
        private ISet<MultiKey<EventBean>> newEvents;
        private SupportJoinSetProcessor filter;
        private SupportJoinSetProcessor indicator;

        [SetUp]
        public virtual void setUp()
        {
            oldEvents = new EHashSet<MultiKey<EventBean>>();
            newEvents = new EHashSet<MultiKey<EventBean>>();

            JoinSetComposer composer = new SupportJoinSetComposer(new UniformPair<ISet<MultiKey<EventBean>>>(newEvents, oldEvents));
            filter = new SupportJoinSetProcessor();
            indicator = new SupportJoinSetProcessor();

            join = new JoinExecutionStrategyImpl(composer, filter, indicator);
        }

        [Test]
        public virtual void testJoin()
        {
            join.Join(null, null);

            Assert.AreSame(newEvents, filter.getLastNewEvents());
            Assert.AreSame(oldEvents, filter.getLastOldEvents());
            Assert.IsNull(indicator.getLastNewEvents());
            Assert.IsNull(indicator.getLastOldEvents());
        }
    }
}
