package com.espertech.esperio.opentick;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.support.util.PrintUpdateListener;

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

        EPServiceProvider engine = EPServiceProviderManager.getProvider("MyEngineURI");
        EPStatement stmt = engine.getEPAdministrator().createEPL("select * from OTQuote");
        stmt.addListener(new PrintUpdateListener());

        try
        {
            Thread.sleep(30000);
        }
        catch (InterruptedException e)
        {
        }

        provider.destroy();
    }
}
