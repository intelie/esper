package com.espertech.esper.example.transaction;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.example.transaction.sim.TxnGenMain;
import com.espertech.esper.plugin.PluginLoader;
import com.espertech.esper.plugin.PluginLoaderInitContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

public class TransactionSamplePlugin implements PluginLoader
{
    private static final Log log = LogFactory.getLog(TransactionSamplePlugin.class);

    private static final String ENGINE_URI = "engineURI";

    private String engineURI;
    private TxnGenMain  main;

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
        log.info("Starting Transaction-example for engine URI '" + engineURI + "'.");

        try {
            main = new TxnGenMain(20, 200, engineURI);
            main.run();
        }
        catch (Exception e) {
            log.error("Error starting Transaction example: " + e.getMessage());
        }

        log.info("Transaction-example started.");
    }

    public void destroy()
    {
        if (main != null) {
            EPServiceProviderManager.getProvider(engineURI).getEPAdministrator().destroyAllStatements();
        }
        log.info("Transaction-example stopped.");
    }
}
