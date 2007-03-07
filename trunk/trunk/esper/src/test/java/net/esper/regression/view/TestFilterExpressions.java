package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanNumeric;
import net.esper.support.util.SupportUpdateListener;

public class TestFilterExpressions extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testExpression()
    {
        String expr = "select * from " + SupportBean.class.getName() + "(intBoxed*intPrimitive > 20)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(expr);
        stmt.addListener(testListener);

        sendBean(5, 5);
        assertTrue(testListener.getAndClearIsInvoked());
    }
    
    // TODO: test connect expressions by and and use comma and mixed
    // TODO: test all different filters
    // TODO: test range "a in [3:4]" in a where clause
    // TODO: test self-comparison expressions, i.e. "A(a=a)" or "A(x=y)"
    // TODO: test pattern where tag name is same event, i.e. "a=A(a.x=a.y)"
    // TODO: test prev and previous functions
    // TODO: test boolean functions allowed, i.e. "A(isTrue(a.x)" or "b=B -> a=A(isTrue(a, b))"
    // TODO: test a function or expression that doesn't return a boolean
    // TODO: right exception throw when errors occur?
    // TODO: test prev and prior
    // TODO: check error message in where clause expression not returning boolean compared to filter expression
    // TODO: function versus event propety resolution
    // TODO: not returning boolean in filter function
    // TODO: break or-expressions into 2-and
    // TODO: break and with and-inside
    // TODO: between works with open/closed ranges
    // TODO: test duplicate filter properties
    // TODO: test filters with multiple expressions
    // TODO: test expressions in all situations
    // TODO: test coercion that IN and others do
    // TODO: test A(ax in (ay, az)) 
    
    public void testExpressionReversed()
    {
        String expr = "select * from " + SupportBean.class.getName() + "(5 = intBoxed)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(expr);
        stmt.addListener(testListener);

        sendBean("intBoxed", 5);
        assertTrue(testListener.getAndClearIsInvoked());
    }

    private void sendBeanInt(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBean(int intPrimitive, double doublePrimtive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        event.setDoublePrimitive(doublePrimtive);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBeanString(String value)
    {
        SupportBean event = new SupportBean();
        event.setString(value);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBeanNumeric(int intOne, int intTwo)
    {
        SupportBeanNumeric num = new SupportBeanNumeric(intOne, intTwo);
        epService.getEPRuntime().sendEvent(num);
    }

    private void sendBean(String fieldName, Object value)
    {
        SupportBean event = new SupportBean();
        if (fieldName.equals("string"))
        {
            event.setString((String) value);
        }
        else if (fieldName.equals("boolPrimitive"))
        {
            event.setBoolPrimitive((Boolean) value);
        }
        else if (fieldName.equals("intBoxed"))
        {
            event.setIntBoxed((Integer) value);
        }
        else if (fieldName.equals("longBoxed"))
        {
            event.setLongBoxed((Long) value);
        }
        else
        {
            throw new IllegalArgumentException("field name not known");
        }
        epService.getEPRuntime().sendEvent(event);
    }

    private void tryInvalid(String expr)
    {
        try
        {
            epService.getEPAdministrator().createEQL(expr);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }
}
