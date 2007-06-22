using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.db
{
	[TestFixture]
	public class TestPollingViewable
	{
		private PollingViewable pollingViewable;

		[SetUp]
		public virtual void setUp()
		{
			IList<String> inputProperties = new String[] { "s0.intPrimitive" };

			DataCache dataCache = new DataCacheLRUImpl( 100 );

			EDictionary<String, Type> resultProperties = new HashDictionary<String, Type>();
			resultProperties.Put( "myvarchar", typeof( String ) );
			EventType resultEventType = SupportEventAdapterService.Service.CreateAnonymousMapType( resultProperties );

			EDictionary<MultiKey<Object>, IList<EventBean>> pollResults = new HashDictionary<MultiKey<Object>, IList<EventBean>>();
			pollResults.Put( new MultiKey<Object>( new Object[] { -1 } ), new List<EventBean>() );
			pollResults.Put( new MultiKey<Object>( new Object[] { 500 } ), new List<EventBean>() );
			SupportPollingStrategy supportPollingStrategy = new SupportPollingStrategy( pollResults );

			pollingViewable = new PollingViewable( 1, inputProperties, supportPollingStrategy, dataCache, resultEventType );

			pollingViewable.Validate( new SupportStreamTypeSvc3Stream() );
		}

		[Test]
		public virtual void testPoll()
		{
			EventBean[][] tmpArray = new EventBean[2][];
			for ( int i = 0 ; i < 2 ; i++ )
			{
				tmpArray[i] = new EventBean[2];
			}
			EventBean[][] input = tmpArray;
			input[0] = new EventBean[] { MakeEvent( -1 ), null };
			input[1] = new EventBean[] { MakeEvent( 500 ), null };
			IList<EventBean>[] resultRows = pollingViewable.Poll( input );

			// should have joined to two rows
			Assert.AreEqual( 2, resultRows.Length );
			Assert.AreEqual( 0, resultRows[0].Count );
			Assert.AreEqual( 0, resultRows[1].Count );
		}

		private EventBean MakeEvent( int intPrimitive )
		{
			SupportBean bean = new SupportBean();
			bean.SetIntPrimitive(intPrimitive);
			return SupportEventAdapterService.Service.AdapterForBean( bean );
		}
	}
}
