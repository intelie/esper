using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{
	[TestFixture]
	public class TestSortedDoubleVector
	{
		private SortedDoubleVector vector;

		[SetUp]
		public virtual void setUp()
		{
			vector = new SortedDoubleVector();
		}

		[Test]
		public virtual void testAdd()
		{
			Assert.AreEqual( 0, vector.Count );

			vector.Add( 10 );
			vector.Add( 0 );
			vector.Add( 5 );
			double[] expected = new double[] { 0, 5, 10 };
			compare( expected, vector );

			vector.Add( 10 );
			vector.Add( 1 );
			vector.Add( 5.5 );
			expected = new double[] { 0, 1, 5, 5.5, 10, 10 };
			compare( expected, vector );

			vector.Add( 9 );
			vector.Add( 2 );
			vector.Add( 5.5 );
			expected = new double[] { 0, 1, 2, 5, 5.5, 5.5, 9, 10, 10 };
			compare( expected, vector );
		}

		[Test]
		public virtual void testRemove()
		{
			vector.Add( 5 );
			vector.Add( 1 );
			vector.Add( 0 );
			vector.Add( -1 );
			vector.Add( 1 );
			vector.Add( 0.5 );
			double[] expected = new double[] { -1, 0, 0.5, 1, 1, 5 };
			compare( expected, vector );

			vector.Remove( 1 );
			expected = new double[] { -1, 0, 0.5, 1, 5 };
			compare( expected, vector );

			vector.Remove( -1 );
			vector.Add( 5 );
			expected = new double[] { 0, 0.5, 1, 5, 5 };
			compare( expected, vector );

			vector.Remove( 5 );
			vector.Remove( 5 );
			expected = new double[] { 0, 0.5, 1 };
			compare( expected, vector );

			vector.Add( 99 );
			vector.Remove( 99 );
			try
			{
				vector.Remove( 99 );
				Assert.Fail();
			}
			catch ( System.SystemException ex )
			{
				// expected
			}
		}

		[Test]
		public virtual void testFindInsertIndex()
		{
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 1 ) );

			// test distinct values, 10 to 80
			this.vector.Values.Add( 10D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 11 ) );

			this.vector.Values.Add( 20D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 11 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 19 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 20 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 21 ) );

			this.vector.Values.Add( 30D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 11 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 19 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 20 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 21 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 29 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 30 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 31 ) );

			this.vector.Values.Add( 40D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 11 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 19 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 20 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 21 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 29 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 30 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 31 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 39 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 40 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 41 ) );

			this.vector.Values.Add( 50D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 11 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 19 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 20 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 21 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 29 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 30 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 31 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 39 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 40 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 41 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 49 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 50 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 51 ) );

			this.vector.Values.Add( 60D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 11 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 19 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 20 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 21 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 29 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 30 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 31 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 39 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 40 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 41 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 49 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 50 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 51 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 59 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 60 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 61 ) );

			this.vector.Values.Add( 70D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 11 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 19 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 20 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 21 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 29 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 30 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 31 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 39 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 40 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 41 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 49 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 50 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 51 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 59 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 60 ) );
			Assert.AreEqual( 6, this.vector.FindInsertIndex( 61 ) );
			Assert.AreEqual( 6, this.vector.FindInsertIndex( 69 ) );
			Assert.AreEqual( 6, this.vector.FindInsertIndex( 70 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 71 ) );

			this.vector.Values.Add( 80D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 10 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 11 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 19 ) );
			Assert.AreEqual( 1, this.vector.FindInsertIndex( 20 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 21 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 29 ) );
			Assert.AreEqual( 2, this.vector.FindInsertIndex( 30 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 31 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 39 ) );
			Assert.AreEqual( 3, this.vector.FindInsertIndex( 40 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 41 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 49 ) );
			Assert.AreEqual( 4, this.vector.FindInsertIndex( 50 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 51 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 59 ) );
			Assert.AreEqual( 5, this.vector.FindInsertIndex( 60 ) );
			Assert.AreEqual( 6, this.vector.FindInsertIndex( 61 ) );
			Assert.AreEqual( 6, this.vector.FindInsertIndex( 69 ) );
			Assert.AreEqual( 6, this.vector.FindInsertIndex( 70 ) );
			Assert.AreEqual( 7, this.vector.FindInsertIndex( 71 ) );
			Assert.AreEqual( 7, this.vector.FindInsertIndex( 79 ) );
			Assert.AreEqual( 7, this.vector.FindInsertIndex( 80 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 81 ) );

			// test homogenous values, all 1
			this.vector.Values.Clear();
			this.vector.Values.Add( 1D );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 0 ) );
			Assert.AreEqual( 0, this.vector.FindInsertIndex( 1 ) );
			Assert.AreEqual( -1, this.vector.FindInsertIndex( 2 ) );
			for ( int i = 0 ; i < 100 ; i++ )
			{
				this.vector.Values.Add( 1D );
                Assert.AreEqual(0, this.vector.FindInsertIndex(0), "for i=" + i);
                Assert.IsTrue(this.vector.FindInsertIndex(1) != -1, "for i=" + i);
                Assert.AreEqual(-1, this.vector.FindInsertIndex(2), "for i=" + i);
			}

			// test various other cases
			double[] vector = new double[] { 1, 1, 2, 2, 2, 3, 4, 5, 5, 6 };
			Assert.AreEqual( 0, findIndex( vector, 0 ) );
			Assert.AreEqual( 0, findIndex( vector, 0.5 ) );
			Assert.AreEqual( 0, findIndex( vector, 1 ) );
			Assert.AreEqual( 2, findIndex( vector, 1.5 ) );
			Assert.AreEqual( 2, findIndex( vector, 2 ) );
			Assert.AreEqual( 5, findIndex( vector, 2.5 ) );
			Assert.AreEqual( 5, findIndex( vector, 3 ) );
			Assert.AreEqual( 6, findIndex( vector, 3.5 ) );
			Assert.AreEqual( 6, findIndex( vector, 4 ) );
			Assert.AreEqual( 7, findIndex( vector, 4.5 ) );
			Assert.AreEqual( 7, findIndex( vector, 5 ) );
			Assert.AreEqual( 9, findIndex( vector, 5.5 ) );
			Assert.AreEqual( 9, findIndex( vector, 6 ) );
			Assert.AreEqual( -1, findIndex( vector, 6.5 ) );
			Assert.AreEqual( -1, findIndex( vector, 7 ) );

			// test various other cases
			vector = new double[] { 1, 8, 100, 1000, 1000, 10000, 10000, 99999 };
			Assert.AreEqual( 0, findIndex( vector, 0 ) );
			Assert.AreEqual( 0, findIndex( vector, 1 ) );
			Assert.AreEqual( 1, findIndex( vector, 2 ) );
			Assert.AreEqual( 1, findIndex( vector, 7 ) );
			Assert.AreEqual( 1, findIndex( vector, 8 ) );
			Assert.AreEqual( 2, findIndex( vector, 9 ) );
			Assert.AreEqual( 2, findIndex( vector, 99 ) );
			Assert.AreEqual( 2, findIndex( vector, 100 ) );
			Assert.AreEqual( 3, findIndex( vector, 101 ) );
			Assert.AreEqual( 3, findIndex( vector, 999 ) );
			Assert.AreEqual( 4, findIndex( vector, 1000 ) );
			Assert.AreEqual( 5, findIndex( vector, 1001 ) );
			Assert.AreEqual( 5, findIndex( vector, 9999 ) );
			Assert.AreEqual( 6, findIndex( vector, 10000 ) );
			Assert.AreEqual( 7, findIndex( vector, 10001 ) );
			Assert.AreEqual( 7, findIndex( vector, 99998 ) );
			Assert.AreEqual( 7, findIndex( vector, 99999 ) );
			Assert.AreEqual( -1, findIndex( vector, 100000 ) );
		}

		private int findIndex( double[] data, double value )
		{
			vector.Values.Clear();
			for ( int i = 0 ; i < data.Length ; i++ )
			{
				vector.Values.Add( data[i] );
			}
			return vector.FindInsertIndex( value );
		}

		private void compare( double[] expected, SortedDoubleVector vector )
		{
			Assert.AreEqual( expected.Length, vector.Count );
			for ( int i = 0 ; i < expected.Length ; i++ )
			{
				Assert.AreEqual( expected[i], vector[i] ) ;
			}
		}
	}
}
