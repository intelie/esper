package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;
import junit.framework.TestCase;

public class TestCountAll extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testSize()
    {
        String statementText = "select size from " + SupportMarketDataBean.class.getName() + ".std:size()";
        selectTestView = epService.getEPAdministrator().createEQL(statementText);
        selectTestView.addListener(listener);

        sendEvent("DELL", 1L);
        assertSize(1, 0);

        sendEvent("DELL", 1L);
        assertSize(2, 1);
    }
    
    public void testCount()
    {
    	String statementText = "select count(*) as cnt from " + SupportMarketDataBean.class.getName() + ".win:time(1)";
        selectTestView = epService.getEPAdministrator().createEQL(statementText);
        selectTestView.addListener(listener);
       
        sendEvent("DELL", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(1L, listener.getLastNewData()[0].get("cnt"));
        
        sendEvent("DELL", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(2L, listener.getLastNewData()[0].get("cnt"));
        
        sendEvent("DELL", 1L);
        assertTrue(listener.getAndClearIsInvoked());
        assertEquals(1, listener.getLastNewData().length);
        assertEquals(3L, listener.getLastNewData()[0].get("cnt"));
    }

    public void testCountHaving()
    {
        String event = SupportBean.class.getName();
        String statementText = "select sum(intPrimitive) as mysum from " + event + " having sum(intPrimitive) = 2";
        selectTestView = epService.getEPAdministrator().createEQL(statementText);
        selectTestView.addListener(listener);

        sendEvent();
        assertFalse(listener.getAndClearIsInvoked());
        sendEvent();
        assertEquals(2, listener.assertOneGetNewAndReset().get("mysum"));
        sendEvent();
        assertEquals(2, listener.assertOneGetOldAndReset().get("mysum"));
    }

    public void testSumHaving()
    {
        String event = SupportBean.class.getName();
        String statementText = "select count(*) as mysum from " + event + " having count(*) = 2";
        selectTestView = epService.getEPAdministrator().createEQL(statementText);
        selectTestView.addListener(listener);

        sendEvent();
        assertFalse(listener.getAndClearIsInvoked());
        sendEvent();
        assertEquals(2L, listener.assertOneGetNewAndReset().get("mysum"));
        sendEvent();
        assertEquals(2L, listener.assertOneGetOldAndReset().get("mysum"));
    }

    private void sendEvent(String symbol, Long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent()
    {
        SupportBean bean = new SupportBean("", 1);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void assertSize(long newSize, long oldSize)
    {
        listener.assertFieldEqualsAndReset("size", new Object[] {newSize}, new Object[] {oldSize});
    }
}
