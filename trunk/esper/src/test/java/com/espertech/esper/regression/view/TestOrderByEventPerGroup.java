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

public class TestOrderByEventPerGroup extends TestCase {

	private static final Log log = LogFactory.getLog(TestOrderByEventPerGroup.class);
	private EPServiceProvider epService;
    private List<String> symbols;
    private List<Double> prices;
	private SupportUpdateListener testListener;

    public void testRowPerGroup()
	{
        String fields[] = "symbol,sum(price)".split(",");
		String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price)";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        statement.addListener(testListener);
        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields,
                new Object[][] {{"IBM", 3.0}, {"IBM", 7.0}, {"CMU", 1.0}, {"CMU", 3.0}, {"CAT", 5.0}, {"CAT", 11.0} });
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastOldData(), fields,
                new Object[][] {{"IBM", null}, {"IBM", 3.0}, {"CMU", null}, {"CMU", 1.0}, {"CAT", null}, {"CAT", 5.0} });
        testListener.reset();

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 5);
        sendEvent("CMU", 5);
        sendEvent("DOG", 0);
        sendEvent("DOG", 1);
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields,
                new Object[][] {{"IBM", 10.0}, {"IBM", 14.0}, {"CMU", 8.0}, {"CMU", 13.0}, {"DOG", 0.0}, {"DOG", 1.0} });
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastOldData(), fields,
                new Object[][] {{"IBM", 7.0}, {"IBM", 10.0}, {"CMU", 3.0}, {"CMU", 8.0}, {"DOG", null}, {"DOG", 0.0} });
    }

    public void testRowPerGroupHaving()
    {
		String statementString = "select symbol, sum(price) from " +
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
		String statementString = "select symbol, sum(price) as mySum from " +
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
