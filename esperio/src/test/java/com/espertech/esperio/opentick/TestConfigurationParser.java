package com.espertech.esperio.opentick;

import junit.framework.TestCase;

import java.net.URL;
import java.util.List;

public class TestConfigurationParser extends TestCase
{
    private ConfigurationOpentick config;

    public void setUp()
    {
        config = new ConfigurationOpentick();
    }

    public void testConfigureFromStream() throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource("esperio-opentickadapter-config-sample.xml");
        if (url == null)
        {
            throw new RuntimeException("File not found at URL");
        }
        ConfigurationParser.doConfigure(config, url.openStream(), url.toString());
        assertFileConfig(config);
    }

    protected static void assertFileConfig(ConfigurationOpentick config) throws Exception
    {
        // assert connection
        assertEquals(2, config.getConnection().getHosts().size());
        assertEquals("feed1.opentick.com", config.getConnection().getHosts().get(0).getHostname());
        assertEquals(10010, config.getConnection().getHosts().get(0).getPort());
        assertEquals("feed2.opentick.com", config.getConnection().getHosts().get(1).getHostname());
        assertEquals(10010, config.getConnection().getHosts().get(1).getPort());
        assertEquals("login", config.getConnection().getLogin().getName());
        assertEquals("password", config.getConnection().getLogin().getPassword());
        assertEquals(20000, config.getConnection().getLogin().getTimeoutMSec());

        // assert streams
        assertEquals(4, config.getStreams().size());
        assertEquals("MyEngineURI", config.getStreams().get("OTQuote").getEngineURI());
        assertTrue(config.getStreams().get("OTQuote").isEnabled());
        assertEquals("OTQuote", config.getStreams().get("OTQuote").getTypeName());

        // assert masks
        assertEquals(1, config.getSymbolLists().size());
        List<ConfigurationOpentick.ExchangeAndSymbol> maskList = config.getSymbolLists().get("list1");
        assertEquals(2, maskList.size());
        assertEquals("NASDAQ", maskList.get(0).getExchange());
        assertEquals("MSFT", maskList.get(0).getSymbol());
        assertEquals("NASDAQ", maskList.get(1).getExchange());
        assertEquals("VDNX", maskList.get(1).getSymbol());

        // assert stream association to masks
        assertEquals(4, config.getStreamSymbolLists().size());
        assertEquals("OTQuote", config.getStreamSymbolLists().get(0).getStreamName());
        assertEquals("list1", config.getStreamSymbolLists().get(0).getSymbolListName());
    }
}
