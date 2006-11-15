package net.esper.regression.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBeanString;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportUpdateListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOrderBySimple extends TestCase {

	private static final Log log = LogFactory.getLog(TestOrderBySimple.class);
	private EPServiceProvider epService;
    private List<Double> prices;
    private List<String> symbols;
    private SupportUpdateListener testListener;
	private List<Long> volumes;

    public void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider();
	    epService.initialize();
	    symbols = new LinkedList<String>();
	    prices = new LinkedList<Double>();
	    volumes = new LinkedList<Long>();
	}

    public void testAcrossJoin()
	{
    	String statementString = "select symbol, string from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesByPriceJoin();
    	assertValues(symbols, "symbol");
    	assertValues(symbols, "string");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "string"}));
    	clearValues();

    	statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by string, price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolPrice();
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
    	clearValues();
	}

    public void testDescending()
	{
		String statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price desc";
		createAndSend(statementString);
		orderValuesByPriceDesc();
		assertValues(symbols, "symbol");
		clearValues();

		statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price desc, symbol asc";
		createAndSend(statementString);
		orderValuesByPrice();
		Collections.reverse(symbols);
		assertValues(symbols, "symbol");
		clearValues();

		statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price asc";
		createAndSend(statementString);
		orderValuesByPrice();
		assertValues(symbols, "symbol");
		clearValues();

		statementString = "select symbol, volume from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by symbol desc";
		createAndSend(statementString);
		orderValuesBySymbol();
		Collections.reverse(symbols);
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
		clearValues();

		statementString = "select symbol, price from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by symbol desc, price desc";
		createAndSend(statementString);
		orderValuesBySymbolPrice();
		Collections.reverse(symbols);
		Collections.reverse(prices);
		assertValues(symbols, "symbol");
		assertValues(prices, "price");
		clearValues();

		statementString = "select symbol, price from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by symbol, price";
		createAndSend(statementString);
		orderValuesBySymbolPrice();
		assertValues(symbols, "symbol");
		assertValues(prices, "price");
		clearValues();
	}

    public void testExpressions()
	{
		String statementString = "select symbol from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by (price * 6) + 5";
	 	createAndSend(statementString);
	 	orderValuesByPrice();
	 	assertValues(symbols, "symbol");
		assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
	 	clearValues();

	 	epService.initialize();

		statementString = "select symbol, price from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by (price * 6) + 5, price";
	 	createAndSend(statementString);
	 	orderValuesByPrice();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "price"}));
	 	clearValues();

	 	epService.initialize();

		statementString = "select symbol, 1+volume*23 from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by (price * 6) + 5, price, volume";
	 	createAndSend(statementString);
	 	orderValuesByPrice();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(1+(volume*23))"}));
	 	clearValues();

	 	epService.initialize();

		statementString = "select symbol from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by volume*price, symbol";
	 	createAndSend(statementString);
	 	orderValuesBySymbol();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
	 	clearValues();

	 	epService.initialize();

		statementString = "select symbol, sum(price) from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by volume*sum(price), symbol";
	 	createAndSend(statementString);
	 	orderValuesBySymbol();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
	 	clearValues();
	}

    public void testExpressionsJoin()
    {
    	String statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
     	"output every 6 events "  +
     	"order by (price * 6) + 5";
     	createAndSend(statementString);
     	sendJoinEvents();
     	orderValuesByPriceJoin();
     	assertValues(symbols, "symbol");
    	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
     	clearValues();

     	epService.initialize();

    	statementString = "select symbol, price from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
     	"output every 6 events "  +
     	"order by (price * 6) + 5, price";
     	createAndSend(statementString);
     	sendJoinEvents();
     	orderValuesByPriceJoin();
     	assertValues(prices, "price");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "price"}));
     	clearValues();

     	epService.initialize();

    	statementString = "select symbol, 1+volume*23 from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
     	"output every 6 events "  +
     	"order by (price * 6) + 5, price, volume";
     	createAndSend(statementString);
     	sendJoinEvents();
     	orderValuesByPriceJoin();
     	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(1+(volume*23))"}));
     	clearValues();

     	epService.initialize();

    	statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
     	"output every 6 events "  +
     	"order by volume*price, symbol";
     	createAndSend(statementString);
     	sendJoinEvents();
     	orderValuesBySymbol();
     	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
     	clearValues();

     	epService.initialize();

    	statementString = "select symbol, sum(price) from " +
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

    public void testInvalid()
	{
		String statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by sum(price)";
		try
		{
			createAndSend(statementString);
			fail();
		}
		catch(EPStatementException ex)
		{
			// expected
		}

		statementString = "select sum(price) from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by sum(price + 6)";
		try
		{
			createAndSend(statementString);
			fail();
		}
		catch(EPStatementException ex)
		{
			// expected
		}

		statementString = "select sum(price + 6) from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by sum(price)";
		try
		{
			createAndSend(statementString);
			fail();
		}
		catch(EPStatementException ex)
		{
			// expected
		}
	}

    public void testInvalidJoin()
    {
    	String statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by sum(price)";
    	try
    	{
    		createAndSend(statementString);
    		fail();
    	}
    	catch(EPStatementException ex)
    	{
    		// expected
    	}

    	statementString = "select sum(price) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by sum(price + 6)";
    	try
    	{
    		createAndSend(statementString);
    		fail();
    	}
    	catch(EPStatementException ex)
    	{
    		// expected
    	}

    	statementString = "select sum(price + 6) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by sum(price)";
    	try
    	{
    		createAndSend(statementString);
    		fail();
    	}
    	catch(EPStatementException ex)
    	{
    		// expected
    	}
    }

    public void testMultipleKeys()
	{
		String statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(10) " +
		"output every 6 events "  +
		"order by symbol, price";
		createAndSend(statementString);
		orderValuesBySymbolPrice();
		assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
		clearValues();

		statementString = "select symbol from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by price, symbol, volume";
	 	createAndSend(statementString);
	 	orderValuesByPriceSymbol();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
	 	clearValues();

		statementString = "select symbol, volume*2 from " +
	 	SupportMarketDataBean.class.getName() + ".win:length(10) " +
	 	"output every 6 events "  +
	 	"order by price, volume";
	 	createAndSend(statementString);
	 	orderValuesByPrice();
	 	assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(volume*2)"}));
	 	clearValues();
	}

	public void testAliases()
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

		statementString = "select symbol as mySymbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"order by price, mySymbol";
		createAndSend(statementString);
		symbols.add("CAT");
		assertValues(symbols, "mySymbol");
		clearValues();
		sendEvent("FOX", 10);
		symbols.add("FOX");
		assertValues(symbols, "mySymbol");
		clearValues();
	}

    public void testMultipleKeysJoin()
    {
    	String statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by symbol, price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolPrice();
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
    	clearValues();

    	statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
     	"output every 6 events "  +
     	"order by price, symbol, volume";
     	createAndSend(statementString);
    	sendJoinEvents();
     	orderValuesByPriceSymbol();
     	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
     	clearValues();

    	statementString = "select symbol, volume*2 from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
     	"output every 6 events "  +
     	"order by price, volume";
     	createAndSend(statementString);
    	sendJoinEvents();
     	orderValuesByPriceJoin();
     	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(volume*2)"}));
     	clearValues();
    }

    public void testSimple()
	{
		String statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price";
		createAndSend(statementString);
		orderValuesByPrice();
		assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
		clearValues();

		statementString = "select symbol, price from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price";
		createAndSend(statementString);
		orderValuesByPrice();
		assertValues(symbols, "symbol");
		assertValues(prices, "price");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "price"}));
		clearValues();

		statementString = "select symbol, volume from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price";
		createAndSend(statementString);
		orderValuesByPrice();
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "volume"}));
		clearValues();

		statementString = "select symbol, volume*2 from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price";
		createAndSend(statementString);
		orderValuesByPrice();
		assertValues(symbols, "symbol");
		assertValues(volumes, "(volume*2)");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(volume*2)"}));
		clearValues();

		statementString = "select symbol, volume from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by symbol";
		createAndSend(statementString);
		orderValuesBySymbol();
		assertValues(symbols, "symbol");
		assertValues(volumes, "volume");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "volume"}));
		clearValues();

		statementString = "select price from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by symbol";
		createAndSend(statementString);
		orderValuesBySymbol();
		assertValues(prices, "price");
	   	assertOnlyProperties(Arrays.asList(new String[] {"price"}));
		clearValues();
	}

    public void testSimpleJoin()
    {
    	String statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesByPriceJoin();
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
    	clearValues();

    	statementString = "select symbol, price from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesByPriceJoin();
    	assertValues(symbols, "symbol");
    	assertValues(prices, "price");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "price"}));
    	clearValues();

    	statementString = "select symbol, volume from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesByPriceJoin();
    	assertValues(symbols, "symbol");
    	assertValues(volumes, "volume");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "volume"}));
    	clearValues();

    	statementString = "select symbol, volume*2 from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesByPriceJoin();
    	assertValues(symbols, "symbol");
    	assertValues(volumes, "(volume*2)");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "(volume*2)"}));
    	clearValues();

    	statementString = "select symbol, volume from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by symbol";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesBySymbol();
    	assertValues(symbols, "symbol");
    	assertValues(volumes, "volume");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "volume"}));
    	clearValues();

    	statementString = "select price from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by symbol, price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolJoin();
    	assertValues(prices, "price");
       	assertOnlyProperties(Arrays.asList(new String[] {"price"}));
    	clearValues();
    }

    public void testWildcard()
	{
		String statementString = "select * from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by price";
		createAndSend(statementString);
		orderValuesByPrice();
		assertValues(symbols, "symbol");
		assertValues(prices, "price");
		assertValues(volumes, "volume");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "volume", "price", "feed"}));
		clearValues();

		statementString = "select * from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"output every 6 events "  +
		"order by symbol";
		createAndSend(statementString);
		orderValuesBySymbol();
		assertValues(symbols, "symbol");
		assertValues(prices, "price");
		assertValues(volumes, "volume");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "volume", "price", "feed"}));
		clearValues();
	}


    public void testWildcardJoin()
    {
    	String statementString = "select * from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events " +
    	"order by price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesByPriceJoin();
    	assertSymbolsJoinWildCard();
    	clearValues();

    	epService.initialize();

    	statementString = "select * from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"output every 6 events "  +
    	"order by symbol, price";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesBySymbolJoin();
    	assertSymbolsJoinWildCard();
    	clearValues();
    }

    public void testNoOutputClauseView()
    {
		String statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:length(5) " +
		"order by price";
		createAndSend(statementString);
		symbols.add("CAT");
		assertValues(symbols, "symbol");
		clearValues();
		sendEvent("FOX", 10);
		symbols.add("FOX");
		assertValues(symbols, "symbol");
		clearValues();

		epService.initialize();

		// Set manual clocking
		epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

		// Set start time
		sendTimeEvent(0);

		statementString = "select symbol from " +
		SupportMarketDataBean.class.getName() + ".win:time_batch(1 sec) " +
		"order by price";
		createAndSend(statementString);
		orderValuesByPrice();
		sendTimeEvent(1000);
		assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
		clearValues();
    }

    public void testNoOutputClauseJoin()
    {
    	String statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"order by price";
    	createAndSend(statementString);
    	sendJoinEvents();
		symbols.add("KGB");
		assertValues(symbols, "symbol");
		clearValues();
		sendEvent("DOG", 10);
		symbols.add("DOG");
		assertValues(symbols, "symbol");
		clearValues();

		epService.initialize();

		// Set manual clocking
		epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

		// Set start time
		sendTimeEvent(0);

    	statementString = "select symbol from " +
    	SupportMarketDataBean.class.getName() + ".win:time_batch(1) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"order by price, symbol";
    	createAndSend(statementString);
    	sendJoinEvents();
    	orderValuesByPriceJoin();
		sendTimeEvent(1000);
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol"}));
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

	private void assertSymbolsJoinWildCard()
    {
    	EventBean[] events = testListener.getLastNewData();
    	log.debug(".assertValues event type = " + events[0].getEventType());
    	log.debug(".assertValues values: " + symbols);
    	log.debug(".assertValues events.length==" + events.length);
    	for(int i = 0; i < events.length; i++)
    	{
    		SupportMarketDataBean event = (SupportMarketDataBean)events[i].get("one");
    		assertEquals(symbols.get(i), event.getSymbol());
    	}
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

    private void orderValuesByPriceDesc()
    {
    	symbols.add(0, "IBM");
    	symbols.add(1, "CAT");
    	symbols.add(2, "CAT");
    	symbols.add(3, "CMU");
    	symbols.add(4, "IBM");
    	symbols.add(5, "KGB");
    	prices.add(0, 6d);
    	prices.add(1, 6d);
    	prices.add(2, 5d);
    	prices.add(3, 3d);
    	prices.add(4, 2d);
    	prices.add(5, 1d);
    	volumes.add(0, 0l);
    	volumes.add(1, 0l);
    	volumes.add(2, 0l);
    	volumes.add(3, 0l);
    	volumes.add(4, 0l);
    	volumes.add(5, 0l);
    }

	private void orderValuesByPriceJoin()
    {
    	symbols.add(0, "KGB");
    	symbols.add(1, "IBM");
    	symbols.add(2, "CMU");
    	symbols.add(3, "CAT");
    	symbols.add(4, "CAT");
    	symbols.add(5, "IBM");
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

    private void orderValuesByPriceSymbol()
    {
    	symbols.add(0, "KGB");
    	symbols.add(1, "IBM");
    	symbols.add(2, "CMU");
    	symbols.add(3, "CAT");
    	symbols.add(4, "CAT");
    	symbols.add(5, "IBM");
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

    private void orderValuesBySymbolJoin()
    {
    	symbols.add(0, "CAT");
    	symbols.add(1, "CAT");
    	symbols.add(2, "CMU");
    	symbols.add(3, "IBM");
    	symbols.add(4, "IBM");
    	symbols.add(5, "KGB");
    	prices.add(0, 5d);
    	prices.add(1, 6d);
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

	private void orderValuesBySymbolPrice()
    {
    	symbols.add(0, "CAT");
    	symbols.add(1, "CAT");
    	symbols.add(2, "CMU");
    	symbols.add(3, "IBM");
    	symbols.add(4, "IBM");
    	symbols.add(5, "KGB");
    	prices.add(0, 5d);
    	prices.add(1, 6d);
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

	private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

	private void sendTimeEvent(int millis)
	{
        CurrentTimeEvent event = new CurrentTimeEvent(millis);
        epService.getEPRuntime().sendEvent(event);
	}

	private void sendJoinEvents()
	{
		epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));
	}

}
