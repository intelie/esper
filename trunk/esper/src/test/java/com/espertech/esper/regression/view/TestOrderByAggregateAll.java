package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestOrderByAggregateAll extends TestCase
{
	private static final Log log = LogFactory.getLog(TestOrderByAggregateAll.class);
	private EPServiceProvider epService;
    private List<Double> prices;
    private List<String> symbols;
    private SupportUpdateListener testListener;

    public void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
	    epService.initialize();
	    symbols = new LinkedList<String>();
	    prices = new LinkedList<Double>();
	}

    public void testIteratorAggregateRowPerEvent()
	{
        String[] fields = new String[] {"symbol", "sumPrice"};
        String statementString = "select symbol, sum(price) as sumPrice from " +
    	            SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	            SupportBeanString.class.getName() + ".win:length(100) as two " +
                    "where one.symbol = two.string " +
                    "order by symbol";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        sendJoinEvents();
        sendEvent("CAT", 50);
        sendEvent("IBM", 49);
        sendEvent("CAT", 15);
        sendEvent("IBM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", 214d},
                                {"CAT", 214d},
                                {"IBM", 214d},
                                {"IBM", 214d},
                                });

        sendEvent("KGB", 75);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", 289d},
                                {"CAT", 289d},
                                {"IBM", 289d},
                                {"IBM", 289d},
                                {"KGB", 289d},
                                });
    }

    public void testAliases()
    {
        String statementString = "select symbol as mySymbol, sum(price) as mySum from " +
        SupportMarketDataBean.class.getName() + ".win:length(10) " +
        "output every 6 events " +
        "order by mySymbol";
        createAndSendAggregate(statementString);
        orderValuesBySymbolAggregateAll();
        assertValues(symbols, "mySymbol");
        assertValues(prices, "mySum");
        clearValues();

        statementString = "select symbol, sum(price) as mySum from " +
        SupportMarketDataBean.class.getName() + ".win:length(20) " +
        "group by symbol " +
        "output every 6 events " +
        "order by mySum";
        createAndSendAggregate(statementString);
        orderValuesBySumPriceGroup();
        assertValues(prices, "mySum");
        assertValues(symbols, "symbol");
           assertOnlyProperties(Arrays.asList(new String[] {"symbol", "mySum"}));
        clearValues();
    }

    public void testAggregateAllJoinOrderFunction()
    {
    	String statementString = "select symbol, sum(price) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
     	"output every 6 events "  +
     	"order by volume*sum(price), symbol";
     	createAndSend(statementString);
     	sendJoinEvents();
     	orderValuesBySymbol();
     	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
     	clearValues();
    }

    public void testAggregateAllOrderFunction()
    {
        String statementString = "select symbol, sum(price) from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by volume*sum(price), symbol";
	 	createAndSend(statementString);
	 	orderValuesBySymbol();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
	 	clearValues();
	}

    public void testAggregateAll()
	{
		String statementString = "select symbol, sum(price) from " +
		SupportMarketDataBean.class.getName() + ".win:length(10) " +
		"output every 6 events " +
		"order by symbol";
		createAndSendAggregate(statementString);
		orderValuesBySymbolAggregateAll();
		assertValues(symbols, "symbol");
		assertValues(prices, "sum(price)");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();

		epService.initialize();

		statementString = "select symbol, max(sum(price)) from " +
                            SupportMarketDataBean.class.getName() + ".win:length(10) " +
                            "output every 6 events " +
                            "order by symbol";
		createAndSendAggregate(statementString);
		orderValuesBySymbolAggregateAll();
		assertValues(symbols, "symbol");
		assertValues(prices, "max(sum(price))");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "max(sum(price))"}));
		clearValues();

		epService.initialize();

		statementString = "select symbol, sum(price) from " +
		SupportMarketDataBean.class.getName() + ".win:length(10) " +
		"having sum(price) > 0 " +
		"output every 6 events " +
		"order by symbol";
		createAndSendAggregate(statementString);
		orderValuesBySymbolAggregateAll();
		assertValues(symbols, "symbol");
		assertValues(prices, "sum(price)");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();

		epService.initialize();

		statementString = "select symbol, sum(price) from " +
		SupportMarketDataBean.class.getName() + ".win:length(10) " +
		"output every 6 events "  +
		"order by symbol, sum(price)";
		createAndSendAggregate(statementString);
		orderValuesBySymbolAggregateAll();
		assertValues(symbols, "symbol");
		assertValues(prices, "sum(price)");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();

		epService.initialize();

		statementString = "select symbol, 21+sum(price)*0 from " +
		SupportMarketDataBean.class.getName() + ".win:length(10) " +
		"output every 6 events "  +
		"order by symbol, 2*sum(price)+1";
		createAndSendAggregate(statementString);
		orderValuesBySymbolAggregateAll();
		assertValues(symbols, "symbol");
		assertValues(prices, "(21+(sum(price)*0))");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(21+(sum(price)*0))"}));
		clearValues();
	}

	public void testAggregateAllJoin()
    {
    	String statementString = "select symbol, sum(price) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events " +
    	"order by symbol";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolAggregateAll();
    	assertValues(symbols, "symbol");
    	assertValues(prices, "sum(price)");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();

    	epService.initialize();

    	statementString = "select symbol, max(sum(price)) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events " +
    	"order by symbol";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolAggregateAll();
    	assertValues(symbols, "symbol");
    	assertValues(prices, "max(sum(price))");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "max(sum(price))"}));
    	clearValues();

    	epService.initialize();

    	statementString = "select symbol, sum(price) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"having sum(price) > 0 " +
    	"output every 6 events " +
    	"order by symbol";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolAggregateAll();
    	assertValues(symbols, "symbol");
    	assertValues(prices, "sum(price)");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();

    	epService.initialize();

    	statementString = "select symbol, sum(price) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by symbol, sum(price)";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolAggregateAll();
    	assertValues(symbols, "symbol");
    	assertValues(prices, "sum(price)");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();

    	epService.initialize();

    	statementString = "select symbol, 21+sum(price)*0 from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by symbol, 2*sum(price)+1";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolAggregateAll();
    	assertValues(symbols, "symbol");
    	assertValues(prices, "(21+(sum(price)*0))");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(21+(sum(price)*0))"}));
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
    }

	private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendJoinEvents()
    {
        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));
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

    private void orderValuesBySymbolAggregateAll() {
        symbols.add("CAT");
        symbols.add("CAT");
        symbols.add("CMU");
        symbols.add("CMU");
        symbols.add("IBM");
        symbols.add("IBM");
        prices.add(21d);
        prices.add(21d);
        prices.add(21d);
        prices.add(21d);
        prices.add(21d);
        prices.add(21d);
    }

    private void orderValuesBySumPriceGroup()
    {
        symbols.add(0, "CMU");
        symbols.add(1, "IBM");
        symbols.add(2, "CAT");
        prices.add(0, 3d);
        prices.add(1, 7d);
        prices.add(2, 11d);
    }    
}
