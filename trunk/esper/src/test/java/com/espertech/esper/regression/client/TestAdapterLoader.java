package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.soda.StreamSelector;
import com.espertech.esper.support.adapter.SupportAdapterLoader;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.Properties;

import junit.framework.TestCase;

public class TestAdapterLoader extends TestCase
{
    public void testAdapterLoader()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.getEngineDefaults().getStreamSelection().setDefaultStreamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH);

        Properties props = new Properties();
        props.put("name", "val");
        config.addAdapterLoader("MyLoader", SupportAdapterLoader.class.getName(), props);

        EPServiceProvider service = EPServiceProviderManager.getProvider("TestAdapterLoader", config);
        assertEquals("MyLoader", SupportAdapterLoader.getNames().get(0));
        assertEquals("val", SupportAdapterLoader.getProps().get(0).get("name"));
    }

    
}
