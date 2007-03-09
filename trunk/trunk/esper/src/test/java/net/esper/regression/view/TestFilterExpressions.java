package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.eql.SupportStaticMethodLib;

// TODO: test pattern self-comparison expressions, i.e. "A(a=a)" or "A(x=y)"
// TODO: test pattern where tag name is same event, i.e. "a=A(a.x=a.y)"
// TODO: test pattern boolean functions allowed, i.e. "A(isTrue(a.x)" or "b=B -> a=A(isTrue(a, b))"
// TODO: right exception throw when errors occur?

// TODO: test filters with multiple expressions
// TODO: test A(ax in (ay, az))
// TODO: test matching by adding a boolean expr first then some more sensible stuff testing the performance
// TODO: test each of the filter params individually as duplicates
// TODO: test null values in equals etc.

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

    public void testFilterStaticFunc()
    {
        String text;

        text = "select * from " + SupportBean.class.getName() + "(" +
                SupportStaticMethodLib.class.getName() + ".isStringEquals('b', string))";
        tryFilter(text, true);

        text = "select * from " + SupportBean.class.getName() + "(" +
                SupportStaticMethodLib.class.getName() + ".isStringEquals('bx', string || 'x'))";
        tryFilter(text, true);

        text = "select * from " + SupportBean.class.getName() + "('b'=string," +
                SupportStaticMethodLib.class.getName() + ".isStringEquals('bx', string || 'x'))";
        tryFilter(text, true);

        text = "select * from " + SupportBean.class.getName() + "('b'=string, string='b', string != 'a')";
        tryFilter(text, true);

        text = "select * from " + SupportBean.class.getName() + "(string != 'a', string != 'c')";
        tryFilter(text, true);

        text = "select * from " + SupportBean.class.getName() + "(string = 'b', string != 'c')";
        tryFilter(text, true);

        text = "select * from " + SupportBean.class.getName() + "(string != 'a' and string != 'c')";
        tryFilter(text, true);

        text = "select * from " + SupportBean.class.getName() + "(string = 'a' and string = 'c' and " +
                SupportStaticMethodLib.class.getName() + ".isStringEquals('bx', string || 'x'))";
        tryFilter(text, false);
    }

    public void tryFilterRelationalOpRange()
    {
        String text;

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in [2:3])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, true, true, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in [2:3] and intBoxed in [2:3])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, true, true, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in [2:3] and intBoxed in [2:2])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, true, false, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in [1:10] and intBoxed in [3:2])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, true, true, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in [3:3] and intBoxed in [1:3])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, false, true, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in [3:3] and intBoxed in [1:3] and intBoxed in [4:5])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, false, false, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed not in [3:3] and intBoxed not in [1:3])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, false, false, true});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed not in (2:4) and intBoxed not in (1:3))";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {true, false, false, true});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed not in [2:4) and intBoxed not in [1:3))";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, false, false, true});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed not in (2:4] and intBoxed not in (1:3])";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {true, false, false, false});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed not in (2:4)";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {true, true, false, true});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed not in [2:4]";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {true, false, false, false});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed not in [2:4)";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {true, false, false, true});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed not in (2:4]";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {true, true, false, false});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed in (2:4)";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, false, true, false});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed in [2:4]";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, true, true, true});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed in [2:4)";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, true, true, false});

        text = "select * from " + SupportBean.class.getName() + " where intBoxed in (2:4]";
        tryFilterRelationalOpRange(text, new int[] {1, 2, 3, 4}, new boolean[] {false, false, true, true});
    }

    public void tryFilterRelationalOpRange(String text, int[] testData, boolean[] isReceived)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        assertEquals(testData.length,  isReceived.length);
        for (int i = 0; i < testData.length; i++)
        {
            sendBeanIntDouble(testData[i], 0D);
            assertEquals("failed testing index " + i, isReceived[i], testListener.getAndClearIsInvoked());
        }
        stmt.removeListener(testListener);
    }
    
    private void tryFilter(String text, boolean isReceived)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        sendBeanString("a");
        assertFalse(testListener.getAndClearIsInvoked());
        sendBeanString("b");
        assertEquals(isReceived, testListener.getAndClearIsInvoked());
        sendBeanString("c");
        assertFalse(testListener.getAndClearIsInvoked());

        stmt.removeListener(testListener);
    }

    public void testFilterWithEqualsSameCompare()
    {
        String text;

        text = "select * from " + SupportBean.class.getName() + "(intBoxed=doubleBoxed)";
        tryFilterWithEqualsSameCompare(text, new int[] {1, 1}, new double[] {1, 10}, new boolean[] {true, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed=intBoxed and doubleBoxed=doubleBoxed)";
        tryFilterWithEqualsSameCompare(text, new int[] {1, 1}, new double[] {1, 10}, new boolean[] {true, true});

        text = "select * from " + SupportBean.class.getName() + "(doubleBoxed=intBoxed)";
        tryFilterWithEqualsSameCompare(text, new int[] {1, 1}, new double[] {1, 10}, new boolean[] {true, false});

        text = "select * from " + SupportBean.class.getName() + "(doubleBoxed in (intBoxed))";
        tryFilterWithEqualsSameCompare(text, new int[] {1, 1}, new double[] {1, 10}, new boolean[] {true, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in (doubleBoxed))";
        tryFilterWithEqualsSameCompare(text, new int[] {1, 1}, new double[] {1, 10}, new boolean[] {true, false});

        text = "select * from " + SupportBean.class.getName() + "(doubleBoxed not in (10, intBoxed))";
        tryFilterWithEqualsSameCompare(text, new int[] {1, 1, 1}, new double[] {1, 5, 10}, new boolean[] {false, true, false});

        text = "select * from " + SupportBean.class.getName() + "(doubleBoxed in (intBoxed:20))";
        tryFilterWithEqualsSameCompare(text, new int[] {0, 1, 2}, new double[] {1, 1, 1}, new boolean[] {true, false, false});
    }

    private void tryFilterWithEqualsSameCompare(String text, int[] intBoxed, double[] doubleBoxed, boolean[] expected)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        assertEquals(intBoxed.length, doubleBoxed.length);
        assertEquals(expected.length, doubleBoxed.length);
        for (int i = 0; i < intBoxed.length; i++)
        {
            sendBeanIntDouble(intBoxed[i], doubleBoxed[i]);
            assertEquals("failed at index " + i, expected[i], testListener.getAndClearIsInvoked());
        }

        stmt.stop();
    }

    public void testInvalid()
    {
        tryInvalid("select * from pattern [every a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportMarketDataBean.class.getName() + "(sum(a.longBoxed) = 2)]",
                "Aggregation functions not allowed within filters [select * from pattern [every a=net.esper.support.bean.SupportBean -> b=net.esper.support.bean.SupportMarketDataBean(sum(a.longBoxed) = 2)]]");

        tryInvalid("select * from pattern [every a=" + SupportBean.class.getName() + "(prior(1, a.longBoxed))]",
                "Prior function cannot be used in this context [select * from pattern [every a=net.esper.support.bean.SupportBean(prior(1, a.longBoxed))]]");
        
        tryInvalid("select * from pattern [every a=" + SupportBean.class.getName() + "(prev(1, a.longBoxed))]",
                "Previous function cannot be used in this context [select * from pattern [every a=net.esper.support.bean.SupportBean(prev(1, a.longBoxed))]]");

        tryInvalid("select * from " + SupportBean.class.getName() + "(5 - 10)",
                "Filter expression not returning a boolean value: '(5-10)' [select * from net.esper.support.bean.SupportBean(5 - 10)]");
    }

    private void tryInvalid(String text, String expectedMsg)
    {
        try
        {
            epService.getEPAdministrator().createEQL(text);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(ex.getMessage(), expectedMsg);
        }
    }

    public void testPatternWithExpr()
    {
        String text = "select * from pattern [every a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportMarketDataBean.class.getName() + "(a.longBoxed=volume*2)]";
        tryPatternWithExpr(text);

        text = "select * from pattern [every a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportMarketDataBean.class.getName() + "(volume*2=a.longBoxed)]";
        tryPatternWithExpr(text);
    }

    private void tryPatternWithExpr(String text)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        sendBeanLong(10L);
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 0L, ""));
        assertFalse(testListener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 5L, ""));
        assertTrue(testListener.getAndClearIsInvoked());

        sendBeanLong(0L);
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 0L, ""));
        assertTrue(testListener.getAndClearIsInvoked());
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 1L, ""));
        assertFalse(testListener.getAndClearIsInvoked());

        sendBeanLong(20L);
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 10L, ""));
        assertTrue(testListener.getAndClearIsInvoked());

        stmt.removeAllListeners();
    }

    public void testMathExpression()
    {
        String text;

        text = "select * from " + SupportBean.class.getName() + "(intBoxed*doubleBoxed > 20)";
        tryArithmatic(text);

        text = "select * from " + SupportBean.class.getName() + "(20 < intBoxed*doubleBoxed)";
        tryArithmatic(text);

        text = "select * from " + SupportBean.class.getName() + "(20/intBoxed < doubleBoxed)";
        tryArithmatic(text);

        text = "select * from " + SupportBean.class.getName() + "(20/intBoxed/doubleBoxed < 1)";
        tryArithmatic(text);
    }

    private void tryArithmatic(String text)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(text);
        stmt.addListener(testListener);

        sendBeanIntDouble(5, 5d);
        assertTrue(testListener.getAndClearIsInvoked());

        sendBeanIntDouble(5, 4d);
        assertFalse(testListener.getAndClearIsInvoked());

        sendBeanIntDouble(5, 4.001d);
        assertTrue(testListener.getAndClearIsInvoked());

        stmt.destroy();
    }
    
    public void testExpressionReversed()
    {
        String expr = "select * from " + SupportBean.class.getName() + "(5 = intBoxed)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(expr);
        stmt.addListener(testListener);

        sendBean("intBoxed", 5);
        assertTrue(testListener.getAndClearIsInvoked());
    }

    private void sendBean(int intPrimitive, Integer intBoxed, Double doubleBoxed)
    {
        SupportBean event = new SupportBean();
        event.setIntBoxed(intBoxed);
        event.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBeanIntDouble(Integer intBoxed, Double doubleBoxed)
    {
        SupportBean event = new SupportBean();
        event.setIntBoxed(intBoxed);
        event.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBeanLong(Long longBoxed)
    {
        SupportBean event = new SupportBean();
        event.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBeanString(String string)
    {
        SupportBean num = new SupportBean(string, -1);
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
}
