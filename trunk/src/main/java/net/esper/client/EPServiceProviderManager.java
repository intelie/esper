/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import net.esper.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for instances of {@link EPServiceProvider}.
 */
public final class EPServiceProviderManager
{
    private static Map<String, EPServiceProvider> runtimes = new HashMap<String, EPServiceProvider>();

    /**
     * Returns the default EPServiceProvider.
     * @return default instance of the service.
     */
    public static EPServiceProvider getDefaultProvider()
    {
        return getProvider(null, new Configuration());
    }

    /**
     * Returns the default EPServiceProvider.
     * @param configuration is the configuration for the service
     * @return default instance of the service.
     * @throws ConfigurationException to indicate a configuration problem
     */
    public static EPServiceProvider getDefaultProvider(Configuration configuration) throws ConfigurationException
    {
        return getProvider(null, configuration);
    }

    /**
     * Returns an EPServiceProvider for a given registration URI.
     * @param uri - the registration URI
     * @return EPServiceProvider for the given registration URI.
     */
    public static EPServiceProvider getProvider(String uri)
    {
        return getProvider(uri, new Configuration());
    }

    /**
     * Returns an EPServiceProvider for a given registration URI.
     * @param uri - the registration URI
     * @param configuration is the configuration for the service
     * @return EPServiceProvider for the given registration URI.
     * @throws ConfigurationException to indicate a configuration problem
     */
    public static EPServiceProvider getProvider(String uri, Configuration configuration) throws ConfigurationException
    {
        EPServiceProvider runtime;

        if (runtimes.containsKey(uri))
        {
           runtime = runtimes.get(uri);
           if (runtime instanceof EPServiceProviderImpl)
           {
            EPServiceProviderImpl spi = (EPServiceProviderImpl) runtime;
            spi.setEPServiceProviderAdapters(configuration);
           }
           return runtime;
        }

        // New runtime
        runtime = new EPServiceProviderImpl(configuration);
        runtimes.put(uri, runtime);

        return runtime;
    }
}
