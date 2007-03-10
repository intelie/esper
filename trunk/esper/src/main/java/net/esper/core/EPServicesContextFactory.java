package net.esper.core;

/**
 * Interface for a factory class to provide services in a services context for an engine instance.
 */
public interface EPServicesContextFactory
{
    /**
     * Factory method for a new set of engine services.
     * @param engineURI is the URI for the engine or null if this is the default engine
     * @param configurationSnapshot is a snapshot of configs at the time of engine creation
     * @return services context
     */
    public EPServicesContext createServicesContext(String engineURI, ConfigurationSnapshot configurationSnapshot);    
}
