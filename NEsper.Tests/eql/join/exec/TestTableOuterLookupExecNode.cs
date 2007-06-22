using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.exec
{
	[TestFixture]
	public class TestTableOuterLookupExecNode
	{
		private TableOuterLookupExecNode exec;
		private UnindexedEventTable index;

		[SetUp]
		public virtual void setUp()
		{
			index = new UnindexedEventTable( 0 );
			exec = new TableOuterLookupExecNode( 1, new FullTableScanLookupStrategy( index ) );
		}

		[Test]
		public virtual void testFlow()
		{
			EventBean[] lookupEvents = SupportEventBeanFactory.MakeMarketDataEvents( new String[] { "a2" } );
			IList<EventBean[]> result = new List<EventBean[]>();
			EventBean[] prefill = new EventBean[] { lookupEvents[0], null };

			// Test lookup on empty index, expect 1 row
			exec.Process( lookupEvents[0], prefill, result );
			Assert.AreEqual( 1, result.Count );
			EventBean[] events = result[0];
			Assert.IsNull( events[1] );
			Assert.AreSame( lookupEvents[0], events[0] );
            result.Clear();

			// Test lookup on filled index, expect row2
			EventBean[] indexEvents = SupportEventBeanFactory.MakeEvents( new String[] { "a1", "a2" } );
			index.Add( indexEvents );
			exec.Process( lookupEvents[0], prefill, result );
			Assert.AreEqual( 2, result.Count );

			IEnumerator<EventBean[]> it = result.GetEnumerator();

            events = CollectionHelper.Next(it);
			Assert.AreSame( lookupEvents[0], events[0] );
			Assert.IsTrue( ( indexEvents[0] == events[1] ) || ( indexEvents[1] == events[1] ) );

            events = CollectionHelper.Next(it);
			Assert.AreSame( lookupEvents[0], events[0] );
			Assert.IsTrue( ( indexEvents[0] == events[1] ) || ( indexEvents[1] == events[1] ) );
		}
	}
}
