package net.esper.core;

public interface EPServicesContextFactory
{
    public EPServicesContext createServicesContext(String engineURI, ConfigurationSnapshot configurationSnapshot);    
}
