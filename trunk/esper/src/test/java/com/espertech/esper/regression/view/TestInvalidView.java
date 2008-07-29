package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.epl.parse.ASTFilterSpecValidationException;
import com.espertech.esper.epl.parse.EPStatementSyntaxException;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_N;
import com.espertech.esper.support.client.SupportConfigFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestInvalidView extends TestCase
{
    private final String EVENT_NUM = SupportBean_N.class.getName();
    private final String EVENT_ALLTYPES = SupportBean.class.getName();

    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testInvalidSyntax()
    {
        // keyword used in package
        String exceptionText = getSyntaxExceptionView("select * from com.true.mycompany.MyEvent");
        assertEquals("Incorrect syntax near 'true' (a reserved keyword) expecting an identifier but found 'true' at line 1 column 18, please check the view specifications within the from clause [select * from com.true.mycompany.MyEvent]", exceptionText);

        // keyword as part of identifier
        exceptionText = getSyntaxExceptionView("select * from MyEvent, MyEvent2 where a.day=b.day");
        assertEquals("Incorrect syntax near 'a' at line 1 column 38, please check the where clause near reserved keyword 'day' [select * from MyEvent, MyEvent2 where a.day=b.day]", exceptionText);

        exceptionText = getSyntaxExceptionView("select * * from " + EVENT_NUM);
        assertEquals("Incorrect syntax near '*' expecting 'from' but found a star '*' at line 1 column 9 [select * * from com.espertech.esper.support.bean.SupportBean_N]", exceptionText);

        // keyword in select clause
        exceptionText = getSyntaxExceptionView("select day from MyEvent, MyEvent2");
        assertEquals("Incorrect syntax near 'day' (a reserved keyword) at line 1 column 7, please check the select clause [select day from MyEvent, MyEvent2]", exceptionText);
    }

    public void testStatementException() throws Exception
    {
        String exceptionText = null;

        // class not found
        exceptionText = getStatementExceptionView("select * from dummypkg.dummy().win:length(10)");
        assertEquals("Failed to resolve event type: Failed to load class dummypkg.dummy [select * from dummypkg.dummy().win:length(10)]", exceptionText);

        // invalid view
        exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".dummy:dummy(10)");
        assertEquals("Error starting view: View name 'dummy:dummy' is not a known view name [select * from com.espertech.esper.support.bean.SupportBean_N.dummy:dummy(10)]", exceptionText);

        // keyword used
        exceptionText = getSyntaxExceptionView("select order from " + SupportBean.class.getName());
        assertEquals("Incorrect syntax near 'order' (a reserved keyword) at line 1 column 7, please check the select clause [select order from com.espertech.esper.support.bean.SupportBean]", exceptionText);

        // invalid view parameter
        exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length('s')");
        assertEquals("Error starting view: Error in view 'win:length', Length window view requires a single integer-type parameter [select * from com.espertech.esper.support.bean.SupportBean_N.win:length('s')]", exceptionText);

        // where-clause equals has invalid type compare
        exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length(1) where doublePrimitive=true");
        assertEquals("Error validating expression: Implicit conversion from datatype 'Boolean' to 'Double' is not allowed [select * from com.espertech.esper.support.bean.SupportBean_N.win:length(1) where doublePrimitive=true]", exceptionText);

        // where-clause relational op has invalid type
        exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) where string > 5");
        assertEquals("Error validating expression: Implicit conversion from datatype 'String' to numeric is not allowed [select * from com.espertech.esper.support.bean.SupportBean.win:length(1) where string > 5]", exceptionText);

        // where-clause has aggregation function
        exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) where sum(intPrimitive) > 5");
        assertEquals("Error validating expression: An aggregate function may not appear in a WHERE clause (use the HAVING clause) [select * from com.espertech.esper.support.bean.SupportBean.win:length(1) where sum(intPrimitive) > 5]", exceptionText);

        // invalid numerical expression
        exceptionText = getStatementExceptionView("select 2 * 's' from " + EVENT_ALLTYPES + ".win:length(1)");
        assertEquals("Error starting view: Implicit conversion from datatype 'String' to numeric is not allowed [select 2 * 's' from com.espertech.esper.support.bean.SupportBean.win:length(1)]", exceptionText);

        // invalid property in select
        exceptionText = getStatementExceptionView("select a[2].m('a') from " + EVENT_ALLTYPES + ".win:length(1)");
        assertEquals("Error starting view: Property named 'a[2].m('a')' is not valid in any stream [select a[2].m('a') from com.espertech.esper.support.bean.SupportBean.win:length(1)]", exceptionText);

        // select clause uses same "as" name twice
        exceptionText = getStatementExceptionView("select 2 as m, 2 as m from " + EVENT_ALLTYPES + ".win:length(1)");
        assertEquals("Error starting view: Property alias name 'm' appears more then once in select clause [select 2 as m, 2 as m from com.espertech.esper.support.bean.SupportBean.win:length(1)]", exceptionText);

        // class in method invocation not found
        exceptionText = getStatementExceptionView("select unknownClass.method() from " + EVENT_NUM + ".win:length(10)");
        assertEquals("Error starting view: Could not load class by name 'unknownClass'  [select unknownClass.method() from com.espertech.esper.support.bean.SupportBean_N.win:length(10)]", exceptionText);

        // method not found
        exceptionText = getStatementExceptionView("select Math.unknownMethod() from " + EVENT_NUM + ".win:length(10)");
        assertEquals("Error starting view: Could not find static method named 'unknownMethod' in class 'Math'  [select Math.unknownMethod() from com.espertech.esper.support.bean.SupportBean_N.win:length(10)]", exceptionText);

        // invalid property in group-by
        exceptionText = getStatementExceptionView("select intPrimitive from " + EVENT_ALLTYPES + ".win:length(1) group by xxx");
        assertEquals("Error starting view: Property named 'xxx' is not valid in any stream [select intPrimitive from com.espertech.esper.support.bean.SupportBean.win:length(1) group by xxx]", exceptionText);

        // group-by not specifying a property
        exceptionText = getStatementExceptionView("select intPrimitive from " + EVENT_ALLTYPES + ".win:length(1) group by 5");
        assertEquals("Error starting view: Group-by expressions must refer to property names [select intPrimitive from com.espertech.esper.support.bean.SupportBean.win:length(1) group by 5]", exceptionText);

        // group-by specifying aggregates
        exceptionText = getStatementExceptionView("select intPrimitive from " + EVENT_ALLTYPES + ".win:length(1) group by sum(intPrimitive)");
        assertEquals("Error starting view: Group-by expressions cannot contain aggregate functions [select intPrimitive from com.espertech.esper.support.bean.SupportBean.win:length(1) group by sum(intPrimitive)]", exceptionText);

        // invalid property in having clause
        exceptionText = getStatementExceptionView("select 2 * 's' from " + EVENT_ALLTYPES + ".win:length(1) group by intPrimitive having xxx > 5");
        assertEquals("Error starting view: Implicit conversion from datatype 'String' to numeric is not allowed [select 2 * 's' from com.espertech.esper.support.bean.SupportBean.win:length(1) group by intPrimitive having xxx > 5]", exceptionText);

        // invalid having clause - not a symbol in the group-by (non-aggregate)
        exceptionText = getStatementExceptionView("select sum(intPrimitive) from " + EVENT_ALLTYPES + ".win:length(1) group by intBoxed having doubleBoxed > 5");
        assertEquals("Error starting view: Non-aggregated property 'doubleBoxed' in the HAVING clause must occur in the group-by clause [select sum(intPrimitive) from com.espertech.esper.support.bean.SupportBean.win:length(1) group by intBoxed having doubleBoxed > 5]", exceptionText);

        // invalid outer join - not a symbol
        exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as aStr " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) on xxxx=yyyy");
        assertEquals("Error validating expression: Property named 'xxxx' is not valid in any stream [select * from com.espertech.esper.support.bean.SupportBean.win:length(1) as aStr left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) on xxxx=yyyy]", exceptionText);

        // invalid outer join for 3 streams - not a symbol
        exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as s0 " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s2 on s0.intPrimitive = s2.yyyy");
        assertEquals("Error validating expression: Failed to resolve property 's2.yyyy' to a stream or nested property in a stream [select * from com.espertech.esper.support.bean.SupportBean.win:length(1) as s0 left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) as s2 on s0.intPrimitive = s2.yyyy]", exceptionText);

        // invalid outer join for 3 streams - wrong stream, the properties in on-clause don't refer to streams
        exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as s0 " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s2 on s0.intPrimitive = s1.intPrimitive");
        assertEquals("Error validating expression: Outer join ON-clause must refer to at least one property of the joined stream for stream 2 [select * from com.espertech.esper.support.bean.SupportBean.win:length(1) as s0 left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) as s1 on s0.intPrimitive = s1.intPrimitive left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) as s2 on s0.intPrimitive = s1.intPrimitive]", exceptionText);

        // invalid outer join - referencing next stream
        exceptionText = getStatementExceptionView("select * from " + EVENT_ALLTYPES + ".win:length(1) as s0 " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s1 on s2.intPrimitive = s1.intPrimitive " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) as s2 on s1.intPrimitive = s2.intPrimitive");
        assertEquals("Error validating expression: Outer join ON-clause invalid scope for property 'intPrimitive', expecting the current or a prior stream scope [select * from com.espertech.esper.support.bean.SupportBean.win:length(1) as s0 left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) as s1 on s2.intPrimitive = s1.intPrimitive left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) as s2 on s1.intPrimitive = s2.intPrimitive]", exceptionText);

        // invalid outer join - same properties
        exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length(1) as aStr " +
                "left outer join " + EVENT_ALLTYPES + ".win:length(1) on string=string");
        assertEquals("Error validating expression: Outer join ON-clause cannot refer to properties of the same stream [select * from com.espertech.esper.support.bean.SupportBean_N.win:length(1) as aStr left outer join com.espertech.esper.support.bean.SupportBean.win:length(1) on string=string]", exceptionText);

        // invalid order by
        exceptionText = getStatementExceptionView("select * from " + EVENT_NUM + ".win:length(1) as aStr order by X");
        assertEquals("Error starting view: Property named 'X' is not valid in any stream [select * from com.espertech.esper.support.bean.SupportBean_N.win:length(1) as aStr order by X]", exceptionText);

        // insert into with wildcard - not allowed
        exceptionText = getStatementExceptionView("insert into Google (a, b) select * from " + EVENT_NUM + ".win:length(1) as aStr");
        assertEquals("Error starting view: Wildcard not allowed when insert-into specifies column order [insert into Google (a, b) select * from com.espertech.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);

        // insert into with duplicate column names
        exceptionText = getStatementExceptionView("insert into Google (a, b, a) select boolBoxed, boolPrimitive, intBoxed from " + EVENT_NUM + ".win:length(1) as aStr");
        assertEquals("Error starting view: Property name 'a' appears more then once in insert-into clause [insert into Google (a, b, a) select boolBoxed, boolPrimitive, intBoxed from com.espertech.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);

        // insert into mismatches selected columns
        exceptionText = getStatementExceptionView("insert into Google (a, b, c) select boolBoxed, boolPrimitive from " + EVENT_NUM + ".win:length(1) as aStr");
        assertEquals("Error starting view: Number of supplied values in the select clause does not match insert-into clause [insert into Google (a, b, c) select boolBoxed, boolPrimitive from com.espertech.esper.support.bean.SupportBean_N.win:length(1) as aStr]", exceptionText);

        // mismatched type on coalesce columns
        exceptionText = getStatementExceptionView("select coalesce(boolBoxed, string) from " + SupportBean.class.getName() + ".win:length(1) as aStr");
        assertEquals("Error starting view: Implicit conversion not allowed: Cannot coerce to Boolean type java.lang.String [select coalesce(boolBoxed, string) from com.espertech.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

        // mismatched case compare type
        exceptionText = getStatementExceptionView("select case boolPrimitive when 1 then true end from " + SupportBean.class.getName() + ".win:length(1) as aStr");
        assertEquals("Error starting view: Implicit conversion not allowed: Cannot coerce to Boolean type java.lang.Integer [select case boolPrimitive when 1 then true end from com.espertech.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

        // mismatched case result type
        exceptionText = getStatementExceptionView("select case when 1=2 then 1 when 1=3 then true end from " + SupportBean.class.getName() + ".win:length(1) as aStr");
        assertEquals("Error starting view: Implicit conversion not allowed: Cannot coerce types java.lang.Integer and java.lang.Boolean [select case when 1=2 then 1 when 1=3 then true end from com.espertech.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

        // case expression not returning bool
        exceptionText = getStatementExceptionView("select case when 3 then 1 end from " + SupportBean.class.getName() + ".win:length(1) as aStr");
        assertEquals("Error starting view: Case node 'when' expressions must return a boolean value [select case when 3 then 1 end from com.espertech.esper.support.bean.SupportBean.win:length(1) as aStr]", exceptionText);

        // function not known
        exceptionText = getStatementExceptionView("select gogglex(1) from " + EVENT_NUM + ".win:length(1)");
        assertEquals("Unknown method named 'gogglex' could not be resolved [select gogglex(1) from com.espertech.esper.support.bean.SupportBean_N.win:length(1)]", exceptionText);

        // insert into column name incorrect
        epService.getEPAdministrator().createEPL("insert into Xyz select 1 as dodi from java.lang.String");
        exceptionText = getStatementExceptionView("select pox from pattern[Xyz(yodo=4)]");
        assertEquals("Property named 'yodo' is not valid in any stream [select pox from pattern[Xyz(yodo=4)]]", exceptionText);
    }

    public void testInvalidView()
    {
        String eventClass = SupportBean.class.getName();

        tryInvalid("select * from " + eventClass + "(dummy='a').win:length(3)");
        tryValid("select * from " + eventClass + "(string='a').win:length(3)");
        tryInvalid("select * from " + eventClass + ".dummy:length(3)");

        tryInvalid("select djdjdj from " + eventClass + ".win:length(3)");
        tryValid("select boolBoxed as xx, intPrimitive from " + eventClass + ".win:length(3)");
        tryInvalid("select boolBoxed as xx, intPrimitive as xx from " + eventClass + ".win:length(3)");
        tryValid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)");

        tryValid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)" +
                " where boolBoxed = true");
        tryInvalid("select boolBoxed as xx, intPrimitive as yy from " + eventClass + "().win:length(3)" +
                " where xx = true");
    }

    private void tryInvalid(String viewStmt)
    {
        try
        {
            epService.getEPAdministrator().createEPL(viewStmt);
            fail();
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
            epService.getEPAdministrator().createEPL(expression);
            fail();
        }
        catch (EPStatementSyntaxException ex)
        {
            exceptionText = ex.getMessage();
            log.debug(".getSyntaxExceptionView expression=" + expression, ex);
            // Expected exception
        }

        return exceptionText;
    }

    private String getStatementExceptionView(String expression) throws Exception
    {
        return getStatementExceptionView(expression, false);
    }

    private String getStatementExceptionView(String expression, boolean isLogException) throws Exception
    {
        String exceptionText = null;
        try
        {
            epService.getEPAdministrator().createEPL(expression);
            fail();
        }
        catch (EPStatementSyntaxException es)
        {
            throw es;
        }
        catch (EPStatementException ex)
        {
            // Expected exception
            exceptionText = ex.getMessage();
            if (isLogException)
            {
                log.debug(".getStatementExceptionView expression=" + expression, ex);
            }
        }

        return exceptionText;
    }

    private void tryValid(String viewStmt)
    {
        epService.getEPAdministrator().createEPL(viewStmt);
    }

    private static Log log = LogFactory.getLog(TestInvalidView.class);
}
