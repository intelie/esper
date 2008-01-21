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
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.client.SupportConfigFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOrderByAggregation extends TestCase {

	private static final Log log = LogFactory.getLog(TestOrderByAggregation.class);
	private EPServiceProvider epService;
    private List<String> symbols;
    private List<Double> prices;
    private List<Long> volumes;
	private SupportUpdateListener testListener;

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

	public void testGroupBySwitch()
	{
		// Instead of the row-per-group behavior, these should
		// get row-per-event behavior since there are properties
		// in the order-by that are not in the select expression.
		String statementString = "select symbol, sum(price) from " +
		SupportMarketDataBean.class.getName() + ".win:length(20) " +
		"group by symbol " +
		"output every 6 events " +
		"order by sum(price), volume";
		createAndSendAggregate(statementString);
		orderValuesBySumPriceEvent();
		assertValues(prices, "sum(price)");
		assertValues(symbols, "symbol");
	   	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
		clearValues();

    	statementString = "select symbol, sum(price) from " +
    	SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"group by symbol " +
    	"output every 6 events " +
    	"order by sum(price), volume";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
    	orderValuesBySumPriceEvent();
    	assertValues(prices, "sum(price)");
    	assertValues(symbols, "symbol");
       	assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)"}));
    	clearValues();
	}

	public void testLast()
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

    	statementString = "select symbol, volume, sum(price) from " +
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
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
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
		"having sum(price) > 0 " +
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
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
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
    	SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
    	SupportBeanString.class.getName() + ".win:length(100) as two " +
    	"where one.symbol = two.string " +
    	"group by symbol " +
    	"having sum(price) > 0 " +
    	"output every 6 events " +
    	"order by sum(price)";
    	createAndSendAggregate(statementString);
    	sendJoinEvents();
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

    public void testIteratorAggregateRowForAll()
	{
        String[] fields = new String[] {"sumPrice"};
        String statementString = "select sum(price) as sumPrice from " +
    	            SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	            SupportBeanString.class.getName() + ".win:length(100) as two " +
                    "where one.symbol = two.string " +
                    "order by price";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        sendJoinEvents();
        sendEvent("CAT", 50);
        sendEvent("IBM", 49);
        sendEvent("CAT", 15);
        sendEvent("IBM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{214d}});

        sendEvent("KGB", 75);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{289d}});
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

    public void testIteratorGroupByEventPerGroup()
	{
        String[] fields = new String[] {"symbol", "sumPrice"};
        String statementString = "select symbol, sum(price) as sumPrice from " +
    	            SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	            SupportBeanString.class.getName() + ".win:length(100) as two " +
                    "where one.symbol = two.string " +
                    "group by symbol " +
                    "order by symbol";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        sendJoinEvents();
        sendEvent("CAT", 50);
        sendEvent("IBM", 49);
        sendEvent("CAT", 15);
        sendEvent("IBM", 100);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", 65d},
                                {"IBM", 149d},
                                });

        sendEvent("KGB", 75);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", 65d},
                                {"IBM", 149d},
                                {"KGB", 75d},
                                });
    }

    public void testIteratorGroupByEventPerRow()
	{
        String[] fields = new String[] {"symbol", "string", "sumPrice"};
        String statementString = "select symbol, string, sum(price) as sumPrice from " +
    	            SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	            SupportBeanString.class.getName() + ".win:length(100) as two " +
                    "where one.symbol = two.string " +
                    "group by symbol " +
                    "order by symbol";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        sendJoinEvents();
        sendEvent("CAT", 50);
        sendEvent("IBM", 49);
        sendEvent("CAT", 15);
        sendEvent("IBM", 100);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", "CAT", 65d},
                                {"CAT", "CAT", 65d},
                                {"IBM", "IBM", 149d},
                                {"IBM", "IBM", 149d},
                                });

        sendEvent("KGB", 75);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", "CAT", 65d},
                                {"CAT", "CAT", 65d},
                                {"IBM", "IBM", 149d},
                                {"IBM", "IBM", 149d},
                                {"KGB", "KGB", 75d},
                                });
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
    		assertEquals(values.get(i), events[i].get(valueName));
    	}
    }
    
    private void clearValues()
    {
    	prices.clear();
    	volumes.clear();
    	symbols.clear();
    }
    
    
	private void sendJoinEvents()
	{
		epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));
	}
    
    private void orderValuesBySumPriceAdditionalEvent() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "DOG");
    	symbols.add(2, "CAT");
    	symbols.add(3, "CMU");
    	symbols.add(4, "CMU");
    	symbols.add(5, "IBM");
    	symbols.add(6, "IBM");
    	prices.add(0, 1d);
    	prices.add(1, 1d);
    	prices.add(2, 11d);
    	prices.add(3, 13d);
    	prices.add(4, 13d);
    	prices.add(5, 14d);
    	prices.add(6, 14d);
    	volumes.add(0,0L);
    	volumes.add(1,0L);
    	volumes.add(2,0L);
    	volumes.add(3,0L);
    	volumes.add(4,0L);
    	volumes.add(5,0L);
    	volumes.add(6,0L);
    }
    
    private void orderValuesBySumPriceAdditionalEventGroup() {
    	symbols.add(0, "DOG");
    	symbols.add(1, "DOG");
    	symbols.add(2, "CMU");
    	symbols.add(3, "CMU");
    	symbols.add(4, "IBM");
    	symbols.add(5, "IBM");
    	prices.add(0, 1d);
    	prices.add(1, 1d);
    	prices.add(2, 13d);
    	prices.add(3, 13d);
    	prices.add(4, 14d);
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
	
	public void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
	    epService.initialize();
	    symbols = new LinkedList<String>();
	    prices = new LinkedList<Double>();
	    volumes = new LinkedList<Long>();
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
