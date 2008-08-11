package com.espertech.esper.regression.view;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportStaticMethodLib;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestFilterExpressions extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("SupportEvent", SupportTradeEvent.class);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testNullBooleanExpr()
    {
        String stmtOneText = "every event1=SupportEvent(userId like '123%')";
        EPStatement statement = epService.getEPAdministrator().createPattern(stmtOneText);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportTradeEvent(1, null, 1001));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportTradeEvent(2, "1234", 1001));
        assertEquals(2, listener.assertOneGetNewAndReset().get("event1.id"));
    }

    public void testFilterOverInClause()
    {
        // Test for Esper-159
        String stmtOneText = "every event1=SupportEvent(userId in ('100','101'),amount>=1000)";
        EPStatement statement = epService.getEPAdministrator().createPattern(stmtOneText);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportTradeEvent(1, "100", 1001));
        assertEquals(1, listener.assertOneGetNewAndReset().get("event1.id"));

        String stmtTwoText = "every event1=SupportEvent(userId in ('100','101'))";
        epService.getEPAdministrator().createPattern(stmtTwoText);

        epService.getEPRuntime().sendEvent(new SupportTradeEvent(2, "100", 1001));
        assertEquals(2, listener.assertOneGetNewAndReset().get("event1.id"));
    }

    public void testConstant()
    {
        String text = "select * from pattern [" +
            SupportBean.class.getName() + "(intPrimitive=" + ISupportA.class.getName() + ".VALUE_1)]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        SupportBean event = new SupportBean("e1", 2);
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.getAndClearIsInvoked());

        event = new SupportBean("e1", 1);
        epService.getEPRuntime().sendEvent(event);
        assertTrue(listener.isInvoked());
    }

    public void testEnumSyntaxOne()
    {
        String text = "select * from pattern [" +
            SupportBeanWithEnum.class.getName() + "(supportEnum=" + SupportEnum.class.getName() + ".valueOf('ENUM_VALUE_1'))]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        SupportBeanWithEnum event = new SupportBeanWithEnum("e1", SupportEnum.ENUM_VALUE_2);
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.getAndClearIsInvoked());

        event = new SupportBeanWithEnum("e1", SupportEnum.ENUM_VALUE_1);
        epService.getEPRuntime().sendEvent(event);
        assertTrue(listener.isInvoked());
    }

    public void testEnumSyntaxTwo()
    {
        String text = "select * from pattern [" +
            SupportBeanWithEnum.class.getName() + "(supportEnum=" + SupportEnum.class.getName() + ".ENUM_VALUE_2)]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        SupportBeanWithEnum event = new SupportBeanWithEnum("e1", SupportEnum.ENUM_VALUE_2);
        epService.getEPRuntime().sendEvent(event);
        assertTrue(listener.getAndClearIsInvoked());

        event = new SupportBeanWithEnum("e2", SupportEnum.ENUM_VALUE_1);
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());

        stmt.destroy();

        // test where clause
        text = "select * from " + SupportBeanWithEnum.class.getName() + " where supportEnum=" + SupportEnum.class.getName() + ".ENUM_VALUE_2";
        stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        event = new SupportBeanWithEnum("e1", SupportEnum.ENUM_VALUE_2);
        epService.getEPRuntime().sendEvent(event);
        assertTrue(listener.getAndClearIsInvoked());

        event = new SupportBeanWithEnum("e2", SupportEnum.ENUM_VALUE_1);
        epService.getEPRuntime().sendEvent(event);
        assertFalse(listener.isInvoked());
    }

    public void testNotEqualsNotIn()
    {
        tryNotEqualsConsolidate("intPrimitive not in (1, 2)");
    }

    public void testNotEqualsComma()
    {
        tryNotEqualsConsolidate("intPrimitive != 1, intPrimitive != 2");
    }

    public void testNotEqualsAnd()
    {
        tryNotEqualsConsolidate("intPrimitive != 1 and intPrimitive != 2");
    }

    public void tryNotEqualsConsolidate(String filter)
    {
        String text = "select * from " + SupportBean.class.getName() + "(" + filter + ")";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        for (int i = 0; i < 5; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("", i));

            if ((i == 1) || (i == 2))
            {
                assertFalse("incorrect:" + i, listener.isInvoked());
            }
            else
            {
                assertTrue("incorrect:" + i, listener.isInvoked());
            }
            listener.reset();
        }

    }

    public void testEqualsSemanticFilter()
    {
        // Test for Esper-114
        String text = "select * from " + SupportBeanComplexProps.class.getName() + "(nested=nested)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        SupportBeanComplexProps eventOne = SupportBeanComplexProps.makeDefaultBean();
        eventOne.setSimpleProperty("1");

        epService.getEPRuntime().sendEvent(eventOne);
        assertTrue(listener.isInvoked());
    }

    public void testEqualsSemanticExpr()
    {
        // Test for Esper-114
        String text = "select * from " + SupportBeanComplexProps.class.getName() + "(simpleProperty='1') as s0" +
                ", " + SupportBeanComplexProps.class.getName() + "(simpleProperty='2') as s1" +
                " where s0.nested = s1.nested";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        SupportBeanComplexProps eventOne = SupportBeanComplexProps.makeDefaultBean();
        eventOne.setSimpleProperty("1");

        SupportBeanComplexProps eventTwo = SupportBeanComplexProps.makeDefaultBean();
        eventTwo.setSimpleProperty("2");

        assertEquals(eventOne.getNested(), eventTwo.getNested());

        epService.getEPRuntime().sendEvent(eventOne);
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(eventTwo);
        assertTrue(listener.isInvoked());
    }

    public void testPatternFunc3Stream()
    {
        String text;

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed=a.intBoxed, intBoxed=b.intBoxed and intBoxed != null)]";
        tryPattern3Stream(text, new Integer[] {null, 2, 1, null,   8,  1,  2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 4, -2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 5, null}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                    new boolean[] {false, false, true, false, false, false, false});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed=a.intBoxed or intBoxed=b.intBoxed)]";
        tryPattern3Stream(text, new Integer[] {null, 2, 1, null,   8, 1, 2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 4, -2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 5, null}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                    new boolean[] {true, true, true, true, true, false, false});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed=a.intBoxed, intBoxed=b.intBoxed)]";
        tryPattern3Stream(text, new Integer[] {null, 2, 1, null,   8,  1,  2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 4, -2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 5, null}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                    new boolean[] {true, false, true, false, false, false, false});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed!=a.intBoxed, intBoxed!=b.intBoxed)]";
        tryPattern3Stream(text, new Integer[] {null, 2, 1, null,   8,  1,  2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 4, -2}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, 3, 1,    8, null, 5, null}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                    new boolean[] {false, false, false, false, false, true, false});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed!=a.intBoxed)]";
        tryPattern3Stream(text, new Integer[] {2,    8,    null, 2, 1, null, 1}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {-2,   null, null, 3, 1,    8, 4}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {null, null, null, 3, 1,    8, 5}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                               new boolean[] {false, false, false, true, false, true, true});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed=a.intBoxed, doubleBoxed=b.doubleBoxed)]";
        tryPattern3Stream(text, new Integer[] {2, 2, 1, 2, 1, 7, 1}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {0, 0, 0, 0, 0, 0, 0}, new Double[] {1d, 2d, 0d, 2d, 0d, 1d, 0d},
                                new Integer[] {2, 2, 3, 2, 1, 7, 5}, new Double[] {1d, 1d, 1d, 2d, 1d, 1d, 1d},
                               new boolean[] {true, false, false, true, false, true, false});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed in (a.intBoxed, b.intBoxed))]";
        tryPattern3Stream(text, new Integer[] {2,    1, 1,     null,   1, null,    1}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {1,    2, 1,     null, null,   2,    0}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {2,    2, 3,     null,   1, null,  null}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                           new boolean[]   {true, true, false, false, true, false, false});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed in [a.intBoxed:b.intBoxed])]";
        tryPattern3Stream(text, new Integer[] {2,    1, 1,     null,   1, null,    1}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {1,    2, 1,     null, null,   2,    0}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {2,    1, 3,     null,   1, null,  null}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                           new boolean[]   {true, true, false, false, false, false, false});

        text = "select * from pattern [" +
                "a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportBean.class.getName() + " -> " +
                "c=" + SupportBean.class.getName() + "(intBoxed not in [a.intBoxed:b.intBoxed])]";
        tryPattern3Stream(text, new Integer[] {2,    1, 1,     null,   1, null,    1}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {1,    2, 1,     null, null,   2,    0}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                                new Integer[] {2,    1, 3,     null,   1, null,  null}, new Double[] {0d, 0d, 0d, 0d, 0d, 0d, 0d},
                           new boolean[]   {false, false, true, false, false, false, false});
    }

    public void testPatternFunc()
    {
        String text;

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(intBoxed = a.intBoxed and doubleBoxed = a.doubleBoxed)]";
        tryPattern(text, new Integer[] {null, 2, 1, null, 8, 1, 2}, new Double[] {2d, 2d, 2d, 1d, 5d, 6d, 7d},
                         new Integer[] {null, 3, 1, 8, null, 1, 2}, new Double[] {2d, 3d, 2d, 1d, 5d, 6d, 8d},
                    new boolean[] {true, false, true, false, false, true, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(a.doubleBoxed = doubleBoxed)]";
        tryPattern(text, new Integer[] {0, 0}, new Double[] {2d, 2d},
                         new Integer[] {0, 0}, new Double[] {2d, 3d},
                    new boolean[] {true, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(a.doubleBoxed = b.doubleBoxed)]";
        tryPattern(text, new Integer[] {0, 0}, new Double[] {2d, 2d},
                         new Integer[] {0, 0}, new Double[] {2d, 3d},
                    new boolean[] {true, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(a.doubleBoxed != doubleBoxed)]";
        tryPattern(text, new Integer[] {0, 0}, new Double[] {2d, 2d},
                         new Integer[] {0, 0}, new Double[] {2d, 3d},
                    new boolean[] {false, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(a.doubleBoxed != b.doubleBoxed)]";
        tryPattern(text, new Integer[] {0, 0}, new Double[] {2d, 2d},
                         new Integer[] {0, 0}, new Double[] {2d, 3d},
                    new boolean[] {false, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed in [a.doubleBoxed:a.intBoxed])]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {false, true, true, true, true, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed in (a.doubleBoxed:a.intBoxed])]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {false, false, true, true, true, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(b.doubleBoxed in (a.doubleBoxed:a.intBoxed))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {false, false, true, true, false, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed in [a.doubleBoxed:a.intBoxed))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {false, true, true, true, false, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed not in [a.doubleBoxed:a.intBoxed])]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {true, false, false, false, false, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed not in (a.doubleBoxed:a.intBoxed])]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {true, true, false, false, false, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(b.doubleBoxed not in (a.doubleBoxed:a.intBoxed))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {true, true, false, false, true, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed not in [a.doubleBoxed:a.intBoxed))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {true, false, false, false, true, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed not in (a.doubleBoxed, a.intBoxed, 9))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {true, false, true, false, false, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed in (a.doubleBoxed, a.intBoxed, 9))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {false, true, false, true, true, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(b.doubleBoxed in (doubleBoxed, a.intBoxed, 9))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {true, true, true, true, true, true});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed not in (doubleBoxed, a.intBoxed, 9))]";
        tryPattern(text, new Integer[] {1, 1, 1, 1, 1, 1}, new Double[] {10d, 10d, 10d, 10d, 10d, 10d},
                         new Integer[] {0, 0, 0, 0, 0, 0}, new Double[] {0d, 1d, 2d, 9d, 10d, 11d},
                    new boolean[] {false, false, false, false, false, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed = " + SupportStaticMethodLib.class.getName() + ".minusOne(a.doubleBoxed))]";
        tryPattern(text, new Integer[] {0, 0, 0}, new Double[] {10d, 10d, 10d},
                         new Integer[] {0, 0, 0}, new Double[] {9d, 10d, 11d, },
                    new boolean[] {true, false, false});

        text = "select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed = " + SupportStaticMethodLib.class.getName() + ".minusOne(a.doubleBoxed) or " +
                    "doubleBoxed = " + SupportStaticMethodLib.class.getName() + ".minusOne(a.intBoxed))]";
        tryPattern(text, new Integer[] {0, 0, 12}, new Double[] {10d, 10d, 10d},
                         new Integer[] {0, 0, 0}, new Double[] {9d, 10d, 11d, },
                    new boolean[] {true, false, true});
    }

    private void tryPattern(String text,
                            Integer[] intBoxedA,
                            Double[] doubleBoxedA,
                            Integer[] intBoxedB,
                            Double[] doubleBoxedB,
                            boolean[] expected)
    {
        assertEquals(intBoxedA.length, doubleBoxedA.length);
        assertEquals(intBoxedB.length, doubleBoxedB.length);
        assertEquals(expected.length, doubleBoxedA.length);
        assertEquals(intBoxedA.length, doubleBoxedB.length);

        for (int i = 0; i < intBoxedA.length; i++)
        {
            EPStatement stmt = epService.getEPAdministrator().createEPL(text);
            stmt.addListener(listener);

            sendBeanIntDouble(intBoxedA[i], doubleBoxedA[i]);
            sendBeanIntDouble(intBoxedB[i], doubleBoxedB[i]);
            assertEquals("failed at index " + i, expected[i], listener.getAndClearIsInvoked());
            stmt.stop();
        }
    }

    private void tryPattern3Stream(String text,
                            Integer[] intBoxedA,
                            Double[] doubleBoxedA,
                            Integer[] intBoxedB,
                            Double[] doubleBoxedB,
                            Integer[] intBoxedC,
                            Double[] doubleBoxedC,
                            boolean[] expected)
    {
        assertEquals(intBoxedA.length, doubleBoxedA.length);
        assertEquals(intBoxedB.length, doubleBoxedB.length);
        assertEquals(expected.length, doubleBoxedA.length);
        assertEquals(intBoxedA.length, doubleBoxedB.length);
        assertEquals(intBoxedC.length, doubleBoxedC.length);
        assertEquals(intBoxedB.length, doubleBoxedC.length);

        for (int i = 0; i < intBoxedA.length; i++)
        {
            EPStatement stmt = epService.getEPAdministrator().createEPL(text);
            stmt.addListener(listener);

            sendBeanIntDouble(intBoxedA[i], doubleBoxedA[i]);
            sendBeanIntDouble(intBoxedB[i], doubleBoxedB[i]);
            sendBeanIntDouble(intBoxedC[i], doubleBoxedC[i]);
            assertEquals("failed at index " + i, expected[i], listener.getAndClearIsInvoked());
            stmt.stop();
        }
    }

    public void testIn3ValuesAndNull()
    {
        String text;

        text = "select * from " + SupportBean.class.getName() + "(intPrimitive in (intBoxed, doubleBoxed))";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{0, 1, 0}, new Double[]{2d, 2d, 1d}, new boolean[]{false, true, true});

        text = "select * from " + SupportBean.class.getName() + "(intPrimitive in (intBoxed, " +
            SupportStaticMethodLib.class.getName() + ".minusOne(doubleBoxed)))";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{0, 1, 0}, new Double[]{2d, 2d, 1d}, new boolean[]{true, true, false});

        text = "select * from " + SupportBean.class.getName() + "(intPrimitive not in (intBoxed, doubleBoxed))";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{0, 1, 0}, new Double[]{2d, 2d, 1d}, new boolean[]{true, false, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed = doubleBoxed)";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{null, 1, null}, new Double[]{null, null, 1d}, new boolean[]{true, false, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in (doubleBoxed))";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{null, 1, null}, new Double[]{null, null, 1d}, new boolean[]{true, false, false});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed not in (doubleBoxed))";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{null, 1, null}, new Double[]{null, null, 1d}, new boolean[]{false, true, true});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed in [doubleBoxed:10))";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{null, 1, 2}, new Double[]{null, null, 1d}, new boolean[]{false, false, true});

        text = "select * from " + SupportBean.class.getName() + "(intBoxed not in [doubleBoxed:10))";
        try3Fields(text, new int[]{1, 1, 1}, new Integer[]{null, 1, 2}, new Double[]{null, null, 1d}, new boolean[]{false, true, false});
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

    public void testFilterRelationalOpRange()
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

    private void tryFilterRelationalOpRange(String text, int[] testData, boolean[] isReceived)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        assertEquals(testData.length,  isReceived.length);
        for (int i = 0; i < testData.length; i++)
        {
            sendBeanIntDouble(testData[i], 0D);
            assertEquals("failed testing index " + i, isReceived[i], listener.getAndClearIsInvoked());
        }
        stmt.removeListener(listener);
    }

    private void tryFilter(String text, boolean isReceived)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        sendBeanString("a");
        assertFalse(listener.getAndClearIsInvoked());
        sendBeanString("b");
        assertEquals(isReceived, listener.getAndClearIsInvoked());
        sendBeanString("c");
        assertFalse(listener.getAndClearIsInvoked());

        stmt.removeListener(listener);
    }

    private void try3Fields(String text,
                            int[] intPrimitive,
                            Integer[] intBoxed,
                            Double[] doubleBoxed,
                            boolean[] expected)
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        assertEquals(intPrimitive.length, doubleBoxed.length);
        assertEquals(intBoxed.length, doubleBoxed.length);
        assertEquals(expected.length, doubleBoxed.length);
        for (int i = 0; i < intBoxed.length; i++)
        {
            sendBeanIntIntDouble(intPrimitive[i], intBoxed[i], doubleBoxed[i]);
            assertEquals("failed at index " + i, expected[i], listener.getAndClearIsInvoked());
        }

        stmt.stop();
    }

    public void testFilterBooleanExpr()
    {
        String text = "select * from " + SupportBean.class.getName() + "(2*intBoxed=doubleBoxed)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        sendBeanIntDouble(20, 50d);
        assertFalse(listener.getAndClearIsInvoked());
        sendBeanIntDouble(25, 50d);
        assertTrue(listener.getAndClearIsInvoked());

        text = "select * from " + SupportBean.class.getName() + "(2*intBoxed=doubleBoxed, string='s')";
        stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listenerTwo = new SupportUpdateListener();
        stmt.addListener(listenerTwo);

        sendBeanIntDoubleString(25, 50d, "s");
        assertTrue(listenerTwo.getAndClearIsInvoked());
        sendBeanIntDoubleString(25, 50d, "x");
        assertFalse(listenerTwo.getAndClearIsInvoked());
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
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        assertEquals(intBoxed.length, doubleBoxed.length);
        assertEquals(expected.length, doubleBoxed.length);
        for (int i = 0; i < intBoxed.length; i++)
        {
            sendBeanIntDouble(intBoxed[i], doubleBoxed[i]);
            assertEquals("failed at index " + i, expected[i], listener.getAndClearIsInvoked());
        }

        stmt.stop();
    }

    public void testInvalid()
    {
        tryInvalid("select * from pattern [every a=" + SupportBean.class.getName() + " -> " +
                "b=" + SupportMarketDataBean.class.getName() + "(sum(a.longBoxed) = 2)]",
                "Aggregation functions not allowed within filters [select * from pattern [every a=com.espertech.esper.support.bean.SupportBean -> b=com.espertech.esper.support.bean.SupportMarketDataBean(sum(a.longBoxed) = 2)]]");

        tryInvalid("select * from pattern [every a=" + SupportBean.class.getName() + "(prior(1, a.longBoxed))]",
                "Prior function cannot be used in this context [select * from pattern [every a=com.espertech.esper.support.bean.SupportBean(prior(1, a.longBoxed))]]");

        tryInvalid("select * from pattern [every a=" + SupportBean.class.getName() + "(prev(1, a.longBoxed))]",
                "Previous function cannot be used in this context [select * from pattern [every a=com.espertech.esper.support.bean.SupportBean(prev(1, a.longBoxed))]]");

        tryInvalid("select * from " + SupportBean.class.getName() + "(5 - 10)",
                "Filter expression not returning a boolean value: '(5-10)' [select * from com.espertech.esper.support.bean.SupportBean(5 - 10)]");

        tryInvalid("select * from " + SupportBeanWithEnum.class.getName() + "(string=" + SupportEnum.class.getName() + ".ENUM_VALUE_1)",
                "Implicit conversion from datatype 'SupportEnum' to 'String' is not allowed [select * from com.espertech.esper.support.bean.SupportBeanWithEnum(string=com.espertech.esper.support.bean.SupportEnum.ENUM_VALUE_1)]");

        tryInvalid("select * from " + SupportBeanWithEnum.class.getName() + "(supportEnum=A.b)",
                "Failed to resolve property 'A.b' to a stream or nested property in a stream [select * from com.espertech.esper.support.bean.SupportBeanWithEnum(supportEnum=A.b)]");

        tryInvalid("select * from pattern [a=" + SupportBean.class.getName() + " -> b=" +
                SupportBean.class.getName() + "(doubleBoxed not in (doubleBoxed, x.intBoxed, 9))]",
                "Failed to resolve property 'x.intBoxed' to a stream or nested property in a stream [select * from pattern [a=com.espertech.esper.support.bean.SupportBean -> b=com.espertech.esper.support.bean.SupportBean(doubleBoxed not in (doubleBoxed, x.intBoxed, 9))]]");

        tryInvalid("select * from pattern [a=" + SupportBean.class.getName()
                + " -> b=" + SupportBean.class.getName() + "(c.intPrimitive=a.intPrimitive)"
                + " -> c=" + SupportBean.class.getName()
                + "]",
                "Failed to resolve property 'c.intPrimitive' to a stream or nested property in a stream [select * from pattern [a=com.espertech.esper.support.bean.SupportBean -> b=com.espertech.esper.support.bean.SupportBean(c.intPrimitive=a.intPrimitive) -> c=com.espertech.esper.support.bean.SupportBean]]");
    }

    private void tryInvalid(String text, String expectedMsg)
    {
        try
        {
            epService.getEPAdministrator().createEPL(text);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(expectedMsg, ex.getMessage());
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
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        sendBeanLong(10L);
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 0L, ""));
        assertFalse(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 5L, ""));
        assertTrue(listener.getAndClearIsInvoked());

        sendBeanLong(0L);
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 0L, ""));
        assertTrue(listener.getAndClearIsInvoked());
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 1L, ""));
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanLong(20L);
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 0, 10L, ""));
        assertTrue(listener.getAndClearIsInvoked());

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
        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        stmt.addListener(listener);

        sendBeanIntDouble(5, 5d);
        assertTrue(listener.getAndClearIsInvoked());

        sendBeanIntDouble(5, 4d);
        assertFalse(listener.getAndClearIsInvoked());

        sendBeanIntDouble(5, 4.001d);
        assertTrue(listener.getAndClearIsInvoked());

        stmt.destroy();
    }

    public void testExpressionReversed()
    {
        String expr = "select * from " + SupportBean.class.getName() + "(5 = intBoxed)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(expr);
        stmt.addListener(listener);

        sendBean("intBoxed", 5);
        assertTrue(listener.getAndClearIsInvoked());
    }

    private void sendBeanIntDouble(Integer intBoxed, Double doubleBoxed)
    {
        SupportBean event = new SupportBean();
        event.setIntBoxed(intBoxed);
        event.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBeanIntDoubleString(Integer intBoxed, Double doubleBoxed, String string)
    {
        SupportBean event = new SupportBean();
        event.setIntBoxed(intBoxed);
        event.setDoubleBoxed(doubleBoxed);
        event.setString(string);
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendBeanIntIntDouble(int intPrimitive, Integer intBoxed, Double doubleBoxed)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
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
