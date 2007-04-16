using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.support.util
{
	public class ArrayAssertionUtil
	{
		/// <summary> Compare the objects in the two 2-dim String arrays assuming the exact same order.</summary>
		/// <param name="data">is the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>
		public static void assertEqualsStringArr( String[][] data, String[][] expectedValues )
		{
			if ( ( expectedValues == null ) && ( data == null ) )
			{
				return;
			}
			if ( ( ( expectedValues == null ) && ( data != null ) ) || ( ( expectedValues != null ) && ( data == null ) ) )
			{
				Assert.IsTrue( false );
			}

            Assert.AreEqual(expectedValues.Length, data.Length, "mismatch in number to elements");

			for ( int i = 0 ; i < expectedValues.Length ; i++ )
			{
				Assert.IsTrue( CollectionHelper.AreEqual( data[i], expectedValues[i] ) ) ;
			}
		}

		/// <summary> Compare the data in the two object arrays.</summary>
		/// <param name="data">is the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>
		public static void assertEqualsExactOrder( EventBean[] data, EventBean[] expectedValues )
		{
			assertEqualsExactOrder( (Object[]) data, (Object[]) expectedValues );
		}

        private static IEnumerator<Object> ConvertEnumerator<T>(IEnumerator<T> sourceEnum)
        {
            while( sourceEnum.MoveNext() )
            {
                yield return sourceEnum.Current;
            }
        }

		/// <summary> Iterate through the views collection and check the presence of all values supplied in the exact same order.</summary>
		/// <param name="iterator">is the iterator to iterate over and check returned values
		/// </param>
		/// <param name="expectedValues">is a map of expected values
		/// </param>
		public static void assertEqualsExactOrder( IEnumerator<EventBean> iterator, EventBean[] expectedValues )
		{
            IEnumerator<Object> objectEnum = ConvertEnumerator(iterator);
            Object[] objectArray = null;
            if (expectedValues != null)
            {
                objectArray = Array.ConvertAll(
                    expectedValues,
                    new Converter<EventBean, Object>(delegate(EventBean item) { return item; }));
            }

            assertEqualsExactOrder(objectEnum, objectArray);
		}

		/// <summary> Compare the objects in the two object arrays assuming the exact same order.</summary>
		/// <param name="data">is the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>
		public static void assertEqualsExactOrder( Object[] data, Object[] expectedValues )
		{
			if ( ( expectedValues == null ) && ( data == null ) )
			{
				return;
			}

			if ((( expectedValues == null ) && ( data != null )) ||
                (( expectedValues != null ) && ( data == null )))
			{
                Assert.Fail();
			}

			Assert.AreEqual( expectedValues.Length, data.Length,
                "Actual length and expected length are different" );

			for ( int i = 0 ; i < expectedValues.Length ; i++ )
			{
				Assert.AreEqual( expectedValues[i], data[i] );
			}
		}

		/// <summary> Compare the integer values in the two int arrays assuming the exact same order.</summary>
		/// <param name="data">is the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>
		public static void assertEqualsExactOrder( int[] data, int[] expectedValues )
		{
			if ( ( expectedValues == null ) && ( data == null ) )
			{
				return;
			}
			if ( ( ( expectedValues == null ) && ( data != null ) ) || ( ( expectedValues != null ) && ( data == null ) ) )
			{
				Assert.IsTrue( false );
			}

			Assert.AreEqual( expectedValues.Length, data.Length );

			for ( int i = 0 ; i < expectedValues.Length ; i++ )
			{
				Assert.AreEqual( expectedValues[i], data[i] );
			}
		}

        /// <summary>
        /// Compare the objects in the two enumerations.
        /// </summary>
        /// <param name="iterator">returns the data to Assert.AreEqualExactOrder against</param>
        /// <param name="expected">The expected.</param>

        public static void assertEqualsExactOrder<T>(IEnumerable<T> iterator, IEnumerable<T> expected)
        {
            assertEqualsExactOrder<T>(
                iterator.GetEnumerator(),
                expected.GetEnumerator());
        }

        /// <summary>
        /// Compare the objects in the two enumerations.
        /// </summary>
        /// <param name="iterator">returns the data to Assert.AreEqualExactOrder against</param>
        /// <param name="expected">The expected.</param>

        public static void assertEqualsExactOrder<T>(IEnumerator<T> iterator, IEnumerator<T> expected)
        {
            while (true)
            {
                bool testA = iterator.MoveNext();
                bool testB = expected.MoveNext();
                if (testA || testB)
                {
                    Assert.AreEqual(testA, testB);
                }
                else
                {
                    return; // test complete
                }

                T itemA = iterator.Current;
                T itemB = expected.Current;

                Assert.AreEqual(itemA, itemB);
            }
        }


		/// <summary> Compare the boolean values in the two bool arrays assuming the exact same order.</summary>
		/// <param name="data">is the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>
		public static void assertEqualsExactOrder( bool[] data, bool[] expectedValues )
		{
			if ( ( expectedValues == null ) && ( data == null ) )
			{
				return;
			}
			if ( ( ( expectedValues == null ) && ( data != null ) ) || ( ( expectedValues != null ) && ( data == null ) ) )
			{
				Assert.IsTrue( false );
			}

			Assert.AreEqual( expectedValues.Length, data.Length );

			for ( int i = 0 ; i < expectedValues.Length ; i++ )
			{
				Assert.AreEqual( expectedValues[i], data[i] );
			}
		}

		/// <summary> Compare the objects in the two object arrays.</summary>
		/// <param name="iterator">returns the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>

        public static void assertEqualsExactOrder( IEnumerator<Object> iterator, Object[] expectedValues )
		{
			List<Object> values = new List<Object>();
			while ( iterator.MoveNext() )
			{
				values.Add( iterator.Current );
			}

            Assert.IsFalse(iterator.MoveNext());

			Object[] data = null;
			if ( values.Count > 0 )
			{
				data = values.ToArray();
			}

			assertEqualsExactOrder( data, expectedValues );
		}

		public static void assertEqualsAnyOrder( int[] expected, ISet<int> intSet )
		{
			if ( ( intSet == null ) && ( expected == null ) )
			{
				return;
			}

            Assert.AreEqual(expected.Length, intSet.Count, "length mismatch");
			for ( int i = 0 ; i < expected.Length ; i++ )
			{
				Assert.IsTrue( intSet.Contains( expected[i] ), "not found: " + expected[i] );
			}
		}


		public static void assertEqualsAnyOrder( int[] expected, int[] result )
		{
			Assert.AreEqual( expected.Length, result.Length, "length mismatch" );

			ISet<int> intSet = new EHashSet<int>();
			for ( int i = 0 ; i < result.Length ; i++ )
			{
				intSet.Add( result[i] );
			}

			assertEqualsAnyOrder( expected, intSet );
		}

        public static void assertEqualsAnyOrder<T>( ICollection<T> expected, ICollection<T> received)
        {
            // Empty lists are fine
            if (((received == null) && (expected == null)) ||
                ((received.Count == 0) && (expected == null)) || 
                ((received == null) && (expected.Count == 0)))
            {
                return;
            }

            // Same number
            Assert.AreEqual(expected.Count, received.Count);

            // For each expected object find a received object
            int numMatches = 0;
            foreach (T expectedObject in expected)
            {
                bool found = false;
                if ( received.Contains(expectedObject) )
                {
                    found = true ;
                    numMatches++;
                    continue ;
                }

                if (!found)
                {
                    log.Error(".Assert.AreEqualAnyOrder Not found in received results is expected=" + expectedObject);
                    log.Error(".Assert.AreEqualAnyOrder received=" + CollectionHelper.Render(received));
                }

                Assert.IsTrue(found);
            }

            // Must have matched exactly the number of objects times
            Assert.AreEqual(numMatches, expected.Count);
        }

		public static void assertEqualsAnyOrder( Object[] expected, Object[] received )
		{
			// Empty lists are fine
			if ( ( ( received == null ) && ( expected == null ) ) || ( ( received.Length == 0 ) && ( expected == null ) ) || ( ( received == null ) && ( expected.Length == 0 ) ) )
			{
				return;
			}

			// Same number
			Assert.AreEqual( expected.Length, received.Length );

			// For each expected object find a received object
			int numMatches = 0;
			foreach ( Object expectedObject in expected )
			{
				bool found = false;
				for ( int i = 0 ; i < received.Length ; i++ )
				{
					// Ignore found received objects
					if ( received[i] == null )
					{
						continue;
					}

					if ( received[i].Equals( expectedObject ) )
					{
						found = true;
						numMatches++;
						// Blank out received object so as to not match again
						received[i] = null;
						break;
					}
				}

				if ( !found )
				{
					log.Error( ".Assert.AreEqualAnyOrder Not found in received results is expected=" + expectedObject );
					log.Error( ".Assert.AreEqualAnyOrder received=" + CollectionHelper.Render( received ) );
				}
				Assert.IsTrue( found );
			}

			// Must have matched exactly the number of objects times
			Assert.AreEqual( numMatches, expected.Length );
		}

		public static void assertEqualsAnyOrder( EventBean[][] expected, EventBean[][] received )
		{
			// Empty lists are fine
			if ( ( ( received == null ) && ( expected == null ) ) || ( ( received.Length == 0 ) && ( expected == null ) ) || ( ( received == null ) && ( expected.Length == 0 ) ) )
			{
				return;
			}

			// Same number
			Assert.AreEqual( expected.Length, received.Length );

			// For each expected object find a received object
			int numMatches = 0;
			bool[] foundReceived = new bool[received.Length];
			foreach ( EventBean[] expectedObject in expected )
			{
				bool found = false;
				for ( int i = 0 ; i < received.Length ; i++ )
				{
					// Ignore found received objects
					if ( foundReceived[i] )
					{
						continue;
					}

					bool match = ArrayCompareUtil.compareEqualsExactOrder( received[i], expectedObject );
					if ( match )
					{
						found = true;
						numMatches++;
						foundReceived[i] = true;
						break;
					}
				}

				if ( !found )
				{
					log.Error( ".Assert.AreEqualAnyOrder Not found in received results is expected=" + CollectionHelper.Render( expectedObject ) );
					log.Error( ".Assert.AreEqualAnyOrder received=" + CollectionHelper.Render( received ) );
				}
				Assert.IsTrue( found );
			}

			// Must have matched exactly the number of objects times
			Assert.AreEqual( numMatches, expected.Length );
		}

		public static void assertRefAnyOrderArr( Object[][] expected, Object[][] received )
		{
			// Empty lists are fine
			if ( ( ( received == null ) && ( expected == null ) ) || ( ( received.Length == 0 ) && ( expected == null ) ) || ( ( received == null ) && ( expected.Length == 0 ) ) )
			{
				return;
			}

			// Same number
			Assert.AreEqual( expected.Length, received.Length );

			// For each expected object find a received object
			int numMatches = 0;
			bool[] foundReceived = new bool[received.Length];
			foreach ( Object[] expectedArr in expected )
			{
				bool found = false;
				for ( int i = 0 ; i < received.Length ; i++ )
				{
					// Ignore found received objects
					if ( foundReceived[i] )
					{
						continue;
					}

					bool match = ArrayCompareUtil.compareRefExactOrder( received[i], expectedArr );
					if ( match )
					{
						found = true;
						numMatches++;
						// Blank out received object so as to not match again
						foundReceived[i] = true;
						break;
					}
				}

				if ( !found )
				{
					log.Error( ".Assert.AreEqualAnyOrder Not found in received results is expected=" + CollectionHelper.Render( expectedArr ) );
					for ( int j = 0 ; j < received.Length ; j++ )
					{
						log.Error( ".Assert.AreEqualAnyOrder                              received (" + j + "):" + CollectionHelper.Render( received[j] ) );
					}
					Assert.Fail();
				}
			}

			// Must have matched exactly the number of objects times
			Assert.AreEqual( numMatches, expected.Length );
		}

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
