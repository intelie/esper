/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.espertech.esper.core.EPServiceProviderImpl;
import com.espertech.esper.core.EPServiceProviderSPI;

/**
 * Factory for instances of {@link EPServiceProvider}.
 */
public final class EPServiceProviderManager
{
    private static Map<String, EPServiceProviderImpl> runtimes = new ConcurrentHashMap<String, EPServiceProviderImpl>();

    /**
     * Returns the default EPServiceProvider.
     * @return default instance of the service.
     */
    public static EPServiceProvider getDefaultProvider()
    {
        return getProvider(EPServiceProviderSPI.DEFAULT_ENGINE_URI, new Configuration());
    }

    /**
     * Returns the default EPServiceProvider.
     * @param configuration is the configuration for the service
     * @return default instance of the service.
     * @throws ConfigurationException to indicate a configuration problem
     */
    public static EPServiceProvider getDefaultProvider(Configuration configuration) throws ConfigurationException
    {
        return getProvider(EPServiceProviderSPI.DEFAULT_ENGINE_URI, configuration);
    }

    /**
     * Returns an EPServiceProvider for a given provider URI.
     * @param providerURI - the provider URI
     * @return EPServiceProvider for the given provider URI.
     */
    public static EPServiceProvider getProvider(String providerURI)
    {
        return getProvider(providerURI, new Configuration());
    }

    /**
     * Returns an EPServiceProvider for a given provider URI.
     * @param providerURI - the provider URI. If null provided it assumes "default".
     * @param configuration is the configuration for the service
     * @return EPServiceProvider for the given provider URI.
     * @throws ConfigurationException to indicate a configuration problem
     */
    public static EPServiceProvider getProvider(String providerURI, Configuration configuration) throws ConfigurationException
    {
    	String providerURINonNull = (providerURI==null)?EPServiceProviderSPI.DEFAULT_ENGINE_URI:providerURI;
    	
        if (runtimes.containsKey(providerURINonNull))
        {
            EPServiceProviderImpl provider = runtimes.get(providerURINonNull);
            if (provider.isDestroyed())
            {
                provider = new EPServiceProviderImpl(configuration, providerURINonNull);
                runtimes.put(providerURINonNull, provider);
            }
            else
            {
                provider.setConfiguration(configuration);
            }
            return provider;
        }

        // New runtime
        EPServiceProviderImpl runtime = new EPServiceProviderImpl(configuration, providerURINonNull);
        runtimes.put(providerURINonNull, runtime);
        runtime.postInitialize();

        return runtime;
    }

    /**
     * Returns a list of known provider URIs.
     * <p>
     * Returns a null-value for the default provider, or if no URI has been supplied when obtaining a service instance.
     * <p>
     * Returns URIs for all engine instances including destroyed instances.
     * @return array of URI strings
     */
    public static String[] getProviderURIs()
    {
        Set<String> uriSet = runtimes.keySet();
        return uriSet.toArray(new String[uriSet.size()]);
    }
}
