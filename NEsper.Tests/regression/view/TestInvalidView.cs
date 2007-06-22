///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.eql.parse;
using net.esper.support.bean;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestInvalidView
	{
	    private readonly String _event_NUM = typeof(SupportBean_N).FullName;
	    private readonly String _event_ALLTYPES = typeof(SupportBean).FullName;

	    private EPServiceProvider epService;

	    [SetUp]
	    public void SetUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void TestSyntaxException()
	    {
	        String exceptionText = GetSyntaxExceptionView("select * * from " + _event_NUM);
	        Assert.AreEqual("expecting \"from\", found '*' near line 1, column 10 [select * * from net.esper.support.bean.SupportBean_N]", exceptionText);
	    }

	    [Test]
	    public void TestStatementException()
	    {
	        String exceptionText = null;

	        // class not found
	        exceptionText = GetStatementExceptionView("select * from dummypkg.Dummy().win:length(10)");
	        Assert.AreEqual("Failed to resolve event type: Failed to load class dummypkg.dummy [select * from dummypkg.Dummy().win:length(10)]", exceptionText);

	        // invalid view
	        exceptionText = GetStatementExceptionView("select * from " + _event_NUM + ".dummy:dummy(10)");
	        Assert.AreEqual("Error starting view: View name 'dummy:dummy' is not a known view name [select * from net.esper.support.bean.SupportBean_N.dummy:dummy(10)]", exceptionText);

	        // invalid view parameter
	        exceptionText = GetStatementExceptionView("select * from " + _event_NUM + ".win:length('s')");
	        Assert.AreEqual("Error starting view: Error in view 'win:length', Length window view requires a single integer-type parameter [select * from net.esper.support.bean.SupportBean_N.win:length('s')]", exceptionText);

	        // where-clause equals has invalid type compare
	        exceptionText = GetStatementExceptionView("select * from " + _event_NUM + ".win:length(1) where doublePrimitive=true");
	        Assert.AreEqual("Error validating expression: Implicit conversion from datatype 'Boolean' to 'double?' is not allowed [select * from net.esper.support.bean.SupportBean_N.win:length(1) where doublePrimitive=true]", exceptionText);

	        // where-clause relational op has invalid type
	        exceptionText = GetStatementExceptionView("select * from " + _event_ALLTYPES + ".win:length(1) where string > 5");
	        Assert.AreEqual("Error validating expression: Implicit conversion from datatype 'String' to numeric is not allowed [select * from net.esper.support.bean.SupportBean.win:length(1) where string > 5]", exceptionText);

	        // where-clause has aggregation function
	        exceptionText = GetStatementExceptionView("select * from " + _event_ALLTYPES + ".win:length(1) where Sum(intPrimitive) > 5");
	        Assert.AreEqual("Error validating expression: An aggregate function may not appear in a WHERE clause (use the HAVING clause) [select * from net.esper.support.bean.SupportBean.win:length(1) where Sum(intPrimitive) > 5]", exceptionText);

	        // invalid numerical expression
	        exceptionText = GetStatementExceptionView("select 2 * 's' from " + _event_ALLTYPES + ".win:length(1)");
	        Assert.AreEqual("Error starting view: Implicit conversion from datatype 'String' to numeric is not allowed [select 2 * 's' from net.esper.support.bean.SupportBean.win:length(1)]", exceptionText);

	        // invalid property in select
	        exceptionText = GetStatementExceptionView("select a[2].m('a') from " + _event_ALLTYPES + ".win:length(1)");
	        Assert.AreEqual("Error starting view: Failed to resolve a[2].m('a') as either an event property or as a static method invocation [select a[2].m('a') from net.esper.support.bean.SupportBean.win:length(1)]", exceptionText);

	        // select clause uses same "as" name twice
	        exceptionText = GetStatementExceptionView("select 2 as m, 2 as m from " + _event_ALLTYPES + ".win:length(1)");
	        Assert.AreEqual("Error starting view: Property alias name 'm' appears more then once in select clause [select 2 as m, 2 as m from net.esper.support.bean.SupportBean.win:length(1)]", exceptionText);

	        // class in method invocation not found
	        exceptionText = GetStatementExceptionView("select unknownClass.Method() from " + _event_NUM + ".win:length(10)");
	        Assert.AreEqual("Error starting view: Could not load class by name 'unknownClass'  [select unknownClass.Method() from net.esper.support.bean.SupportBean_N.win:length(10)]", exceptionText);

	        // method not found
	        exceptionText = GetStatementExceptionView("select Math.UnknownMethod() from " + _event_NUM + ".win:length(10)");
	        Assert.AreEqual("Error starting view: Could not find method named 'unknownMethod' in class 'Math'  [select Math.UnknownMethod() from net.esper.support.bean.SupportBean_N.win:length(10)]", exceptionText);

	        // invalid property in group-by
	        exceptionText = GetStatementExceptionView("select intPrimitive from " + _event_ALLTYPES + ".win:length(1) group by xxx");
	        Assert.AreEqual("Error starting view: Property named 'xxx' is not valid in any stream [select intPrimitive from net.esper.support.bean.SupportBean.win:length(1) group by xxx]", exceptionText);

	        // group-by not specifying a property
	        exceptionText = GetStatementExceptionView("select intPrimitive from " + _event_ALLTYPES + ".win:length(1) group by 5");
	        Assert.AreEqual("Error starting view: Group-by expressions must refer to property names [select intPrimitive from net.esper.support.bean.SupportBean.win:length(1) group by 5]", exceptionText);

	        // group-by specifying aggregates
	        exceptionText = GetStatementExceptionView("select intPrimitive from " + _event_ALLTYPES + ".win:length(1) group by Sum(intPrimitive)");
	        Assert.AreEqual("Error starting view: Group-by expressions cannot contain aggregate functions [select intPrimitive from net.esper.support.bean.SupportBean.win:length(1) group by Sum(intPrimitive)]", exceptionText);

	        // group-by specifying a property that is aggregated through select clause
	        exceptionText = GetStatementExceptionView("select intPrimitive, Sum(doublePrimitive) from " + _event_ALLTYPES + ".win:length(1) group by doublePrimitive");
	        Assert.AreEqual("Error starting view: Group-by property 'doublePrimitive' cannot also occur in an aggregate function in the select clause [select intPrimitive, Sum(doublePrimitive) from net.esper.support.bean.SupportBean.win:length(1) group by doublePrimitive]", exceptionText);

	        // invalid property in having clause
	        exceptionText = GetStatementExceptionView("select 2 * 's' from " + _event_ALLTYPES + ".win:length(1) group by intPrimitive having xxx > 5");
	        Assert.AreEqual("Error starting view: Implicit conversion from datatype 'String' to numeric is not allowed [select 2 * 's' from net.esper.support.bean.SupportBean.win:length(1) group by intPrimitive having xxx > 5]", exceptionText);

	        // invalid having clause - not a symbol in the group-by (non-aggregate)
	        exceptionText = GetStatementExceptionView("select Sum(intPrimitive) from " + _event_ALLTYPES + ".win:length(1) group by intBoxed having doubleBoxed > 5");
	        Assert.AreEqual("Error starting view: Non-aggregated property 'doubleBoxed' in the HAVING clause must occur in the group-by clause [select Sum(intPrimitive) from net.esper.support.bean.SupportBean.win:length(1) group by intBoxed having doubleBoxed > 5]", exceptionText);

	        // invalid outer join - not a symbol
	        exceptionText = GetStatementExceptionView("select * from " + _event_ALLTYPES + ".win:length(1) as aStr " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) on xxxx=yyyy");
	        Assert.AreEqual("Error validating expression: Property named 'xxxx' is not valid in any stream [select * from net.esper.support.bean.SupportBean.win:length(1) as aStr left outer join net.esper.support.bean.SupportBean.win:length(1) on xxxx=yyyy]", exceptionText);

	        // invalid outer join for 3 streams - not a symbol
	        exceptionText = GetStatementExceptionView("select * from " + _event_ALLTYPES + ".win:length(1) as s0 " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) as s2 on s0.intPrimitive = s2.yyyy");
	        Assert.AreEqual("Error validating expression: Failed to resolve property 's2.yyyy' to a stream or nested property in a stream [select * from net.esper.support.bean.SupportBean.win:length(1) as s0 left outer join net.esper.support.bean.SupportBean.win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive left outer join net.esper.support.bean.SupportBean.win:length(1) as s2 on s0.intPrimitive = s2.yyyy]", exceptionText);

	        // invalid outer join for 3 streams - wrong stream, the properties in on-clause don't refer to streams
	        exceptionText = GetStatementExceptionView("select * from " + _event_ALLTYPES + ".win:length(1) as s0 " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) as s2 on s0.intPrimitive = s1.intPrimitive");
	        Assert.AreEqual("Error validating expression: Outer join ON-clause must refer to at least one property of the joined stream for stream 2 [select * from net.esper.support.bean.SupportBean.win:length(1) as s0 left outer join net.esper.support.bean.SupportBean.win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive left outer join net.esper.support.bean.SupportBean.win:length(1) as s2 on s0.intPrimitive = s1.intPrimitive]", exceptionText);

	        // invalid outer join - referencing next stream
	        exceptionText = GetStatementExceptionView("select * from " + _event_ALLTYPES + ".win:length(1) as s0 " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) as s1 on s2.intPrimitive = s1.intPrimitive " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) as s2 on s1.intPrimitive = s2.intPrimitive");
	        Assert.AreEqual("Error validating expression: Outer join ON-clause invalid scope for property 'intPrimitive', expecting the current or a prior stream scope [select * from net.esper.support.bean.SupportBean.win:length(1) as s0 left outer join net.esper.support.bean.SupportBean.win:length(1) as s1 on s2.intPrimitive = s1.intPrimitive left outer join net.esper.support.bean.SupportBean.win:length(1) as s2 on s1.intPrimitive = s2.intPrimitive]", exceptionText);

	        // invalid outer join - same properties
	        exceptionText = GetStatementExceptionView("select * from " + _event_NUM + ".win:length(1) as aStr " +
	                "left outer join " + _event_ALLTYPES + ".win:length(1) on string=string");
	        Assert.AreEqual("Error validating expression: Outer join ON-clause cannot refer to properties of the same stream [select * from net.esper.support.bean.SupportBean_N.win:length(1) as aStr left outer join net.esper.support.bean.SupportBean.win:length(1) on string=string]", exceptionText);

	        // invalid order by
	        exceptionText = GetStatementExceptionView("select * from " + _event_NUM + ".win:length(1) as aStr order by X");
	        Assert.AreEqual("Error starting view: Property named 'X' is not valid in any stream [select * from net.esper.support.bean.SupportBean_N.win:length(1) as aStr order by X]", exceptionText);

	        // insert into with wildcard - not allowed
	        exceptionText = GetStatementExceptionView("insert into Google (a, b) select * from " + _event_NUM + ".win:length(1) as aStr");
	        Assert.AreEqual("Error starting view: Wildcard not allowed when insert-into specifies column order [insert into Google (a, b) select * from net.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);

	        // insert into with duplicate column names
	        exceptionText = GetStatementExceptionView("insert into Google (a, b, a) select boolBoxed, boolPrimitive, intBoxed from " + _event_NUM + ".win:length(1) as aStr");
	        Assert.AreEqual("Error starting view: Property name 'a' appears more then once in insert-into clause [insert into Google (a, b, a) select boolBoxed, boolPrimitive, intBoxed from net.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);

	        // insert into mismatches selected columns
	        exceptionText = GetStatementExceptionView("insert into Google (a, b, c) select boolBoxed, boolPrimitive from " + _event_NUM + ".win:length(1) as aStr");
	        Assert.AreEqual("Error starting view: Number of supplied values in the select clause does not match insert-into clause [insert into Google (a, b, c) select boolBoxed, boolPrimitive from net.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);

	        // mismatched type on coalesce columns
	        exceptionText = GetStatementExceptionView("select Coalesce(boolBoxed, string) from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
	        Assert.AreEqual("Error starting view: Implicit conversion not allowed: Cannot coerce to Boolean type System.String [select Coalesce(boolBoxed, string) from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

	        // mismatched case compare type
	        exceptionText = GetStatementExceptionView("select case boolPrimitive when 1 then true end from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
	        Assert.AreEqual("Error starting view: Implicit conversion not allowed: Cannot coerce to Boolean type System.Int32 [select case boolPrimitive when 1 then true end from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

	        // mismatched case result type
	        exceptionText = GetStatementExceptionView("select case when 1=2 then 1 when 1=3 then true end from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
	        Assert.AreEqual("Error starting view: Implicit conversion not allowed: Cannot coerce types System.Int32 and System.Boolean [select case when 1=2 then 1 when 1=3 then true end from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

	        // case expression not returning bool
	        exceptionText = GetStatementExceptionView("select case when 3 then 1 end from " + typeof(SupportBean).FullName + ".win:length(1) as aStr");
	        Assert.AreEqual("Error starting view: Case node 'when' expressions must return a bool value [select case when 3 then 1 end from net.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

	        // function not known
	        exceptionText = GetStatementExceptionView("select Gogglex(1) from " + _event_NUM + ".win:length(1)");
	        Assert.AreEqual("Unknown method named 'gogglex' could not be resolved [select Gogglex(1) from net.esper.support.bean.SupportBean_N.win:length(1)]", exceptionText);

	        // insert into column name incorrect
	        epService.EPAdministrator.CreateEQL("insert into Xyz select 1 as dodi from System.String");
	        exceptionText = GetStatementExceptionView("select pox from pattern[Xyz(yodo=4)]");
	        Assert.AreEqual("Property named 'yodo' is not valid in any stream [select pox from pattern[Xyz(yodo=4)]]", exceptionText);
	    }

	    [Test]
	    public void _TestInvalidView()
	    {
	        String eventClass = typeof(SupportBean).FullName;

	        TryInvalid("select * from " + eventClass + "(dummy='a').win:length(3)");
	        TryValid("select * from " + eventClass + "(string='a').win:length(3)");
	        TryInvalid("select * from " + eventClass + ".dummy:length(3)");

	        TryInvalid("select djdjdj from " + eventClass + ".win:length(3)");
	        TryValid("select boolBoxed as xx, intPrimitive from " + eventClass + ".win:length(3)");
	        TryInvalid("select boolBoxed as xx, intPrimitive as xx from " + eventClass + ".win:length(3)");
	        TryValid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)");

	        TryValid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)" +
	                " where boolBoxed = true");
	        TryInvalid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)" +
	                " where xx = true");
	    }

	    private void TryInvalid(String viewStmt)
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL(viewStmt);
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

	    private String GetSyntaxExceptionView(String expression)
	    {
	        String exceptionText = null;
	        try
	        {
	            epService.EPAdministrator.CreateEQL(expression);
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

	    private String GetStatementExceptionView(String expression)
	    {
	        return GetStatementExceptionView(expression, false);
	    }

	    private String GetStatementExceptionView(String expression, bool isLogException)
	    {
	        String exceptionText = null;
	        try
	        {
	            epService.EPAdministrator.CreateEQL(expression);
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

	    private void TryValid(String viewStmt)
	    {
	        epService.EPAdministrator.CreateEQL(viewStmt);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
