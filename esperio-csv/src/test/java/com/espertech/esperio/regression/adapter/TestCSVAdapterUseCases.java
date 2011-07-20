/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.regression.adapter;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.TimerControlEvent;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.adapter.InputAdapter;
import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.AdapterCoordinatorImpl;
import com.espertech.esperio.AdapterCoordinator;
import com.espertech.esperio.support.util.SupportUpdateListener;
import com.espertech.esperio.csv.CSVInputAdapter;
import com.espertech.esperio.csv.CSVInputAdapterSpec;
import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

public class TestCSVAdapterUseCases extends TestCase
{
    private static String NEW_LINE = System.getProperty("line.separator");
    protected static String CSV_FILENAME_ONELINE_TRADE = "regression/csvtest_tradedata.csv";
    private static String CSV_FILENAME_ONELINE_TRADE_MULTIPLE = "regression/csvtest_tradedata_multiple.csv";
    private static String CSV_FILENAME_TIMESTAMPED_PRICES = "regression/csvtest_timestamp_prices.csv";
    private static String CSV_FILENAME_TIMESTAMPED_TRADES = "regression/csvtest_timestamp_trades.csv";

    protected EPServiceProvider epService;
    private boolean useBean = false;

    public TestCSVAdapterUseCases() {
		this(false);
	}


    public TestCSVAdapterUseCases(boolean ub) {
		useBean = ub;
	}

	/**
     * Play a CSV file using an existing event type definition (no timestamps).
     *
     * Should not require a timestamp column, should block thread until played in.
     */
    public void testExistingTypeNoOptions()
    {
        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", makeConfig("TypeA", useBean));
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        (new CSVInputAdapter(epService, new AdapterInputSource(CSV_FILENAME_ONELINE_TRADE), "TypeA")).start();

        assertEquals(1, listener.getNewDataList().size());
    }

    /**
     * Play a CSV file that is from memory.
     */
    public void testPlayFromInputStream()
    {
        String myCSV = "symbol, price, volume" + NEW_LINE + "IBM, 10.2, 10000";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(myCSV.getBytes());
        trySource(new AdapterInputSource(inputStream));
    }

    /**
     * Play a CSV file that is from memory.
     */
    public void testPlayFromStringReader()
    {
        String myCSV = "symbol, price, volume" + NEW_LINE + "IBM, 10.2, 10000";
        StringReader reader = new StringReader(myCSV);
        trySource(new AdapterInputSource(reader));
    }

    /**
     * Play a CSV file using an engine thread
     */
    public void testEngineThread1000PerSec() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", makeConfig("TypeA"));
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(CSV_FILENAME_ONELINE_TRADE), "TypeA");
        spec.setEventsPerSec(1000);
        spec.setUsingEngineThread(true);

        InputAdapter inputAdapter = new CSVInputAdapter(epService, spec);
        inputAdapter.start();
        Thread.sleep(1000);

        assertEquals(1, listener.getNewDataList().size());
    }

    /**
     * Play a CSV file using an engine thread.
     */
    public void testEngineThread1PerSec() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", makeConfig("TypeA"));
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(CSV_FILENAME_ONELINE_TRADE_MULTIPLE), "TypeA");
        spec.setEventsPerSec(1);
        spec.setUsingEngineThread(true);

        InputAdapter inputAdapter = new CSVInputAdapter(epService, spec);
        inputAdapter.start();

        Thread.sleep(1500);
        assertEquals(1, listener.getNewDataList().size());
        listener.reset();
        Thread.sleep(300);
        assertEquals(0, listener.getNewDataList().size());

        Thread.sleep(2000);
        assertTrue(listener.getNewDataList().size() >= 2);
    }

    /**
     * Play a CSV file using the application thread
     */
    public void testAppThread() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", makeConfig("TypeA"));
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(CSV_FILENAME_ONELINE_TRADE), "TypeA");
        spec.setEventsPerSec(1000);

        InputAdapter inputAdapter = new CSVInputAdapter(epService, spec);
        inputAdapter.start();

        assertEquals(1, listener.getNewDataList().size());
    }

    /**
     * Play a CSV file using no existing (dynamic) event type (no timestamp)
     */
    public void testDynamicType()
    {
        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(CSV_FILENAME_ONELINE_TRADE), "TypeB");

        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        InputAdapter feed = new CSVInputAdapter(epService, spec);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select symbol, price, volume from TypeB.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        assertEquals(String.class, stmt.getEventType().getPropertyType("symbol"));
        assertEquals(String.class, stmt.getEventType().getPropertyType("price"));
        assertEquals(String.class, stmt.getEventType().getPropertyType("volume"));

        feed.start();
        assertEquals(1, listener.getNewDataList().size());
    }

    public void testCoordinated() throws Exception
    {
        Map<String, Object> priceProps = new HashMap<String, Object>();
        priceProps.put("timestamp", Long.class);
        priceProps.put("symbol", String.class);
        priceProps.put("price", Double.class);

        Map<String, Object> tradeProps = new HashMap<String, Object>();
        tradeProps.put("timestamp", Long.class);
        tradeProps.put("symbol", String.class);
        tradeProps.put("notional", Double.class);

        Configuration config = new Configuration();
        config.addEventType("TradeEvent", tradeProps);
        config.addEventType("PriceEvent", priceProps);

        epService = EPServiceProviderManager.getProvider("testCoordinated", config);
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        AdapterInputSource sourcePrices = new AdapterInputSource(CSV_FILENAME_TIMESTAMPED_PRICES);
        CSVInputAdapterSpec inputPricesSpec = new CSVInputAdapterSpec(sourcePrices, "PriceEvent");
        inputPricesSpec.setTimestampColumn("timestamp");
        inputPricesSpec.setPropertyTypes(priceProps);
        CSVInputAdapter inputPrices = new CSVInputAdapter(inputPricesSpec);

        AdapterInputSource sourceTrades = new AdapterInputSource(CSV_FILENAME_TIMESTAMPED_TRADES);
        CSVInputAdapterSpec inputTradesSpec = new CSVInputAdapterSpec(sourceTrades, "TradeEvent");
        inputTradesSpec.setTimestampColumn("timestamp");
        inputTradesSpec.setPropertyTypes(tradeProps);
        CSVInputAdapter inputTrades = new CSVInputAdapter(inputTradesSpec);

        EPStatement stmtPrices = epService.getEPAdministrator().createEPL("select symbol, price from PriceEvent.win:length(100)");
        SupportUpdateListener listenerPrice = new SupportUpdateListener();
        stmtPrices.addListener(listenerPrice);
        EPStatement stmtTrade = epService.getEPAdministrator().createEPL("select symbol, notional from TradeEvent.win:length(100)");
        SupportUpdateListener listenerTrade = new SupportUpdateListener();
        stmtTrade.addListener(listenerTrade);

        AdapterCoordinator coordinator = new AdapterCoordinatorImpl(epService, true);
        coordinator.coordinate(inputPrices);
        coordinator.coordinate(inputTrades);
        coordinator.start();

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(400));
        assertFalse(listenerTrade.isInvoked());
        assertFalse(listenerPrice.isInvoked());

        // invoke read of events at 500 (see CSV)
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(1000));
        assertEquals(1, listenerTrade.getNewDataList().size());
        assertEquals(1, listenerPrice.getNewDataList().size());
        listenerTrade.reset(); listenerPrice.reset();

        // invoke read of price events at 1500 (see CSV)
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(2000));
        assertEquals(0, listenerTrade.getNewDataList().size());
        assertEquals(1, listenerPrice.getNewDataList().size());
        listenerTrade.reset(); listenerPrice.reset();

        // invoke read of trade events at 2500 (see CSV)
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(3000));
        assertEquals(1, listenerTrade.getNewDataList().size());
        assertEquals(0, listenerPrice.getNewDataList().size());
        listenerTrade.reset(); listenerPrice.reset();
    }

    private Configuration makeConfig(String typeName) {
    	return makeConfig(typeName, false);
    }
    private Configuration makeConfig(String typeName, boolean useBean)
    {
        Configuration configuration = new Configuration();
    	if (useBean) {
            configuration.addEventType(typeName, ExampleMarketDataBean.class);
    	}
    	else {
            Map<String, Object> eventProperties = new HashMap<String, Object>();
            eventProperties.put("symbol", String.class);
            eventProperties.put("price", double.class);
            eventProperties.put("volume", Integer.class);
            configuration.addEventType(typeName, eventProperties);
    	}

        return configuration;
    }

    private void trySource(AdapterInputSource source)
    {
        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(source, "TypeC");

        epService = EPServiceProviderManager.getProvider("testPlayFromInputStream", makeConfig("TypeC"));
        epService.initialize();
        InputAdapter feed = new CSVInputAdapter(epService, spec);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from TypeC.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        feed.start();
        assertEquals(1, listener.getNewDataList().size());
    }

    /**
     * Bean with same properties as map type used in this test
     */
    public static class ExampleMarketDataBean {
    	private String symbol;
    	private double price;
    	private Integer volume;

		public String getSymbol() {
			return symbol;
		}
		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public Integer getVolume() {
			return volume;
		}
		public void setVolume(Integer volume) {
			this.volume = volume;
		}
    }
}
