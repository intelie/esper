package net.esper.client;


/**
 * This class provides access to the EPRuntime and EPAdministrator implementations.
 */
public interface EPServiceProvider
{
    /**
     * Returns a class instance of EPRuntime.
     * @return an instance of EPRuntime
     */
    public EPRuntime getEPRuntime();

    /**
     * Returns a class instance of EPAdministrator.
     * @return an instance of EPAdministrator
     */
    public EPAdministrator getEPAdministrator();

    /**
     * Frees any resources associated with this runtime instance.
     * Stops and destroys any event filters, patterns, expressions, views.
     */
    public void initialize();
}
