package com.espertech.esperio.opentick;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.plugin.PluginLoader;
import com.espertech.esperio.AdapterState;
import com.espertech.esperio.SpringContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class OpentickPluginLoader implements PluginLoader
{
    private static final Log log = LogFactory.getLog(OpentickPluginLoader.class);

    /**
     * Use to configure a classpath context.
     */
    public final static String CLASSPATH_CONTEXT = "classpath-app-context";

    /**
     * Use to configure a file context.
     */
    public final static String FILE_APP_CONTEXT = "file-app-context";

    private OpentickInputAdapter adapter;

    public void postInitialize()
    {
        // no action required
    }

    public void destroy()
    {
        log.info("Destroying adapter");

        if (adapter == null)
        {
            return;
        }
        if (adapter.getState() == AdapterState.STARTED)
        {
            adapter.stop();
        }
        if ((adapter.getState() == AdapterState.OPENED) || (adapter.getState() == AdapterState.PAUSED))
        {
            adapter.destroy();
        }
        adapter = null;
    }

    public void init(String name, Properties properties, EPServiceProviderSPI epService)
    {
        log.info("Initializing adapter named " + name + " configured as " + properties);
        
        if (adapter != null)
        {
            destroy();
        }

        // Load configuration
        ConfigurationOpentick configurationOpenTick = new ConfigurationOpentick();
        String resource = properties.getProperty(CLASSPATH_CONTEXT);
        if (resource != null)
        {
            InputStream stream = getResourceAsStream(resource);
            ConfigurationParser.doConfigure(configurationOpenTick, stream, resource);
        }
        else
        {
            resource = properties.getProperty(SpringContext.FILE_APP_CONTEXT);
            if (resource == null)
            {
                throw new IllegalArgumentException("Required property not found: " + CLASSPATH_CONTEXT + " or " + FILE_APP_CONTEXT);
            }
            try
            {
                ConfigurationParser.doConfigure(configurationOpenTick, new FileInputStream(resource), resource);
            }
            catch (FileNotFoundException fnfe) {
                throw new EPException( "could not find file: " + resource, fnfe );
            }
        }

        // Load adapters
        log.debug("Configuring from resource: " + resource);

        // Initialize adapter
        adapter = new OpentickInputAdapter(configurationOpenTick);
        adapter.start();
    }

    private static InputStream getResourceAsStream(String resource)
    {
        String stripped = resource.startsWith("/") ?
                resource.substring(1) : resource;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader!=null) {
            stream = classLoader.getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            stream = Configuration.class.getResourceAsStream( resource );
        }
        if ( stream == null ) {
            stream = Configuration.class.getClassLoader().getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            throw new EPException( resource + " not found" );
        }
        return stream;
    }
}
