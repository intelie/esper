package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPException;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanNumeric;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestFilterInAndBetween extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testInDynamic()
    {
        String expr = "select * from pattern [a=" + SupportBeanNumeric.class.getName() + " -> every b=" + SupportBean.class.getName()
                + "(intPrimitive in (a.intOne, a.intTwo))]";
        EPStatement stmt = epService.getEPAdministrator().createEQL(expr);
        stmt.addListener(testListener);

        sendBeanNumeric(10, 20);
        sendBeanInt(10);
        assertTrue(testListener.getAndClearIsInvoked());
        sendBeanInt(11);
        assertFalse(testListener.getAndClearIsInvoked());
        sendBeanInt(20);
        assertTrue(testListener.getAndClearIsInvoked());
        stmt.stop();

        expr = "select * from pattern [a=" + SupportBean_S0.class.getName() + " -> every b=" + SupportBean.class.getName()
                + "(string in (a.p00, a.p01, a.p02))]";
        stmt = epService.getEPAdministrator().createEQL(expr);
        stmt.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "a", "b", "c", "d"));
        sendBeanString("a");
        assertTrue(testListener.getAndClearIsInvoked());
        sendBeanString("x");
        assertFalse(testListener.getAndClearIsInvoked());
        sendBeanString("b");
        assertTrue(testListener.getAndClearIsInvoked());
        sendBeanString("c");
        assertTrue(testListener.getAndClearIsInvoked());
        sendBeanString("d");
        assertFalse(testListener.getAndClearIsInvoked());
    }

    public void testSimpleInt()
    {
        String expr = "select * from " + SupportBean.class.getName() + "(intPrimitive in (1, 10))";
        EPStatement stmt = epService.getEPAdministrator().createEQL(expr);
        stmt.addListener(testListener);

        sendBeanInt(10);
        assertTrue(testListener.getAndClearIsInvoked());
        sendBeanInt(11);
        assertFalse(testListener.getAndClearIsInvoked());
        sendBeanInt(1);
        assertTrue(testListener.getAndClearIsInvoked());
    }

    public void testInvalid()
    {
        // we do not coerce
        tryInvalid("select * from " + SupportBean.class.getName() + "(intPrimitive in (1L, 10L))");
        tryInvalid("select * from " + SupportBean.class.getName() + "(intPrimitive in (1, 10L))");
        tryInvalid("select * from " + SupportBean.class.getName() + "(intPrimitive in (1, 'x'))");

        String expr = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" + SupportBean.class.getName()
                + "(intPrimitive in (a.longPrimitive, a.longBoxed))]";
        tryInvalid(expr);
    }

    public void testInExpr()
    {
        tryExpr("(string in ('a', 'b'))", "string", new String[] {"a", "x", "b", "y"}, new boolean [] {true, false, true, false});
        tryExpr("(boolPrimitive in (false))", "boolPrimitive", new Object[] {true, false}, new boolean [] {false, true});
        tryExpr("(boolPrimitive in (false, false, false))", "boolPrimitive", new Object[] {true, false}, new boolean [] {false, true});
        tryExpr("(boolPrimitive in (false, true, false))", "boolPrimitive", new Object[] {true, false}, new boolean [] {true, true});
        tryExpr("(intBoxed in (4, 6, 1))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, true, false, false, true, false, true});
        tryExpr("(intBoxed in (3))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, false, false, true, false, false, false});
        tryExpr("(longBoxed in (3))", "longBoxed", new Object[] {0L, 1L, 2L, 3L, 4L, 5L, 6L}, new boolean [] {false, false, false, true, false, false, false});
        tryExpr("(intBoxed between 4 and 6)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, false, false, false, true, true, true});
        tryExpr("(intBoxed between 2 and 1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, true, true, false, false, false, false});
        tryExpr("(intBoxed between 4 and -1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, true, true, true, true, false, false});
        tryExpr("(intBoxed in [2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, false, true, true, true, false, false});
        tryExpr("(intBoxed in (2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, false, false, true, true, false, false});
        tryExpr("(intBoxed in [2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, false, true, true, false, false, false});
        tryExpr("(intBoxed in (2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, false, false, true, false, false, false});

    }

    public void testNotInExpr()
    {
        tryExpr("(string not in ('a', 'b'))", "string", new String[] {"a", "x", "b", "y"}, new boolean [] {false, true, false, true});
        tryExpr("(boolPrimitive not in (false))", "boolPrimitive", new Object[] {true, false}, new boolean [] {true, false});
        tryExpr("(boolPrimitive not in (false, false, false))", "boolPrimitive", new Object[] {true, false}, new boolean [] {true, false});
        tryExpr("(boolPrimitive not in (false, true, false))", "boolPrimitive", new Object[] {true, false}, new boolean [] {false, false});
        tryExpr("(intBoxed not in (4, 6, 1))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, false, true, true, false, true, false});
        tryExpr("(intBoxed not in (3))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, true, true, false, true, true, true});
        tryExpr("(longBoxed not in (3))", "longBoxed", new Object[] {0L, 1L, 2L, 3L, 4L, 5L, 6L}, new boolean [] {true, true, true, false, true, true, true});
        tryExpr("(intBoxed not between 4 and 6)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, true, true, true, false, false, false});
        tryExpr("(intBoxed not between 2 and 1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, false, false, true, true, true, true});
        tryExpr("(intBoxed not between 4 and -1)", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {false, false, false, false, false, true, true});
        tryExpr("(intBoxed not in [2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, true, false, false, false, true, true});
        tryExpr("(intBoxed not in (2:4])", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, true, true, false, false, true, true});
        tryExpr("(intBoxed not in [2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, true, false, false, true, true, true});
        tryExpr("(intBoxed not in (2:4))", "intBoxed", new Object[] {0, 1, 2, 3, 4, 5, 6}, new boolean [] {true, true, true, false, true, true, true});
    }

    public void testReuse()
    {
        String expr = "select * from " + SupportBean.class.getName() + "(intBoxed in [2:4])";
        tryReuse(new String[] {expr, expr});

        expr = "select * from " + SupportBean.class.getName() + "(intBoxed in (1, 2, 3))";
        tryReuse(new String[] {expr, expr});

        String exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed in (2:3])";
        String exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed in (1:3])";
        tryReuse(new String[] {exprOne, exprTwo});

        exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed in (2, 3, 4))";
        exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed in (1, 3))";
        tryReuse(new String[] {exprOne, exprTwo});

        exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed in (2, 3, 4))";
        exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed in (1, 3))";
        String exprThree = "select * from " + SupportBean.class.getName() + "(intBoxed in (8, 3))";
        tryReuse(new String[] {exprOne, exprTwo, exprThree});

        exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed in (3, 1, 3))";
        exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed in (3, 3))";
        exprThree = "select * from " + SupportBean.class.getName() + "(intBoxed in (1, 3))";
        tryReuse(new String[] {exprOne, exprTwo, exprThree});

        exprOne = "select * from " + SupportBean.class.getName() + "(boolPrimitive=false, intBoxed in (1, 2, 3))";
        exprTwo = "select * from " + SupportBean.class.getName() + "(boolPrimitive=false, intBoxed in (3, 4))";
        exprThree = "select * from " + SupportBean.class.getName() + "(boolPrimitive=false, intBoxed in (3))";
        tryReuse(new String[] {exprOne, exprTwo, exprThree});

        exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed in (1, 2, 3), longPrimitive >= 0)";
        exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed in (3, 4), intPrimitive >= 0)";
        exprThree = "select * from " + SupportBean.class.getName() + "(intBoxed in (3), bytePrimitive < 1)";
        tryReuse(new String[] {exprOne, exprTwo, exprThree});
    }

    public void testReuseNot()
    {
        String expr = "select * from " + SupportBean.class.getName() + "(intBoxed not in [1:2])";
        tryReuse(new String[] {expr, expr});

        String exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed in (3, 1, 3))";
        String exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed not in (2, 1))";
        String exprThree = "select * from " + SupportBean.class.getName() + "(intBoxed not between 0 and -3)";
        tryReuse(new String[] {exprOne, exprTwo, exprThree});

        exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed not in (1, 4, 5))";
        exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed not in (1, 4, 5))";
        exprThree = "select * from " + SupportBean.class.getName() + "(intBoxed not in (4, 5, 1))";
        tryReuse(new String[] {exprOne, exprTwo, exprThree});

        exprOne = "select * from " + SupportBean.class.getName() + "(intBoxed not in (3:4))";
        exprTwo = "select * from " + SupportBean.class.getName() + "(intBoxed not in [1:3))";
        exprThree = "select * from " + SupportBean.class.getName() + "(intBoxed not in (1,1,1,33))";
        tryReuse(new String[] {exprOne, exprTwo, exprThree});
    }

    private void tryReuse(String[] statements)
    {
        SupportUpdateListener testListener[] = new SupportUpdateListener[statements.length];
        EPStatement stmt[] = new EPStatement[statements.length];

        // create all statements
        for (int i = 0; i < statements.length; i++)
        {
            testListener[i] = new SupportUpdateListener();
            stmt[i] = epService.getEPAdministrator().createEQL(statements[i]);
            stmt[i].addListener(testListener[i]);
        }

        // send event, all should receive the event
        sendBean("intBoxed", 3);
        for (int i = 0; i < testListener.length; i++)
        {
            assertTrue(testListener[i].isInvoked());
            testListener[i].reset();
        }

        // stop first, then second, then third etc statement
        for (int toStop = 0; toStop < statements.length; toStop++)
        {
            stmt[toStop].stop();

            // send event, all remaining statement received it
            sendBean("intBoxed", 3);
            for (int i = 0; i <= toStop; i++)
            {
                assertFalse(testListener[i].isInvoked());
                testListener[i].reset();
            }
            for (int i = toStop + 1; i < testListener.length; i++)
            {
                assertTrue(testListener[i].isInvoked());
                testListener[i].reset();
            }
        }

        // now all statements are stopped, send event and verify no listener received
        sendBean("intBoxed", 3);
        for (int i = 0; i < testListener.length; i++)
        {
            assertFalse(testListener[i].isInvoked());
        }
    }

    private void tryExpr(String filterExpr, String fieldName, Object[] values, boolean[] isInvoked)
    {
        String expr = "select * from " + SupportBean.class.getName() + filterExpr;
        EPStatement stmt = epService.getEPAdministrator().createEQL(expr);
        stmt.addListener(testListener);

        for (int i = 0; i < values.length; i++)
        {
            sendBean(fieldName, values[i]);
            assertEquals("Listener invocation unexpected for " + fieldName + "=" + values[i], isInvoked[i], testListener.isInvoked());
            testListener.reset();
        }

        stmt.stop();
    }

    private void sendBeanInt(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
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
        if (fieldName.equals("boolPrimitive"))
        {
            event.setBoolPrimitive((Boolean) value);
        }
        if (fieldName.equals("intBoxed"))
        {
            event.setIntBoxed((Integer) value);
        }
        if (fieldName.equals("longBoxed"))
        {
            event.setLongBoxed((Long) value);
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
