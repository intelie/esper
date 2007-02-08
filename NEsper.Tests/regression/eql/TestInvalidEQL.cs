using System;

using net.esper.client;
using net.esper.eql.parse;
using net.esper.regression.pattern;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{

	[TestFixture]
	public class TestInvalidEQL
	{
		private EPServiceProvider epService;

		[SetUp]
		public virtual void setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
		}

		[Test]
		public virtual void testSyntaxException()
		{
			String EVENT = typeof( SupportBean_N ).FullName;

			String exceptionText = getSyntaxExceptionEQL( "select * from *" );
			Assert.AreEqual( "unexpected token: * near line 1, column 15 [select * from *]", exceptionText );
		}

		[Test]
		public virtual void testDifferentJoins()
		{
			String streamDef = "select * from " + typeof( SupportBean ).FullName + ".win:length(3) as sa," + typeof( SupportBean ).FullName + ".win:length(3) as sb" + " where ";

			String streamDefTwo = "select * from " + typeof( SupportBean ).FullName + ".win:length(3)," + typeof( SupportMarketDataBean ).FullName + ".win:length(3)" + " where ";

			tryInvalid( streamDef + "sa.IntPrimitive = sb.string" );
			tryValid( streamDef + "sa.IntPrimitive = sb.IntBoxed" );
			tryValid( streamDef + "sa.IntPrimitive = sb.IntPrimitive" );
			tryValid( streamDef + "sa.IntPrimitive = sb.longBoxed" );

			tryInvalid( streamDef + "sa.IntPrimitive = sb.IntPrimitive and sb.IntBoxed = sa.boolPrimitive" );
			tryValid( streamDef + "sa.IntPrimitive = sb.IntPrimitive and sb.BoolBoxed = sa.boolPrimitive" );

			tryInvalid( streamDef + "sa.IntPrimitive = sb.IntPrimitive and sb.IntBoxed = sa.IntPrimitive and sa.string=sX.string" );
			tryValid( streamDef + "sa.IntPrimitive = sb.IntPrimitive and sb.IntBoxed = sa.IntPrimitive and sa.string=sb.string" );

			tryInvalid( streamDef + "sa.IntPrimitive = sb.IntPrimitive or sa.string=sX.string" );
			tryValid( streamDef + "sa.IntPrimitive = sb.IntPrimitive or sb.IntBoxed = sa.IntPrimitive" );

			// try constants
			tryValid( streamDef + "sa.IntPrimitive=5" );
			tryValid( streamDef + "sa.string='4'" );
			tryValid( streamDef + "sa.string=\"4\"" );
			tryValid( streamDef + "sa.boolPrimitive=false" );
			tryValid( streamDef + "sa.longPrimitive=-5L" );
			tryValid( streamDef + "sa.doubleBoxed=5.6d" );
			tryValid( streamDef + "sa.floatPrimitive=-5.6f" );

			tryInvalid( streamDef + "sa.IntPrimitive='5'" );
			tryInvalid( streamDef + "sa.string=5" );
			tryInvalid( streamDef + "sa.BoolBoxed=f" );
			tryInvalid( streamDef + "sa.IntPrimitive=x" );
			tryValid( streamDef + "sa.IntPrimitive=5.5" );

			// try addition and subtraction
			tryValid( streamDef + "sa.IntPrimitive=sa.IntBoxed + 5" );
			tryValid( streamDef + "sa.IntPrimitive=2*sa.IntBoxed - sa.IntPrimitive/10 + 1" );
			tryValid( streamDef + "sa.IntPrimitive=2*(sa.IntBoxed - sa.IntPrimitive)/(10 + 1)" );
			tryInvalid( streamDef + "sa.IntPrimitive=2*(sa.IntBoxed" );

			// try comparison
			tryValid( streamDef + "sa.IntPrimitive > sa.IntBoxed and sb.doublePrimitive < sb.doubleBoxed" );
			tryValid( streamDef + "sa.IntPrimitive >= sa.IntBoxed and sa.doublePrimitive <= sa.doubleBoxed" );
			tryValid( streamDef + "sa.IntPrimitive > (sa.IntBoxed + sb.doublePrimitive)" );
			tryInvalid( streamDef + "sa.IntPrimitive >= sa.string" );
			tryInvalid( streamDef + "sa.BoolBoxed >= sa.BoolPrimitive" );

			// Try some nested
			tryValid( streamDef + "(sa.IntPrimitive=3) or (sa.IntBoxed=3 and sa.IntPrimitive=1)" );
			tryValid( streamDef + "((sa.IntPrimitive>3) or (sa.IntBoxed<3)) and sa.BoolBoxed=false" );
			tryValid( streamDef + "(sa.IntPrimitive<=3 and sa.IntPrimitive>=1) or (sa.BoolBoxed=false and sa.boolPrimitive=true)" );
			tryInvalid( streamDef + "sa.IntPrimitive=3 or (sa.IntBoxed=2" );
			tryInvalid( streamDef + "sa.IntPrimitive=3 or sa.IntBoxed=2)" );
			tryInvalid( streamDef + "sa.IntPrimitive=3 or ((sa.IntBoxed=2)" );

			// Try some without stream name
			tryInvalid( streamDef + "intPrimitive=3" );
			tryValid( streamDefTwo + "intPrimitive=3" );

			// Try invalid outer join criteria
			String outerJoinDef = "select * from " + typeof( SupportBean ).FullName + ".win:length(3) as sa " + "left outer join " + typeof( SupportBean ).FullName + ".win:length(3) as sb ";
			tryInvalid( outerJoinDef + "" );
			tryValid( outerJoinDef + "on sa.IntPrimitive = sb.IntBoxed" );
			tryInvalid( outerJoinDef + "on sa.IntPrimitive = sb.XX" );
			tryInvalid( outerJoinDef + "on sa.XX = sb.XX" );
			tryInvalid( outerJoinDef + "on sa.XX = sb.IntBoxed" );
			tryInvalid( outerJoinDef + "on sa.BoolBoxed = sb.IntBoxed" );
			tryValid( outerJoinDef + "on sa.boolPrimitive = sb.BoolBoxed" );
			tryInvalid( outerJoinDef + "on sa.boolPrimitive = sb.string" );
			tryInvalid( outerJoinDef + "on sa.IntPrimitive <= sb.IntBoxed" );
			tryInvalid( outerJoinDef + "on sa.IntPrimitive = sa.IntBoxed" );
			tryInvalid( outerJoinDef + "on sb.IntPrimitive = sb.IntBoxed" );
			tryValid( outerJoinDef + "on sb.IntPrimitive = sa.IntBoxed" );
		}

		private void tryInvalid( String eqlInvalidEQL )
		{
			try
			{
				epService.EPAdministrator.createEQL( eqlInvalidEQL );
				Assert.Fail();
			}
			catch ( EPException )
			{
				// Expected exception
			}
		}

		private void tryValid( String eqlInvalidEQL )
		{
			epService.EPAdministrator.createEQL( eqlInvalidEQL );
		}

		private String getSyntaxExceptionEQL( String expression )
		{
			String exceptionText = null;
			try
			{
				epService.EPAdministrator.createEQL( expression );
				Assert.Fail();
			}
			catch ( EPStatementSyntaxException ex )
			{
				exceptionText = ex.Message;
				log.Debug( ".getSyntaxExceptionEQL eql=" + expression, ex );
				// Expected exception
			}

			return exceptionText;
		}

		private static readonly Log log = LogFactory.GetLog( System.Reflection.MethodBase.GetCurrentMethod().DeclaringType );
	}
}
