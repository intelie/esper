using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	[TestFixture]
	public class TestEvalAndStateNode
	{
		private EDictionary<String, EventBean> events;

		[SetUp]
		public virtual void setUp()
		{
			events = new EHashDictionary<String, EventBean>();
			String[] ids = new String[] { "0", "a", "b", "c", "d", "e", "f" };
			foreach ( String id in ids )
			{
				events.Put( id, SupportEventBeanFactory.createObject( id ) );
			}
		}

		[Test]
		public virtual void testGenerate()
		{
			MatchedEventMap beginState = new MatchedEventMap();
			beginState.Add( "0", events.Fetch( "0" ) );

			List<IList<MatchedEventMap>> listArray = new List<IList<MatchedEventMap>>();
			listArray.Add( makeList( "a", "b" ) );
			listArray.Add( makeList( "c", "d" ) );
			listArray.Add( makeList( "e", "f" ) );

			IList<MatchedEventMap> result = new List<MatchedEventMap>();
			EvalAndStateNode.GenerateMatchEvents( listArray, 0, result, beginState );

			Assert.IsTrue( result.Count == 8 );
			for ( int i = 0 ; i < 2 ; i++ )
			{
				for ( int j = 0 ; j < 2 ; j++ )
				{
					for ( int k = 0 ; k < 2 ; k++ )
					{
						int index = i * 4 + j * 2 + k;
						MatchedEventMap _event = result[index];

						log.Debug( ".testGenerate index=" + index + "  event=" + _event );
					}
				}
			}

			Assert.IsTrue( result[0].GetMatchingEvent( "0" ) == events.Fetch( "0" ) );

			Assert.IsTrue( result[0].GetMatchingEvent( "a" ) == events.Fetch( "a" ) );
			Assert.IsTrue( result[0].GetMatchingEvent( "c" ) == events.Fetch( "c" ) );
			Assert.IsTrue( result[0].GetMatchingEvent( "e" ) == events.Fetch( "e" ) );

			Assert.IsTrue( result[1].GetMatchingEvent( "0" ) == events.Fetch( "0" ) );
			Assert.IsTrue( result[1].GetMatchingEvent( "a" ) == events.Fetch( "a" ) );
			Assert.IsTrue( result[1].GetMatchingEvent( "c" ) == events.Fetch( "c" ) );
			Assert.IsTrue( result[1].GetMatchingEvent( "f" ) == events.Fetch( "f" ) );

			Assert.IsTrue( result[7].GetMatchingEvent( "0" ) == events.Fetch( "0" ) );
			Assert.IsTrue( result[7].GetMatchingEvent( "b" ) == events.Fetch( "b" ) );
			Assert.IsTrue( result[7].GetMatchingEvent( "d" ) == events.Fetch( "d" ) );
			Assert.IsTrue( result[7].GetMatchingEvent( "f" ) == events.Fetch( "f" ) );
		}

		/// <summary> Make a list of MatchEvents for testing each containing 2 entries in the list</summary>
		private IList<MatchedEventMap> makeList( String valueOne, String valueTwo )
		{
			IList<MatchedEventMap> list = new List<MatchedEventMap>();

			MatchedEventMap event1 = new MatchedEventMap();
			event1.Add( valueOne, events[valueOne] );
			list.Add( event1 );

			MatchedEventMap event2 = new MatchedEventMap();
			event2.Add( valueTwo, events[valueTwo] );
			list.Add( event2 );

			return list;
		}

		private static readonly Log log = LogFactory.GetLog( typeof( TestEvalAndStateNode ) );
	}
}
