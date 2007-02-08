using System;

using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
	[TestFixture]
	public class TestEventBuffer
	{
		private EventBuffer buffer;
		private EventBean[] events;

		[SetUp]
		public virtual void setUp()
		{
			buffer = new EventBuffer();
			events = new EventBean[10];

			for ( int i = 0 ; i < events.Length ; i++ )
			{
				events[i] = SupportEventBeanFactory.createObject( i );
			}
		}

		[Test]
		public virtual void testFlow()
		{
			// test empty buffer
			buffer.Add( null );
			Assert.IsNull( buffer.GetAndFlush() );
			buffer.Flush();

			// test add single events
			buffer.Add( new EventBean[] { events[0] } );
			EventBean[] results = buffer.GetAndFlush();
			Assert.IsTrue( ( results.Length == 1 ) && ( results[0] == events[0] ) );

			buffer.Add( new EventBean[] { events[0] } );
			buffer.Add( new EventBean[] { events[1] } );
			results = buffer.GetAndFlush();
			Assert.IsTrue( ( results.Length == 2 ) );
			Assert.AreSame( events[0], results[0] );
			Assert.AreSame( events[1], results[1] );

			buffer.Flush();
			Assert.IsNull( buffer.GetAndFlush() );

			// Add multiple events
			buffer.Add( new EventBean[] { events[2], events[3] } );
			buffer.Add( new EventBean[] { events[4], events[5] } );
			results = buffer.GetAndFlush();
			Assert.IsTrue( ( results.Length == 4 ) );
			Assert.AreSame( events[2], results[0] );
			Assert.AreSame( events[3], results[1] );
			Assert.AreSame( events[4], results[2] );
			Assert.AreSame( events[5], results[3] );

			buffer.Flush();
			Assert.IsNull( buffer.GetAndFlush() );
		}
	}
}
