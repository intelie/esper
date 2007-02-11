using System;

using net.esper.client;
using net.esper.eql.parse;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	
	[TestFixture]
	public class TestInvalidView 
	{
		public TestInvalidView()
		{
			EVENT_NUM = typeof(SupportBean_N).FullName;
			EVENT_ALLTYPES = typeof(SupportBean).FullName;
		}

		private readonly String EVENT_NUM;
		private readonly String EVENT_ALLTYPES;
		
		private EPServiceProvider epService;
		
		[SetUp]
		public virtual void  setUp()
		{
			epService = EPServiceProviderManager.GetDefaultProvider();
			epService.Initialize();
		}
		
		[Test]
		public virtual void  testSyntaxException()
		{
			String exceptionText = getSyntaxExceptionView("select * * from " + EVENT_NUM);
			Assert.AreEqual("expecting \"from\", found '*' near line 1, column 10 [select * * from net.esper.support.bean.SupportBean_N]", exceptionText);
		}
		
		[Test]
		public virtual void  testStatementException()
		{
			String exceptionText = null;
			
			// class not found
			exceptionText = getStatementExceptionView("select * from dummypkg.dummy().win:length(10)");
			Assert.AreEqual("Failed to resolve event type: Failed to load class dummypkg.dummy [select * from dummypkg.dummy().win:length(10)]", exceptionText);
			
			// invalid view
			exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".dummy:dummy(10)");
			Assert.AreEqual("Error Starting view: View name 'dummy' is not a known view name [select * from net.esper.support.bean.SupportBean_N.dummy:dummy(10)]", exceptionText);
			
			// invalid view parameter
			exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length('s')");
			Assert.AreEqual("Error Starting view: Error invoking constructor for view 'length', the view parameter list is not valid for the view [select * from net.esper.support.bean.SupportBean_N.win:length('s')]", exceptionText);
			
			// where-clause equals has invalid type compare
			exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length(1) where doublePrimitive=true");
			Assert.AreEqual("Error validating expression: Implicit conversion from datatype 'java.lang.Double' to 'java.lang.Boolean' is not allowed [select * from net.esper.support.bean.SupportBean_N.win:length(1) where doublePrimitive=true]", exceptionText);
			
			// where-clause relational op has invalid type
			exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) where string > 5");
			Assert.AreEqual("Error validating expression: Implicit conversion from datatype 'System.String' to numeric is not allowed [select * from net.esper.support.bean.SupportBean.win:length(1) where string > 5]", exceptionText);
			
			// where-clause has aggregation function
			exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) where sum(intPrimitive) > 5");
			Assert.AreEqual("Error validating expression: An aggregate function may not appear in a WHERE clause (use the HAVING clause) [select * from net.esper.support.bean.SupportBean.win:length(1) where sum(intPrimitive) > 5]", exceptionText);
			
			// invalid numerical expression
			exceptionText = getStatementExceptionView("select 2 * 's' from " + EVENT_ALLTYPES + ".win:length(1)");
			Assert.AreEqual("Error Starting view: Implicit conversion from datatype 'System.String' to numeric is not allowed [select 2 * 's' from net.esper.support.bean.SupportBean.win:length(1)]", exceptionText);
			
			// invalid property in select
			exceptionText = getStatementExceptionView("select a[2].m('a') from " + EVENT_ALLTYPES + ".win:length(1)");
			Assert.AreEqual("Error Starting view: Property named 'a[2].m('a')' is not valid in any stream [select a[2].m('a') from net.esper.support.bean.SupportBean.win:length(1)]", exceptionText);
			
			// select clause uses same "as" name twice
			exceptionText = getStatementExceptionView("select 2 as m, 2 as m from " + EVENT_ALLTYPES + ".win:length(1)");
			Assert.AreEqual("Error Starting view: Property alias name 'm' appears more then once in select clause [select 2 as m, 2 as m from net.esper.support.bean.SupportBean.win:length(1)]", exceptionText);
			
			// class in method invocation not found
			exceptionText = getStatementExceptionView("select unknownClass.method() from " + EVENT_NUM + ".win:length(10)");
			Assert.AreEqual("Error Starting view: Unknown class unknownClass [select unknownClass.method() from net.esper.support.bean.SupportBean_N.win:length(10)]", exceptionText);
			
			// method not found
			exceptionText = getStatementExceptionView("select Math.unknownMethod() from " + EVENT_NUM + ".win:length(10)");
			Assert.AreEqual("Error Starting view: Unknown method Math.unknownMethod() [select Math.unknownMethod() from net.esper.support.bean.SupportBean_N.win:length(10)]", exceptionText);
			
			// invalid property in group-by
			exceptionText = getStatementExceptionView("select intPrimitive from " + EVENT_ALLTYPES + ".win:length(1) group by xxx");
			Assert.AreEqual("Error Starting view: Property named 'xxx' is not valid in any stream [select intPrimitive from net.esper.support.bean.SupportBean.win:length(1) group by xxx]", exceptionText);
			
			// group-by not specifying a property
			exceptionText = getStatementExceptionView("select intPrimitive from " + EVENT_ALLTYPES + ".win:length(1) group by 5");
			Assert.AreEqual("Error Starting view: Group-by expressions must refer to property names [select intPrimitive from net.esper.support.bean.SupportBean.win:length(1) group by 5]", exceptionText);
			
			// group-by specifying aggregates
			exceptionText = getStatementExceptionView("select intPrimitive from " + EVENT_ALLTYPES + ".win:length(1) group by sum(intPrimitive)");
			Assert.AreEqual("Error Starting view: Group-by expressions cannot contain aggregate functions [select intPrimitive from net.esper.support.bean.SupportBean.win:length(1) group by sum(intPrimitive)]", exceptionText);
			
			// group-by specifying a property that is aggregated through select clause
			exceptionText = getStatementExceptionView("select intPrimitive, sum(doublePrimitive) from " + EVENT_ALLTYPES + ".win:length(1) group by doublePrimitive");
			Assert.AreEqual("Error Starting view: Group-by property 'doublePrimitive' cannot also occur in an aggregate function in the select clause [select intPrimitive, sum(doublePrimitive) from net.esper.support.bean.SupportBean.win:length(1) group by doublePrimitive]", exceptionText);
			
			// invalid property in having clause
			exceptionText = getStatementExceptionView("select 2 * 's' from " + EVENT_ALLTYPES + ".win:length(1) group by intPrimitive having xxx > 5");
			Assert.AreEqual("Error Starting view: Implicit conversion from datatype 'System.String' to numeric is not allowed [select 2 * 's' from net.esper.support.bean.SupportBean.win:length(1) group by intPrimitive having xxx > 5]", exceptionText);
			
			// invalid having clause - not the same aggregate as used in select
			exceptionText = getStatementExceptionView("select sum(intPrimitive) from " + EVENT_ALLTYPES + ".win:length(1) group by intBoxed having sum(doubleBoxed) > 5");
			Assert.AreEqual("Error Starting view: Aggregate functions in the HAVING clause must match aggregate functions in the select clause [select sum(intPrimitive) from net.esper.support.bean.SupportBean.win:length(1) group by intBoxed having sum(doubleBoxed) > 5]", exceptionText);
			
			// invalid having clause - not a symbol in the group-by (non-aggregate)
			exceptionText = getStatementExceptionView("select sum(intPrimitive) from " + EVENT_ALLTYPES + ".win:length(1) group by intBoxed having doubleBoxed > 5");
			Assert.AreEqual("Error Starting view: Non-aggregated property 'doubleBoxed' in the HAVING clause must occur in the group-by clause [select sum(intPrimitive) from net.esper.support.bean.SupportBean.win:length(1) group by intBoxed having doubleBoxed > 5]", exceptionText);
			
			// invalid outer join - not a symbol
			exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as aStr " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) on xxxx=yyyy");
			Assert.AreEqual("Error validating expression: Property named 'xxxx' is not valid in any stream [select * from net.esper.support.bean.SupportBean.win:length(1) as aStr left outer join net.esper.support.bean.SupportBean.win:length(1) on xxxx=yyyy]", exceptionText);
			
			// invalid outer join for 3 streams - not a symbol
			exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as s0 " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s2 on s0.intPrimitive = s2.yyyy");
			Assert.AreEqual("Error validating expression: Failed to resolve property 's2.yyyy' to a stream or nested property in a stream [select * from net.esper.support.bean.SupportBean.win:length(1) as s0 left outer join net.esper.support.bean.SupportBean.win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive left outer join net.esper.support.bean.SupportBean.win:length(1) as s2 on s0.intPrimitive = s2.yyyy]", exceptionText);
			
			// invalid outer join for 3 streams - wrong stream, the properties in on-clause don't refer to streams
			exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as s0 " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s2 on s0.intPrimitive = s1.intPrimitive");
			Assert.AreEqual("Error validating expression: Outer join ON-clause must refer to at least one property of the joined stream for stream 2 [select * from net.esper.support.bean.SupportBean.win:length(1) as s0 left outer join net.esper.support.bean.SupportBean.win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive left outer join net.esper.support.bean.SupportBean.win:length(1) as s2 on s0.intPrimitive = s1.intPrimitive]", exceptionText);
			
			// invalid outer join - referencing next stream
			exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as s0 " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s1 on s2.intPrimitive = s1.intPrimitive " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s2 on s1.intPrimitive = s2.intPrimitive");
			Assert.AreEqual("Error validating expression: Outer join ON-clause invalid scope for property 'intPrimitive', expecting the current or a prior stream scope [select * from net.esper.support.bean.SupportBean.win:length(1) as s0 left outer join net.esper.support.bean.SupportBean.win:length(1) as s1 on s2.intPrimitive = s1.intPrimitive left outer join net.esper.support.bean.SupportBean.win:length(1) as s2 on s1.intPrimitive = s2.intPrimitive]", exceptionText);
			
			// invalid outer join - same properties
			exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length(1) as aStr " + "left outer join " + EVENT_ALLTYPES + ".win:length(1) on string=string");
			Assert.AreEqual("Error validating expression: Outer join ON-clause cannot refer to properties of the same stream [select * from net.esper.support.bean.SupportBean_N.win:length(1) as aStr left outer join net.esper.support.bean.SupportBean.win:length(1) on string=string]", exceptionText);
			
			// invalid order by
			exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length(1) as aStr order by X");
			Assert.AreEqual("Error Starting view: Property named 'X' is not valid in any stream [select * from net.esper.support.bean.SupportBean_N.win:length(1) as aStr order by X]", exceptionText);
			
			// insert into with wildcard - not allowed
			exceptionText = getStatementExceptionView("insert into Google select * from " + EVENT_NUM + ".win:length(1) as aStr");
			Assert.AreEqual("Error Starting view: Wildcard not allowed in combination with insert-into [insert into Google select * from net.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);
			
			// insert into with duplicate column names
			exceptionText = getStatementExceptionView("insert into Google (a, b, a) select boolBoxed, boolPrimitive, intBoxed from " + EVENT_NUM + ".win:length(1) as aStr");
			Assert.AreEqual("Error Starting view: Property name 'a' appears more then once in insert-into clause [insert into Google (a, b, a) select boolBoxed, boolPrimitive, intBoxed from net.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);
			
			// insert into mismatches selected columns
			exceptionText = getStatementExceptionView("insert into Google (a, b, c) select boolBoxed, boolPrimitive from " + EVENT_NUM + ".win:length(1) as aStr");
			Assert.AreEqual("Error Starting view: Number of supplied values in the select clause does not match insert-into clause [insert into Google (a, b, c) select boolBoxed, boolPrimitive from net.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);
			
			// mismatched type on coalesce columns
			exceptionText = getStatementExceptionView("select coalesce(boolBoxed, string) from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
			Assert.AreEqual("Error Starting view: Implicit conversion not allowed: Cannot coerce to Boolean type System.String [select coalesce(boolBoxed, string) from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);
			
			// mismatched case compare type
			exceptionText = getStatementExceptionView("select case boolPrimitive when 1 then true end from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
			Assert.AreEqual("Error Starting view: Implicit conversion not allowed: Cannot coerce to Boolean type java.lang.Integer [select case boolPrimitive when 1 then true end from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);
			
			// mismatched case result type
			exceptionText = getStatementExceptionView("select case when 1=2 then 1 when 1=3 then true end from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
			Assert.AreEqual("Error Starting view: Implicit conversion not allowed: Cannot coerce types java.lang.Integer and java.lang.Boolean [select case when 1=2 then 1 when 1=3 then true end from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);
			
			// case expression not returning bool
			exceptionText = getStatementExceptionView("select case when 3 then 1 end from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
			Assert.AreEqual("Error Starting view: Case node 'when' expressions must return a boolean value [select case when 3 then 1 end from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);
			
			// function not known
			exceptionText = getStatementExceptionView("select gogglex(1) from " + EVENT_NUM + ".win:length(1)");
			Assert.AreEqual("Unknown method named 'gogglex' could not be resolved [select gogglex(1) from net.esper.support.bean.SupportBean_N.win:length(1)]", exceptionText);
		}
		
		[Test]
		public virtual void  testInvalidView()
		{
			String eventClass = typeof(SupportBean).FullName;
			
			tryInvalid("select * from " + eventClass + "(dummy='a').win:length(3)");
			tryValid("select * from " + eventClass + "(string='a').win:length(3)");
			tryInvalid("select * from " + eventClass + ".dummy:length(3)");
			
			tryInvalid("select djdjdj from " + eventClass + ".win:length(3)");
			tryValid("select boolBoxed as xx, intPrimitive from " + eventClass + ".win:length(3)");
			tryInvalid("select boolBoxed as xx, intPrimitive as xx from " + eventClass + ".win:length(3)");
			tryValid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)");
			
			tryValid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)" + " where boolBoxed = true");
			tryInvalid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)" + " where xx = true");
		}
		
		private void  tryInvalid(String viewStmt)
		{
			try
			{
				epService.EPAdministrator.createEQL(viewStmt);
				Assert.Fail();
			}
			catch (ASTFilterSpecValidationException ex)
			{
				// expected
			}
			catch (EPException ex)
			{
				// Expected exception
			}
		}
		
		private String getSyntaxExceptionView(String expression)
		{
			String exceptionText = null;
			try
			{
				epService.EPAdministrator.createEQL(expression);
				Assert.Fail();
			}
			catch (EPStatementSyntaxException ex)
			{
				exceptionText = ex.Message;
				log.Debug(".getSyntaxExceptionView expression=" + expression, ex);
				// Expected exception
			}
			
			return exceptionText;
		}
		
		private String getStatementExceptionView(String expression)
		{
			return getStatementExceptionView(expression, false);
		}
		
		private String getStatementExceptionView(String expression, bool isLogException)
		{
			String exceptionText = null;
			try
			{
				epService.EPAdministrator.createEQL(expression);
				Assert.Fail();
			}
			catch (EPStatementSyntaxException es)
			{
				throw es;
			}
			catch (EPStatementException ex)
			{
				// Expected exception
				exceptionText = ex.Message;
				if (isLogException)
				{
					log.Debug(".getStatementExceptionView expression=" + expression, ex);
				}
			}
			
			return exceptionText;
		}
		
		private void  tryValid(String viewStmt)
		{
			epService.EPAdministrator.createEQL(viewStmt);
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
