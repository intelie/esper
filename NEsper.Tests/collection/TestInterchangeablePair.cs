using System;

using net.esper.compat;
using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
	[TestFixture]
	public class TestInterchangeablePair
	{
		private InterchangeablePair<String, String> pair1a = new InterchangeablePair<String, String>( "a", "b" );
		private InterchangeablePair<String, String> pair1b = new InterchangeablePair<String, String>( "a", "c" );
		private InterchangeablePair<String, String> pair1c = new InterchangeablePair<String, String>( "c", "b" );
		private InterchangeablePair<String, String> pair1d = new InterchangeablePair<String, String>( "a", "b" );
		private InterchangeablePair<String, String> pair1e = new InterchangeablePair<String, String>( "b", "a" );

		private InterchangeablePair<String, String> pair2a = new InterchangeablePair<String, String>( "a", null );
		private InterchangeablePair<String, String> pair2b = new InterchangeablePair<String, String>( "b", null );
		private InterchangeablePair<String, String> pair2c = new InterchangeablePair<String, String>( "a", null );

		private InterchangeablePair<String, String> pair3a = new InterchangeablePair<String, String>( null, "b" );
		private InterchangeablePair<String, String> pair3b = new InterchangeablePair<String, String>( null, "c" );
		private InterchangeablePair<String, String> pair3c = new InterchangeablePair<String, String>( null, "b" );

		private InterchangeablePair<String, String> pair4a = new InterchangeablePair<String, String>( null, null );
		private InterchangeablePair<String, String> pair4b = new InterchangeablePair<String, String>( null, null );

		[Test]
		public virtual void testEquals()
		{
			Assert.IsTrue( pair1a.Equals( pair1d ) && pair1d.Equals( pair1a ) );
			Assert.IsTrue( pair1a.Equals( pair1e ) && pair1e.Equals( pair1a ) );
			Assert.IsFalse( pair1a.Equals( pair1b ) );
			Assert.IsFalse( pair1a.Equals( pair1c ) );
			Assert.IsFalse( pair1a.Equals( pair2a ) );
			Assert.IsFalse( pair1a.Equals( pair3a ) );
			Assert.IsFalse( pair1a.Equals( pair4a ) );

			Assert.IsTrue( pair2a.Equals( pair2c ) && pair2c.Equals( pair2a ) );
			Assert.IsTrue( pair2b.Equals( pair3a ) && pair3a.Equals( pair2b ) );
			Assert.IsFalse( pair2a.Equals( pair2b ) );
			Assert.IsFalse( pair2a.Equals( pair1a ) );
			Assert.IsFalse( pair2b.Equals( pair1e ) );
			Assert.IsFalse( pair2b.Equals( pair3b ) );
			Assert.IsFalse( pair2a.Equals( pair4a ) );

			Assert.IsTrue( pair3a.Equals( pair3c ) && pair3c.Equals( pair3a ) );
			Assert.IsTrue( pair3c.Equals( pair2b ) && pair2b.Equals( pair3c ) );
			Assert.IsFalse( pair3a.Equals( pair3b ) );
			Assert.IsFalse( pair3b.Equals( pair3a ) );
			Assert.IsFalse( pair3a.Equals( pair1a ) );
			Assert.IsFalse( pair3a.Equals( pair2a ) );
			Assert.IsFalse( pair3a.Equals( pair4a ) );

			Assert.IsTrue( pair4a.Equals( pair4b ) && pair4b.Equals( pair4a ) );
			Assert.IsFalse( pair4a.Equals( pair1b ) || pair4a.Equals( pair2a ) || pair4a.Equals( pair3a ) );
		}

		[Test]
		public virtual void testHashCode()
		{
			Assert.IsTrue( pair1a.GetHashCode() == ( "a".GetHashCode() ^ "b".GetHashCode() ) );
			Assert.IsTrue( pair2a.GetHashCode() == "a".GetHashCode() );
			Assert.IsTrue( pair3a.GetHashCode() == "b".GetHashCode() );
			Assert.IsTrue( pair4a.GetHashCode() == 0 );

			Assert.IsTrue( pair1a.GetHashCode() != pair2a.GetHashCode() );
			Assert.IsTrue( pair1a.GetHashCode() != pair3a.GetHashCode() );
			Assert.IsTrue( pair1a.GetHashCode() != pair4a.GetHashCode() );

			Assert.IsTrue( pair1a.GetHashCode() == pair1d.GetHashCode() );
			Assert.IsTrue( pair2a.GetHashCode() == pair2c.GetHashCode() );
			Assert.IsTrue( pair3a.GetHashCode() == pair3c.GetHashCode() );
			Assert.IsTrue( pair4a.GetHashCode() == pair4b.GetHashCode() );

			Assert.IsTrue( pair2b.GetHashCode() == pair3a.GetHashCode() );
		}

		[Test]
		public virtual void testSetBehavior()
		{
			ISet<InterchangeablePair<EventBean, EventBean>> eventPairs = new EHashSet<InterchangeablePair<EventBean, EventBean>>();

			EventBean[] events = new EventBean[4];
			for ( int i = 0 ; i < events.Length ; i++ )
			{
				events[i] = SupportEventBeanFactory.createObject( (Object) i );
			}

			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[0], events[1] ) );
			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[0], events[2] ) );
			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[1], events[2] ) );
			Assert.AreEqual( 3, eventPairs.Count );

			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[0], events[1] ) );
			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[1], events[2] ) );
			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[2], events[0] ) );
			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[2], events[1] ) );
			eventPairs.Add( new InterchangeablePair<EventBean, EventBean>( events[1], events[0] ) );
			Assert.AreEqual( 3, eventPairs.Count );

			Assert.IsTrue( eventPairs.Contains( new InterchangeablePair<EventBean, EventBean>( events[1], events[0] ) ) );
			Assert.IsFalse( eventPairs.Contains( new InterchangeablePair<EventBean, EventBean>( events[3], events[0] ) ) );
			Assert.IsTrue( eventPairs.Contains( new InterchangeablePair<EventBean, EventBean>( events[1], events[2] ) ) );
			Assert.IsTrue( eventPairs.Contains( new InterchangeablePair<EventBean, EventBean>( events[2], events[0] ) ) );

			eventPairs.Remove( new InterchangeablePair<EventBean, EventBean>( events[2], events[0] ) );
			Assert.IsFalse( eventPairs.Contains( new InterchangeablePair<EventBean, EventBean>( events[2], events[0] ) ) );
			eventPairs.Remove( new InterchangeablePair<EventBean, EventBean>( events[1], events[2] ) );
			eventPairs.Remove( new InterchangeablePair<EventBean, EventBean>( events[1], events[0] ) );

			Assert.IsTrue( eventPairs.Count == 0 );
		}
	}
}
