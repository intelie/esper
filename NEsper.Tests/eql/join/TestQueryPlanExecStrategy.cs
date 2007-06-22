using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join
{

	[TestFixture]
	public class TestQueryPlanExecStrategy
	{
		private ExecNodeQueryStrategy strategy;
		private SupportQueryExecNode supportQueryExecNode;

		[SetUp]
		public virtual void  setUp()
		{
			supportQueryExecNode = new SupportQueryExecNode(null);
			strategy = new ExecNodeQueryStrategy(4, 20, supportQueryExecNode);
		}

		[Test]
		public virtual void  testLookup()
		{
			EventBean lookupEvent = SupportEventBeanFactory.CreateObject(new SupportBean());

			strategy.Lookup(new EventBean[]{lookupEvent}, null);

			Assert.AreSame(lookupEvent, supportQueryExecNode.LastPrefillPath[4]);
		}
	}
}
