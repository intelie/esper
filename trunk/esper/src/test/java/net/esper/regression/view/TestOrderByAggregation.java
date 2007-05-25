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
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOrderByAggregation extends TestCase {

	private static final Log log = LogFactory.getLog(TestOrderByAggregation.class);
	private EPServiceProvider epService;
    private List<String> symbols;
    private List<Double> prices;
    private List<Double[]> priceChoicesUnordered; // For joins there needs to be a couple of choices as the ordering is not guaranteed 
    private List<Long> volumes;
	private SupportUpdateListener testListener;

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
		
		statementString = "select symbol, sum(price) as sumprice from " +
                        SupportMarketDataBean.class.getName() + ".win:length(10) " +
                        "output every 6 events "  +
                        "order by symbol, 2*sum(price)+1";
		createAndSendAggregate(statementString);
		orderValuesBySymbolAggregateAll();
		assertValues(symbols, "symbol");
		assertValues(prices, "sumprice");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sumprice"}));
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
    	orderValuesBySymbolAggregateAllJoin();
    	assertValues(symbols, "symbol");
        assertValuesDoubleChoiceList(priceChoicesUnordered, "sum(price)");
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
    	orderValuesBySymbolAggregateAllJoin();
    	assertValues(symbols, "symbol");
        assertValuesDoubleChoiceList(priceChoicesUnordered, "max(sum(price))");
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
    	orderValuesBySymbolAggregateAllJoin();
    	assertValues(symbols, "symbol");
        assertValuesDoubleChoiceList(priceChoicesUnordered, "sum(price)");
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
    	orderValuesBySymbolAggregateAllJoin();
    	assertValues(symbols, "symbol");
        assertValuesDoubleChoiceList(priceChoicesUnordered, "sum(price)");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();    	
    }
	
    public void testRowPerGroup()
	{
		String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price)";
		createAndSendAggregate(statementString);
		orderValuesBySumPriceGroup();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();
		
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalGroup();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		clearValues();
		
		epService.initialize();
		
		statementString = "select symbol, sum(price) from " +
                            SupportMarketDataBean.class.getName() + ".win:length(20) " +
                            "group by symbol " +
                            "having sum(price) > 0 " +
                            "output every 6 events " +
                            "order by sum(price)";
		createAndSendAggregate(statementString);
		orderValuesBySumPriceGroup();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();
		
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalGroup();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		clearValues();
	}

	public void testRowPerGroupJoin()
    {
    	String statementString = "select symbol, sum(price) from " +
                            SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                            SupportBeanString.class.getName() + ".win:length(100) as two " +
                            "where one.symbol = two.string " +
                            "group by symbol " +
                            "output every 6 events " +
                            "order by sum(price)";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();    	
    	orderValuesBySumPriceGroup();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();
    	
    	sendAdditionalAggregate();
    	orderValuesBySumPriceAdditionalGroup();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
    	clearValues();
    	
    	epService.initialize();
    	
    	statementString = "select symbol, sum(price) from " +
                        SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                        SupportBeanString.class.getName() + ".win:length(100) as two " +
                        "where one.symbol = two.string " +
                        "group by symbol " +
                        "having sum(price) > 0 " +
                        "output every 6 events " +
                        "order by sum(price)";
    	createAndSendAggregate(statementString);
    	sendJoinEvents(); 
    	orderValuesBySumPriceGroup();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();
    	
    	sendAdditionalAggregate();
    	orderValuesBySumPriceAdditionalGroup();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
    	clearValues();
    }
	
	public void testAliases() 
	{
		String statementString = "select symbol, volume, sum(price) as mySum from " +
                                    SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                    "group by symbol " +
                                    "output every 6 events " +
                                    "order by mySum";
		createAndSendAggregate(statementString);
		orderValuesBySumPriceEvent();
		assertValues(prices, "mySum");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "mySum", "volume"}));
		clearValues();
		
		statementString = "select symbol as mySymbol, sum(price) as mySum from " +
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

	public void testFullyGroupedLast()
	{
		String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output last every 6 events " +
                                "order by sum(price)";
		createAndSendAggregate(statementString);
		orderValuesBySumPriceGroup();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();
		
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalGroupLast();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();
		
		epService.initialize();

    	statementString = "select symbol, sum(price) from " +
                        SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                        SupportBeanString.class.getName() + ".win:length(100) as two " +
                        "where one.symbol = two.string " +
                        "group by symbol " +
                        "output last every 6 events " +
                        "order by sum(price)";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();    	
    	orderValuesBySumPriceGroup();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();
    	
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalGroupLast();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();
		
		epService.initialize();
    }

    public void testLastAggregateGrouped()
    {   
        String statementString = "select symbol, volume, sum(price) from " +
                        SupportMarketDataBean.class.getName() + ".win:length(20) " +
                        "group by symbol " +
                        "output last every 6 events " +
                        "order by sum(price)";
		createAndSendAggregate(statementString);
		orderValuesBySumPriceEvent();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
		clearValues();
		
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalEventGroup();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
		clearValues();
		
		epService.initialize();
		
    	statementString = "select symbol, volume, sum(price) from " +
                        SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                        SupportBeanString.class.getName() + ".win:length(100) as two " +
                        "where one.symbol = two.string " +
                        "group by symbol " +
                        "output last every 6 events " +
                        "order by sum(price)";
    	createAndSendAggregateScene2(statementString);
    	sendJoinEvents();
    	orderValuesBySumPriceEventJoin();
        assertValuesDoubleChoiceList(priceChoicesUnordered, "sum(price)");
    	assertValues(symbols, "symbol");
    	assertValues(volumes, "volume");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
    	clearValues();
    	
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalEventJoinScene2();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
		clearValues();
		
		epService.initialize();
	}

    public void testAggregateGrouped()
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
		
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalEvent();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
		clearValues();
		
		epService.initialize();
		
		statementString = "select symbol, volume, sum(price) from " +
                            SupportMarketDataBean.class.getName() + ".win:length(20) " +
                            "group by symbol " +
                            "having sum(price) > -1 " +
                            "output every 6 events " +
                            "order by sum(price)";
		createAndSendAggregate(statementString);
		orderValuesBySumPriceEvent();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
		clearValues();
		
		sendAdditionalAggregate();
		orderValuesBySumPriceAdditionalEvent();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
		clearValues();
	}

	public void testAggregateGroupedJoin()
    {
    	String statementString = "select symbol, volume, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                                SupportBeanString.class.getName() + ".win:length(100) as two " +
                                "where one.symbol = two.string " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price)";
    	createAndSendAggregateScene2(statementString);
    	sendJoinEvents();
    	orderValuesBySumPriceEventJoin();
        assertValues(symbols, "symbol");
    	assertValuesDoubleChoiceList(priceChoicesUnordered, "sum(price)");
    	assertValues(volumes, "volume");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
    	clearValues();
    	
    	sendAdditionalAggregate();
    	orderValuesBySumPriceAdditionalEventJoin();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
    	assertValues(volumes, "volume");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
    	clearValues();
    	
    	epService.initialize();
    	
    	statementString = "select symbol, volume, sum(price) from " +
                            SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                            SupportBeanString.class.getName() + ".win:length(100) as two " +
                            "where one.symbol = two.string " +
                            "group by symbol " +
                            "having sum(price) > -1 " +
                            "output every 6 events " +
                            "order by sum(price)";
    	createAndSendAggregateScene2(statementString);
    	sendJoinEvents();
        orderValuesBySumPriceEventJoin();
        assertValuesDoubleChoiceList(priceChoicesUnordered, "sum(price)");
    	assertValues(symbols, "symbol");
    	assertValues(volumes, "volume");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
    	clearValues();
    	
    	sendAdditionalAggregate();
    	orderValuesBySumPriceAdditionalEventJoin();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
    	assertValues(volumes, "volume");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
    	clearValues();
    }
    
    private void orderValuesBySumPriceAdditionalEvent() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "DOG");
    	symbols.add(2, "CMU");
    	symbols.add(3, "IBM");
    	symbols.add(4, "CAT");
    	symbols.add(5, "CMU");
    	symbols.add(6, "IBM");
    	prices.add(0, 0d);
    	prices.add(1, 1d);
    	prices.add(2, 8d);
    	prices.add(3, 10d);
    	prices.add(4, 11d);
    	prices.add(5, 13d);
    	prices.add(6, 14d);
    	volumes.add(0,0L);
    	volumes.add(1,0L);
    	volumes.add(2,0L);
    	volumes.add(3,0L);
    	volumes.add(4,0L);
    	volumes.add(5,0L);
    	volumes.add(6,0L);
    }
    
    private void orderValuesBySumPriceAdditionalEventJoin() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "DOG");
    	symbols.add(2, "CAT");
    	symbols.add(3, "IBM");
    	symbols.add(4, "IBM");
    	symbols.add(5, "CMU");
    	symbols.add(6, "CMU");
    	prices.add(0, 0d);
    	prices.add(1, 1d);
    	prices.add(2, 11d);
    	prices.add(3, 73d);
    	prices.add(4, 77d);
    	prices.add(5, 305d);
    	prices.add(6, 310d);
    	volumes.add(0,0L);
    	volumes.add(1,0L);
    	volumes.add(2,0L);
    	volumes.add(3,0L);
    	volumes.add(4,0L);
    	volumes.add(5,0L);
    	volumes.add(6,0L);
    }

    private void orderValuesBySumPriceAdditionalEventJoinScene2() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "DOG");
    	symbols.add(2, "IBM");
    	symbols.add(3, "IBM");
    	symbols.add(4, "CMU");
    	symbols.add(5, "CMU");
    	prices.add(0, 0d);
    	prices.add(1, 1d);
    	prices.add(2, 73d);
    	prices.add(3, 77d);
    	prices.add(4, 305d);
    	prices.add(5, 310d);
    	volumes.add(0,0L);
    	volumes.add(1,0L);
    	volumes.add(2,0L);
    	volumes.add(3,0L);
    	volumes.add(4,0L);
    	volumes.add(5,0L);
    }

    private void orderValuesBySumPriceAdditionalEventGroup() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "DOG");
    	symbols.add(2, "CMU");
    	symbols.add(3, "IBM");
    	symbols.add(4, "CMU");
    	symbols.add(5, "IBM");
    	prices.add(0, 0d);
    	prices.add(1, 1d);
    	prices.add(2, 8d);
    	prices.add(3, 10d);
    	prices.add(4, 13d);
    	prices.add(5, 14d);
    	volumes.add(0,0L);
    	volumes.add(1,0L);
    	volumes.add(2,0L);
    	volumes.add(3,0L);
    	volumes.add(4,0L);
    	volumes.add(5,0L);
    }

	private void orderValuesBySumPriceEvent()
    {
    	symbols.add(0, "CMU");
    	symbols.add(1, "IBM");
    	symbols.add(2, "CMU");
    	symbols.add(3, "CAT");
    	symbols.add(4, "IBM");
    	symbols.add(5, "CAT");
    	prices.add(0, 1d);
    	prices.add(1, 3d);
    	prices.add(2, 3d);
    	prices.add(3, 5d);
    	prices.add(4, 7d);
    	prices.add(5, 11d);
    	volumes.add(0, 0l);
    	volumes.add(1, 0l);
    	volumes.add(2, 0l);
    	volumes.add(3, 0l);
    	volumes.add(4, 0l);
    	volumes.add(5, 0l);
    }
    
    private void orderValuesBySumPriceEventJoin()
    {
        symbols.add(0, "CAT");
        symbols.add(1, "CAT");
        symbols.add(2, "IBM");
        symbols.add(3, "IBM");
        symbols.add(4, "CMU");
        symbols.add(5, "CMU");
        priceChoicesUnordered.add(0, new Double[] {5d, 6d});
        priceChoicesUnordered.add(1, new Double[] {11d});
        priceChoicesUnordered.add(2, new Double[] {30d, 40d});
        priceChoicesUnordered.add(3, new Double[] {70d});
        priceChoicesUnordered.add(4, new Double[] {100d, 200d});
        priceChoicesUnordered.add(5, new Double[] {300d});
        volumes.add(0, 0l);
        volumes.add(1, 0l);
        volumes.add(2, 0l);
        volumes.add(3, 0l);
        volumes.add(4, 0l);
        volumes.add(5, 0l);
    }

    private void orderValuesBySumPriceAdditionalGroup() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "CAT");
    	symbols.add(2, "CMU");
		symbols.add(3, "IBM");
		prices.add(0, 1d);
		prices.add(1, 11d);
		prices.add(2, 13d);
		prices.add(3, 14d);
	}
    
    private void orderValuesBySumPriceAdditionalGroupLast() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "CMU");
		symbols.add(2, "IBM");
		prices.add(0, 1d);
		prices.add(1, 13d);
		prices.add(2, 14d);
	}

	private void sendAdditionalAggregate() {
    	sendEvent("IBM", 3);
    	sendEvent("IBM", 4);
    	sendEvent("CMU", 5);
    	sendEvent("CMU", 5);
    	sendEvent("DOG", 0);
    	sendEvent("DOG", 1);		
	}

    private void orderValuesBySymbolAggregateAllJoin() {
        // Factors:
        // - join can produce unordered results
        // - order by top to bottom
        // - order in which events are applied matters because of sum
        symbols.add("CAT");
        symbols.add("CAT");
        symbols.add("CMU");
        symbols.add("CMU");
        symbols.add("IBM");
        symbols.add("IBM");
        priceChoicesUnordered.add(new Double[] {5d, 6d});
        priceChoicesUnordered.add(new Double[] {11d});
        priceChoicesUnordered.add(new Double[] {19d, 20d});
        priceChoicesUnordered.add(new Double[] {21d});
        priceChoicesUnordered.add(new Double[] {14d, 15d});
        priceChoicesUnordered.add(new Double[] {18d});
        volumes.add(0l);
        volumes.add(0l);
        volumes.add(0l);
        volumes.add(0l);
        volumes.add(0l);
        volumes.add(0l);
        volumes.add(0l);
    }

	private void orderValuesBySymbolAggregateAll() {
        symbols.add("CAT");
    	symbols.add("CAT");
    	symbols.add("CMU");
    	symbols.add("CMU");
    	symbols.add("IBM");
    	symbols.add("IBM");
    	prices.add(15d);
    	prices.add(21d);
    	prices.add(8d);
    	prices.add(10d);
    	prices.add(3d);
    	prices.add(7d);
    	volumes.add(0l);
    	volumes.add(0l);
    	volumes.add(0l);
    	volumes.add(0l);
    	volumes.add(0l);
    	volumes.add(0l);
    	volumes.add(0l);
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

    private void createAndSendAggregateScene2(String statementString) {
        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        statement.addListener(testListener);
        sendEvent("IBM", 30);
        sendEvent("IBM", 40);
        sendEvent("CMU", 100);
        sendEvent("CMU", 200);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);
    }

	public void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider();
	    epService.initialize();
	    symbols = new LinkedList<String>();
	    prices = new LinkedList<Double>();
        priceChoicesUnordered = new LinkedList<Double[]>();
	    volumes = new LinkedList<Long>();
	}

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
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
    		assertEquals("error at event " + i, values.get(i), events[i].get(valueName));
    	}
    }

    private void assertValuesDoubleChoiceList(List<Double[]> values, String valueName)
    {
    	EventBean[] events = testListener.getLastNewData();
    	assertEquals(values.size(), events.length);
    	log.debug(".assertValues values: " + values);
    	for(int i = 0; i < events.length; i++)
    	{
    		log.debug(".assertValues events["+i+"]=="+events[i].get(valueName));
            double value = (Double) events[i].get(valueName);
            boolean inArray = false;
            Double[] choices = values.get(i);
            for (int j = 0; j < choices.length; j++)
            {
                if (choices[j] == value)
                {
                    inArray = true;
                }
            }
            assertTrue("error at event " + i + " not in choice:" + Arrays.toString(choices) + " received value " + value, inArray);
    	}
    }

    private void sendJoinEvents()
    {
        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));
    }

    private void clearValues()
    {
    	prices.clear();
        priceChoicesUnordered.clear();
    	volumes.clear();
    	symbols.clear();
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
