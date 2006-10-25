package net.esper.client;

import net.esper.core.EPServiceProviderImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for instances of {@link EPServiceProvider}.
 */
public final class EPServiceProviderManager
{
    private static Map<String, EPServiceProviderImpl> runtimes = new HashMap<String, EPServiceProviderImpl>();

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
            // Existing runtime, setting a new configuration that will be picked up with next initialize()
            EPServiceProviderImpl impl = runtimes.get(uri);
            impl.setConfiguration(configuration);
            return impl;
        }

        // New runtime
        EPServiceProviderImpl runtime = new EPServiceProviderImpl(uri, configuration);
        runtimes.put(uri, runtime);

        return runtime;
    }
}
