using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.exec
{
	[TestFixture]
	public class TestTableLookupExecNode
	{
		private TableLookupExecNode exec;
		private PropertyIndexedEventTable index;

		[SetUp]
		public virtual void setUp()
		{
			EventType eventTypeIndex = SupportEventTypeFactory.CreateBeanType( typeof( SupportBean ) );
            index = new PropertyIndexedEventTable(0, eventTypeIndex, new String[] { "str" });

			EventType eventTypeKeyGen = SupportEventTypeFactory.CreateBeanType( typeof( SupportMarketDataBean ) );

			exec = new TableLookupExecNode( 1, new IndexedTableLookupStrategy( eventTypeKeyGen, new String[] { "symbol" }, index ) );
		}

		[Test]
		public virtual void testFlow()
		{
			EventBean[] indexEvents = SupportEventBeanFactory.MakeEvents( new String[] { "a1", "a2" } );
			index.Add( indexEvents );

			EventBean[] lookupEvents = SupportEventBeanFactory.MakeMarketDataEvents( new String[] { "a2", "a3" } );

			IList<EventBean[]> result = new List<EventBean[]>();
			EventBean[] prefill = new EventBean[] { lookupEvents[0], null };
			exec.Process( lookupEvents[0], prefill, result );

			// Test lookup found 1 row
			Assert.AreEqual( 1, result.Count );
			EventBean[] events = result[0];
			Assert.AreSame( indexEvents[1], events[1] );
			Assert.AreSame( lookupEvents[0], events[0] );

			// Test lookup found no rows
			result.Clear();
			exec.Process( lookupEvents[1], prefill, result );
			Assert.AreEqual( 0, result.Count );
		}
	}
}
