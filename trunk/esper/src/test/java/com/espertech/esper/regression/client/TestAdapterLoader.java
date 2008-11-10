package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.plugin.SupportPluginLoader;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.Properties;

import junit.framework.TestCase;

public class TestAdapterLoader extends TestCase
{
    public void testAdapterLoader()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        Properties props = new Properties();
        props.put("name", "val");
        config.addPluginLoader("MyLoader", SupportPluginLoader.class.getName(), props);

        EPServiceProvider service = EPServiceProviderManager.getProvider("TestAdapterLoader", config);
        assertEquals("MyLoader", SupportPluginLoader.getNames().get(0));
        assertEquals("val", SupportPluginLoader.getProps().get(0).get("name"));
    }

    
}
