package net.esper.adapter;

import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;

/**
 * An Adapter takes some external data, converts it into events, and sends it
 * into the runtime engine.
 */
public interface AdapterSPI
{
    /**
     * An adapter takes an engine instance to process events.
     * @param epService is the service instance for the adapter.
     */
    public void setEPServiceProvider(EPServiceProvider epService);

    /**
     * Returns the engine instance.
     * @return engine
     */
    public EPServiceProvider getEPServiceProvider();
}
