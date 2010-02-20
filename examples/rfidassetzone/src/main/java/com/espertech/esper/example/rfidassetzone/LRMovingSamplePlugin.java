package com.espertech.esper.example.rfidassetzone;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.plugin.PluginLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

public class LRMovingSamplePlugin implements PluginLoader
{
    private static final Log log = LogFactory.getLog(LRMovingSamplePlugin.class);

    private static final String ENGINE_URI = "engineURI";

    private String engineURI;
    private LRMovingSimMain main;

    public void init(String pluginName, Properties properties, EPServiceProviderSPI epServiceProviderSPI)
    {
        if (properties.getProperty(ENGINE_URI) != null)
        {
            engineURI = properties.getProperty(ENGINE_URI);
        }
        else
        {
            engineURI = epServiceProviderSPI.getURI();
        }
    }

    public void postInitialize()
    {
        log.info("Starting RFIDAssetZone-example for engine URI '" + engineURI + "'.");

        try {
            main = new LRMovingSimMain(1, 100, 1, false, engineURI);
        }
        catch (Exception e) {
            log.error("Error starting RFIDAssetZone example: " + e.getMessage());
        }

        log.info("RFIDAssetZone-example started.");
    }

    public void destroy()
    {
        if (main != null) {
            EPServiceProviderManager.getProvider(engineURI).getEPAdministrator().destroyAllStatements();
        }
        log.info("RFIDAssetZone-example stopped.");
    }
}
