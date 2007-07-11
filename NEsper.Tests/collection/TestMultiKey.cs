using System;

using net.esper.compat;
using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
	[TestFixture]
	public class TestMultiKey
	{
		MultiKey<String> keys1 = new MultiKey<String>( new String[] { "a", "b" } );
		MultiKey<String> keys2 = new MultiKey<String>( new String[] { "a", "b" } );
		MultiKey<String> keys3 = new MultiKey<String>( new String[] { "a", null } );
		MultiKey<String> keys4 = new MultiKey<String>( new String[] { null, "b" } );
		MultiKey<String> keys5 = new MultiKey<String>( new String[] { null, null } );
		MultiKey<String> keys6 = new MultiKey<String>( new String[] { "a" } );
		MultiKey<String> keys7 = new MultiKey<String>( new String[] { "a", "b", "c" } );
		MultiKey<String> keys8 = new MultiKey<String>( new String[] { "a", "b", null } );
		MultiKey<String> keys9 = new MultiKey<String>( new String[] { "a", "b", "c", "d" } );
		MultiKey<String> keys10 = new MultiKey<String>( new String[] { "a", "b", "c", "d" } );

		[Test]
		public void testHashCode()
		{
			Assert.IsTrue( keys1.GetHashCode() == ( "a".GetHashCode() ^ "b".GetHashCode() ) );
			Assert.IsTrue( keys10.GetHashCode() == ( "a".GetHashCode() ^ "b".GetHashCode() ^ "c".GetHashCode() ^ "d".GetHashCode() ) );
			Assert.IsTrue( keys3.GetHashCode() == "a".GetHashCode() );
			Assert.IsTrue( keys4.GetHashCode() == "b".GetHashCode() );
			Assert.IsTrue( keys5.GetHashCode() == 0 );

			Assert.IsTrue( keys8.GetHashCode() == keys1.GetHashCode() );
			Assert.IsTrue( keys1.GetHashCode() == keys2.GetHashCode() );
			Assert.IsTrue( keys1.GetHashCode() != keys3.GetHashCode() );
			Assert.IsTrue( keys1.GetHashCode() != keys4.GetHashCode() );
			Assert.IsTrue( keys1.GetHashCode() != keys5.GetHashCode() );

			Assert.IsTrue( keys7.GetHashCode() != keys8.GetHashCode() );
			Assert.IsTrue( keys9.GetHashCode() == keys10.GetHashCode() );
		}

		[Test]
		public void testEquals()
		{
			Assert.AreEqual( keys2, keys1 );
			Assert.AreEqual( keys1, keys2 );

			Assert.IsFalse( keys1.Equals( keys3 ) );
			Assert.IsFalse( keys3.Equals( keys1 ) );
			Assert.IsFalse( keys1.Equals( keys4 ) );
			Assert.IsFalse( keys2.Equals( keys5 ) );
			Assert.IsFalse( keys3.Equals( keys4 ) );
			Assert.IsFalse( keys4.Equals( keys5 ) );

			Assert.IsTrue( keys1.Equals( keys1 ) );
			Assert.IsTrue( keys2.Equals( keys2 ) );
			Assert.IsTrue( keys3.Equals( keys3 ) );
			Assert.IsTrue( keys4.Equals( keys4 ) );
			Assert.IsTrue( keys5.Equals( keys5 ) );

			Assert.IsFalse( keys1.Equals( keys7 ) );
			Assert.IsFalse( keys1.Equals( keys8 ) );
			Assert.IsFalse( keys1.Equals( keys9 ) );
			Assert.IsFalse( keys1.Equals( keys10 ) );
			Assert.IsTrue( keys9.Equals( keys10 ) );
		}

		[Test]
		public void testGet()
		{
			Assert.AreEqual( 1, keys6.Count );
			Assert.AreEqual( 2, keys1.Count );
			Assert.AreEqual( 3, keys8.Count );
			Assert.AreEqual( 4, keys9.Count );

			Assert.AreEqual( "a", keys1[0] );
			Assert.AreEqual( "b", keys1[1] );
			Assert.IsTrue( null == keys4[0] );
			Assert.IsTrue( Object.Equals( "d", keys10[3] ) );
		}

		[Test]
		public void testWithSet()
		{
			EventBean[][] testEvents = new EventBean[][] {
				SupportEventBeanFactory.MakeEvents( new String[] { "a", "b" } ), 
				SupportEventBeanFactory.MakeEvents( new String[] { "a" } ), 
				SupportEventBeanFactory.MakeEvents( new String[] { "a", "b", "c" } ), 
				SupportEventBeanFactory.MakeEvents( new String[] { "a", "b" } )
			};

			Set<MultiKey<EventBean>> mapSet = new HashSet<MultiKey<EventBean>>();

			// Test contains
			mapSet.Add( new MultiKey<EventBean>( testEvents[0] ) );
			Assert.IsTrue( mapSet.Contains( new MultiKey<EventBean>( testEvents[0] ) ) );
			Assert.IsFalse( mapSet.Contains( new MultiKey<EventBean>( testEvents[1] ) ) );
			Assert.IsFalse( mapSet.Contains( new MultiKey<EventBean>( testEvents[2] ) ) );
			Assert.IsFalse( mapSet.Contains( new MultiKey<EventBean>( testEvents[3] ) ) );

			// Test unique
			mapSet.Add( new MultiKey<EventBean>( testEvents[0] ) );
			Assert.AreEqual( 1, mapSet.Count );

			mapSet.Add( new MultiKey<EventBean>( testEvents[1] ) );
			mapSet.Add( new MultiKey<EventBean>( testEvents[2] ) );
			mapSet.Add( new MultiKey<EventBean>( testEvents[3] ) );
			Assert.AreEqual( 4, mapSet.Count );

			mapSet.Remove( new MultiKey<EventBean>( testEvents[0] ) );
			Assert.AreEqual( 3, mapSet.Count );
			Assert.IsFalse( mapSet.Contains( new MultiKey<EventBean>( testEvents[0] ) ) );

			mapSet.Remove( new MultiKey<EventBean>( testEvents[1] ) );
			mapSet.Remove( new MultiKey<EventBean>( testEvents[2] ) );
			mapSet.Remove( new MultiKey<EventBean>( testEvents[3] ) );
			Assert.AreEqual( 0, mapSet.Count );
		}
	}
}
