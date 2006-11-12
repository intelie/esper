package net.esper.regression.adapter;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.client.EPStatement;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.InputAdapter;
import net.esper.adapter.csv.CSVInputAdapter;
import net.esper.adapter.csv.CSVInputAdapterSpec;
import net.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.StringReader;

public class TestCSVAdapterUseCases extends TestCase
{
    private static String NEW_LINE = System.getProperty("line.separator");
    private static String CSV_FILENAME = "regression/csvtest_tradedata.csv";

    private EPServiceProvider epService;

    /**
     * Play a CSV file using an existing event type definition (no timestamps).
     *
     * Should not require a timestamp column, should block thread until played in.
     */
    public void testExistingTypeNoOptions()
    {
        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", makeConfig("TypeA"));
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        (new CSVInputAdapter(epService, new AdapterInputSource(CSV_FILENAME), "TypeA")).start();

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
    public void testEngineThread() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", makeConfig("TypeA"));
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(CSV_FILENAME), "TypeA");
        spec.setEventsPerSec(1000);
//        spec.setLooping(true);
        spec.setUsingEngineThread(true);

        InputAdapter inputAdapter = new CSVInputAdapter(epService, spec);
        inputAdapter.start();
        Thread.sleep(1000);
//        inputAdapter.stop();

        assertEquals(1, listener.getNewDataList().size());
    }

    /**
     * Play a CSV file using the application thread
     */
    public void testAppThread() throws Exception
    {
        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", makeConfig("TypeA"));
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(CSV_FILENAME), "TypeA");
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
        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(CSV_FILENAME), "TypeB");

        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        InputAdapter feed = new CSVInputAdapter(epService, spec);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select symbol, price, volume from TypeB.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        assertEquals(String.class, stmt.getEventType().getPropertyType("symbol"));
        assertEquals(String.class, stmt.getEventType().getPropertyType("price"));
        assertEquals(String.class, stmt.getEventType().getPropertyType("volume"));

        feed.start();
        assertEquals(1, listener.getNewDataList().size());
    }

    private Configuration makeConfig(String typeName)
    {
        Map<String, Class> eventProperties = new HashMap<String, Class>();
        eventProperties.put("symbol", String.class);
        eventProperties.put("price", double.class);
        eventProperties.put("volume", Integer.class);

        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias(typeName, eventProperties);

        return configuration;
    }

    private void trySource(AdapterInputSource source)
    {
        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(source, "TypeC");

        epService = EPServiceProviderManager.getProvider("testPlayFromInputStream", makeConfig("TypeC"));
        epService.initialize();
        InputAdapter feed = new CSVInputAdapter(epService, spec);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from TypeC.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        feed.start();
        assertEquals(1, listener.getNewDataList().size());
    }
}
