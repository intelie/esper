using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.table
{
	[TestFixture]
	public class TestPropertyIndexedEventTable
	{
		private String[] propertyNames;
		private EventType eventType;
		private EventBean[] testEvents;
		private PropertyIndexedEventTable index;

		[SetUp]
		public virtual void setUp()
		{
			propertyNames = new String[] { "IntPrimitive", "string" };
			eventType = SupportEventTypeFactory.createBeanType( typeof( SupportBean ) );
			index = new PropertyIndexedEventTable( 1, eventType, propertyNames );

			// Populate with testEvents
			int[] intValues = new int[] { 0, 1, 1, 2, 1, 0 };
			String[] stringValues = new String[] { "a", "b", "c", "a", "b", "c" };

			testEvents = new EventBean[intValues.Length];
			for ( int i = 0 ; i < intValues.Length ; i++ )
			{
				testEvents[i] = makeBean( intValues[i], stringValues[i] );
			}
			index.Add( testEvents );
		}

		[Test]
		public virtual void testFind()
		{
			ISet<EventBean> result = index.lookup( new Object[] { 1, "a" } );
			Assert.IsNull( result );

			result = index.lookup( new Object[] { 1, "b" } );
			Assert.AreEqual( 2, result.Count );
			Assert.IsTrue( result.Contains( testEvents[1] ) );
			Assert.IsTrue( result.Contains( testEvents[4] ) );

			result = index.lookup( new Object[] { 0, "c" } );
			Assert.AreEqual( 1, result.Count );
			Assert.IsTrue( result.Contains( testEvents[5] ) );

			result = index.lookup( new Object[] { 0, "a" } );
			Assert.AreEqual( 1, result.Count );
			Assert.IsTrue( result.Contains( testEvents[0] ) );
		}

		[Test]
		public virtual void testAdd()
		{
			// Add event without these properties should fail
			EventBean _event = SupportEventBeanFactory.createObject( new SupportBean_A( "d" ) );
			try
			{
				index.Add( new EventBean[] { _event } );
				Assert.Fail();
			}
			catch ( PropertyAccessException )
			{
				// Expected
			}

			// Add null should fail
			try
			{
				index.Add( new EventBean[] { null } );
				Assert.Fail();
			}
			catch ( NullReferenceException )
			{
				// Expected
			}

			// Same event added twice fails
			_event = makeBean( 1, "aa" );
			index.Add( new EventBean[] { _event } );
			try
			{
				index.Add( new EventBean[] { _event } );
				Assert.Fail();
			}
			catch ( ArgumentException )
			{
				// Expected
			}
		}

		[Test]
		public virtual void testRemove()
		{
			index.Remove( testEvents );
		}

		[Test]
		public virtual void testAddArray()
		{
			index = new PropertyIndexedEventTable( 1, eventType, propertyNames );

			// Add just 2
			EventBean[] events = new EventBean[2];
			events[0] = testEvents[1];
			events[1] = testEvents[4];
			index.Add( events );

			ISet<EventBean> result = index.lookup( new Object[] { 1, "b" } );
			Assert.AreEqual( 2, result.Count );

			try
			{
				index.Add( testEvents );
				Assert.Fail();
			}
			catch ( ArgumentException ex )
			{
				// expected
			}
		}

		[Test]
		public virtual void testRemoveArray()
		{
			index.Remove( testEvents );

			ISet<EventBean> result = index.lookup( new Object[] { 1, "b" } );
			Assert.IsNull( result );

			// Remove again - already removed but won't throw an exception
			index.Remove( testEvents );
		}

		[Test]
		public virtual void testMixed()
		{
			index.Remove( new EventBean[] { testEvents[1] } );
			ISet<EventBean> result = index.lookup( new Object[] { 1, "b" } );
			Assert.AreEqual( 1, result.Count );
			Assert.IsTrue( result.Contains( testEvents[4] ) );

			index.Remove( new EventBean[] { testEvents[4] } );
			result = index.lookup( new Object[] { 1, "b" } );
			Assert.IsNull( result );

			index.Add( new EventBean[] { testEvents[1] } );
			result = index.lookup( new Object[] { 1, "b" } );
			Assert.AreEqual( 1, result.Count );
			Assert.IsTrue( result.Contains( testEvents[1] ) );
		}

		private EventBean makeBean( int intValue, String stringValue )
		{
			SupportBean bean = new SupportBean();
			bean.IntPrimitive = intValue;
			bean.StringValue = stringValue;
			return SupportEventBeanFactory.createObject( bean );
		}
	}
}
