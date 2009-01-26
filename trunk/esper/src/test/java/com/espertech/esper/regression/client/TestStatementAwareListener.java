package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportStmtAwareUpdateListener;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestStatementAwareListener extends TestCase
{
    private EPServiceProvider epService;
    private SupportStmtAwareUpdateListener listener;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventTypeAlias("Bean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        listener = new SupportStmtAwareUpdateListener();
    }

    public void testStmtAware()
    {
        String stmtText = "select * from Bean";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean());
        assertTrue(listener.isInvoked());
        assertEquals(1, listener.getStatementList().size());
        assertEquals(statement, listener.getStatementList().get(0));
        assertEquals(1, listener.getSvcProviderList().size());
        assertEquals(epService, listener.getSvcProviderList().get(0));
    }

    public void testInvalid()
    {
        String stmtText = "select * from Bean";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        StatementAwareUpdateListener listener = null;
        try
        {
            statement.addListener(listener);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    public void testBothListeners()
    {
        String stmtText = "select * from Bean";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);

        SupportStmtAwareUpdateListener awareListeners[] = new SupportStmtAwareUpdateListener[3];
        SupportUpdateListener updateListeners[] = new SupportUpdateListener[awareListeners.length];
        for (int i = 0; i < awareListeners.length; i++)
        {
            awareListeners[i] = new SupportStmtAwareUpdateListener();
            statement.addListener(awareListeners[i]);
            updateListeners[i] = new SupportUpdateListener();
            statement.addListener(updateListeners[i]);
        }        

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        for (int i = 0; i < awareListeners.length; i++)
        {
            assertSame(event, updateListeners[i].assertOneGetNewAndReset().getUnderlying());
            assertSame(event, awareListeners[i].assertOneGetNewAndReset().getUnderlying());
        }

        statement.removeListener(awareListeners[1]);
        event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        for (int i = 0; i < awareListeners.length; i++)
        {
            if(i == 1)
            {
                assertSame(event, updateListeners[i].assertOneGetNewAndReset().getUnderlying());
                assertFalse(awareListeners[i].isInvoked());
            }
            else
            {
                assertSame(event, updateListeners[i].assertOneGetNewAndReset().getUnderlying());
                assertSame(event, awareListeners[i].assertOneGetNewAndReset().getUnderlying());
            }
        }

        statement.removeListener(updateListeners[1]);
        event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        for (int i = 0; i < awareListeners.length; i++)
        {
            if(i == 1)
            {
                assertFalse(updateListeners[i].isInvoked());
                assertFalse(awareListeners[i].isInvoked());
            }
            else
            {
                assertSame(event, updateListeners[i].assertOneGetNewAndReset().getUnderlying());
                assertSame(event, awareListeners[i].assertOneGetNewAndReset().getUnderlying());
            }
        }

        statement.addListener(updateListeners[1]);
        statement.addListener(awareListeners[1]);
        event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        for (int i = 0; i < awareListeners.length; i++)
        {
            assertSame(event, updateListeners[i].assertOneGetNewAndReset().getUnderlying());
            assertSame(event, awareListeners[i].assertOneGetNewAndReset().getUnderlying());
        }

        statement.removeAllListeners();
        for (int i = 0; i < awareListeners.length; i++)
        {
            assertFalse(updateListeners[i].isInvoked());
            assertFalse(awareListeners[i].isInvoked());
        }
    }
    
    public void testUseOnMultipleStmts()
    {
        EPStatement statementOne = epService.getEPAdministrator().createEPL("select * from Bean(string='A' or string='C')");
        EPStatement statementTwo = epService.getEPAdministrator().createEPL("select * from Bean(string='B' or string='C')");

        SupportStmtAwareUpdateListener awareListener = new SupportStmtAwareUpdateListener();
        statementOne.addListener(awareListener);
        statementTwo.addListener(awareListener);

        epService.getEPRuntime().sendEvent(new SupportBean("B", 1));
        assertEquals("B", awareListener.assertOneGetNewAndReset().get("string"));

        epService.getEPRuntime().sendEvent(new SupportBean("A", 1));
        assertEquals("A", awareListener.assertOneGetNewAndReset().get("string"));

        epService.getEPRuntime().sendEvent(new SupportBean("C", 1));
        assertEquals(2, awareListener.getNewDataList().size());
        assertEquals("C", awareListener.getNewDataList().get(0)[0].get("string"));
        assertEquals("C", awareListener.getNewDataList().get(1)[0].get("string"));
        EPStatement stmts[] = awareListener.getStatementList().toArray(new EPStatement[0]);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmts, new Object[] {statementOne, statementTwo});
    }

    public void testOrderOfInvocation()
    {
        String stmtText = "select * from Bean";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);

        MyStmtAwareUpdateListener awareListeners[] = new MyStmtAwareUpdateListener[2];
        MyUpdateListener updateListeners[] = new MyUpdateListener[awareListeners.length];
        List<Object> invoked = new ArrayList<Object>();
        for (int i = 0; i < awareListeners.length; i++)
        {
            awareListeners[i] = new MyStmtAwareUpdateListener(invoked);
            updateListeners[i] = new MyUpdateListener(invoked);
        }

        statement.addListener(awareListeners[0]);
        statement.addListener(updateListeners[1]);
        statement.addListener(updateListeners[0]);
        statement.addListener(awareListeners[1]);

        epService.getEPRuntime().sendEvent(new SupportBean());

        assertEquals(updateListeners[1], invoked.get(0));
        assertEquals(updateListeners[0], invoked.get(1));
        assertEquals(awareListeners[0], invoked.get(2));
        assertEquals(awareListeners[1], invoked.get(3));

        try
        {
            Iterator<UpdateListener> itOne = statement.getUpdateListeners();
            itOne.next();
            itOne.remove();
            fail();
        }
        catch (UnsupportedOperationException ex)
        {
            // expected
        }

        try
        {
            Iterator<StatementAwareUpdateListener> itOne = statement.getStatementAwareListeners();
            itOne.next();
            itOne.remove();
            fail();
        }
        catch (UnsupportedOperationException ex)
        {
            // expected
        }

        Iterator<UpdateListener> itOne = statement.getUpdateListeners();
        assertEquals(updateListeners[1], itOne.next());
        assertEquals(updateListeners[0], itOne.next());
        assertFalse(itOne.hasNext());

        Iterator<StatementAwareUpdateListener> itTwo = statement.getStatementAwareListeners();
        assertEquals(awareListeners[0], itTwo.next());
        assertEquals(awareListeners[1], itTwo.next());
        assertFalse(itTwo.hasNext());

        statement.removeAllListeners();
        assertFalse(statement.getStatementAwareListeners().hasNext());
        assertFalse(statement.getUpdateListeners().hasNext());

    }

    public class MyUpdateListener implements UpdateListener
    {
        private List<Object> invoked;

        public MyUpdateListener(List<Object> invoked)
        {
            this.invoked = invoked;
        }

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            invoked.add(this);
        }
    }

    public class MyStmtAwareUpdateListener implements StatementAwareUpdateListener
    {
        private List<Object> invoked;

        public MyStmtAwareUpdateListener(List<Object> invoked)
        {
            this.invoked = invoked;
        }

        public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPServiceProvider epServiceProvider)
        {
            invoked.add(this);
        }
    }
}
