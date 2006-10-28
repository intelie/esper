package net.esper.regression.adapter;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.client.EPStatement;
import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.Feed;
import net.esper.adapter.csv.CSVFeedSpec;
import net.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;
import java.io.StringReader;

public class TestCSVAdapterUseCases extends TestCase
{
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
        type.put("price", Double.class);
        type.put("volume", Integer.class);

        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("TypeA", type);

        epService = EPServiceProviderManager.getProvider("useCaseProvider", configuration);
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from TypeA.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPAdapters().getCSVAdapter().startFeed(new AdapterInputSource(CSV_FILENAME), "TypeA");

        assertEquals(1, listener.getNewDataList().size());
    }

    /**
     * Play a CSV file that is from memory.
     */
    public void testPlayFromMemory()
    {
        String myCSV = "IBM, 10.2, 10000";
        StringReader reader = new StringReader(myCSV);

        // TODO: doesn't compile
        // Would be good if a Reader or InputStream of an existing source could be used
        // CSVFeedSpec spec = new CSVFeedSpec(new AdapterInputSource(reader), "TypeB");
    }

    /**
     * Play a CSV file using no existing (dynamic) event type (no timestamp)
     */
    public void testDynamicType()
    {
        CSVFeedSpec spec = new CSVFeedSpec(new AdapterInputSource(CSV_FILENAME), "TypeB");

        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        Feed feed = epService.getEPAdapters().createFeed(spec);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from TypeB.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        feed.start();
        assertEquals(1, listener.getNewDataList().size());
    }    
}
