package net.esper.view.stream;

/**
 * Static factory for implementations of the StreamFactoryService interface.
 */
public final class StreamFactoryServiceProvider
{
    /**
     * Creates an implementation of the StreamFactoryService interface.
     * @param isMultithreading is a flag indicating whether or not to share streams or not
     * @return implementation depending on needs for multithread-safety
     */
    public static StreamFactoryService newService(boolean isMultithreading)
    {
        if (isMultithreading)
        {
            return new StreamFactorySvcCreate();
        }
        return new StreamFactorySvcReuse();
    }
}
