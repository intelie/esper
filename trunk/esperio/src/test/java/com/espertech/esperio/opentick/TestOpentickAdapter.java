package com.espertech.esperio.opentick;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.support.util.PrintUpdateListener;

import java.util.Properties;
import java.net.URL;

import junit.framework.TestCase;

public class TestOpentickAdapter extends TestCase
{
    public void testPlugInLoader()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);   // automated tests run usually without timer

        Properties pluginProperties = new Properties();
        pluginProperties.put("classpath-app-context","esperio-opentickadapter-config.xml");
        config.addPluginLoader("OpentickPluginLoader", OpentickPluginLoader.class.getName(), pluginProperties);

        /**
        EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider(config);
        provider.initialize();

        EPServiceProvider engine = EPServiceProviderManager.getProvider("MyEngineURI");
        EPStatement stmt = engine.getEPAdministrator().createEPL("select * from OTQuote");
        stmt.addListener(new PrintUpdateListener());

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
        }

        provider.destroy();
        */
    }

    public void testAdapterStart()
    {
        Configuration config = new Configuration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);   // automated tests run usually without timer

         /*
        EPServiceProvider provider = EPServiceProviderManager.getDefaultProvider(config);
        provider.initialize();

        ConfigurationOpentick configOT = new ConfigurationOpentick();
        URL url = Thread.currentThread().getContextClassLoader().getResource("esperio-opentickadapter-config-sample.xml");
        configOT.configure(url);

        OpentickInputAdapter adapter = new OpentickInputAdapter(configOT);
        adapter.start();

        EPServiceProvider engine = EPServiceProviderManager.getProvider("MyEngineURI");
        EPStatement stmt = engine.getEPAdministrator().createEPL("select * from OTQuote");
        stmt.addListener(new PrintUpdateListener());

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
        }
         provider.destroy();
         */

    }
}
