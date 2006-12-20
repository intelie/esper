package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBeanString;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestOutputLimitEventPerRow extends TestCase
{
    private static String SYMBOL_DELL = "DELL";
    private static String SYMBOL_IBM = "IBM";

    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testNoJoinLast()
	{
	    // Every event generates a new row, this time we sum the price by symbol and output volume
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
	                      "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(testListener);

	    runAssertionLast();
	}

    private void assertEvent(String symbol, Double mySum, Long volume)
	{
	    EventBean[] newData = testListener.getLastNewData();

	    assertEquals(1, newData.length);

	    assertEquals(symbol, newData[0].get("symbol"));
	    assertEquals(mySum, newData[0].get("mySum"));
	    assertEquals(volume, newData[0].get("volume"));

	    testListener.reset();
	    assertFalse(testListener.isInvoked());
	}

	private void runAssertionSingle()
	{
	    // assert select result type
	    assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
	    assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));
	    assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));

	    sendEvent(SYMBOL_DELL, 10, 100);
	    assertTrue(testListener.isInvoked());
	    assertEvent(SYMBOL_DELL, 100d, 10L);

	    sendEvent(SYMBOL_IBM, 15, 50);
	    assertEvent(SYMBOL_IBM, 50d, 15L);
	}

	public void testNoOutputClauseView()
	{
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
	                      "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
	                      "group by symbol ";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(testListener);

	    runAssertionSingle();
	}

	public void testNoJoinAll()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String viewExpr = "select symbol, volume, sum(price) as mySum " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "where symbol='DELL' or symbol='IBM' or symbol='GE' " +
                          "group by symbol " +
                          "output all every 2 events";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        runAssertionAll();
    }

    public void testJoinAll()
	{
	    // Every event generates a new row, this time we sum the price by symbol and output volume
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
	                                SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
	                      "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
	                      "  and one.string = two.symbol " +
	                      "group by symbol " +
	                      "output all every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(testListener);

	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

	    runAssertionAll();
	}

	public void testJoinLast()
	{
	    // Every event generates a new row, this time we sum the price by symbol and output volume
	    String viewExpr = "select symbol, volume, sum(price) as mySum " +
	                      "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
	                                SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
	                      "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
	                      "  and one.string = two.symbol " +
	                      "group by symbol " +
	                      "output last every 2 events";

	    selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
	    selectTestView.addListener(testListener);

	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
	    epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

	    runAssertionLast();
	}

	public void testNoOutputClauseJoin()
    {
        // Every event generates a new row, this time we sum the price by symbol and output volume
        String viewExpr = "select symbol, volume, sum(price) as mySum " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "where (symbol='DELL' or symbol='IBM' or symbol='GE') " +
                          "  and one.string = two.symbol " +
                          "group by symbol " +
                          "output last every 2 events";

        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));
        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_IBM));

        runAssertionLast();
    }

    private void runAssertionAll()
    {
    	// assert select result type
    	assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
    	assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));
    	assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));

    	sendEvent(SYMBOL_IBM, 10000, 20);
    	assertFalse(testListener.getAndClearIsInvoked());

    	sendEvent(SYMBOL_DELL, 10000, 51);
    	assertTwoEvents(SYMBOL_IBM, 10000, 20,
    			SYMBOL_DELL, 10000, 51);

    	sendEvent(SYMBOL_DELL, 20000, 52);
    	assertFalse(testListener.getAndClearIsInvoked());

    	sendEvent(SYMBOL_DELL, 40000, 45);
    	assertThreeEvents(SYMBOL_IBM, 10000, 20,
    					  SYMBOL_DELL, 20000, 51+52+45,
    					  SYMBOL_DELL, 40000, 51+52+45);
    }

	private void runAssertionLast()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(Long.class, selectTestView.getEventType().getPropertyType("volume"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("mySum"));

        sendEvent(SYMBOL_DELL, 10000, 51);
        assertFalse(testListener.getAndClearIsInvoked());

        sendEvent(SYMBOL_DELL, 20000, 52);
        assertTwoEvents(SYMBOL_DELL, 10000, 103,
        				SYMBOL_DELL, 20000, 103);

        sendEvent(SYMBOL_DELL, 30000, 70);
        assertFalse(testListener.getAndClearIsInvoked());

        sendEvent(SYMBOL_IBM, 10000, 20);
        assertTwoEvents(SYMBOL_DELL, 30000, 173,
        				SYMBOL_IBM, 10000, 20);
  }

    private void assertTwoEvents(String symbol1, long volume1, double sum1,
    							 String symbol2, long volume2, double sum2)
    {
        EventBean[] oldData = testListener.getLastOldData();
        EventBean[] newData = testListener.getLastNewData();

        assertNull(oldData);
        assertEquals(2, newData.length);

        if(matchesEvent(newData[0], symbol1, volume1, sum1))
        {
        	assertTrue(matchesEvent(newData[1], symbol2, volume2, sum2));
        }
        else
        {
        	assertTrue(matchesEvent(newData[0], symbol2, volume2, sum2));
        	assertTrue(matchesEvent(newData[1], symbol1, volume1, sum1));
        }

        testListener.reset();
        assertFalse(testListener.isInvoked());
    }

    private void assertThreeEvents(String symbol1, long volume1, double sum1,
    		String symbol2, long volume2, double sum2,
    		String symbol3, long volume3, double sum3)
    {
    	EventBean[] oldData = testListener.getLastOldData();
    	EventBean[] newData = testListener.getLastNewData();

    	assertNull(oldData);
    	assertEquals(3, newData.length);

    	if(matchesEvent(newData[0], symbol1, volume1, sum1))
    	{
    		if(matchesEvent(newData[1], symbol2, volume2, sum2))
    		{
    			assertTrue(matchesEvent(newData[2], symbol3, volume3, sum3));
    		}
    		else
    		{
    			assertTrue(matchesEvent(newData[1], symbol3, volume3, sum3));
    			assertTrue(matchesEvent(newData[2], symbol2, volume2, sum2));
    		}
    	}
    	else if(matchesEvent(newData[0], symbol2, volume2, sum2))
    	{
    		if(matchesEvent(newData[1], symbol1, volume1, sum1))
    		{
    			assertTrue(matchesEvent(newData[2], symbol3, volume3, sum3));
    		}
    		else
    		{
    			assertTrue(matchesEvent(newData[1], symbol3, volume3, sum3));
    			assertTrue(matchesEvent(newData[2], symbol1, volume1, sum1));
    		}
    	}
    	else
    	{
    		if(matchesEvent(newData[1], symbol1, volume1, sum1))
    		{
    			assertTrue(matchesEvent(newData[2], symbol2, volume2, sum2));
    		}
    		else
    		{
    			assertTrue(matchesEvent(newData[1], symbol2, volume2, sum2));
    			assertTrue(matchesEvent(newData[2], symbol1, volume1, sum1));
    		}
    	}


    		testListener.reset();
    		assertFalse(testListener.isInvoked());
    	}

    private boolean matchesEvent(EventBean event, String symbol, long volume, double sum)
    {
    	return
        symbol.equals(event.get("symbol")) &&
        new Long(volume).equals(event.get("volume")) &&
        new Double(sum).equals(event.get("mySum"));
    }



    private void sendEvent(String symbol, long volume, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestOutputLimitEventPerRow.class);
}
