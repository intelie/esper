/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import net.esper.core.EPServiceProviderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for instances of {@link EPServiceProvider}.
 */
public final class EPServiceProviderManager
{
    @SuppressWarnings({"CollectionWithoutInitialCapacity"})
    private static Map<String, EPServiceProviderImpl> runtimes = Collections.synchronizedMap(new HashMap<String, EPServiceProviderImpl>());

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
        if (runtimes.containsKey(uri))
        {
            EPServiceProviderImpl provider = runtimes.get(uri);
            provider.setConfiguration(configuration);
            return provider;
        }

        // New runtime
        EPServiceProviderImpl runtime = new EPServiceProviderImpl(configuration, uri);
        runtimes.put(uri, runtime);

        return runtime;
    }
}
