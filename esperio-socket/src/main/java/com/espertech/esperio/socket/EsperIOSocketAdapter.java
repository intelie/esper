/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.socket;

import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esperio.socket.config.ConfigurationSocketAdapter;
import com.espertech.esperio.socket.config.SocketConfig;
import com.espertech.esperio.socket.core.EsperSocketService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EsperIOSocketAdapter
{
    private static Log log = LogFactory.getLog(EsperIOSocketAdapter.class);

    private final ConfigurationSocketAdapter config;
    private final String engineURI;

    private final Map<String, EsperSocketService> sockets = new HashMap<String, EsperSocketService>();

    /**
     * Quickstart constructor.
     * @param config configuration
     */
    public EsperIOSocketAdapter(ConfigurationSocketAdapter config, String engineURI)
    {
        this.config = config;
        this.engineURI = engineURI;
    }

    /**
     * Re-initialize.
     */
    public void initialize()
    {
    }

    /**
     * Start the DDS endpoint.
     */
    public synchronized void start()
    {
        if (log.isInfoEnabled())
        {
            log.info("Starting EsperIO Socket Adapter for engine URI '" + engineURI + "'");
        }

        EPServiceProviderSPI engineSPI = (EPServiceProviderSPI) EPServiceProviderManager.getProvider(engineURI);

        // Configure sockets (input adapter)
        Set<Integer> ports = new HashSet<Integer>();
        for (Map.Entry<String, SocketConfig> entry : config.getSockets().entrySet()) {
            if (sockets.containsKey(entry.getKey())) {
                throw new ConfigurationException("A socket by name '" + entry.getKey() + "' has already been configured.");
            }

            int port = entry.getValue().getPort();
            if (ports.contains(port)) {
                throw new ConfigurationException("A socket for port '" + port + "' has already been configured.");
            }
            ports.add(port);

            EsperSocketService socketService = new EsperSocketService(entry.getKey(), entry.getValue());
            sockets.put(entry.getKey(), socketService);
        }

        // Start sockets
        for (Map.Entry<String, EsperSocketService> entry : sockets.entrySet()) {
            try {
                entry.getValue().start(entry.getKey(), engineSPI);
            }
            catch (IOException e) {
                log.error("Error starting socket '" + entry.getKey() + "' :" + e.getMessage());
            }
        }

        if (log.isInfoEnabled())
        {
            log.info("Completed starting EsperIO Socket Adapter for engine URI '" + engineURI + "'.");
        }
    }

    /**
     * Destroy the adapter.
     */
    public synchronized void destroy()
    {
        if (log.isDebugEnabled())
        {
            log.debug("Destroying Esper Socket Adapter");
        }

        for (EsperSocketService service : sockets.values()) {
            try {
                service.destroy();
            } catch (Throwable t) {
                log.info("Error destroying service '" + service.getServiceName() + "' :" + t.getMessage());
            }
        }
        sockets.clear();
    }
}
