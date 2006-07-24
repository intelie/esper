package net.esper.dispatch;

/**
 * Provider of implementations for the dispatch service.
 */
public class DispatchServiceProvider
{
    /**
     * Returns new service.
     * @return new dispatch service implementation.
     */
    public static DispatchService newService()
    {
        return new DispatchServiceImpl();
    }
}
