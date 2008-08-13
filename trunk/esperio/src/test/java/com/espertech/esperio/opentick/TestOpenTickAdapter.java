package com.espertech.esperio.opentick;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import java.util.Properties;

import junit.framework.TestCase;

public class TestOpenTickAdapter extends TestCase
{
    public void testAdapter()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);   // automated tests run usually without timer

        Properties pluginProperties = new Properties();
        pluginProperties.put("classpath-app-context","esperio-opentickadapter-config-sample.xml");
        config.addPluginLoader("OpenTickPluginLoader", OpenTickPluginLoader.class.getName(), pluginProperties);
        
        EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider(config);
        provider.initialize();
    }
}
