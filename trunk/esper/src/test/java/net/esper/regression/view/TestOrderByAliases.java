package net.esper.regression.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOrderByAliases extends TestCase {

	private static final Log log = LogFactory.getLog(TestOrderByAliases.class);
	private EPServiceProvider epService;
    private List<Double> prices;
    private List<String> symbols;
    private SupportUpdateListener testListener;
	private List<Long> volumes;

    public void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
	    epService.initialize();
	    symbols = new LinkedList<String>();
	    prices = new LinkedList<Double>();
	    volumes = new LinkedList<Long>();
	}
    
	public void testAliasesSimple()
	{
		String statementString = "select symbol as mySymbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  + 
		"order by mySymbol";    	
		createAndSend(statementString);
		orderValuesBySymbol();
		assertValues(symbols, "mySymbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"mySymbol"}));
		clearValues();
		
		statementString = "select symbol as mySymbol, price as myPrice from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  + 
		"order by myPrice";   
		createAndSend(statementString);
		orderValuesByPrice();
		assertValues(symbols, "mySymbol");
		assertValues(prices, "myPrice");
	   	assertOnlyProperties(Arrays.asList(new String[] {"mySymbol", "myPrice"}));
		clearValues();	
		
		statementString = "select symbol, price as myPrice from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  + 
	 	"order by (myPrice * 6) + 5, price";    	
	 	createAndSend(statementString);
	 	orderValuesByPrice();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "myPrice"}));
	 	clearValues();
	 	
		statementString = "select symbol, 1+volume*23 as myVol from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  + 
	 	"order by (price * 6) + 5, price, myVol";    	
	 	createAndSend(statementString);
	 	orderValuesByPrice();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "myVol"}));
	 	clearValues();
	}
	
	public void testAliasesAggregation()
	{
		String statementString = "select symbol, volume, sum(price) from " +
		SupportMarketDataBean.class.getName() + ".win:length(20) " +
		"group by symbol " + 
		"output every 6 events " + 
		"order by sum(price)"; 
		createAndSendAggregate(statementString);
		orderValuesBySumPriceEvent();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
		clearValues();
	}
    
    private void assertOnlyProperties(List<String> requiredProperties)
    {
    	EventBean[] events = testListener.getLastNewData();
    	if(events == null || events.length == 0)
    	{
    		return;
    	}
    	EventType type = events[0].getEventType();
    	List<String> actualProperties = new ArrayList<String>(Arrays.asList(type.getPropertyNames()));
    	log.debug(".assertOnlyProperties actualProperties=="+actualProperties);
    	assertTrue(actualProperties.containsAll(requiredProperties));
    	actualProperties.removeAll(requiredProperties);
    	assertTrue(actualProperties.isEmpty());
    }

	private void assertValues(List values, String valueName)
    {
    	EventBean[] events = testListener.getLastNewData();
    	assertEquals(values.size(), events.length);
    	log.debug(".assertValues values: " + values);
    	for(int i = 0; i < events.length; i++)
    	{
    		log.debug(".assertValues events["+i+"]=="+events[i].get(valueName));
    		assertEquals(values.get(i), events[i].get(valueName));
    	}
    }

	private void clearValues()
    {
    	prices.clear();
    	volumes.clear();
    	symbols.clear();
    }
	
	private void createAndSend(String statementString) {
		testListener = new SupportUpdateListener();
		EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
    	statement.addListener(testListener);
    	sendEvent("IBM", 2);
    	sendEvent("KGB", 1);
    	sendEvent("CMU", 3);
    	sendEvent("IBM", 6);
    	sendEvent("CAT", 6);
    	sendEvent("CAT", 5);
	}
    

	private void orderValuesByPrice()
    {
    	symbols.add(0, "KGB");
    	symbols.add(1, "IBM");
    	symbols.add(2, "CMU");
    	symbols.add(3, "CAT");
    	symbols.add(4, "IBM");
    	symbols.add(5, "CAT");
    	prices.add(0, 1d);
    	prices.add(1, 2d);
    	prices.add(2, 3d);
    	prices.add(3, 5d);
    	prices.add(4, 6d);
    	prices.add(5, 6d);
    	volumes.add(0, 0l);
    	volumes.add(1, 0l);
    	volumes.add(2, 0l);
    	volumes.add(3, 0l);
    	volumes.add(4, 0l);
    	volumes.add(5, 0l);
    }
    
    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}
	
    private void orderValuesBySumPriceEvent()
	{
		symbols.add(0, "CMU");
		symbols.add(1, "CMU");
		symbols.add(2, "IBM");
		symbols.add(3, "IBM");
		symbols.add(4, "CAT");
		symbols.add(5, "CAT");
		prices.add(0, 3d);
		prices.add(1, 3d);
		prices.add(2, 7d);
		prices.add(3, 7d);
		prices.add(4, 11d);
		prices.add(5, 11d);
		volumes.add(0, 0l);
		volumes.add(1, 0l);
		volumes.add(2, 0l);
		volumes.add(3, 0l);
		volumes.add(4, 0l);
		volumes.add(5, 0l);
	}

	private void orderValuesBySymbol()
	{
		symbols.add(0, "CAT");
		symbols.add(1, "CAT");
		symbols.add(2, "CMU");
		symbols.add(3, "IBM");
		symbols.add(4, "IBM");
		symbols.add(5, "KGB");
		prices.add(0, 6d);
		prices.add(1, 5d);
		prices.add(2, 3d);
		prices.add(3, 2d);
		prices.add(4, 6d);
		prices.add(5, 1d);
		volumes.add(0, 0l);
		volumes.add(1, 0l);
		volumes.add(2, 0l);
		volumes.add(3, 0l);
		volumes.add(4, 0l);
		volumes.add(5, 0l);
	}

    private void createAndSendAggregate(String statementString) {
        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        statement.addListener(testListener);
        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);
    }
}
