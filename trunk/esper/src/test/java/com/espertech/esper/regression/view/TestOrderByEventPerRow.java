package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestOrderByEventPerRow extends TestCase
{
	private static final Log log = LogFactory.getLog(TestOrderByEventPerRow.class);
	private EPServiceProvider epService;
    private List<String> symbols;
    private List<Double> prices;
    private List<Long> volumes;
	private SupportUpdateListener testListener;

    public void testAliasesAggregationCompile() throws Exception
    {
        String statementString = "select symbol, volume, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price)";

        EPStatementObjectModel model = epService.getEPAdministrator().compileEQL(statementString);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(statementString, model.toEQL());

        createAndSendAggregate(model);
        orderValuesBySumPriceEvent();
        assertValues(prices, "sum(price)");
        assertValues(symbols, "symbol");
        assertValues(volumes, "volume");
        assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
        clearValues();
    }

    public void testAliasesAggregationOM() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("symbol", "volume").add(Expressions.sum("price")));
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarketDataBean.class.getName()).addView(View.create("win", "length", 20))));
        model.setGroupByClause(GroupByClause.create("symbol"));
        model.setOutputLimitClause(OutputLimitClause.create(6, OutputLimitUnit.EVENTS));
        model.setOrderByClause(OrderByClause.create(Expressions.sum("price")));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String statementString = "select symbol, volume, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price)";

        assertEquals(statementString, model.toEQL());

        createAndSendAggregate(model);
        orderValuesBySumPriceEvent();
        assertValues(prices, "sum(price)");
        assertValues(symbols, "symbol");
        assertValues(volumes, "volume");
        assertOnlyProperties(Arrays.asList(new String[] {"symbol", "sum(price)", "volume"}));
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

	private void sendAdditionalAggregate() {
    	sendEvent("IBM", 3);
    	sendEvent("IBM", 4);
    	sendEvent("CMU", 5);
    	sendEvent("CMU", 5);
    	sendEvent("DOG", 0);
    	sendEvent("DOG", 1);
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

    private void createAndSendAggregate(EPStatementObjectModel model) {
        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().create(model);
        statement.addListener(testListener);
        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);
    }    
}
