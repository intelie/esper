package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.plugin.SupportPluginLoader;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.Properties;

import junit.framework.TestCase;

public class TestAdapterLoader extends TestCase
{
    public void setUp()
    {
        SupportPluginLoader.reset();        
    }

    public void testAdapterLoader() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();

        Properties props = new Properties();
        props.put("name", "val");
        config.addPluginLoader("MyLoader", SupportPluginLoader.class.getName(), props);

        EPServiceProvider service = EPServiceProviderManager.getProvider("TestAdapterLoader", config);
        assertEquals(1, SupportPluginLoader.getNames().size());
        assertEquals(1, SupportPluginLoader.getPostInitializes().size());
        assertEquals("MyLoader", SupportPluginLoader.getNames().get(0));
        assertEquals("val", SupportPluginLoader.getProps().get(0).get("name"));

        EPServiceProviderSPI spi = (EPServiceProviderSPI) service;
        Object loader = spi.getEngineEnvContext().getEnvironment().get("plugin-loader/MyLoader");
        assertTrue(loader instanceof SupportPluginLoader);
                
        SupportPluginLoader.getPostInitializes().clear();
        SupportPluginLoader.getNames().clear();
        service.initialize();
        assertEquals(1, SupportPluginLoader.getPostInitializes().size());
        assertEquals(1, SupportPluginLoader.getNames().size());
    }

    public void testDestroy() {
        Configuration cf = new Configuration();
        cf.addPluginLoader("AP", SupportPluginLoader.class.getName(), null);
        EPServiceProvider ep = EPServiceProviderManager.getDefaultProvider(cf);
        ep.destroy();
        assertEquals(1, SupportPluginLoader.getDestroys().size());
    }

    public void testDestroyObtainTwice() {
        Configuration cf = new Configuration();
        cf.addPluginLoader("AP", SupportPluginLoader.class.getName(), null);
        EPServiceProviderManager.getDefaultProvider(cf);
        EPServiceProvider ep = EPServiceProviderManager.getProvider(EPServiceProviderSPI.DEFAULT_ENGINE_URI);
        ep.destroy();
        assertEquals(1, SupportPluginLoader.getDestroys().size());
    }
}
