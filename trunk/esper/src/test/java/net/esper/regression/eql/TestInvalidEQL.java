package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatementException;
import net.esper.eql.parse.EPStatementSyntaxException;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_N;
import net.esper.support.bean.SupportMarketDataBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestInvalidEQL extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
    }

    public void testSyntaxException()
    {
        final String EVENT = SupportBean_N.class.getName();

        String exceptionText = getSyntaxExceptionEQL("select * from *");
        assertEquals("unexpected token: * near line 1, column 15 [select * from *]", exceptionText);
    }

    public void testLongTypeConstant()
    {
        try
        {
            String stmt = "select * from " + SupportBean.class.getName() + " where longPrimitive = 2512570244 and intPrimitive > 3";
            epService.getEPAdministrator().createEQL(stmt);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals("", ex.toString());
        }
    }

    public void testDifferentJoins()
    {
        String streamDef = "select * from " +
                SupportBean.class.getName() + ".win:length(3) as sa," +
                SupportBean.class.getName() + ".win:length(3) as sb" +
                            " where ";

        String streamDefTwo = "select * from " +
                SupportBean.class.getName() + ".win:length(3)," +
                SupportMarketDataBean.class.getName() + ".win:length(3)" +
                            " where ";

        tryInvalid(streamDef + "sa.intPrimitive = sb.string");
        tryValid(streamDef + "sa.intPrimitive = sb.intBoxed");
        tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive");
        tryValid(streamDef + "sa.intPrimitive = sb.longBoxed");

        tryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.boolPrimitive");
        tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.boolBoxed = sa.boolPrimitive");

        tryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.intPrimitive and sa.string=sX.string");
        tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.intPrimitive and sa.string=sb.string");

        tryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive or sa.string=sX.string");
        tryValid(streamDef + "sa.intPrimitive = sb.intPrimitive or sb.intBoxed = sa.intPrimitive");

        // try constants
        tryValid(streamDef + "sa.intPrimitive=5");
        tryValid(streamDef + "sa.string='4'");
        tryValid(streamDef + "sa.string=\"4\"");
        tryValid(streamDef + "sa.boolPrimitive=false");
        tryValid(streamDef + "sa.longPrimitive=-5L");
        tryValid(streamDef + "sa.doubleBoxed=5.6d");
        tryValid(streamDef + "sa.floatPrimitive=-5.6f");

        tryInvalid(streamDef + "sa.intPrimitive='5'");
        tryInvalid(streamDef + "sa.string=5");
        tryInvalid(streamDef + "sa.boolBoxed=f");
        tryInvalid(streamDef + "sa.intPrimitive=x");
        tryValid(streamDef + "sa.intPrimitive=5.5");

        // try addition and subtraction
        tryValid(streamDef + "sa.intPrimitive=sa.intBoxed + 5");
        tryValid(streamDef + "sa.intPrimitive=2*sa.intBoxed - sa.intPrimitive/10 + 1");
        tryValid(streamDef + "sa.intPrimitive=2*(sa.intBoxed - sa.intPrimitive)/(10 + 1)");
        tryInvalid(streamDef + "sa.intPrimitive=2*(sa.intBoxed");

        // try comparison
        tryValid(streamDef + "sa.intPrimitive > sa.intBoxed and sb.doublePrimitive < sb.doubleBoxed");
        tryValid(streamDef + "sa.intPrimitive >= sa.intBoxed and sa.doublePrimitive <= sa.doubleBoxed");
        tryValid(streamDef + "sa.intPrimitive > (sa.intBoxed + sb.doublePrimitive)");
        tryInvalid(streamDef + "sa.intPrimitive >= sa.string");
        tryInvalid(streamDef + "sa.boolBoxed >= sa.boolPrimitive");

        // Try some nested
        tryValid(streamDef + "(sa.intPrimitive=3) or (sa.intBoxed=3 and sa.intPrimitive=1)");
        tryValid(streamDef + "((sa.intPrimitive>3) or (sa.intBoxed<3)) and sa.boolBoxed=false");
        tryValid(streamDef + "(sa.intPrimitive<=3 and sa.intPrimitive>=1) or (sa.boolBoxed=false and sa.boolPrimitive=true)");
        tryInvalid(streamDef + "sa.intPrimitive=3 or (sa.intBoxed=2");
        tryInvalid(streamDef + "sa.intPrimitive=3 or sa.intBoxed=2)");
        tryInvalid(streamDef + "sa.intPrimitive=3 or ((sa.intBoxed=2)");

        // Try some without stream name
        tryInvalid(streamDef + "intPrimitive=3");
        tryValid(streamDefTwo + "intPrimitive=3");

        // Try invalid outer join criteria
        String outerJoinDef = "select * from " +
                SupportBean.class.getName() + ".win:length(3) as sa " +
                "left outer join " +
                SupportBean.class.getName() + ".win:length(3) as sb ";
        tryInvalid(outerJoinDef + "");
        tryValid(outerJoinDef + "on sa.intPrimitive = sb.intBoxed");
        tryInvalid(outerJoinDef + "on sa.intPrimitive = sb.XX");
        tryInvalid(outerJoinDef + "on sa.XX = sb.XX");
        tryInvalid(outerJoinDef + "on sa.XX = sb.intBoxed");
        tryInvalid(outerJoinDef + "on sa.boolBoxed = sb.intBoxed");
        tryValid(outerJoinDef + "on sa.boolPrimitive = sb.boolBoxed");
        tryInvalid(outerJoinDef + "on sa.boolPrimitive = sb.string");
        tryInvalid(outerJoinDef + "on sa.intPrimitive <= sb.intBoxed");
        tryInvalid(outerJoinDef + "on sa.intPrimitive = sa.intBoxed");
        tryInvalid(outerJoinDef + "on sb.intPrimitive = sb.intBoxed");
        tryValid(outerJoinDef + "on sb.intPrimitive = sa.intBoxed");
    }

    private void tryInvalid(String eqlInvalidEQL)
    {
        try
        {
            epService.getEPAdministrator().createEQL(eqlInvalidEQL);
            fail();
        }
        catch (EPException ex)
        {
            // Expected exception
        }
    }

    private void tryValid(String eqlInvalidEQL)
    {
        epService.getEPAdministrator().createEQL(eqlInvalidEQL);
    }

    private String getSyntaxExceptionEQL(String expression)
    {
        String exceptionText = null;
        try
        {
            epService.getEPAdministrator().createEQL(expression);
            fail();
        }
        catch (EPStatementSyntaxException ex)
        {
            exceptionText = ex.getMessage();
            log.debug(".getSyntaxExceptionEQL eql=" + expression, ex);
            // Expected exception
        }

        return exceptionText;
    }

    private static Log log = LogFactory.getLog(TestInvalidEQL.class);
}
