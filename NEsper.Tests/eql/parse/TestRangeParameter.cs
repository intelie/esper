using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestRangeParameter
	{
		[Test]
		public virtual void testIsWildcard()
		{
			RangeParameter rangeParameter = new RangeParameter( 10, 20 );
			Assert.IsTrue( rangeParameter.IsWildcard( 10, 20 ) );
			Assert.IsFalse( rangeParameter.IsWildcard( 11, 20 ) );
			Assert.IsFalse( rangeParameter.IsWildcard( 10, 19 ) );
			Assert.IsTrue( rangeParameter.IsWildcard( 9, 21 ) );
		}

		[Test]
		public virtual void testGetValues()
		{
			RangeParameter rangeParameter = new RangeParameter( 0, 5 );
			ISet<int> values = rangeParameter.GetValuesInRange( 1, 3 );
			ArrayAssertionUtil.assertEqualsAnyOrder( new int[] { 1, 2, 3 }, values );

			values = rangeParameter.GetValuesInRange( -2, 3 );
			ArrayAssertionUtil.assertEqualsAnyOrder( new int[] { 0, 1, 2, 3 }, values );

			values = rangeParameter.GetValuesInRange( 4, 6 );
			ArrayAssertionUtil.assertEqualsAnyOrder( new int[] { 4, 5 }, values );

			values = rangeParameter.GetValuesInRange( 10, 20 );
			ArrayAssertionUtil.assertEqualsAnyOrder( new int[] { }, values );

			values = rangeParameter.GetValuesInRange( -7, -1 );
			ArrayAssertionUtil.assertEqualsAnyOrder( new int[] { }, values );
		}
	}
}
