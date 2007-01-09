package net.esper.view.stream;

/**
 * Static factory for implementations of the StreamFactoryService interface.
 */
public final class StreamFactoryServiceProvider
{
    /**
     * Creates an implementation of the StreamFactoryService interface.
     * @return implementation
     */
    public static StreamFactoryService newService()
    {
        return new StreamFactorySvcImpl();
    }
}
