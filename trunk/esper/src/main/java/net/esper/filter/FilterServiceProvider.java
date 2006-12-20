package net.esper.filter;

/**
 * Static factory for implementations of the {@link FilterService} interface.
 */
public final class FilterServiceProvider
{
    /**
     * Creates an implementation of the FilterEvaluationService interface.
     * @return implementation
     */
    public static FilterService newService()
    {
        return new FilterServiceImpl();
    }
}
