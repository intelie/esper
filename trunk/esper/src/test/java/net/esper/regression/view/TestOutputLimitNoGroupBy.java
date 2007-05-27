package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.util.SupportUpdateListener;

public class TestOutputLimitNoGroupBy extends TestCase
{
    private final static String JOIN_KEY = "KEY";

	private EPServiceProvider epService;	

    
    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }
    
    public void testSimpleNoJoinAll()
	{
	    String viewExpr = "select longBoxed " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output all every 2 events";
	    
	    runAssertAll(createStmtAndListenerNoJoin(viewExpr));
	    
	    viewExpr = "select longBoxed " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output every 2 events";
	    
	    runAssertAll(createStmtAndListenerNoJoin(viewExpr));
	    
	    viewExpr = "select * " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output every 2 events";
	    
	    runAssertAll(createStmtAndListenerNoJoin(viewExpr));
	}
	public void testAggregateAllNoJoinAll()
	{
	    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "having sum(longBoxed) > 0 " + 
	    "output all every 2 events";
	    
	    runAssertAllSum(createStmtAndListenerNoJoin(viewExpr));
	    
	    viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output every 2 events";
	    
	    runAssertAllSum(createStmtAndListenerNoJoin(viewExpr));
	}

	public void testAggregateAllNoJoinLast()
	{
	    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "having sum(longBoxed) > 0 " +
	    "output last every 2 events";
	    
	    runAssertLastSum(createStmtAndListenerNoJoin(viewExpr));  
	    
	    viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBean.class.getName() + ".win:length(3) " +
	    "output last every 2 events";
	    
	    runAssertLastSum(createStmtAndListenerNoJoin(viewExpr));   
	}

	public void testSimpleNoJoinLast()
    {
        String viewExpr = "select longBoxed " +
        "from " + SupportBean.class.getName() + ".win:length(3) " +
        "output last every 2 events";

        runAssertLast(createStmtAndListenerNoJoin(viewExpr));
        
        viewExpr = "select * " +
        "from " + SupportBean.class.getName() + ".win:length(3) " +
        "output last every 2 events";

        runAssertLast(createStmtAndListenerNoJoin(viewExpr));
    }
    
    public void testSimpleJoinAll()
	{
	    String viewExpr = "select longBoxed  " +
	    "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
	    SupportBean.class.getName() + ".win:length(3) as two " +
	    "output all every 2 events";
	    
		runAssertAll(createStmtAndListenerJoin(viewExpr));
	}
    
    private SupportUpdateListener createStmtAndListenerNoJoin(String viewExpr) {
		epService.initialize();
		SupportUpdateListener updateListener = new SupportUpdateListener();
		EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
	    view.addListener(updateListener);
	    
	    return updateListener;
	}
    
	public void testAggregateAllJoinAll()
	{
	    String viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
	    SupportBean.class.getName() + ".win:length(3) as two " +
	    "having sum(longBoxed) > 0 " +
	    "output all every 2 events";
	    
	    runAssertAllSum(createStmtAndListenerJoin(viewExpr));
	    
	    viewExpr = "select longBoxed, sum(longBoxed) as result " +
	    "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
	    SupportBean.class.getName() + ".win:length(3) as two " +
	    "output every 2 events";
	    
	    runAssertAllSum(createStmtAndListenerJoin(viewExpr));
	}

	public void testAggregateAllJoinLast()
    {
        String viewExpr = "select longBoxed, sum(longBoxed) as result " +
        "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
        SupportBean.class.getName() + ".win:length(3) as two " +
        "having sum(longBoxed) > 0 " +
        "output last every 2 events";
        
        runAssertLastSum(createStmtAndListenerJoin(viewExpr));
        
        viewExpr = "select longBoxed, sum(longBoxed) as result " +
        "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
        SupportBean.class.getName() + ".win:length(3) as two " +
        "output last every 2 events";
        
        runAssertLastSum(createStmtAndListenerJoin(viewExpr));
    }

	private void runAssertAll(SupportUpdateListener updateListener) 
	{
		// send an event
	    sendEvent(1);
	
	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());
	    
	    // send another event
	    sendEvent(2);
	    
	    // check update, all events present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(2, updateListener.getLastNewData().length);
	    assertEquals(1L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertEquals(2L, updateListener.getLastNewData()[1].get("longBoxed"));
	    assertNull(updateListener.getLastOldData());
	}

	private void runAssertAllSum(SupportUpdateListener updateListener) 
	{
		// send an event
	    sendEvent(1);
	
	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());
	    
	    // send another event
	    sendEvent(2);
	    
	    // check update, all events present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(2, updateListener.getLastNewData().length);
	    assertEquals(1L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertEquals(1L, updateListener.getLastNewData()[0].get("result"));
	    assertEquals(2L, updateListener.getLastNewData()[1].get("longBoxed"));
	    assertEquals(3L, updateListener.getLastNewData()[1].get("result"));
	    assertNull(updateListener.getLastOldData());
	}

	private void runAssertLastSum(SupportUpdateListener updateListener) 
	{
		// send an event
	    sendEvent(1);
	
	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());
	    
	    // send another event
	    sendEvent(2);
	    
	    // check update, all events present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(1, updateListener.getLastNewData().length);
	    assertEquals(2L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertEquals(3L, updateListener.getLastNewData()[0].get("result"));
	    assertNull(updateListener.getLastOldData());
	}
    
    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
	{
	    SupportBean bean = new SupportBean();
	    bean.setString(JOIN_KEY);
	    bean.setLongBoxed(longBoxed);
	    bean.setIntBoxed(intBoxed);
	    bean.setShortBoxed(shortBoxed);
	    epService.getEPRuntime().sendEvent(bean);
	}

	private void sendEvent(long longBoxed)
	{
	    sendEvent(longBoxed, 0, (short)0);
	}

	public void testSimpleJoinLast()
	{
	    String viewExpr = "select longBoxed " +
	    "from " + SupportBeanString.class.getName() + ".win:length(3) as one, " +
	    SupportBean.class.getName() + ".win:length(3) as two " +
	    "output last every 2 events";
	    
		runAssertLast(createStmtAndListenerJoin(viewExpr));
	}

	private SupportUpdateListener createStmtAndListenerJoin(String viewExpr) {
		epService.initialize();
		
		SupportUpdateListener updateListener = new SupportUpdateListener();
		EPStatement view = epService.getEPAdministrator().createEQL(viewExpr);
	    view.addListener(updateListener);
	    
	    epService.getEPRuntime().sendEvent(new SupportBeanString(JOIN_KEY));
	    
	    return updateListener;
	}

	private void runAssertLast(SupportUpdateListener updateListener) 
	{
		// send an event
	    sendEvent(1);
	
	    // check no update
	    assertFalse(updateListener.getAndClearIsInvoked());
	    
	    // send another event
	    sendEvent(2);
	    
	    // check update, only the last event present
	    assertTrue(updateListener.getAndClearIsInvoked());
	    assertEquals(1, updateListener.getLastNewData().length);
	    assertEquals(2L, updateListener.getLastNewData()[0].get("longBoxed"));
	    assertNull(updateListener.getLastOldData());
	}
    
}
