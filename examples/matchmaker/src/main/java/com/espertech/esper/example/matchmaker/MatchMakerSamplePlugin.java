package com.espertech.esper.example.matchmaker;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.plugin.PluginLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * PluginLoader for added this example as part of an Esper configuration file and therefore execute it during startup.
 */
public class MatchMakerSamplePlugin implements PluginLoader
{
    private static final Log log = LogFactory.getLog(MatchMakerSamplePlugin.class);

    private static final String ENGINE_URI = "engineURI";

    private String engineURI;
    private MatchMakerMain matchMakerMain;

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
        log.info("Starting MatchMaker-example for engine URI '" + engineURI + "'.");

        try {
            matchMakerMain = new MatchMakerMain();
            matchMakerMain.run(engineURI);
        }
        catch (Exception e) {
            log.error("Error starting MatchMaker example: " + e.getMessage());
        }

        log.info("MatchMaker-example started.");
    }

    public void destroy()
    {
        if (matchMakerMain != null) {
            EPServiceProviderManager.getProvider(engineURI).getEPAdministrator().destroyAllStatements();
        }
        log.info("MatchMaker-example stopped.");
    }
}
