package com.espertech.esperio.regression.adapter;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.event.EventBean;
import com.espertech.esperio.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;
import com.espertech.esperio.support.util.SupportUpdateListener;
import com.espertech.esperio.support.util.ExampleMarketDataBeanReadWrite;

/**
 * Cause all parent class unit tests to be run but sending beans instead of Maps
 * @author Jerry Shea
 */
public class TestCSVAdapterUseCasesBean extends TestCSVAdapterUseCases {

	public TestCSVAdapterUseCasesBean() {
		super(true);
	}
	
    public void testReadWritePropsBean()
    {
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("ReadWrite", ExampleMarketDataBeanReadWrite.class);

        epService = EPServiceProviderManager.getProvider("testExistingTypeNoOptions", configuration);
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from ReadWrite.win:length(100)");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        (new CSVInputAdapter(epService, new AdapterInputSource(CSV_FILENAME_ONELINE_TRADE), "ReadWrite")).start();

        assertEquals(1, listener.getNewDataList().size());
        EventBean eb = listener.getNewDataList().get(0)[0];
        assertTrue(ExampleMarketDataBeanReadWrite.class == eb.getUnderlying().getClass());
        assertEquals(55.5 * 1000, eb.get("value"));
    }    
}
