package net.esper.regression.pattern;

import junit.framework.TestCase;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatementException;
import net.esper.support.bean.SupportBean_N;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.bean.SupportBean;
import net.esper.eql.parse.EPStatementSyntaxException;
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
        epService = EPServiceProviderManager.getDefaultProvider();
    }

    public void testSyntaxException()
    {
        String exceptionText = getSyntaxExceptionPattern(EVENT_NUM + "(doublePrimitive='ss'");
        assertEquals("end of input when expecting a closing parenthesis ')' near line 1, column 58 [net.esper.support.bean.SupportBean_N(doublePrimitive='ss']", exceptionText);
    }

    public void testStatementException() throws Exception
    {
        String exceptionText = null;

        // class not found
        exceptionText = getStatementExceptionPattern("dummypkg.dummy()");
        assertEquals("Failed to resolve event type: Failed to load class dummypkg.dummy [dummypkg.dummy()]", exceptionText);

        // simple property not found
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(dummy=1)");
        assertEquals("Property named 'dummy' is not valid in any stream [net.esper.support.bean.SupportBean_N(dummy=1)]", exceptionText);

        // nested property not found
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(dummy.nested=1)");
        assertEquals("Failed to resolve property 'dummy.nested' to a stream or nested property in a stream [net.esper.support.bean.SupportBean_N(dummy.nested=1)]", exceptionText);

        // property wrong type
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(intPrimitive='s')");
        assertEquals("Implicit conversion from datatype 'String' to 'Integer' is not allowed [net.esper.support.bean.SupportBean_N(intPrimitive='s')]", exceptionText);

        // property not a primitive type
        exceptionText = getStatementExceptionPattern(EVENT_COMPLEX + "(nested=1)");
        assertEquals("Implicit conversion from datatype 'Integer' to 'SupportBeanSpecialGetterNested' is not allowed [net.esper.support.bean.SupportBeanComplexProps(nested=1)]", exceptionText);

        // no tag matches prior use
        exceptionText = getStatementExceptionPattern(EVENT_NUM + "(doublePrimitive=x.abc)");
        assertEquals("Failed to resolve property 'x.abc' to a stream or nested property in a stream [net.esper.support.bean.SupportBean_N(doublePrimitive=x.abc)]", exceptionText);

        // range not valid on string
        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + "(string in [1:2])");
        assertEquals("Implicit conversion from datatype 'String' to numeric is not allowed [net.esper.support.bean.SupportBean(string in [1:2])]", exceptionText);

        // range does not allow string params
        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + "(doubleBoxed in ['a':2])");
        assertEquals("Implicit conversion from datatype 'String' to numeric is not allowed [net.esper.support.bean.SupportBean(doubleBoxed in ['a':2])]", exceptionText);

        // invalid observer arg
        exceptionText = getStatementExceptionPattern("timer:at(9l)");
        assertEquals("Error invoking constructor for observer 'timer:at', invalid parameter list for the object [timer:at(9l)]", exceptionText);

        // invalid guard arg
        exceptionText = getStatementExceptionPattern(EVENT_ALLTYPES + " where timer:within('s')");
        assertEquals("Error invoking constructor for guard 'within', invalid parameter list for the object [net.esper.support.bean.SupportBean where timer:within('s')]", exceptionText);

        // use-result property is wrong type
        exceptionText = getStatementExceptionPattern("x=" + EVENT_ALLTYPES + " -> " + EVENT_ALLTYPES + "(doublePrimitive=x.boolBoxed)");
        assertEquals("Implicit conversion from datatype 'Boolean' to 'Double' is not allowed [x=net.esper.support.bean.SupportBean -> net.esper.support.bean.SupportBean(doublePrimitive=x.boolBoxed)]", exceptionText);
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
