package com.espertech.esper.example.qos_sla;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.plugin.PluginLoader;
import com.espertech.esper.plugin.PluginLoaderInitContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * PluginLoader for added this example as part of an Esper configuration file and therefore execute it during startup.
 */
public class QualityOfServiceSamplePlugin implements PluginLoader
{
    private static final Log log = LogFactory.getLog(QualityOfServiceSamplePlugin.class);

    private static final String ENGINE_URI = "engineURI";

    private String engineURI;
    private QualityOfServiceMain main;

    public void init(PluginLoaderInitContext context)
    {
        if (context.getProperties().getProperty(ENGINE_URI) != null)
        {
            engineURI = context.getProperties().getProperty(ENGINE_URI);
        }
        else
        {
            engineURI = context.getEpServiceProvider().getURI();
        }
    }

    public void postInitialize()
    {
        log.info("Starting QualityOfService-example for engine URI '" + engineURI + "'.");

        try {
            main = new QualityOfServiceMain();
            main.run(engineURI);
        }
        catch (Exception e) {
            log.error("Error starting QualityOfService example: " + e.getMessage());
        }

        log.info("QualityOfService-example started.");
    }

    public void destroy()
    {
        if (main != null) {
            EPServiceProviderManager.getProvider(engineURI).getEPAdministrator().destroyAllStatements();
        }
        log.info("QualityOfService-example stopped.");
    }
}