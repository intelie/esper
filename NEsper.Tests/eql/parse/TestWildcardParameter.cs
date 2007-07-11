using System;

using net.esper.compat;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestWildcardParameter
	{
		private WildcardParameter wildcard;

		[SetUp]
		public virtual void setUp()
		{
			wildcard = new WildcardParameter();
		}

		[Test]
		public void testIsWildcard()
		{
			Assert.IsTrue( wildcard.IsWildcard( 1, 10 ) );
		}

		[Test]
		public void testGetValuesInRange()
		{
			Set<int> result = wildcard.GetValuesInRange( 1, 10 );
			for ( int i = 1 ; i <= 10 ; i++ )
			{
				Assert.IsTrue( result.Contains( i ) );
			}
			Assert.AreEqual( 10, result.Count );
		}
	}
}
