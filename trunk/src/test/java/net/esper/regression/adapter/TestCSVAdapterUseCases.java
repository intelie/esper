package net.esper.regression.adapter;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.client.EPStatement;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.InputAdapter;
import net.esper.adapter.csv.CSVInputAdapterSpec;
import net.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayInputStream;

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
        Map<String, Class> type = new HashMap<String, Class>();
        type.put("symbol", String.class);
        type.put("price", double.class);
        type.put("volume", Integer.class);

        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("TypeA", type);

        epService = EPServiceProviderManager.getProvider("useCaseProvider", configuration);
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL("select symbol, price, volume from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPAdapters().getCSVAdapter().start(new AdapterInputSource(CSV_FILENAME), "TypeA");

        assertEquals(1, listener.getNewDataList().size());
    }

    /**
     * Play a CSV file that is from memory.
     */
    public void testPlayFromMemoryInputStream()
    {
        String myCSV = "symbol, price, volume" + NEW_LINE +
                       "IBM, 10.2, 10000";

        ByteArrayInputStream inStream = new ByteArrayInputStream(myCSV.getBytes());
        CSVInputAdapterSpec spec = new CSVInputAdapterSpec(new AdapterInputSource(inStream), "TypeC");

        epService = EPServiceProviderManager.getProvider("testPlayFromMemoryInputStream");
        epService.initialize();
        InputAdapter feed = epService.getEPAdapters().createAdapter(spec);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from TypeC.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        feed.start();
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

        InputAdapter feed = epService.getEPAdapters().createAdapter(spec);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select symbol, price, volume from TypeB.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        assertEquals(String.class, stmt.getEventType().getPropertyType("symbol"));
        assertEquals(String.class, stmt.getEventType().getPropertyType("price"));
        assertEquals(String.class, stmt.getEventType().getPropertyType("volume"));

        feed.start();
        assertEquals(1, listener.getNewDataList().size());
    }
}
