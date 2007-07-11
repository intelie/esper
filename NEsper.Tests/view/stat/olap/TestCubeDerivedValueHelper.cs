using System;

using net.esper.collection;
using net.esper.support.util;
using net.esper.support.view.olap;
using net.esper.view;
using net.esper.view.stat;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view.stat.olap
{
	[TestFixture]
	public class TestCubeDerivedValueHelper
	{
		[Test]
		public void testDerive()
		{
			// Test on a 2-dimensional cube
			MultidimCube<BaseStatisticsBean> testCube = SupportCubeFactory.make2DimSchema();

			String[] measureList = {
				ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name,
				ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name,
				ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name,
				ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name,
				ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name,
				ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name
			};

			// derive
			Pair<Dimension[], Cell[]> result = CubeDerivedValueHelper.Derive( measureList, testCube );

			Dimension[] dimensions = result.First;
			Cell[] measures = result.Second;

			// Now its 3-dimensional, dimension 0 contains all derived fields for each cell containing a fact
			Assert.IsTrue( dimensions.Length == 3 );
			Assert.IsTrue( measures.Length == 12 * 6 );

			// Check each dimension and member for correct references
			for ( int dimension = 0 ; dimension < dimensions.Length ; dimension++ )
			{
				DimensionMember[] members = dimensions[dimension].GetMembers();

				for ( int i = 0 ; i < members.Length ; i++ )
				{
					Assert.IsTrue( members[i].Dimension == dimensions[dimension] );
				}
			}

			// Check cell dimension member object values - should match derived measure name
			for ( int i = 0 ; i < dimensions[0].GetMembers().Length ; i++ )
			{
				DimensionMember dimensionMember = dimensions[0].GetMembers()[i];
				Assert.AreEqual( dimensionMember.Values[0], measureList[i] );
			}

			// Check some derived values
			double[] results = new double[] { 1, 1, 1, 0, Double.NaN, Double.NaN };
			checkMeasures( results, measures, 0 );

			results = new double[] { 2, 10, 5, 3, 4.242640687, 18 };
			checkMeasures( results, measures, 6 * 4 ); // cell a-y

			results = new double[] { 2, 14, 7, 3, 4.242640687, 18 };
			checkMeasures( results, measures, 6 * 10 ); // cell c-z
		}

		private void checkMeasures( double[] results, Cell[] measures, int offset )
		{
			for ( int i = 0 ; i < results.Length ; i++ )
			{
				double value = measures[offset + i].Value;
				Assert.IsTrue( DoubleValueAssertionUtil.Equals( value, results[i], 6 ) );
			}
		}
	}
}
