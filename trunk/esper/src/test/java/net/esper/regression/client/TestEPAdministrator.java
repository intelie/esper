package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;

public class TestEPAdministrator extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void test1kValidStmtsPerformance()
    {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            String text = "select * from " + SupportBean.class.getName();
            EPStatement stmt = epService.getEPAdministrator().createEQL(text, "s1");
            assertEquals("s1", stmt.getName());
            stmt.stop();
            stmt.start();
            stmt.stop();
            stmt.destroy();
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue(".test10kValid delta=" + delta, delta < 5000);
    }

    public void test1kInvalidStmts()
    {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            try
            {
                String text = "select xxx from " + SupportBean.class.getName();
                epService.getEPAdministrator().createEQL(text, "s1");
            }
            catch (Exception ex)
            {
                // expected
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue(".test1kInvalid delta=" + delta, delta < 2500);
    }

    public void testGetStmtByName()
    {
        String[] names = new String[] {"s1", "s2", "s3--0", "s3", "s3"};
        String[] expected = new String[] {"s1", "s2", "s3--0", "s3", "s3--1"};
        EPStatement[] stmts = createStmts(names);
        for (int i = 0; i < stmts.length; i++)
        {
            assertSame("failed for " + names[i], stmts[i], epService.getEPAdministrator().getStatement(expected[i]));
            assertEquals("failed for " + names[i], expected[i], epService.getEPAdministrator().getStatement(expected[i]).getName());
        }
    }

    public void testStatementArray()
    {
        assertEquals(0, epService.getEPAdministrator().getStatementNames().length);

        String[] names = new String[] {"s1"};
        EPStatement[] stmtsSetOne = createStmts(names);
        ArrayAssertionUtil.assertEqualsAnyOrder(names, epService.getEPAdministrator().getStatementNames());

        names = new String[] {"s1", "s2"};
        EPStatement[] stmtsSetTwo = createStmts(names);
        ArrayAssertionUtil.assertEqualsAnyOrder(new String[] {"s1", "s1--0", "s2"} , epService.getEPAdministrator().getStatementNames());
    }

    public void testCreateEQLByName()
    {
        String stmt = "select * from " + SupportBean.class.getName();
        EPStatement stmtOne = epService.getEPAdministrator().createEQL(stmt, "s1");
        stmtOne.addListener(testListener);
        assertEquals("s1", stmtOne.getName());
        assertEquals(stmt, stmtOne.getText());

        // check working
        sendEvent();
        testListener.assertOneGetNewAndReset();

        // create a second with the same name
        stmt = "select intPrimitive from " + SupportBean.class.getName();
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmt, "s1");
        assertEquals("s1--0", stmtTwo.getName());
        assertEquals(stmt, stmtTwo.getText());

        // create a third invalid statement with the same name
        stmt = "select xxx from " + SupportBean.class.getName();
        try
        {
            epService.getEPAdministrator().createEQL(stmt, "s1");
            fail();
        }
        catch (RuntimeException ex)
        {
            // expected
        }

        // create a forth statement with the same name
        stmt = "select string from " + SupportBean.class.getName();
        EPStatement stmtFour = epService.getEPAdministrator().createEQL(stmt, "s1");
        assertEquals("s1--1", stmtFour.getName());
        assertEquals(stmt, stmtFour.getText());

        // create a fifth pattern statement with the same name
        stmt = SupportBean.class.getName();
        EPStatement stmtFive = epService.getEPAdministrator().createPattern(stmt, "s1");
        assertEquals("s1--2", stmtFive.getName());
        assertEquals(stmt, stmtFive.getText());

        // should allow null statement name
        epService.getEPAdministrator().createPattern(stmt, null);
    }

    public void testCreatePatternByName()
    {
        String stmt = SupportBean.class.getName();
        EPStatement stmtOne = epService.getEPAdministrator().createPattern(stmt, "s1");
        stmtOne.addListener(testListener);
        assertEquals("s1", stmtOne.getName());
        assertEquals(stmt, stmtOne.getText());

        // check working
        sendEvent();
        testListener.assertOneGetNewAndReset();

        // create a second with the same name
        stmt = SupportMarketDataBean.class.getName();
        EPStatement stmtTwo = epService.getEPAdministrator().createPattern(stmt, "s1");
        assertEquals("s1--0", stmtTwo.getName());
        assertEquals(stmt, stmtTwo.getText());

        // create a third invalid statement with the same name
        stmt = "xxx" + SupportBean.class.getName();
        try
        {
            epService.getEPAdministrator().createPattern(stmt, "s1");
            fail();
        }
        catch (RuntimeException ex)
        {
            // expected
        }

        // create a forth statement with the same name
        stmt = SupportBean.class.getName();
        EPStatement stmtFour = epService.getEPAdministrator().createPattern(stmt, "s1");
        assertEquals("s1--1", stmtFour.getName());
        assertEquals(stmt, stmtFour.getText());

        // create a fifth pattern statement with the same name
        stmt = "select * from " + SupportBean.class.getName();
        EPStatement stmtFive = epService.getEPAdministrator().createEQL(stmt, "s1");
        assertEquals("s1--2", stmtFive.getName());
        assertEquals(stmt, stmtFive.getText());

        // Null statement names should be allowed
        epService.getEPAdministrator().createPattern("every " + SupportBean.class.getName(), null);
        epService.getEPAdministrator().destroyAllStatements();
    }

    public void testDestroyAll()
    {
        EPStatement[] stmts = createStmts(new String[] {"s1", "s2", "s3"});
        stmts[0].addListener(testListener);
        stmts[1].addListener(testListener);
        stmts[2].addListener(testListener);
        sendEvent();
        assertEquals(3, testListener.getNewDataList().size());
        testListener.reset();

        epService.getEPAdministrator().destroyAllStatements();
        assertDestroyed(stmts);
    }

    public void testStopStartAll()
    {
        EPStatement[] stmts = createStmts(new String[] {"s1", "s2", "s3"});
        stmts[0].addListener(testListener);
        stmts[1].addListener(testListener);
        stmts[2].addListener(testListener);

        assertStarted(stmts);

        epService.getEPAdministrator().stopAllStatements();
        assertStopped(stmts);

        epService.getEPAdministrator().startAllStatements();
        assertStarted(stmts);

        epService.getEPAdministrator().destroyAllStatements();
        assertDestroyed(stmts);
    }

    public void testStopStartSome()
    {
        EPStatement[] stmts = createStmts(new String[] {"s1", "s2", "s3"});
        stmts[0].addListener(testListener);
        stmts[1].addListener(testListener);
        stmts[2].addListener(testListener);
        assertStarted(stmts);

        stmts[0].stop();
        sendEvent();
        assertEquals(2, testListener.getNewDataList().size());
        testListener.reset();

        epService.getEPAdministrator().stopAllStatements();
        assertStopped(stmts);

        stmts[1].start();
        sendEvent();
        assertEquals(1, testListener.getNewDataList().size());
        testListener.reset();

        epService.getEPAdministrator().startAllStatements();
        assertStarted(stmts);

        epService.getEPAdministrator().destroyAllStatements();
        assertDestroyed(stmts);
    }

    private void assertStopped(EPStatement[] stmts)
    {
        for (int i = 0; i < stmts.length; i++)
        {
            assertEquals(EPStatementState.STOPPED, stmts[i].getState());
        }
        sendEvent();
        assertEquals(0, testListener.getNewDataList().size());
        testListener.reset();
    }

    private void assertStarted(EPStatement[] stmts)
    {
        for (int i = 0; i < stmts.length; i++)
        {
            assertEquals(EPStatementState.STARTED, stmts[i].getState());
        }
        sendEvent();
        assertEquals(stmts.length, testListener.getNewDataList().size());
        testListener.reset();
    }

    private void assertDestroyed(EPStatement[] stmts)
    {
        for (int i = 0; i < stmts.length; i++)
        {
            assertEquals(EPStatementState.DESTROYED, stmts[i].getState());
        }
        sendEvent();
        assertEquals(0, testListener.getNewDataList().size());
        testListener.reset();
    }

    private EPStatement[] createStmts(String[] statementNames)
    {
        EPStatement statements[] = new EPStatement[statementNames.length];
        for (int i = 0; i < statementNames.length; i++)
        {
            statements[i] = epService.getEPAdministrator().createEQL("select * from " + SupportBean.class.getName(), statementNames[i]);
        }
        return statements;
    }

    private void sendEvent()
    {
        SupportBean bean = new SupportBean();
        epService.getEPRuntime().sendEvent(bean);
    }
}
