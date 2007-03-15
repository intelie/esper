package net.esper.regression.client;

import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.adapter.SupportAdapterLoader;

import java.util.Properties;

import junit.framework.TestCase;

public class TestAdapterLoader extends TestCase
{
    public void testAdapterLoader()
    {
        Configuration config = new Configuration();
        Properties props = new Properties();
        props.put("name", "val");
        config.addAdapterLoader("MyLoader", SupportAdapterLoader.class.getName(), props);

        EPServiceProvider service = EPServiceProviderManager.getDefaultProvider(config);
        assertEquals("MyLoader", SupportAdapterLoader.getNames().get(0));
        assertEquals("val", SupportAdapterLoader.getProps().get(0).get("name"));
    }
}
