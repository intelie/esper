package com.espertech.esper.regression.pattern;

import com.espertech.esper.client.*;
import com.espertech.esper.eql.parse.EPStatementSyntaxException;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBean_N;
import com.espertech.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestInvalidPattern extends TestCase
{
    private EPServiceProvider epService;
    private final String EVENT_NUM = SupportBean_N.class.getName();
    private final String EVENT_COMPLEX = SupportBeanComplexProps.class.getName();
    private final String EVENT_ALLTYPES = SupportBean.class.getName();

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
    }

    public void testNullStatementName()
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName(), null);
    }

    public void testInvalid()
    {
        String exceptionText = getSyntaxExceptionPattern(EVENT_NUM + "(doublePrimitive='ss'");
        assertEquals("Incorrect syntax near ''ss'' expecting a closing parenthesis ')' but found end of input at line 1 column 63, please check the filter specification within the pattern expression [com.espertech.esper.support.bean.SupportBean_N(doublePrimitive='ss']", exceptionText);
    }

    public void testStatementException() throws Exception
    {
        String exceptionText = null;

        exceptionText = getStatementExceptionPattern(SupportBean.class.getName() + " -> timer:at(2,3,4,4,4)");
        assertEquals("Invalid parameter for pattern observer: Invalid combination between days of week and days of month fields for timer:at [com.espertech.esper.support.bean.SupportBean -> timer:at(2,3,4,4,4)]", exceptionText);

        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + " -> timer:within()");
        assertEquals("Failed to resolve pattern object: Pattern guard function 'within' cannot be used as a pattern observer [com.espertech.esper.support.bean.SupportBean -> timer:within()]", exceptionText);

        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + " where timer:interval(100)");
        assertEquals("Failed to resolve pattern object: Pattern observer function 'interval' cannot be used as a pattern guard [com.espertech.esper.support.bean.SupportBean where timer:interval(100)]", exceptionText);

        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + " -> timer:interval()");
        assertEquals("Invalid parameter for pattern observer: Timer-interval observer requires a single numeric or time period parameter [com.espertech.esper.support.bean.SupportBean -> timer:interval()]", exceptionText);

        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + " where timer:within()");
        assertEquals("Invalid parameter for pattern guard: Timer-within guard requires a single numeric or time period parameter [com.espertech.esper.support.bean.SupportBean where timer:within()]", exceptionText);

        // class not found
        exceptionText = getStatementExceptionPattern("dummypkg.dummy()");
        assertEquals("Failed to resolve event type: Failed to load class dummypkg.dummy [dummypkg.dummy()]", exceptionText);

        // simple property not found
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(dummy=1)");
        assertEquals("Property named 'dummy' is not valid in any stream [com.espertech.esper.support.bean.SupportBean_N(dummy=1)]", exceptionText);

        // nested property not found
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(dummy.nested=1)");
        assertEquals("Failed to resolve property 'dummy.nested' to a stream or nested property in a stream [com.espertech.esper.support.bean.SupportBean_N(dummy.nested=1)]", exceptionText);

        // property wrong type
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(intPrimitive='s')");
        assertEquals("Implicit conversion from datatype 'String' to 'Integer' is not allowed [com.espertech.esper.support.bean.SupportBean_N(intPrimitive='s')]", exceptionText);

        // property not a primitive type
        exceptionText = getStatementExceptionPattern(EVENT_COMPLEX + "(nested=1)");
        assertEquals("Implicit conversion from datatype 'Integer' to 'SupportBeanSpecialGetterNested' is not allowed [com.espertech.esper.support.bean.SupportBeanComplexProps(nested=1)]", exceptionText);

        // no tag matches prior use
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(doublePrimitive=x.abc)");
        assertEquals("Failed to resolve property 'x.abc' to a stream or nested property in a stream [com.espertech.esper.support.bean.SupportBean_N(doublePrimitive=x.abc)]", exceptionText);

        // range not valid on string
        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + "(string in [1:2])");
        assertEquals("Implicit conversion from datatype 'String' to numeric is not allowed [com.espertech.esper.support.bean.SupportBean(string in [1:2])]", exceptionText);

        // range does not allow string params
        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + "(doubleBoxed in ['a':2])");
        assertEquals("Implicit conversion from datatype 'String' to numeric is not allowed [com.espertech.esper.support.bean.SupportBean(doubleBoxed in ['a':2])]", exceptionText);

        // invalid observer arg
        exceptionText = getStatementExceptionPattern("timer:at(9l)");
        assertEquals("Invalid parameter for pattern observer: Invalid number of parameters for timer:at [timer:at(9l)]", exceptionText);

        // invalid guard arg
        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + " where timer:within('s')");
        assertEquals("Invalid parameter for pattern guard: Timer-within guard requires a single numeric or time period parameter [com.espertech.esper.support.bean.SupportBean where timer:within('s')]", exceptionText);

        // use-result property is wrong type
        exceptionText = getStatementExceptionPattern("x=" + EVENT_ALLTYPES + " -> " + EVENT_ALLTYPES + "(doublePrimitive=x.boolBoxed)");
        assertEquals("Implicit conversion from datatype 'Boolean' to 'Double' is not allowed [x=com.espertech.esper.support.bean.SupportBean -> com.espertech.esper.support.bean.SupportBean(doublePrimitive=x.boolBoxed)]", exceptionText);
    }

    public void testUseResult()
    {
        final String EVENT = SupportBean_N.class.getName();

        tryValid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive)");
        tryInvalid("xx=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive)");
        tryInvalid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = xx.doublePrimitive)");
        tryInvalid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.xx)");
        tryInvalid("xx=" + EVENT + " -> nb=" + EVENT + "(xx = na.doublePrimitive)");
        tryInvalid("na=" + EVENT + " -> nb=" + EVENT + "(xx = na.xx)");
        tryValid("na=" + EVENT + " -> nb=" + EVENT + "(doublePrimitive = na.doublePrimitive, intBoxed=na.intBoxed)");
        tryValid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in (na.doublePrimitive:na.doubleBoxed))");
        tryValid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.doublePrimitive:na.doubleBoxed])");
        tryValid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.intBoxed:na.intPrimitive])");
        tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.intBoxed:na.xx])");
        tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.intBoxed:na.boolBoxed])");
        tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.xx:na.intPrimitive])");
        tryInvalid("na=" + EVENT + "() -> nb=" + EVENT + "(doublePrimitive in [na.boolBoxed:na.intPrimitive])");
    }

    private void tryInvalid(String eqlInvalidPattern)
    {
        try
        {
            epService.getEPAdministrator().createPattern(eqlInvalidPattern);
            fail();
        }
        catch (EPException ex)
        {
            // Expected exception
        }
    }

    private String getSyntaxExceptionPattern(String expression)
    {
        String exceptionText = null;
        try
        {
            epService.getEPAdministrator().createPattern(expression);
            fail();
        }
        catch (EPStatementSyntaxException ex)
        {
            exceptionText = ex.getMessage();
            log.debug(".getSyntaxExceptionPattern pattern=" + expression, ex);
            // Expected exception
        }

        return exceptionText;
    }

    private String getStatementExceptionPattern(String expression) throws Exception
    {
        return getStatementExceptionPattern(expression, false);
    }

    private String getStatementExceptionPattern(String expression, boolean isLogException) throws Exception
    {
        String exceptionText = null;
        try
        {
            epService.getEPAdministrator().createPattern(expression);
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
                log.debug(".getSyntaxExceptionPattern pattern=" + expression, ex);
            }
        }

        return exceptionText;
    }

    private void tryValid(String eqlInvalidPattern)
    {
        epService.getEPAdministrator().createPattern(eqlInvalidPattern);
    }

    private static Log log = LogFactory.getLog(TestInvalidPattern.class);
}
