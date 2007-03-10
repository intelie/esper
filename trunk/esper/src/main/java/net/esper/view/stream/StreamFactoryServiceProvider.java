package net.esper.view.stream;

import net.esper.event.EventAdapterService;

/**
 * Static factory for implementations of the StreamFactoryService interface.
 */
public final class StreamFactoryServiceProvider
{
    /**
     * Creates an implementation of the StreamFactoryService interface.
     * @return implementation
     */
    public static StreamFactoryService newService(EventAdapterService eventAdapterService)
    {
        return new StreamFactorySvcImpl(eventAdapterService);
    }
}
