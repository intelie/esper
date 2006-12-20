package net.esper.view.stream;

/**
 * Static factory for implementations of the StreamReuseService interface.
 */
public final class StreamReuseServiceProvider
{
    /**
     * Creates an implementation of the StreamReuseService interface.
     * @return implementation
     */
    public static StreamReuseService newService()
    {
        return new StreamReuseServiceImpl();
    }
}
