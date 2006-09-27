package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
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

    private void sendEvent(String symbol, Long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }
}